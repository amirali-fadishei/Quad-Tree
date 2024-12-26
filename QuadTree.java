public class QuadTree {
    public QuadTreeNode root;
    public int imageSize;
    public boolean isGrayscale;

    public QuadTree(int[] pixels, int imageSize, boolean isGrayscale) {
        this.imageSize = imageSize;
        this.isGrayscale = isGrayscale;
        this.root = buildQuadTree(pixels, 0, 0, imageSize);
    }

    private QuadTreeNode buildQuadTree(int[] pixels, int x, int y, int size) {
        if (size == 1) {
            return new QuadTreeNode(pixels[y * imageSize + x]);
        }

        int newSize = size / 2;
        if (newSize == 0) {
            return null;
        }
        QuadTreeNode topLeft = buildQuadTree(pixels, x, y, newSize);
        QuadTreeNode topRight = buildQuadTree(pixels, x + newSize, y, newSize);
        QuadTreeNode bottomLeft = buildQuadTree(pixels, x, y + newSize, newSize);
        QuadTreeNode bottomRight = buildQuadTree(pixels, x + newSize, y + newSize, newSize);

        QuadTreeNode node = new QuadTreeNode(-1);
        node.setTopLeft(topLeft);
        node.setTopRight(topRight);
        node.setBottomLeft(bottomLeft);
        node.setBottomRight(bottomRight);
        return node;
    }

    public int getImageSize() {
        return imageSize;
    }

    public void fillImage(int[] image) {
        fillImage(root, image, 0, 0, imageSize);
    }

    private void fillImage(QuadTreeNode node, int[] image, int x, int y, int size) {
        if (node == null) {
            return;
        }
        if (size == 1) {
            image[y * imageSize + x] = node.getPixelValue();
            return;
        }

        int newSize = size / 2;
        fillImage(node.getTopLeft(), image, x, y, newSize);
        fillImage(node.getTopRight(), image, x + newSize, y, newSize);
        fillImage(node.getBottomLeft(), image, x, y + newSize, newSize);
        fillImage(node.getBottomRight(), image, x + newSize, y + newSize, newSize);
    }

    public int search(int x, int y) {
        return search(root, x, y, 0, 0, imageSize);
    }

    private int search(QuadTreeNode node, int x, int y, int nodeX, int nodeY, int size) {
        if (size == 1 || node.isLeaf()) {
            return node.getPixelValue();
        }

        int newSize = size / 2;
        if (x < nodeX + newSize && y < nodeY + newSize) {
            return search(node.getTopLeft(), x, y, nodeX, nodeY, newSize);
        } else if (x >= nodeX + newSize && y < nodeY + newSize) {
            return search(node.getTopRight(), x, y, nodeX + newSize, nodeY, newSize);
        } else if (x < nodeX + newSize && y >= nodeY + newSize) {
            return search(node.getBottomLeft(), x, y, nodeX, nodeY + newSize, newSize);
        } else {
            return search(node.getBottomRight(), x, y, nodeX + newSize, nodeY + newSize, newSize);
        }
    }

    public int getDepth() {
        return getDepth(root);
    }

    private int getDepth(QuadTreeNode node) {
        if (node == null || node.isLeaf()) {
            return 0;
        }
        int topLeftDepth = getDepth(node.getTopLeft());
        int topRightDepth = getDepth(node.getTopRight());
        int bottomLeftDepth = getDepth(node.getBottomLeft());
        int bottomRightDepth = getDepth(node.getBottomRight());
        return 1 + Math.max(Math.max(topLeftDepth, topRightDepth), Math.max(bottomLeftDepth, bottomRightDepth));
    }

    public int pixelDepth(int x, int y) {
        return pixelDepth(root, x, y, 0, 0, imageSize, 0);
    }

    private int pixelDepth(QuadTreeNode node, int x, int y, int nodeX, int nodeY, int size, int depth) {
        if (size == 1 || node.isLeaf()) {
            return depth;
        }

        int newSize = size / 2;
        if (x < nodeX + newSize && y < nodeY + newSize) {
            return pixelDepth(node.getTopLeft(), x, y, nodeX, nodeY, newSize, depth + 1);
        } else if (x >= nodeX + newSize && y < nodeY + newSize) {
            return pixelDepth(node.getTopRight(), x, y, nodeX + newSize, nodeY, newSize, depth + 1);
        } else if (x < nodeX + newSize && y >= nodeY + newSize) {
            return pixelDepth(node.getBottomLeft(), x, y, nodeX, nodeY + newSize, newSize, depth + 1);
        } else {
            return pixelDepth(node.getBottomRight(), x, y, nodeX + newSize, nodeY + newSize, newSize, depth + 1);
        }
    }

    public QuadTree searchSubspacesWithRange(int x1, int y1, int x2, int y2) {
        int[] image = new int[imageSize * imageSize];
        for (int i = 0; i < image.length; i++) {
            image[i] = 0xFFFFFF;
        }
        searchSubspacesWithRange(root, image, 0, 0, imageSize, x1, y1, x2, y2);
        return new QuadTree(image, imageSize, isGrayscale);
    }

    private void searchSubspacesWithRange(QuadTreeNode node, int[] image, int x, int y, int size, int x1, int y1,
            int x2, int y2) {
        if (node == null) {
            return;
        }
        if (size == 1 || node.isLeaf()) {
            if (x >= x1 && x <= x2 && y >= y1 && y <= y2) {
                image[y * imageSize + x] = node.getPixelValue();
            }
            return;
        }

        int newSize = size / 2;
        searchSubspacesWithRange(node.getTopLeft(), image, x, y, newSize, x1, y1, x2, y2);
        searchSubspacesWithRange(node.getTopRight(), image, x + newSize, y, newSize, x1, y1, x2, y2);
        searchSubspacesWithRange(node.getBottomLeft(), image, x, y + newSize, newSize, x1, y1, x2, y2);
        searchSubspacesWithRange(node.getBottomRight(), image, x + newSize, y + newSize, newSize, x1, y1, x2, y2);
    }

    public QuadTree mask(int x1, int y1, int x2, int y2) {
        int[] image = new int[imageSize * imageSize];
        fillImage(image);
        mask(root, image, 0, 0, imageSize, x1, y1, x2, y2);
        return new QuadTree(image, imageSize, isGrayscale);
    }

    private void mask(QuadTreeNode node, int[] image, int x, int y, int size, int x1, int y1, int x2, int y2) {
        if (node == null) {
            return;
        }
        if (size == 1 || node.isLeaf()) {
            if (x >= x1 && x <= x2 && y >= y1 && y <= y2) {
                image[y * imageSize + x] = 0xFFFFFF;
            }
            return;
        }

        int newSize = size / 2;
        mask(node.getTopLeft(), image, x, y, newSize, x1, y1, x2, y2);
        mask(node.getTopRight(), image, x + newSize, y, newSize, x1, y1, x2, y2);
        mask(node.getBottomLeft(), image, x, y + newSize, newSize, x1, y1, x2, y2);
        mask(node.getBottomRight(), image, x + newSize, y + newSize, newSize, x1, y1, x2, y2);
    }

    public QuadTree compress(int newSize) {
        int[] compressedImage = new int[newSize * newSize];
        int factor = imageSize / newSize;
        for (int y = 0; y < newSize; y++) {
            for (int x = 0; x < newSize; x++) {
                compressedImage[y * newSize + x] = averageColor(x * factor, y * factor, factor);
            }
        }
        return new QuadTree(compressedImage, newSize, isGrayscale);
    }

    private int averageColor(int startX, int startY, int size) {
        int totalRed = 0, totalGreen = 0, totalBlue = 0;
        int totalValue = 0;
        int pixelCount = size * size;

        for (int y = startY; y < startY + size; y++) {
            for (int x = startX; x < startX + size; x++) {
                int pixel = search(x, y);
                if (isGrayscale) {
                    totalValue += pixel & 0xFF;
                } else {
                    totalRed += (pixel >> 16) & 0xFF;
                    totalGreen += (pixel >> 8) & 0xFF;
                    totalBlue += pixel & 0xFF;
                }
            }
        }

        if (isGrayscale) {
            int avgGray = totalValue / pixelCount;

            return (avgGray << 16) | (avgGray << 8) | avgGray;
        } else {
            int avgRed = totalRed / pixelCount;
            int avgGreen = totalGreen / pixelCount;
            int avgBlue = totalBlue / pixelCount;

            return (avgRed << 16) | (avgGreen << 8) | avgBlue;
        }

    }
}

public class QuadTree {
    public QuadTreeNode root;
    private int aspect;
    private boolean isGray;

    public QuadTree(int[] pixels, int aspect, boolean isGray) {
        this.setIsGray(isGray);
        this.setAspect(aspect);
        this.root = buildTree(pixels, 0, 0, aspect);
    }

    public void setAspect(int aspect) {
        this.aspect = aspect;
    }

    public int getAspect() {
        return this.aspect;
    }

    public void setIsGray(boolean isGray) {
        this.isGray = isGray;
    }

    private QuadTreeNode buildTree(int[] pixels, int x, int y, int size) {
        if (size == 1) {
            return new QuadTreeNode(pixels[y * aspect + x]);
        }

        int newSize = size / 2;

        QuadTreeNode topLeft = buildTree(pixels, x, y, newSize);
        QuadTreeNode topRight = buildTree(pixels, x + newSize, y, newSize);
        QuadTreeNode bottomLeft = buildTree(pixels, x, y + newSize, newSize);
        QuadTreeNode bottomRight = buildTree(pixels, x + newSize, y + newSize, newSize);

        QuadTreeNode newNode = new QuadTreeNode(-1);

        newNode.setTopLeft(topLeft);
        newNode.setTopRight(topRight);
        newNode.setBottomLeft(bottomLeft);
        newNode.setBottomRight(bottomRight);

        return newNode;
    }

    public void fillImage(int[] image) {
        fillImage(this.root, image, 0, 0, aspect);
    }

    private void fillImage(QuadTreeNode node, int[] image, int x, int y, int size) {
        if (node == null) {
            return;
        }
        if (size == 1) {
            image[y * aspect + x] = node.getPixel();
            return;
        }

        int newSize = size / 2;

        fillImage(node.getTopLeft(), image, x, y, newSize);
        fillImage(node.getTopRight(), image, x + newSize, y, newSize);
        fillImage(node.getBottomLeft(), image, x, y + newSize, newSize);
        fillImage(node.getBottomRight(), image, x + newSize, y + newSize, newSize);
    }

    public int search(int x, int y) {
        return search(this.root, x, y, 0, 0, aspect);
    }

    private int search(QuadTreeNode node, int x, int y, int nodeX, int nodeY, int size) {
        if (size == 1 || node.isLeaf()) {
            return node.getPixel();
        }

        int newSize = size / 2;
        int midX = nodeX + newSize;
        int midY = nodeY + newSize;

        if (x < midX) {
            if (y < midY) {
                return search(node.getTopLeft(), x, y, nodeX, nodeY, newSize);
            } else {
                return search(node.getBottomLeft(), x, y, nodeX, nodeY + newSize, newSize);
            }
        } else {
            if (y < midY) {
                return search(node.getTopRight(), x, y, nodeX + newSize, nodeY, newSize);
            } else {
                return search(node.getBottomRight(), x, y, nodeX + newSize, nodeY + newSize, newSize);
            }
        }
    }

    public int treeDepth() {
        return treeDepth(this.root);
    }

    private int treeDepth(QuadTreeNode node) {
        if (node == null || node.isLeaf()) {
            return 0;
        }
        int topLeftDepth = treeDepth(node.getTopLeft());
        int topRightDepth = treeDepth(node.getTopRight());
        int bottomLeftDepth = treeDepth(node.getBottomLeft());
        int bottomRightDepth = treeDepth(node.getBottomRight());

        return 1
                + Math.max(Math.max(topLeftDepth, topRightDepth), Math.max(bottomLeftDepth, bottomRightDepth));
    }

    public int pixelDepth(int x, int y) {
        return pixelDepth(root, x, y, 0, 0, aspect, 0);
    }

    private int pixelDepth(QuadTreeNode node, int x, int y, int nodeX, int nodeY, int size, int depth) {
        if (size == 1 || node.isLeaf()) {
            return depth;
        }

        int newSize = size / 2;
        int midX = nodeX + newSize;
        int midY = nodeY + newSize;

        if (x < midX) {
            if (y < midY) {
                return pixelDepth(node.getTopLeft(), x, y, nodeX, nodeY, newSize, depth + 1);
            } else {
                return pixelDepth(node.getBottomLeft(), x, y, nodeX, nodeY + newSize, newSize, depth + 1);
            }
        } else {
            if (y < midY) {
                return pixelDepth(node.getTopRight(), x, y, nodeX + newSize, nodeY, newSize, depth + 1);
            } else {
                return pixelDepth(node.getBottomRight(), x, y, nodeX + newSize, nodeY + newSize, newSize, depth + 1);
            }
        }
    }

    public QuadTree searchSubspacesWithRange(int x1, int y1, int x2, int y2) {
        int[] image = new int[aspect * aspect];

        for (int counter = 0; counter < image.length; counter++) {
            image[counter] = 0xFFFFFF;
        }
        searchSubspacesWithRange(root, image, 0, 0, aspect, x1, y1, x2, y2);
        return new QuadTree(image, aspect, isGray);
    }

    private void searchSubspacesWithRange(QuadTreeNode node, int[] image, int x, int y, int size, int x1, int y1,
            int x2, int y2) {

        if (node == null) {
            return;
        }

        if (size == 1 || node.isLeaf()) {
            if (x >= x1 && x <= x2 && y >= y1 && y <= y2) {
                image[y * aspect + x] = node.getPixel();
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
        int[] image = new int[aspect * aspect];
        fillImage(image);
        mask(root, image, 0, 0, aspect, x1, y1, x2, y2);
        return new QuadTree(image, aspect, isGray);
    }

    private void mask(QuadTreeNode node, int[] image, int x, int y, int size, int x1, int y1, int x2, int y2) {
        if (node == null) {
            return;
        }

        if (size == 1 || node.isLeaf()) {
            if (x >= x1 && x <= x2 && y >= y1 && y <= y2) {
                image[y * aspect + x] = 0xFFFFFF;
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

        int factor = aspect / newSize;

        for (int y = 0; y < newSize; y++) {
            for (int x = 0; x < newSize; x++) {
                compressedImage[y * newSize + x] = averageColor(x * factor, y * factor, factor);
            }
        }
        return new QuadTree(compressedImage, newSize, isGray);
    }

    private int averageColor(int startX, int startY, int size) {
        int pixelCount = size * size;
        int totalRed = 0;
        int totalGreen = 0;
        int totalBlue = 0;
        int totalGray = 0;

        for (int y = startY; y < startY + size; y++) {
            for (int x = startX; x < startX + size; x++) {
                int pixel = search(x, y);
                if (isGray) {
                    totalGray += pixel & 0xFF;
                } else {
                    totalRed += (pixel >> 16) & 0xFF;
                    totalGreen += (pixel >> 8) & 0xFF;
                    totalBlue += pixel & 0xFF;
                }
            }
        }

        if (isGray) {
            int avgGray = totalGray / pixelCount;
            return (avgGray << 16) | (avgGray << 8) | avgGray;
        } else {
            int avgRed = totalRed / pixelCount;
            int avgGreen = totalGreen / pixelCount;
            int avgBlue = totalBlue / pixelCount;
            return (avgRed << 16) | (avgGreen << 8) | avgBlue;
        }
    }
}

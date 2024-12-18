package Fadishei.quadtree;

import java.awt.image.BufferedImage;
import Fadishei.image.ImageUtils;

public class QuadTree {
    private Node root;
    private int depth;

    public QuadTree() {
        this.root = null;
        this.depth = 0;
    }

    public void buildTree(int[] pixels, int width, int height) {
        this.root = buildRecursive(pixels, 0, 0, width, height, width);
    }

    private Node buildRecursive(int[] pixels, int x, int y, int width, int height, int rowStride) {
        if (width == 1 && height == 1) {
            return new Node(pixels[y * rowStride + x]);
        }

        int halfWidth = width / 2;
        int halfHeight = height / 2;

        Node node = new Node(0);
        node.setTopLeft(buildRecursive(pixels, x, y, halfWidth, halfHeight, rowStride));
        node.setTopRight(buildRecursive(pixels, x + halfWidth, y, halfWidth, halfHeight, rowStride));
        node.setBottomLeft(buildRecursive(pixels, x, y + halfHeight, halfWidth, halfHeight, rowStride));
        node.setBottomRight(buildRecursive(pixels, x + halfWidth, y + halfHeight, halfWidth, halfHeight, rowStride));

        int topLeftColor = node.getTopLeft().getColor();
        int topRightColor = node.getTopRight().getColor();
        int bottomLeftColor = node.getBottomLeft().getColor();
        int bottomRightColor = node.getBottomRight().getColor();

        if (topLeftColor == topRightColor && topRightColor == bottomRightColor && bottomRightColor == bottomLeftColor) {
            node.setColor(topLeftColor);
            node.setTopLeft(null);
            node.setTopRight(null);
            node.setBottomLeft(null);
            node.setBottomRight(null);
        }

        return node;
    }

    public int TreeDepth() {
        return calculateDepth(this.root);
    }

    private int calculateDepth(Node node) {
        if (node == null)
            return 0;
        return 1 + Math.max(
                Math.max(calculateDepth(node.getTopLeft()), calculateDepth(node.getTopRight())),
                Math.max(calculateDepth(node.getBottomLeft()), calculateDepth(node.getBottomRight())));
    }

    public int pixelDepth(int x, int y, int width, int height) {
        return findPixelDepth(root, x, y, 0, 0, width, height, 0);
    }

    private int findPixelDepth(Node node, int px, int py, int x, int y, int width, int height, int depth) {
        if (node == null)
            return -1;
        if (width == 1 && height == 1)
            return depth;

        int halfWidth = width / 2;
        int halfHeight = height / 2;

        if (px < x + halfWidth && py < y + halfHeight) {
            return findPixelDepth(node.getTopLeft(), px, py, x, y, halfWidth, halfHeight, depth + 1);
        } else if (px >= x + halfWidth && py < y + halfHeight) {
            return findPixelDepth(node.getTopRight(), px, py, x + halfWidth, y, halfWidth, halfHeight, depth + 1);
        } else if (px < x + halfWidth && py >= y + halfHeight) {
            return findPixelDepth(node.getBottomLeft(), px, py, x, y + halfHeight, halfWidth, halfHeight, depth + 1);
        } else {
            return findPixelDepth(node.getBottomRight(), px, py, x + halfWidth, y + halfHeight, halfWidth, halfHeight,
                    depth + 1);
        }
    }

    public void searchSubspacesWithRange(int startX, int startY, int endX, int endY, int width, int height) {
        int[] resultImage = new int[width * height];
        for (int i = 0; i < resultImage.length; i++) {
            resultImage[i] = 0xFFFFFF;
        }

        findSubspaces(root, 0, 0, width, height, startX, startY, endX, endY, resultImage);
        BufferedImage result = ImageUtils.createImageFromArray(resultImage, width, height);
        ImageUtils.saveImage(result, "range_output.png");
        System.out.println("range_output.png");
    }

    private void findSubspaces(Node node, int x, int y, int width, int height, int startX, int startY, int endX,
            int endY, int[] resultImage) {
        if (node == null)
            return;

        if (x + width <= startX || x >= endX || y + height <= startY || y >= endY) {
            return;
        }

        if (width == 1 && height == 1) {
            resultImage[y * width + x] = node.getColor();
            return;
        }

        int halfWidth = width / 2;
        int halfHeight = height / 2;

        findSubspaces(node.getTopLeft(), x, y, halfWidth, halfHeight, startX, startY, endX, endY, resultImage);
        findSubspaces(node.getTopRight(), x + halfWidth, y, halfWidth, halfHeight, startX, startY, endX, endY,
                resultImage);
        findSubspaces(node.getBottomLeft(), x, y + halfHeight, halfWidth, halfHeight, startX, startY, endX, endY,
                resultImage);
        findSubspaces(node.getBottomRight(), x + halfWidth, y + halfHeight, halfWidth, halfHeight, startX, startY, endX,
                endY, resultImage);
    }

    public void mask(int startX, int startY, int endX, int endY, int width, int height) {
        int[] maskedImage = new int[width * height];

        for (int i = 0; i < maskedImage.length; i++) {
            maskedImage[i] = 0xFFFFFF;
        }

        maskSubspaces(root, 0, 0, width, height, startX, startY, endX, endY, maskedImage);

        BufferedImage maskedResult = ImageUtils.createImageFromArray(maskedImage, width, height);
        ImageUtils.saveImage(maskedResult, "masked_output.png");
        System.out.println("masked_output.png");
    }

    private void maskSubspaces(Node node, int x, int y, int width, int height, int startX, int startY, int endX,
            int endY, int[] maskedImage) {
        if (node == null)
            return;

        if (x + width <= startX || x >= endX || y + height <= startY || y >= endY) {
            return;
        }

        if (width == 1 && height == 1) {
            maskedImage[y * width + x] = 0xFFFFFF;
            return;
        }

        int halfWidth = width / 2;
        int halfHeight = height / 2;

        maskSubspaces(node.getTopLeft(), x, y, halfWidth, halfHeight, startX, startY, endX, endY, maskedImage);
        maskSubspaces(node.getTopRight(), x + halfWidth, y, halfWidth, halfHeight, startX, startY, endX, endY,
                maskedImage);
        maskSubspaces(node.getBottomLeft(), x, y + halfHeight, halfWidth, halfHeight, startX, startY, endX, endY,
                maskedImage);
        maskSubspaces(node.getBottomRight(), x + halfWidth, y + halfHeight, halfWidth, halfHeight, startX, startY, endX,
                endY, maskedImage);
    }

    public int[] compress(int newSize, int originalWidth, int originalHeight) {
        int[] compressedPixels = new int[newSize * newSize];
        compressRecursive(root, 0, 0, originalWidth, originalHeight, newSize, compressedPixels, 0, 0);
        return compressedPixels;
    }

    private void compressRecursive(Node node, int x, int y, int width, int height, int newSize, int[] compressedPixels,
            int targetX, int targetY) {
        if (node == null)
            return;

        if (width == 1 && height == 1) {
            compressedPixels[targetY * newSize + targetX] = node.getColor();
            return;
        }

        int halfWidth = width / 2;
        int halfHeight = height / 2;
        int halfNewSize = newSize / 2;

        compressRecursive(node.getTopLeft(), x, y, halfWidth, halfHeight, halfNewSize, compressedPixels, targetX,
                targetY);
        compressRecursive(node.getTopRight(), x + halfWidth, y, halfWidth, halfHeight, halfNewSize, compressedPixels,
                targetX + halfNewSize, targetY);
        compressRecursive(node.getBottomLeft(), x, y + halfHeight, halfWidth, halfHeight, halfNewSize, compressedPixels,
                targetX, targetY + halfNewSize);
        compressRecursive(node.getBottomRight(), x + halfWidth, y + halfHeight, halfWidth, halfHeight, halfNewSize,
                compressedPixels, targetX + halfNewSize, targetY + halfNewSize);
    }

}

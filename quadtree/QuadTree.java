package Fadishei.quadtree;

public class QuadTree {
    private Node root;
    private int depth;

    public QuadTree() {
        this.root = null;
        this.depth = 0;
    }

    public void buildTree(int[] pixels, int width, int height) {
        root = buildRecursive(pixels, 0, 0, width, height, width);
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

        if (topLeftColor == topRightColor && topRightColor == bottomLeftColor && bottomLeftColor == bottomRightColor) {
            node.setColor(topLeftColor);
            node.setTopLeft(null);
            node.setTopRight(null);
            node.setBottomLeft(null);
            node.setBottomRight(null);
        }

        return node;
    }

    public int TreeDepth() {
        return calculateDepth(root);
    }

    private int calculateDepth(Node node) {
        if (node == null)
            return 0;
        return 1 + Math.max(
                Math.max(calculateDepth(node.getTopLeft()), calculateDepth(node.getTopRight())),
                Math.max(calculateDepth(node.getBottomLeft()), calculateDepth(node.getBottomRight())));
    }
}

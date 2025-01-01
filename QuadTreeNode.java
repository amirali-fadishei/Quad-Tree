public class QuadTreeNode {
    private final int pixel;
    private QuadTreeNode topLeft;
    private QuadTreeNode topRight;
    private QuadTreeNode bottomLeft;
    private QuadTreeNode bottomRight;

    public QuadTreeNode(int pixelValue) {
        this.setTopLeft(null);
        this.setTopRight(null);
        this.setBottomLeft(null);
        this.setBottomRight(null);
        this.pixel = pixelValue;
    }

    public boolean isLeaf() {
        return topLeft == null && topRight == null && bottomLeft == null && bottomRight == null;
    }

    public void setTopLeft(QuadTreeNode node) {
        this.topLeft = node;
    }

    public QuadTreeNode getTopLeft() {
        return topLeft;
    }

    public void setTopRight(QuadTreeNode node) {
        this.topRight = node;
    }

    public QuadTreeNode getTopRight() {
        return topRight;
    }

    public void setBottomLeft(QuadTreeNode node) {
        this.bottomLeft = node;
    }

    public QuadTreeNode getBottomLeft() {
        return bottomLeft;
    }

    public void setBottomRight(QuadTreeNode node) {
        this.bottomRight = node;
    }

    public QuadTreeNode getBottomRight() {
        return bottomRight;
    }

    public int getPixel() {
        return pixel;
    }
}

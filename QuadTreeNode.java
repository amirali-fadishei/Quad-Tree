public class QuadTreeNode {
    private int pixelValue;
    private QuadTreeNode topLeft;
    private QuadTreeNode topRight;
    private QuadTreeNode bottomLeft;
    private QuadTreeNode bottomRight;

    public QuadTreeNode(int pixelValue) {
        this.pixelValue = pixelValue;
        this.topLeft = null;
        this.topRight = null;
        this.bottomLeft = null;
        this.bottomRight = null;
    }

    public boolean isLeaf() {
        return topLeft == null && topRight == null && bottomLeft == null && bottomRight == null;
    }

    public void setTopLeft(QuadTreeNode node) {
        this.topLeft = node;
    }

    public void setTopRight(QuadTreeNode node) {
        this.topRight = node;
    }

    public void setBottomLeft(QuadTreeNode node) {
        this.bottomLeft = node;
    }

    public void setBottomRight(QuadTreeNode node) {
        this.bottomRight = node;
    }

    public QuadTreeNode getTopLeft() {
        return topLeft;
    }

    public QuadTreeNode getTopRight() {
        return topRight;
    }

    public QuadTreeNode getBottomLeft() {
        return bottomLeft;
    }

    public QuadTreeNode getBottomRight() {
        return bottomRight;
    }

    public int getPixelValue() {
        return pixelValue;
    }

    public void setPixelValue(int pixelValue) {
        this.pixelValue = pixelValue;
    }
}

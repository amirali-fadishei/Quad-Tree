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
        boolean state = topLeft == null && topRight == null && bottomLeft == null && bottomRight == null;
        return state;
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

    public void setPixelValue(int pixelValue) {
        this.pixelValue = pixelValue;
    }

    public int getPixelValue() {
        return pixelValue;
    }
}

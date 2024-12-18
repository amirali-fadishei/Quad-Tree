package Fadishei.quadtree;

public class Node {
    private int color;
    private Node topLeft;
    private Node topRight;
    private Node bottomLeft;
    private Node bottomRight;

    public Node(int color) {
        this.setColor(color);
        this.setTopLeft(null);
        this.setTopRight(null);
        this.setBottomLeft(null);
        this.setBottomRight(null);
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public Node getTopLeft() {
        return topLeft;
    }

    public void setTopLeft(Node topLeft) {
        this.topLeft = topLeft;
    }

    public Node getTopRight() {
        return topRight;
    }

    public void setTopRight(Node topRight) {
        this.topRight = topRight;
    }

    public Node getBottomLeft() {
        return bottomLeft;
    }

    public void setBottomLeft(Node bottomLeft) {
        this.bottomLeft = bottomLeft;
    }

    public Node getBottomRight() {
        return bottomRight;
    }

    public void setBottomRight(Node bottomRight) {
        this.bottomRight = bottomRight;
    }

}

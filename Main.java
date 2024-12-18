package Fadishei;

import Fadishei.quadtree.QuadTree;

public class Main {
    public static void main(String[] args) {
        int width = 4, height = 4;
        int[] pixels = {
            0xFF0000, 0xFF0000, 0xFF0000, 0xFF0000,
            0xFF0000, 0xFF0000, 0xFF0000, 0xFF0000,
            0x00FF00, 0x00FF00, 0x00FF00, 0x00FF00,
            0x00FF00, 0x00FF00, 0x00FF00, 0x00FF00
        };

        QuadTree quadTree = new QuadTree();
        quadTree.buildTree(pixels, width, height);

        System.out.println("عمق درخت: " + quadTree.TreeDepth());
    }
}


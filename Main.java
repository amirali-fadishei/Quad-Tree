package Fadishei;

import Fadishei.quadtree.QuadTree;
import Fadishei.image.ImageUtils;

import java.awt.image.BufferedImage;

public class Main {
    public static void main(String[] args) {
        int width = 4, height = 4;
        int[] pixels = {
                0xFF0000, 0x00FF00, 0x0000FF, 0xFFFF00,
                0x00FFFF, 0xFF00FF, 0x888888, 0xFFFFFF,
                0xFF0000, 0x00FF00, 0x0000FF, 0xFFFF00,
                0x00FFFF, 0xFF00FF, 0x888888, 0xFFFFFF
        };

        QuadTree quadTree = new QuadTree();
        quadTree.buildTree(pixels, width, height);

        BufferedImage originalImage = ImageUtils.createImageFromArray(pixels, width, height);
        ImageUtils.saveImage(originalImage, "original_image.png");
        System.out.println("original_image.png");

        System.out.println(quadTree.TreeDepth());

        int pixelX = 2, pixelY = 2;
        System.out.println(
                "pixel depth:(" + pixelX + ", " + pixelY + "): " + quadTree.pixelDepth(pixelX, pixelY, width, height));

        int rangeStartX = 1, rangeStartY = 1, rangeEndX = 3, rangeEndY = 3;
        quadTree.searchSubspacesWithRange(rangeStartX, rangeStartY, rangeEndX, rangeEndY, width, height);

        quadTree.mask(rangeStartX, rangeStartY, rangeEndX, rangeEndY, width, height);

        int newSize = 2;
        int[] compressedPixels = quadTree.compress(newSize, width, height);
        BufferedImage compressedImage = ImageUtils.createImageFromArray(compressedPixels, newSize, newSize);
        ImageUtils.saveImage(compressedImage, "compressed_image.png");
        System.out.println("compressed_image.png");
    }
}

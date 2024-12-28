public class Main {
    public static void main(String[] args) {
        boolean isGrayscale = false;
        String inputFilePath = "C:\\Users\\amira\\Desktop\\Quad-Tree\\testcase\\image4_RGB.csv";
        int[] pixelArray = ImageProcess.loadPixelsFromFile(inputFilePath, isGrayscale);
        int totalPixels = pixelArray.length;
        int imageSize = (int) Math.sqrt(totalPixels);

        QuadTree quadTree = new QuadTree(pixelArray, imageSize, isGrayscale);
        ImageProcess.saveImage(quadTree, "output.png");

        int depth = quadTree.getDepth();
        System.out.println("Depth of the QuadTree: " + depth);

        int pixelDepth = quadTree.pixelDepth(128, 128);
        System.out.println("Depth of the pixel at (128, 128): " + pixelDepth);

        QuadTree rangeQuadTree = quadTree.searchSubspacesWithRange(100, 100, 150, 150);
        ImageProcess.saveImage(rangeQuadTree, "range_output.png");

        QuadTree maskedQuadTree = quadTree.mask(50, 50, 200, 200);
        ImageProcess.saveImage(maskedQuadTree, "masked_output.png");

        int newSize = 64;
        QuadTree compressedQuadTree = quadTree.compress(newSize);
        ImageProcess.saveImage(compressedQuadTree, "compressed_output.png");
    }
}

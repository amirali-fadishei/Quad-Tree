public class Main {
    public static void main(String[] args) {

        // test-1

        // boolean isGray = true;
        // String inputFilePath = "C:\\Users\\amira\\Desktop\\Quad-Tree\\testcases\\image1_gray.csv";
        // int[] pixelArray = ImageProcess.loadPixels(inputFilePath, isGray);
        // int totalPixels = pixelArray.length;
        // int imageSize = (int) Math.sqrt(totalPixels);

        // QuadTree quadTree = new QuadTree(pixelArray, imageSize, isGray);
        // ImageProcess.saveImage(quadTree, "outputs\\output.png");

        // int depth = quadTree.treeDepth();
        // System.out.println(depth);

        // int pixelDepth = quadTree.pixelDepth(126, 124);
        // System.out.println(pixelDepth);

        // QuadTree rangeQuadTree = quadTree.searchSubspacesWithRange(100, 150, 200,
        //         250);
        // ImageProcess.saveImage(rangeQuadTree, "outputs\\range_output.png");

        // QuadTree maskedQuadTree = quadTree.mask(50, 50, 200, 200);
        // ImageProcess.saveImage(maskedQuadTree, "outputs\\masked_output.png");

        // int newSize = 128;
        // QuadTree compressedQuadTree = quadTree.compress(newSize);
        // ImageProcess.saveImage(compressedQuadTree, "outputs\\compressed_output.png");

        // test-2

        // boolean isGray = true;
        // String inputFilePath = "C:\\Users\\amira\\Desktop\\Quad-Tree\\testcases\\image2_gray.csv";
        // int[] pixelArray = ImageProcess.loadPixels(inputFilePath, isGray);
        // int totalPixels = pixelArray.length;
        // int imageSize = (int) Math.sqrt(totalPixels);

        // QuadTree quadTree = new QuadTree(pixelArray, imageSize, isGray);
        // ImageProcess.saveImage(quadTree, "outputs\\output.png");

        // int depth = quadTree.treeDepth();
        // System.out.println(depth);

        // int pixelDepth = quadTree.pixelDepth(200, 207);
        // System.out.println(pixelDepth);

        // QuadTree rangeQuadTree = quadTree.searchSubspacesWithRange(100, 150, 200,
        //         250);
        // ImageProcess.saveImage(rangeQuadTree, "outputs\\range_output.png");

        // QuadTree maskedQuadTree = quadTree.mask(50, 50, 200, 200);
        // ImageProcess.saveImage(maskedQuadTree, "outputs\\masked_output.png");

        // int newSize = 128;
        // QuadTree compressedQuadTree = quadTree.compress(newSize);
        // ImageProcess.saveImage(compressedQuadTree, "outputs\\compressed_output.png");

        // test-3

        boolean isGray = true;
        String inputFilePath = "C:\\Users\\amira\\Desktop\\Quad-Tree\\testcases\\image3_gray.csv";
        int[] pixelArray = ImageProcess.loadPixels(inputFilePath, isGray);
        int totalPixels = pixelArray.length;
        int imageSize = (int) Math.sqrt(totalPixels);

        QuadTree quadTree = new QuadTree(pixelArray, imageSize, isGray);
        ImageProcess.saveImage(quadTree, "outputs\\\\output.png");

        int depth = quadTree.treeDepth();
        System.out.println(depth);

        int pixelDepth = quadTree.pixelDepth(128, 128);
        System.out.println(pixelDepth);

        QuadTree rangeQuadTree = quadTree.searchSubspacesWithRange(100, 150, 200,
                250);
        ImageProcess.saveImage(rangeQuadTree, "outputs\\\\range_output.png");

        QuadTree maskedQuadTree = quadTree.mask(50, 50, 200, 200);
        ImageProcess.saveImage(maskedQuadTree, "outputs\\\\masked_output.png");

        int newSize = 64;
        QuadTree compressedQuadTree = quadTree.compress(newSize);
        ImageProcess.saveImage(compressedQuadTree, "outputs\\\\compressed_output.png");

        // test-4

        // boolean isGray = false;
        // String inputFilePath = "C:\\Users\\amira\\Desktop\\Quad-Tree\\testcases\\image4_RGB.csv";
        // int[] pixelArray = ImageProcess.loadPixels(inputFilePath, isGray);
        // int totalPixels = pixelArray.length;
        // int imageSize = (int) Math.sqrt(totalPixels);

        // QuadTree quadTree = new QuadTree(pixelArray, imageSize, isGray);
        // ImageProcess.saveImage(quadTree, "outputs\\output.png");

        // int depth = quadTree.treeDepth();
        // System.out.println(depth);

        // int pixelDepth = quadTree.pixelDepth(128, 128);
        // System.out.println(pixelDepth);

        // QuadTree rangeQuadTree = quadTree.searchSubspacesWithRange(100, 150, 200,
        //         250);
        // ImageProcess.saveImage(rangeQuadTree, "outputs\\range_output.png");

        // QuadTree maskedQuadTree = quadTree.mask(50, 50, 200, 200);
        // ImageProcess.saveImage(maskedQuadTree, "outputs\\masked_output.png");

        // int newSize = 32;
        // QuadTree compressedQuadTree = quadTree.compress(newSize);
        // ImageProcess.saveImage(compressedQuadTree, "outputs\\compressed_output.png");

    }
}

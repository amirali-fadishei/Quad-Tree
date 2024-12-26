import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        String filePath = "./testcase/image4_RGB.csv";
        List<Integer> pixels = new ArrayList<>();
        boolean isGrayscale = false;

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line = br.readLine();
            if (line != null) {
                String[] pixelValues = line.replaceAll("\"", "").split(",");
                if (isGrayscale) {
                    parseGrayscalePixels(pixelValues, pixels);
                } else {
                    parseColorPixels(pixelValues, pixels);
                }
            }
        } catch (FileNotFoundException e) {
            System.err.println("File not found: " + e.getMessage());
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
        }

        int[] pixelArray = pixels.stream().mapToInt(i -> i).toArray();

        int totalPixels = pixelArray.length;
        int imageSize = (int) Math.sqrt(totalPixels);
        if (imageSize * imageSize != totalPixels) {
            System.err.println("Image data is not a perfect square. Cannot form a square image.");
            return;
        }

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

    private static void parseGrayscalePixels(String[] pixelValues, List<Integer> pixels) {
        for (String pixelValue : pixelValues) {
            int gray = Integer.parseInt(pixelValue.trim());
            pixels.add((gray << 16) | (gray << 8) | gray);
        }
    }

    private static void parseColorPixels(String[] pixelValues, List<Integer> pixels) {
        for (int i = 0; i < pixelValues.length; i += 3) {
            int r = Integer.parseInt(pixelValues[i].trim());
            int g = Integer.parseInt(pixelValues[i + 1].trim());
            int b = Integer.parseInt(pixelValues[i + 2].trim());
            int rgb = (r << 16) | (g << 8) | b;
            pixels.add(rgb);
        }
    }
}

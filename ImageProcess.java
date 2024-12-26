import java.awt.image.BufferedImage;
import java.io.*;
import javax.imageio.ImageIO;

public class ImageProcess {

    public static int[] loadPixelsFromFile(String filePath, boolean isGrayscale) {
        int[] pixels = null;
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line = br.readLine();
            if (line != null) {
                String[] pixelValues = line.replaceAll("\"", "").split(",");
                if (isGrayscale) {
                    pixels = parseGrayscalePixels(pixelValues);
                } else {
                    pixels = parseColorPixels(pixelValues);
                }
            }
        } catch (FileNotFoundException e) {
            System.err.println("File not found: " + e.getMessage());
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
        }
        return pixels;
    }

    private static int[] parseGrayscalePixels(String[] pixelValues) {
        int[] pixels = new int[pixelValues.length];
        for (int i = 0; i < pixelValues.length; i++) {
            int gray = Integer.parseInt(pixelValues[i].trim());
            pixels[i] = (gray << 16) | (gray << 8) | gray;
        }
        return pixels;
    }

    private static int[] parseColorPixels(String[] pixelValues) {
        int[] pixels = new int[pixelValues.length / 3];
        for (int i = 0, j = 0; i < pixelValues.length; i += 3, j++) {
            int r = Integer.parseInt(pixelValues[i].trim());
            int g = Integer.parseInt(pixelValues[i + 1].trim());
            int b = Integer.parseInt(pixelValues[i + 2].trim());
            pixels[j] = (r << 16) | (g << 8) | b;
        }
        return pixels;
    }

    public static void saveImage(QuadTree quadTree, String filePath) {
        int imageSize = quadTree.getImageSize();
        int[] pixels = new int[imageSize * imageSize];
        quadTree.fillImage(pixels);

        BufferedImage image = createBufferedImage(imageSize, pixels);

        saveBufferedImageToFile(image, filePath);
    }

    private static BufferedImage createBufferedImage(int imageSize, int[] pixels) {
        BufferedImage image = new BufferedImage(imageSize, imageSize, BufferedImage.TYPE_INT_RGB);
        for (int y = 0; y < imageSize; y++) {
            for (int x = 0; x < imageSize; x++) {
                int rgb = pixels[y * imageSize + x];
                image.setRGB(x, y, rgb);
            }
        }
        return image;
    }

    private static void saveBufferedImageToFile(BufferedImage image, String filePath) {
        try {
            ImageIO.write(image, "png", new File(filePath));
        } catch (IOException e) {
            System.err.println("Error saving image to " + filePath);
            e.printStackTrace();
        }
    }
}

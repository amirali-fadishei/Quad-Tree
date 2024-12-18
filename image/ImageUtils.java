package Fadishei.image;

import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;

public class ImageUtils {

    public static BufferedImage createImageFromArray(int[] pixels, int width, int height) {
        if (pixels.length != width * height) {
            throw new IllegalArgumentException("image aspect didn't match!");
        }

        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int rgb = pixels[y * width + x];
                image.setRGB(x, y, rgb);
            }
        }
        return image;
    }

    public static void saveImage(BufferedImage image, String fileName) {
        try {
            ImageIO.write(image, "png", new File(fileName));
            System.out.println("image saved!" + fileName);
        } catch (Exception e) {
            System.err.println("Error in save image!" + fileName);
            e.printStackTrace();
        }
    }

    public static int[] loadImage(String fileName) {
        try {
            BufferedImage image = ImageIO.read(new File(fileName));
            int width = image.getWidth();
            int height = image.getHeight();
            int[] pixels = new int[width * height];

            for (int y = 0; y < height; y++) {
                for (int x = 0; x < width; x++) {
                    pixels[y * width + x] = image.getRGB(x, y);
                }
            }
            return pixels;
        } catch (Exception e) {
            System.err.println("Error in reading image!" + fileName);
            e.printStackTrace();
            return null;
        }
    }
}

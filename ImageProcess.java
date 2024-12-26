import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class ImageProcess {

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

package Fadishei;

import Fadishei.image.ImageUtils;
import java.awt.image.BufferedImage;

public class Main {
    public static void main(String[] args){
        int width = 4, height = 4;
        int[] pixels = {
            0xFF0000, 0x00FF00, 0x0000FF, 0xFFFFFF,
            0xFFFFFF, 0xFF0000, 0x00FF00, 0x0000FF,
            0x0000FF, 0xFFFFFF, 0xFF0000, 0x00FF00,
            0x00FF00, 0x0000FF, 0xFFFFFF, 0xFF0000
        };

        // تبدیل آرایه به تصویر
        BufferedImage image = ImageUtils.createImageFromArray(pixels, width, height);

        // ذخیره تصویر
        ImageUtils.saveImage(image, "output.png");
        System.out.println("تصویر ذخیره شد: output.png");
    }

}

import java.awt.image.BufferedImage;
import javax.imageio.*;
import java.io.File;
import java.awt.*;
public class ImageEditModel {
    BufferedImage image;

    public ImageEditModel(String imagePath) {
        try {
            this.image = ImageIO.read(new File(imagePath));
        }
        catch (Exception e) {
            // TODO: Raise dialog error.
            e.printStackTrace();
        }   
    }

    public void fillZone(Rectangle z, int[][] pixels) {
        /*
            1. For each 'pixel' in rectangle z
            2. Get new pixel value
            3. Set new pixel value in this.image

        */
        for (int h=0; h < z.getHeight(); h++) {
            for (int w = 0; w < z.getWidth(); w++) {
                int newPixelValue = pixels[h][w];
                this.image.setRGB(h, w, newPixelValue);;
            }
        }
        
    }

    public void clearZone(Rectangle z) {
        Color whiteColor = Color.white;
        int srgb = whiteColor.getRGB(); // FIXME: Not sure this will work because the method return a double value.
        int[][] pixels = new int[(int)z.getHeight()][(int)z.getWidth()];
        for (int i=0; i<pixels.length; i++) {
           for (int j=0; j<pixels[i].length; j++) {
               pixels[i][j] = srgb;
           }
        }
        fillZone(z, pixels);
    }

    public BufferedImage getImage() {
        return this.image;
    }

    // public static void main(String[] args) { // TODO: Remove this method, only for testing.
    //     ImageEditModel editor = new ImageEditModel("blue.jpg");
    //     Rectangle z = new Rectangle(editor.image.getHeight(), editor.image.getWidth());

    //     System.out.println(editor.image.getRGB(0, 0));
    //     editor.clearZone(z);
    //     System.out.println(editor.image.getRGB(0, 0));
    // }

    public class Coupe {
        Rectangle rect;
        int[][] pixels;

        public Coupe(int x, int y, int width, int height, BufferedImage image) {
            this.rect = new Rectangle(x, y, width, height);
            for (int i=0; i<image.getHeight(); i++) {
                for (int j=0; j<image.getWidth(); j++) {
                    pixels[i][j] = image.getRGB(i, j);
                }
             }
        }

        void doit() {
            clearZone(rect);
        }
        void undo() {
            fillZone(rect, pixels);
        }
    }
}
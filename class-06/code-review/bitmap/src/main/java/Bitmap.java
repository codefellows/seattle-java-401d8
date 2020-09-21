import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;

public class Bitmap {

    public Path path;
    public BufferedImage imageData;
    // the bitmap data is being stored as instance variables
    // where the bitmap lives, the data in the bitmap

    public Bitmap() {
        Path imagePath = FileSystems.getDefault().getPath("src/main/java/resources", "img.bmp");

        this.path = imagePath;
        // actually read in file and save that data in an instance variable
        BufferedImage img = null;
        try {
            img = ImageIO.read(imagePath.toFile());
            this.imageData = img;
        } catch (IOException e) {
        }
    }

    // constructor for testing purposes
    public Bitmap(BufferedImage imageData) {
        this.imageData = imageData;
    }

    public void flipVertically() {
        // logic on instance variable to flip it
        // take each row
        // reverse it (aka flip it)
        for (int i = 0; i < this.imageData.getWidth(); i++) {
            for (int j = 0; j < this.imageData.getHeight() / 2; j++) {
                int temp = this.imageData.getRGB(i,j);
                this.imageData.setRGB(i,j, this.imageData.getRGB(i, this.imageData.getWidth() - j - 1));
                this.imageData.setRGB(i, this.imageData.getWidth() - j - 1, temp);
            }
        }

    }

    public void grayscale(){
        for(int width = 0; width < this.imageData.getWidth(); width++){
            for(int height = 0; height < this.imageData.getHeight(); height++){
                int newGrayColor = grayscale(this.imageData.getRGB(width, height));
                this.imageData.setRGB(width, height, newGrayColor);
            }
        }
        this.save(new File("src/main/java/resources/gray.bmp").toPath());
    }

    private int grayscale(int originalColor){
        Color color = new Color(originalColor);
        int average = (color.getBlue() + color.getGreen() + color.getRed()) / 3;
        Color newColor = new Color(average, average, average);
        return newColor.getRGB();
    }

    public boolean save(Path savePath) {
        // output instance variable data into a file at the save path
        try {
            return ImageIO.write(imageData, "bmp", savePath.toFile());
        } catch (IOException e) {

        }
        return false;
    }
}

import org.junit.Test;

import java.awt.*;

import static org.junit.Assert.assertEquals;

public class BitmapTest {
    // test that the constructor actually makes a bitmap
    // test that the files save with .save
    // test things about the flip output
    // that the positions have flipped
    // test that 0,0 is pink and 0,100 is orange
    // test that grayscalse makes things gray
//    public Bitmap bmp = new Bitmap();
    @Test
    public void testGrayscale(){
        Bitmap bmp = new Bitmap();
        bmp.grayscale();
        Color actualColor = new Color(bmp.imageData.getRGB(0,0));

        assertEquals("rgb should be grayscale at 165, 165 165 at position 0,0, RED", actualColor.getRed(), 165 );
        assertEquals("rgb should be grayscale at 165, 165 165 at position 0,0, GREEN", actualColor.getGreen(), 165 );
        assertEquals("rgb should be grayscale at 165, 165 165 at position 0,0, BLUE", actualColor.getBlue(), 165 );
    }
}

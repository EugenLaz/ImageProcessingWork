package ImgWork.Readers;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ImgReader {

    public static BufferedImage readImage(String filePath){
        try{
            BufferedImage img = new BufferedImage(
                    500, 500, BufferedImage.TYPE_INT_RGB );
            File f = new File(filePath);
            int r = 5;
            int g = 25;
            int b = 255;
            int col = (r << 16) | (g << 8) | b;
            for(int x = 0; x < 500; x++){
                for(int y = 20; y < 300; y++){
                    img.setRGB(x, y, col);
                }
            }
            ImageIO.write(img, "PNG", f);
            return img;
        }
        catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }
}

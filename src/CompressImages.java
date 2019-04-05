
import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.stream.ImageOutputStream;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
public class CompressImages {

    public static void main(String... args) throws IOException {



        File input = new File("C:/Users/Realplaza/Pictures/avion-11.jpg");
        BufferedImage image = ImageIO.read(input);

        File output = new File("C:/Users/Realplaza/Pictures/avion-111.jpg");
        OutputStream out = new FileOutputStream(output);

        ImageWriter writer =  ImageIO.getImageWritersByFormatName("jpg").next();
        ImageOutputStream ios = ImageIO.createImageOutputStream(out);
        writer.setOutput(ios);

        ImageWriteParam param = writer.getDefaultWriteParam();
        if (param.canWriteCompressed()){
            param.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
            param.setCompressionQuality(0.5f);
        }

        writer.write(null, new IIOImage(image, null, null), param);

        try {
            removeMetadata("C:/Users/Realplaza/Pictures/avion-111.jpg", "png");
        }
        catch (IOException e) {
            System.err.println("I/O Exception");
        }

        out.close();
        ios.close();
        writer.dispose();

    }

    public static void removeMetadata(String imagePath, String imageType)
            throws IOException {
        BufferedImage image = ImageIO.read(new File(imagePath));
        ImageIO.write(image, imageType, new File(imagePath));
    }


}
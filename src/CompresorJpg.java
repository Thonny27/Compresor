import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.stream.IIOByteBuffer;
import javax.imageio.stream.ImageOutputStream;
import javax.imageio.stream.MemoryCacheImageOutputStream;
import java.awt.image.RenderedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.ByteOrder;

public class CompresorJpg {
    public static void main (String[] args){

        File originslImage= new File("C:/Users/Realplaza/Pictures/avion.jpg");
        try{
            byte[] imageData = getCompressedImageBytes(originslImage,0.5f);

            System.out.println(imageData.length);
            System.out.println("Done!");
        }catch (IOException e){

        }
    }

    public static void compressJPGimage(File originalImage,File compressImage,float compresionQuality) throws IOException{
        RenderedImage image = ImageIO.read(originalImage);
        ImageWriter jpgWriter =ImageIO.getImageWritersByFormatName("jpg").next();
        ImageWriteParam jpgWriteParam = jpgWriter.getDefaultWriteParam();
        jpgWriteParam.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
        jpgWriteParam.setCompressionQuality(compresionQuality);

        try (ImageOutputStream output = ImageIO.createImageOutputStream(compressImage)){
            jpgWriter.setOutput(output);
            IIOImage outputImage = new IIOImage(image,null,null);
            jpgWriter.write(null,outputImage,jpgWriteParam);
        }
        jpgWriter.dispose();

        }

    public static byte[] getCompressedImageBytes(File originalImage,float compresionQuality) throws IOException {

        byte[] compressImageBytes;
        RenderedImage image = ImageIO.read(originalImage);
        ImageWriter jpgWriter = ImageIO.getImageWritersByFormatName("jpg").next();
        ImageWriteParam jpgWriteParam = jpgWriter.getDefaultWriteParam();
        jpgWriteParam.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
        jpgWriteParam.setCompressionQuality(compresionQuality);

        try (ByteArrayOutputStream baos = new ByteArrayOutputStream(); ImageOutputStream output = new MemoryCacheImageOutputStream(baos)) {
            jpgWriter.setOutput(output);
            IIOImage outputImage = new IIOImage(image,null,null);
            jpgWriter.write(null,outputImage,jpgWriteParam);
            compressImageBytes=baos.toByteArray();
        }

        return compressImageBytes;

    }
}

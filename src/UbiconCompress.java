import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.stream.ImageOutputStream;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class UbiconCompress {


    public static void main(String[] args) throws IOException {
        scale(new File("C:/Users/Realplaza/Pictures/imagenes")); // Ruta de carpeta de imagenes a cargar
        compress(new File("C:/Users/Realplaza/Pictures/salida")); // Ruta de carpeta de imagenes escaladas
    }

    public static void scale(File dir) throws IOException {

        File listFile[] = dir.listFiles();
        if (listFile != null) {
            for (int i = 0; i < listFile.length; i++) {
                if (listFile[i].isDirectory()) {
                    scale(listFile[i]);
                } else {
                    //System.out.println(listFile[i].getPath());

                    File input = new File(listFile[i].getPath());
                    BufferedImage image = ImageIO.read(input);

                    BufferedImage resized = resize(image, 500, 500);

                    File output = new File("C:/Users/Realplaza/Pictures/salida/imagen"+i+".jpg"); //Salida de imagenes escaladas
                    ImageIO.write(resized, "png", output);

                    System.out.println("Escalando: "+output);

                }
            }
        }
    }

    private static BufferedImage resize(BufferedImage img, int height, int width) {
        Image tmp = img.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        BufferedImage resized = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = resized.createGraphics();
        g2d.drawImage(tmp, 0, 0, null);
        g2d.dispose();
        return resized;
    }

    public static void removeMetadata(String imagePath, String imageType)
            throws IOException {
        BufferedImage image = ImageIO.read(new File(imagePath));
        ImageIO.write(image, imageType, new File(imagePath));
    }

    public static void compress(File dir) throws IOException {

        File listFile[] = dir.listFiles();
        if (listFile != null) {
            for (int i = 0; i < listFile.length; i++) {
                if (listFile[i].isDirectory()) {
                    compress(listFile[i]);
                } else {
                    //System.out.println(listFile[i].getPath());

                    File input = new File(listFile[i].getPath());
                    BufferedImage image = ImageIO.read(input);

                    File output = new File("C:/Users/Realplaza/Pictures/comprimido/comprimido"+i+".jpg"); // Salida final de imagenes comprimidas
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
                        removeMetadata("C:/Users/Realplaza/Pictures/comprimido/comprimido"+i+".jpg", "png");
                        System.out.println("Comprimiendo: "+output);

                    }
                    catch (IOException e) {
                        System.err.println("I/O Exception");
                    }

                    out.close();
                    ios.close();
                    writer.dispose();


                }
            }
        }
    }



}



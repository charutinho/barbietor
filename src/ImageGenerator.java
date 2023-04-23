import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

public class ImageGenerator {

    public static void makeImage(InputStream imageStream)  {
        try {
            BufferedImage uploadedImage = resizeImage(imageStream);

            uploadedImage = BackgroundRemover.backgroundRemover(uploadedImage);

            BufferedImage background = ImageIO.read(new File("src/assets/bg-pink.png"));
            BufferedImage detail = ImageIO.read(new File("src/assets/detail.png"));

            BufferedImage newImage = new BufferedImage(1080, 1920, BufferedImage.TRANSLUCENT);
            Graphics2D graphic = (Graphics2D) newImage.getGraphics();

            graphic.drawImage(background, 0, 0, null);
            graphic.drawImage(uploadedImage, 0, 500, null);
            graphic.drawImage(detail, 0, 0, null);

            ImageIO.write(newImage, "png", new File("src/output/" + "barbie-image" + ".png"));
            Desktop desktop = Desktop.getDesktop();
            desktop.open(new File("src/output/" + "barbie-image" + ".png"));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static BufferedImage resizeImage (InputStream imageStream) throws IOException {
        BufferedImage image = ImageIO.read(imageStream);
        int originalWidth = image.getWidth();
        int originalHeight = image.getHeight();

        double ratio = (double) originalWidth / 1080;
        int height = (int) (originalHeight / ratio);

        BufferedImage resizedImage = new BufferedImage(1080, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = resizedImage.createGraphics();
        g.drawImage(image, 0, 0, 1080, height, null);
        g.dispose();

        return resizedImage;
    }
}

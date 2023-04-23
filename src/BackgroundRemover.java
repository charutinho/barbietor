import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.UUID;

public class BackgroundRemover {

    public static BufferedImage backgroundRemover(BufferedImage imageUploaded) {

        try {
            String boundary = UUID.randomUUID().toString();

            byte[] body = convertBufferedImageToByteArray(imageUploaded);
            byte[] header = ("--" + boundary + "\r\n" +
                    "Content-Disposition: form-data; name=\"" + "image_file" + "\"; filename=\"" + "imageUploadedByUserToByteArray" + "\"\r\n" +
                    "Content-Type: image/jpeg\r\n\r\n").getBytes();
            byte[] footer = ("\r\n--" + boundary + "--\r\n").getBytes();
            byte[] requestPayload = new byte[header.length + body.length + footer.length];
            System.arraycopy(header, 0, requestPayload, 0, header.length);
            System.arraycopy(body, 0, requestPayload, header.length, body.length);
            System.arraycopy(footer, 0, requestPayload, header.length + body.length, footer.length);

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("https://clipdrop-api.co/remove-background/v1"))
                    .header("Content-Type", "multipart/form-data; boundary=" + boundary)
                    .header("x-api-key", System.getenv("API_CODE_REMOVEBG"))
                    .POST(HttpRequest.BodyPublishers.ofByteArray(requestPayload))
                    .build();

            HttpClient client = HttpClient.newHttpClient();
            HttpResponse<InputStream> response = client.send(request, HttpResponse.BodyHandlers.ofInputStream());

            return ImageIO.read(response.body());
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static byte[] convertBufferedImageToByteArray(BufferedImage image){
        try {
            ByteArrayOutputStream imageUploadedToByteArray = new ByteArrayOutputStream();
            ImageIO.write(image, "png", imageUploadedToByteArray);
            return imageUploadedToByteArray.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}

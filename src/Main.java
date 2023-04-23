import java.net.URL;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        try (sc) {
            System.out.println("Insert image URL: ");
            String url = sc.next();

            System.out.println("Generating image...");
            ImageGenerator.makeImage(new URL(url).openStream());

            System.out.println("Successful, your image are in src/output folder");
        } catch (Exception e) {
            System.out.println("Sorry, error generating your image, try again later: ");
            e.printStackTrace();
        }
    }
}
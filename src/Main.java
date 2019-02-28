import model.Photo;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class Main {

    private static Photo[] photos;

    public static void main(String[] args) throws IOException {
        if(args.length == 0) {
            System.out.println("Give the filename as an argument!");
            System.exit(1);
        }
        for(String arg: args) {
            readInput(arg);
        }
    }

    private static void readInput(String fileName) throws IOException {
        File file = new File("input/" + fileName);
        Scanner scanner = new Scanner(file);

        int photoNo = scanner.nextInt();
        photos = new Photo[photoNo];
        scanner.nextLine();

        for (int i=0; i<photoNo; i++) {
            String photoLine = scanner.nextLine();
            String[] photoDetails = photoLine.split(" ");
            int orientation = photoDetails[0].equals("H") ? 0 : 1;
            int tagsNo = Integer.parseInt(photoDetails[1]);
            String[] tags = new String[tagsNo];
            System.arraycopy(photoDetails, 2, tags, 0, tagsNo);
            photos[i] = new Photo(i, orientation, tagsNo, tags);
            System.out.println("Read: " + photos[i]);
        }

    }
}

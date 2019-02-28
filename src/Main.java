import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils;
import model.Photo;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashSet;
import java.util.Scanner;

public class Main {

    private static Photo[] photos;
    private static int photoNo;
    private static int slideNo;

    public static void main(String[] args) throws IOException {
        if(args.length == 0) {
            System.out.println("Give the filename as an argument!");
            System.exit(1);
        }
        for(String arg: args) {
            readInput(arg);
            writeOutput(arg);
        }
    }

    private static void readInput(String fileName) throws IOException {
        File file = new File("input/" + fileName);
        Scanner scanner = new Scanner(file);

        photoNo = scanner.nextInt();
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

    private static void writeOutput(String fileName) throws IOException {
        File file = new File("output/" + fileName);
        BufferedWriter bf = new BufferedWriter(new FileWriter(file));
        int slideNo = 0;
        HashSet<Integer> ommitedIds = new HashSet<>();
        StringBuilder builder = new StringBuilder();
        for(int i=0;i<photoNo;i++) {
            if(ommitedIds.contains(i)) {
                continue;
            }
            Photo photo1 = photos[i];
            Photo photo2 = null;
            if(photo1.getOrientation() == 1) {
                int j=i+1;
                while(j < photoNo && photos[j].getOrientation() == 0 && ommitedIds.contains(j)) {
                    j++;
                }
                if(j < photoNo) {
                    photo2 = photos[j];
                    ommitedIds.add(j);
                }
            }
            builder.append(photo2 != null ? photo1.getId() + " " + photo2.getId() + "\n" : photo1.getId() + "\n");
            slideNo++;
        }
        bf.write(slideNo + "\n" + builder.toString());
        bf.close();
        System.out.println("Write completed");
    }
}

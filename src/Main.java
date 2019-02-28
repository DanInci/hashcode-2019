import model.Photo;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class Main {

    private static List<Photo> horizontalPhotos = new ArrayList<Photo>();
    private static int horizontalPhotosNo;

    private static List<Photo> verticalPhotos = new ArrayList<Photo>();
    private static int verticalPhotosNo;


    public static void main(String[] args) throws IOException {
        if(args.length == 0) {
            System.out.println("Give the filename as an argument!");
            System.exit(1);
        }
        for(String arg: args) {
            readInput(arg);
            groupVerticalIntoHorizontal();
            optimze();
            writeOutput(arg);
        }
    }

    private static void readInput(String fileName) throws IOException {
        File file = new File("input/" + fileName);
        Scanner scanner = new Scanner(file);

        verticalPhotosNo = scanner.nextInt();
        scanner.nextLine();

        for (int i=0; i<verticalPhotosNo; i++) {
            String photoLine = scanner.nextLine();
            String[] photoDetails = photoLine.split(" ");
            String orientation = photoDetails[0];
            int tagsNo = Integer.parseInt(photoDetails[1]);
            String[] tags = new String[tagsNo];
            System.arraycopy(photoDetails, 2, tags, 0, tagsNo);
            if(orientation.equals("H")) {
                Photo photo = new Photo(String.valueOf(i), tagsNo, tags);
                horizontalPhotosNo++;
                horizontalPhotos.add(photo);
                System.out.println("Read horizontal: " + photo);
            } else {
                Photo photo = new Photo(String.valueOf(i), tagsNo, tags);
                verticalPhotos.add(photo);
                System.out.println("Read vertical: " + photo);

            }

        }
        verticalPhotosNo -= horizontalPhotosNo;
    }

    private static void groupVerticalIntoHorizontal() {
        int length = verticalPhotosNo % 2 == 0 ? verticalPhotosNo : verticalPhotosNo - 1;
        int i = 0;
        Collections.shuffle(verticalPhotos);
        while (i < length) {
            Photo firstPhoto = verticalPhotos.get(i);
            Photo secondPhoto = verticalPhotos.get(i + 1);
            String id = firstPhoto.getId() + " " + secondPhoto.getId();
            HashSet<String> tags = new HashSet<>();
            tags.addAll(Arrays.asList(firstPhoto.getTags()));
            tags.addAll(Arrays.asList(secondPhoto.getTags()));
            int tagsIntersectionNo = tags.size();
            Photo combinedPhoto = new Photo(id, tagsIntersectionNo, tags.toArray(new String[tagsIntersectionNo]));
            horizontalPhotosNo++;
            horizontalPhotos.add(combinedPhoto);
            i += 2;
        }
    }

    private static void optimze() {
        for (int i = 0; i < horizontalPhotosNo - 1; i++) {
            Photo currentPhoto = horizontalPhotos.get(i);
            int maxPhotoIndex = i+1;
            int maxScore = currentPhoto.compareTo(horizontalPhotos.get(i+1));

            for (int j = i + 2; j < horizontalPhotosNo; j++) {
                int score = currentPhoto.compareTo(horizontalPhotos.get(j));
                if (score > maxScore) {
                    maxScore = score;
                    maxPhotoIndex = j;
                }
            }

            if(maxPhotoIndex != i+1) {
                Photo aux = horizontalPhotos.get(i+1);
                Photo maxPhoto = horizontalPhotos.get(maxPhotoIndex);
                horizontalPhotos.set(i+1, maxPhoto);
                horizontalPhotos.set(maxPhotoIndex, aux);
            }
        }
    }

    private static void writeOutput(String fileName) throws IOException {
        File file = new File("output/" + fileName);
        BufferedWriter bf = new BufferedWriter(new FileWriter(file));
        bf.write(horizontalPhotosNo + "\n");
        for(Photo photo : horizontalPhotos) {
            bf.write(photo.getId() + "\n");
        }
        bf.close();
        System.out.println("Write completed");
    }
}

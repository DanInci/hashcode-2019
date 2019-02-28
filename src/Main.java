import model.Photo;
import model.Transition;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class Main {

    private static List<Photo> horizontalPhotos;
    private static int horizontalPhotosNo;

    private static List<Photo> verticalPhotos;
    private static int verticalPhotosNo;

    private static List<Transition> transitions = new ArrayList<>();
    private static int transitionsNo;


    public static void main(String[] args) throws IOException {
        if(args.length == 0) {
            System.out.println("Give the filename as an argument!");
            System.exit(1);
        }
        for(String arg: args) {
            int score = 0;
            while(score < 174900) {
                horizontalPhotos = new ArrayList<Photo>();
                horizontalPhotosNo = 0;
                verticalPhotos =  new ArrayList<Photo>();
                verticalPhotosNo = 0;
                transitions = new ArrayList<>();
                transitionsNo = 0;
                System.out.println("Reading input " + arg + " ...");
                readInput(arg);
                System.out.println("Read completed");
                groupVerticalIntoHorizontal();
                System.out.println("Vertical photos group completed");
                groupHorizontalIntoTransitions();
                Collections.sort(transitions);
                System.out.println("Optimized slide presentation");
                writeTransitionOutput(arg);
                System.out.println("Write completed");
                score = calculateScore();
                System.out.println("Estimated score: " + score);
            }

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
            } else if (orientation.equals("V")){
                Photo photo = new Photo(String.valueOf(i), tagsNo, tags);
                verticalPhotos.add(photo);
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

    private static void groupHorizontalIntoTransitions() {
        transitionsNo = horizontalPhotosNo % 2 == 0 ? horizontalPhotosNo : horizontalPhotosNo - 1;
        int i = 0;
        Collections.shuffle(horizontalPhotos);
        while (i < transitionsNo) {
            Photo firstPhoto = horizontalPhotos.get(i);
            Photo secondPhoto = horizontalPhotos.get(i + 1);
            transitions.add(new Transition(firstPhoto, secondPhoto));
            i += 2;
        }
    }

//    private static void optimize() {
//        for (int i = 0; i < horizontalPhotosNo - 1; i++) {
//            System.out.println("Finding match for photo " + i + " ...");
//            Photo currentPhoto = horizontalPhotos.get(i);
//            int maxPhotoIndex = i+1;
//            int maxScore = currentPhoto.compareTo(horizontalPhotos.get(i+1));
//
//            for (int j = i + 2; j < horizontalPhotosNo; j++) {
//                int score = currentPhoto.compareTo(horizontalPhotos.get(j));
//                if (score > maxScore) {
//                    maxScore = score;
//                    maxPhotoIndex = j;
//                }
//            }
//
//            if(maxPhotoIndex != i+1) {
//                Photo aux = horizontalPhotos.get(i+1);
//                Photo maxPhoto = horizontalPhotos.get(maxPhotoIndex);
//                horizontalPhotos.set(i+1, maxPhoto);
//                horizontalPhotos.set(maxPhotoIndex, aux);
//            }
//        }
//    }

    private static void writeOutput(String fileName) throws IOException {
        File file = new File("output/" + fileName);
        BufferedWriter bf = new BufferedWriter(new FileWriter(file));
        bf.write(horizontalPhotosNo + "\n");
        for(Photo photo : horizontalPhotos) {
            bf.write(photo.getId() + "\n");
        }
        bf.close();
    }

    private static void writeTransitionOutput(String fileName) throws IOException {
        File file = new File("output/" + fileName);
        BufferedWriter bf = new BufferedWriter(new FileWriter(file));
        bf.write(transitionsNo + "\n");
        for(Transition transition : transitions) {
            bf.write(transition.getP1().getId() + "\n");
            bf.write(transition.getP2().getId() + "\n");
        }
        bf.close();
    }

    private static int calculateScore() throws IOException {
        int score = 0;
        for(int i=0;i<horizontalPhotosNo-1;i++) {
            score += horizontalPhotos.get(i).compareTo(horizontalPhotos.get(i+1));
        }
        return score;
    }
}

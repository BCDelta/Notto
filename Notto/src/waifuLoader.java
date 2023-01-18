import java.io.*;

import java.util.Random;

import javax.imageio.ImageTypeSpecifier;

public class waifuLoader {

    private File selectedImage;

    public waifuLoader() {
        File directory = new File("C:/Users/BCDelta/Documents/Java/Notto/waifuPics");

        File[] imageFiles = directory.listFiles();

        Random randomNumbers = new Random();

        int indexToUse = randomNumbers.nextInt(imageFiles.length);
        selectedImage = imageFiles[indexToUse];
    }    

    public String getImageFile() {
        return selectedImage.toString().replace('\\', '/');
    }
}

package pc.knn.app.services;

import java.io.FileNotFoundException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.FileReader;
import java.io.File;

import java.awt.image.DataBufferByte;
import java.awt.image.BufferedImage;

import java.util.ArrayList;
import java.util.List;

import pc.knn.app.model.Record;

import javax.imageio.ImageIO;

public class FileService {
    // This method will load and convert an image to a matrix of pixels
    public static int[] resizeImageAndConvertToGray (String path){
        // Load phase
        BufferedImage image = null;
        try {
            image = ImageIO.read(new File(path));
        } catch (IOException ioe) {
            System.out.println("The image could not be loaded");
            System.out.println("The path is: " + path);
            // ioe.printStackTrace();
        }

        // Resize phase
        BufferedImage resizedImage = new BufferedImage(50, 50, BufferedImage.TYPE_BYTE_GRAY);
        resizedImage.getGraphics().drawImage(image, 0, 0, 50, 50, null);

        // Convert image as a byte array
        byte[] imageBytes = ((DataBufferByte) resizedImage.getRaster().getDataBuffer()).getData();
        int[] attributes = new int[imageBytes.length];
        for (int i = 0; i < imageBytes.length; i++) {
            attributes[i] = imageBytes[i] & 0xFF; // Converter de byte para valor numérico (0-255)
        }
        return attributes;
    }

    public static List<Record> preLoadRecords(String csvPath, String stage) throws FileNotFoundException {
        if (stage != "train" && stage != "test") {
            throw new IllegalArgumentException("The stage must be 'train' or 'test'");
        }

        List<Record> records = new ArrayList<>();
        BufferedReader reader = new BufferedReader(new FileReader(csvPath));

        try {
            String line;
            // Skip the header
            reader.readLine();
            while ((line = reader.readLine()) != null) {
                String[] parts  = line.split(",");
                if (!parts[3].trim().equals(stage)){
                    continue;
                }
                int classIndex = (int) Double.parseDouble(parts[0]);
                String imagePath = parts[1];
                // Change / to \ in the path to work in Windows
                imagePath = imagePath.replace("/", "\\");
                String [] labels = parts[2].split(" ");
                String name = parts[4];

                records.add(new Record(classIndex, imagePath, labels, name));
            }
        } catch (IOException ioe) {
            ioe.printStackTrace();
        } finally {
            try {
                reader.close();
            } catch (IOException ioe) {
                ioe.printStackTrace();
            }
        }
        return records;
    }
}


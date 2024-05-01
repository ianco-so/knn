package pc.knn;

import java.io.FileNotFoundException;
import java.util.List;

import pc.knn.app.services.FileService;
import pc.knn.app.services.knnService;
import pc.knn.app.model.Record;

/**
 * Hello world!
 *
 */
public class Knn {
    private final static String csvPath = "C:\\Users\\ianco\\Documents\\datasets\\birds\\birds.csv";
    private final static String birdsPath = "C:\\Users\\ianco\\Documents\\datasets\\birds";

    public static void main( String[] args ){
        List<Record> records = null;
        try {
            records = FileService.preLoadRecords(csvPath, "train");
        } catch (FileNotFoundException fnfe) {
            fnfe.printStackTrace();
        }

        for (Record r : records) {
            r.setAttributes(FileService.resizeImageAndConvertToGray(birdsPath + "\\" + r.getImagePath()));
        }
        String labels [] = {"ABBOTTS", "BABBLER"};
        Record testRecord = new Record(0, "test\\ABBOTTS BABBLER\\1.jpg", labels, "MALACOCINCLA ABBOTTI");

        testRecord.setAttributes(FileService.resizeImageAndConvertToGray(birdsPath + "\\" + testRecord.getImagePath()));

        List<Record> nearestRecords = knnService.knn(records, testRecord, 5);

        for (Record r : nearestRecords) {
            System.out.println(r.getName());
        }
    }
}

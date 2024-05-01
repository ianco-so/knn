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
    public final static String CSV_PATH = "C:\\Users\\ianco\\Documents\\datasets\\birds\\birds.csv";
    public final static String BIRDS_PATH = "C:\\Users\\ianco\\Documents\\datasets\\birds";

    public static void main( String[] args ){
        List<Record> records = null;
        try {
            records = FileService.preLoadRecords(CSV_PATH, "train");
        } catch (FileNotFoundException fnfe) {
            fnfe.printStackTrace();
        }

        for (Record r : records) {
            r.setAttributes(FileService.resizeImageAndConvertToGray(BIRDS_PATH + "\\" + r.getImagePath()));
        }
        String labels [] = {"ABBOTTS", "BABBLER"};
        Record testRecord = new Record(0, "test\\ABBOTTS BABBLER\\1.jpg", labels, "MALACOCINCLA ABBOTTI");

        testRecord.setAttributes(FileService.resizeImageAndConvertToGray(BIRDS_PATH + "\\" + testRecord.getImagePath()));

        List<Record> nearestRecords = knnService.knn(records, testRecord, 5);

        for (Record r : nearestRecords) {
            System.out.println(r.getName());
        }
    }
}

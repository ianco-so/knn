package pc.knn.app.services;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import pc.knn.app.model.Record;

public class knnService {
    private static int euclideanDistance(Record a , Record b) {
        if (a.getAttributes().length != b.getAttributes().length) {
            throw new IllegalArgumentException("The records must have the same number of attributes");
        }

        int sum = 0;
        for (int i = 0; i < a.getAttributes().length; i++) {
            sum += Math.pow(a.getAttributes()[i] - b.getAttributes()[i], 2);
        }
        return (int) Math.sqrt(sum);
    }

    public static List<Record> knn(List<Record> records, Record record, int k) {
        List<Record> nearestRecords = new ArrayList<>();
        for (Record r : records) {
            r.setDistance(euclideanDistance(r, record));
        }
        records.sort(Comparator.comparingInt(Record::getDistance));
        for (int i = 0; i < k; i++) {
            nearestRecords.add(records.get(i));
        }
        return nearestRecords;
    }
}

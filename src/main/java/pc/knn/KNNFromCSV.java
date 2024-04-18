package pc.knn;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class KNNFromCSV {

    private static class Record {
        int id;
        double[] features;

        public Record(int id, double[] features) {
            this.id = id;
            this.features = features;
        }
    }

    private static List<Record> readCSV(String filename) throws IOException {
        List<Record> records = new ArrayList<>();
        BufferedReader reader = new BufferedReader(new FileReader(filename));
        String line;

        // Skip header
        reader.readLine();

        while ((line = reader.readLine()) != null) {
            String[] parts = line.split(",");
            int id = Integer.parseInt(parts[0]);
            double[] features = new double[parts.length - 1];

            for (int i = 1; i < parts.length; i++) {
                // Convert string features to double
                if (parts[i].startsWith("CL")) {
                    features[i - 1] = Double.NaN;
                } else {
                    features[i - 1] = Double.parseDouble(parts[i]);
                }
            }

            records.add(new Record(id, features));
        }

        reader.close();
        return records;
    }

    private static double euclideanDistance(double[] point1, double[] point2) {
        double sum = 0.0;
        for (int i = 0; i < point1.length; i++) {
            // Ignore NaN values
            if (!Double.isNaN(point1[i]) && !Double.isNaN(point2[i])) {
                sum += Math.pow((point1[i] - point2[i]), 2);
            }
        }
        return Math.sqrt(sum);
    }

    private static int[] findNearestNeighbors(List<Record> data, double[] query, int k) {
        double[] distances = new double[data.size()];
        for (int i = 0; i < data.size(); i++) {
            distances[i] = euclideanDistance(data.get(i).features, query);
        }

        int[] nearestNeighbors = new int[k];
        for (int i = 0; i < k; i++) {
            nearestNeighbors[i] = findMinIndex(distances);
            distances[nearestNeighbors[i]] = Double.MAX_VALUE; // Mark as visited
        }

        return nearestNeighbors;
    }

    private static int findMinIndex(double[] arr) {
        int minIndex = 0;
        double minValue = arr[0];
        for (int i = 1; i < arr.length; i++) {
            if (arr[i] < minValue) {
                minIndex = i;
                minValue = arr[i];
            }
        }
        return minIndex;
    }

    public static void main(String[] args) throws IOException {
        List<Record> data = readCSV("src\\main\\java\\pc\\knn\\resources\\drug_consumption.csv");

        // Example query
        double[] query = {0.49788, 0.48246, -0.05921, 0.96082, 0.126, 0.31287, -0.57545, -0.58331, -0.91699, -0.00665, -0.21712, -1.18084};

        int k = 3;
        int[] nearestNeighbors = findNearestNeighbors(data, query, k);

        System.out.println("The " + k + " nearest neighbors to the query point are:");
        for (int neighbor : nearestNeighbors) {
            System.out.println("ID: " + data.get(neighbor).id + ", Features: " + Arrays.toString(data.get(neighbor).features));
        }
    }
}


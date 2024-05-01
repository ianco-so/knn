package pc.knn.app.model;

import lombok.Data;

@Data
public class Record {
    private final int       classIndex; // The class index of the specie. (the knn algorithm will classify the image based on this index)
    private final String    imagePath;  // The path of the image.
    private final String[]  labels;     // The labels of the specie.
    private final String    name;       // The scientific name of the specie.
    private       int[]     attributes; // The numeric velues of the pixels of the image.
    private       int       distance;   // The distance between this record and another record.
}
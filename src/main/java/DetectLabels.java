import software.amazon.awssdk.core.SdkBytes;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.rekognition.RekognitionClient;
import software.amazon.awssdk.services.rekognition.model.*;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class DetectLabels {
    RekognitionClient rekClient = RekognitionClient.builder().region(Region.US_WEST_2).build();

    public void closeRek() {
        rekClient.close();
    }

    public void detectImageLabels(String sourceImage) {
        try { /*InputStream sourceStream = new URL("https://images.unsplash.com/photo-1557456170-0cf4f4d0d362?ixid=MnwxMjA3fDB8MHxzZWFyY2h8MXx8bGFrZXxlbnwwfHwwfHw%3D&ixlib=rb-1.2.1&w=1000&q=80").openStream();*/
            InputStream sourceStream = new FileInputStream(sourceImage);
            SdkBytes sourceBytes = SdkBytes.fromInputStream(sourceStream); /* Create an Image object for the source image.*/
            Image souImage = Image.builder().bytes(sourceBytes).build();
            DetectLabelsRequest detectLabelsRequest = DetectLabelsRequest.builder().image(souImage).maxLabels(10).build();
            DetectLabelsResponse labelsResponse = rekClient.detectLabels(detectLabelsRequest);
            List<Label> labels = labelsResponse.labels();
            System.out.println("Detected labels for the given photo");
            for (Label label : labels)
                if (label.name().equals("Car")) System.out.println(label.name() + ": " + label.confidence().toString());
        } catch (RekognitionException | FileNotFoundException e) {
            System.out.println(e.getMessage());
            System.exit(1);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public float getLabelsfromImage(String bucket, String image) {
        float confidence = 0;
        try {
            S3Object s3Object = S3Object.builder().bucket(bucket).name(image).build();
            Image myImage = Image.builder().s3Object(s3Object).build();
            DetectLabelsRequest detectLabelsRequest = DetectLabelsRequest.builder().image(myImage).maxLabels(10).build();
            DetectLabelsResponse labelsResponse = rekClient.detectLabels(detectLabelsRequest);
            List<Label> labels = labelsResponse.labels();
            System.out.println("Detected labels for the given photo" + image);
            for (Label label : labels)
                if (label.name().equals("Car")) {
                    System.out.println(image + ": " + label.name() + ": " + label.confidence().toString());
                    confidence = label.confidence();
                }
        } catch (RekognitionException e) {
            System.out.println(e.getMessage());
            System.exit(1);
        }
        return confidence;
    }

}

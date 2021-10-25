import software.amazon.awssdk.core.SdkBytes;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.rekognition.RekognitionClient;
import software.amazon.awssdk.services.rekognition.model.*;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class DetectText {
    RekognitionClient rekClient = RekognitionClient.builder().region(Region.US_WEST_2).build();

    void recClose() {
        rekClient.close();
    }

    public List<Textid> detectTextLabels(String sourceImage) {
        List<Textid> textidList = new ArrayList<Textid>();
        try {
            InputStream sourceStream = new FileInputStream(sourceImage);
            SdkBytes sourceBytes = SdkBytes.fromInputStream(sourceStream); /* Create an Image object for the source image*/
            Image souImage = Image.builder().bytes(sourceBytes).build();
            DetectTextRequest textRequest = DetectTextRequest.builder().image(souImage).build();
            DetectTextResponse textResponse = rekClient.detectText(textRequest);
            List<TextDetection> textCollection = textResponse.textDetections();
            System.out.println("Detected lines and words");
            for (TextDetection text : textCollection) textidList.add(new Textid(text.id(), text.detectedText()));
        } catch (RekognitionException | FileNotFoundException e) {
            System.out.println(e.getMessage());
            System.exit(1);
        }
        return textidList;
    }
}
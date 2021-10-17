import software.amazon.awssdk.core.SdkBytes;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.rekognition.RekognitionClient;
import software.amazon.awssdk.services.rekognition.model.DetectTextRequest;
import software.amazon.awssdk.services.rekognition.model.Image;
import software.amazon.awssdk.services.rekognition.model.DetectTextResponse;
import software.amazon.awssdk.services.rekognition.model.TextDetection;
import software.amazon.awssdk.services.rekognition.model.RekognitionException;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;


public class DetectText {

    RekognitionClient rekClient = RekognitionClient.builder()
            .region(Region.US_WEST_2)
            .build();
//    public static void main(String[] args) {
//
//        String sourceImage = "output/1.jpeg" ;
//        DetectText dt =new DetectText();
//        List<Textid> hop= dt.detectTextLabels(sourceImage );
//        for (Textid hi:  hop){
//        System.out.println(hi);
//        }
//        dt.recClose();
//        ImageText im= new ImageText(sourceImage,hop);
//        System.out.println(im);
//
//    }

    void recClose(){
        rekClient.close();
    }
    // snippet-start:[rekognition.java2.detect_text.main]
    public  List<Textid>  detectTextLabels(String sourceImage) {
        List<Textid> textidList = new ArrayList<Textid>();
        try {

            InputStream sourceStream = new FileInputStream(sourceImage);
            SdkBytes sourceBytes = SdkBytes.fromInputStream(sourceStream);

            // Create an Image object for the source image
            Image souImage = Image.builder()
                    .bytes(sourceBytes)
                    .build();

            DetectTextRequest textRequest = DetectTextRequest.builder()
                    .image(souImage)
                    .build();

            DetectTextResponse textResponse = rekClient.detectText(textRequest);
            List<TextDetection> textCollection = textResponse.textDetections();

            System.out.println("Detected lines and words");
            for (TextDetection text: textCollection) {
                System.out.println("Detected: " + text.detectedText());
                System.out.println("Confidence: " + text.confidence().toString());
                System.out.println("Id : " + text.id());
                System.out.println("Parent Id: " + text.parentId());
                System.out.println("Type: " + text.type());
                System.out.println();
                textidList.add(new Textid(text.id(),text.detectedText()));
            }

        } catch (RekognitionException | FileNotFoundException e) {
            System.out.println(e.getMessage());
            System.exit(1);
        }
        return textidList;
    }

    // snippet-end:[rekognition.java2.detect_text.main]
}
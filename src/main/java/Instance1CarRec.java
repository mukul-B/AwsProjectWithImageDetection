import software.amazon.awssdk.services.sqs.SqsClient;

import java.util.List;

public class Instance1CarRec {
    public static void main(String[] args) {
        String queueName = "queueM";
        String bucketName="unr-cs442";
        SQSOperations sw = new SQSOperations();
        DetectLabels fg = new DetectLabels();
        try {
            S3Operations s3 = new S3Operations(bucketName);
            List<String> labels = s3.ListObject();
            labels.add("-1");
            for (String os : labels) {
                float confidence = 100;
                if (!os.equals("-1")) confidence = fg.getLabelsfromImage(bucketName, os);
                if (confidence > 90) {
                    System.out.println("sending " + os + " with : " + confidence);
                    sw.sendMessage(queueName, os);
                } else System.out.println("not sending " + os + " with : " + confidence);
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException ex) {
                    Thread.currentThread().interrupt();
                }
            }
        } finally {
            sw.closeConnection();
            fg.closeRek();
        }
    }
}

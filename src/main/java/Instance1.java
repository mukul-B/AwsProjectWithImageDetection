import java.util.List;

public class Instance1 {

    public static void main(String[] args) {

        String queueName = "queueM";
        sqstest sw = new sqstest();
        DetectLabels fg = new DetectLabels();
        try {
            S3 s3 = new S3();
            List<String> labels = s3.ListObject();
            for (String os : labels) {
                float confidence = fg.getLabelsfromImage("unr-cs442", os);

                if (confidence > 90) {
                    System.out.println("sending" + os + "with : " + confidence);
                    sw.sendMessage(queueName, os);
                } else {
                    System.out.println("not sending " + os + "with : " + confidence);
                }
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException ex) {
                    Thread.currentThread().interrupt();
                }
            }
            sw.sendMessage(queueName, "-1");
        } finally {
            sw.closeConnection();
            fg.closeRek();
        }
    }
}

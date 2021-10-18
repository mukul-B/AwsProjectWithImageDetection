import java.util.List;

public class Instance1 {

    public static void main(String[] args) {

        String queueName = "queueM";
        sqstest sw = new sqstest();
        DetectLabels fg = new DetectLabels();
        try {
            S3 s3 = new S3();
            List<String> labels = s3.ListObject();
            labels.add("-1");
            for (String os : labels) {

                float confidence = 100;
                if(! os.equals("-1")){
                    confidence=fg.getLabelsfromImage("unr-cs442", os);
                }
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

        } finally {

            sw.closeConnection();
            fg.closeRek();
        }
    }
}

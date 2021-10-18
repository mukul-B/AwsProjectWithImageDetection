import software.amazon.awssdk.services.sqs.model.Message;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Instance2 {

    public static void main(String[] args) {
        String queueName = "queueM";
        sqstest sw = new sqstest();
        S3 s3 = new S3();
        DetectText dt =new DetectText();
        int end=0;
        List<ImageText> ims = new ArrayList<ImageText>();
        try {
            for (int i = 0; i < 30; i++) {

                List<Message> messages = sw.receiveMessages("queueM");
                System.out.println(messages.size()+" message received");
                for (Message message : messages) {
                    System.out.println(message.body());

                    if(message.body().equals("-1"))
                        end=-1;
                    else{
                        s3.GetObject(message.body());
                        String sourceImage = "output/" + message.body();
                        List<Textid> hop= dt.detectTextLabels(sourceImage );
                        ims.add(new ImageText(message.body(),hop));
                    }
                    sw.deleteMessage("queueM", message);

                }
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException ex) {
                    Thread.currentThread().interrupt();
                }

                if(end==-1)
                    break;

            }
        } finally {
            dt.recClose();
            sw.closeConnection();
        }

        for (ImageText im : ims){
            System.out.println(im);
        }
    }
}

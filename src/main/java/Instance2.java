import software.amazon.awssdk.services.sqs.model.Message;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static java.lang.System.exit;

public class Instance2 {

    public static void main(String[] args) {
        String queueName = "queueM";
        sqstest sw = new sqstest();
        S3 s3 = new S3();
        DetectText dt =new DetectText();
        int end=0;
        List<ImageText> ims = new ArrayList<ImageText>();
        try {
            for (int i = 0; i < 50; i++) {

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
        String fileContent="";
        for (ImageText im : ims){
            System.out.println(im);
            fileContent=fileContent+im.imageid+"\n";
            for (Textid txt : im.getTextid()) {
                fileContent=fileContent+"\t"+txt.textid+":"+txt.text+"\n";
            }
            }



        try{usingFileWriter(fileContent);}
        catch(IOException ie){
            exit(1);
        }
    }
    public static void usingFileWriter(String fileContent ) throws IOException
    {


        FileWriter fileWriter = new FileWriter("output/outputfile.txt");
        fileWriter.write(fileContent);
        fileWriter.close();
    }
}

import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.sqs.SqsClient;
import software.amazon.awssdk.services.sqs.model.*;

import java.util.ArrayList;
import java.util.List;
// snippet-end:[sqs.java2.sqs_example.import]


/**
 * To run this Java V2 code example, ensure that you have setup your development environment, including your credentials.
 *
 * For information, see this documentation topic:
 *
 * https://docs.aws.amazon.com/sdk-for-java/latest/developer-guide/get-started.html
 */
public class SQSExample {


    public static void main(String[] args) {
        String queueName = "queueM" ;
        //+ System.currentTimeMillis();

//        SqsClient sqsClient = SqsClient.builder()
//                .region(Region.US_WEST_2)
//                .build();

        sqstest sw =new sqstest();
        // Perform various tasks on the Amazon SQS queue
        //String queueUrl= sw.createQueue(sqsClient, queueName );
        //sw.createQueue("queueM");
        //sw.listQueues();
        try {
            for(int i=0;i<20;i++) {
                List<Message> messages = sw.receiveMessages("queueM");
                for (Message message : messages) {
                    System.out.println(message.body());
                    sw.deleteMessage("queueM",message);
                }
                try
                {
                    Thread.sleep(1000);
                }
                catch(InterruptedException ex)
                {
                    Thread.currentThread().interrupt();
                }
            }
        }
        finally {
            sw.closeConnection();
        }


//        listQueuesFilter(sqsClient, queueUrl);
//        List<Message> messages = receiveMessages(sqsClient, queueUrl);
//        sendBatchMessages(sqsClient, queueUrl);
//        changeMessages(sqsClient, queueUrl, messages);
//        deleteMessages(sqsClient, queueUrl,  messages) ;
//        sqsClient.close();
    }

    // snippet-end:[sqs.java2.sqs_example.main]
}


import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.sqs.SqsClient;
import software.amazon.awssdk.services.sqs.model.*;

import java.util.List;

public class SQSOperations {
    SqsClient sqsClient = SqsClient.builder().region(Region.US_WEST_2).build();

    public void closeConnection() {
        sqsClient.close();
    }

    public String createQueue(String queueName) {
        try {
            System.out.println("\nCreate Queue");
            CreateQueueRequest createQueueRequest = CreateQueueRequest.builder().queueName(queueName).build();
            sqsClient.createQueue(createQueueRequest);
            System.out.println("\nGet queue url");
            GetQueueUrlResponse getQueueUrlResponse = sqsClient.getQueueUrl(GetQueueUrlRequest.builder().queueName(queueName).build());
            String queueUrl = getQueueUrlResponse.queueUrl();
            return queueUrl;
        } catch (SqsException e) {
            System.err.println(e.awsErrorDetails().errorMessage());
            System.exit(1);
        }
        return "";
    }

    public void listQueues() {
        System.out.println("\nList Queues");
        String prefix = "que";
        try {
            ListQueuesRequest listQueuesRequest = ListQueuesRequest.builder().queueNamePrefix(prefix).build();
            ListQueuesResponse listQueuesResponse = sqsClient.listQueues(listQueuesRequest);
            for (String url : listQueuesResponse.queueUrls()) {
                DeleteQueueRequest d = DeleteQueueRequest.builder().queueUrl(url).build();
                System.out.println(url);
            }
        } catch (SqsException e) {
            System.err.println(e.awsErrorDetails().errorMessage());
            System.exit(1);
        }
    }

    public void listQueuesFilter(String queueUrl) { /* List queues with filters*/
        String namePrefix = "queue";
        ListQueuesRequest filterListRequest = ListQueuesRequest.builder()
                .queueNamePrefix(queueUrl).build();
        ListQueuesResponse listQueuesFilteredResponse = sqsClient.listQueues(filterListRequest);

        for (String url : listQueuesFilteredResponse.queueUrls()) {
            System.out.println(url.endsWith(queueUrl));
        }
    }

    public void sendMessage(String queueUrl, String msg) {
        System.out.println("\nSend message");
        try {
            sqsClient.sendMessage(SendMessageRequest.builder().queueUrl(queueUrl).messageBody(msg).delaySeconds(10).build());
        } catch (SqsException e) {
            System.err.println(e.awsErrorDetails().errorMessage());
            System.exit(1);
        }
    }

    public void sendBatchMessages(String queueUrl) {
        System.out.println("\nSend multiple messages");
        try {
            SendMessageBatchRequest sendMessageBatchRequest = SendMessageBatchRequest.builder().queueUrl(queueUrl).entries(SendMessageBatchRequestEntry.builder().id("id1").messageBody("Hello from msg 1").build(), SendMessageBatchRequestEntry.builder().id("id2").messageBody("msg 2").delaySeconds(10).build()).build();
            sqsClient.sendMessageBatch(sendMessageBatchRequest);
        } catch (SqsException e) {
            System.err.println(e.awsErrorDetails().errorMessage());
            System.exit(1);
        }
    }

    public List<Message> receiveMessages(String queueUrl) {
        try {
            ReceiveMessageRequest receiveMessageRequest = ReceiveMessageRequest.builder().queueUrl(queueUrl).maxNumberOfMessages(5).build();
            List<Message> messages = sqsClient.receiveMessage(receiveMessageRequest).messages();
            return messages;
        } catch (SqsException e) {
            System.err.println(e.awsErrorDetails().errorMessage());
            System.exit(1);
        }
        return null;
    }

    public void changeMessages(String queueUrl, List<Message> messages) {
        System.out.println("\nChange Message Visibility");
        try {
            for (Message message : messages) {
                ChangeMessageVisibilityRequest req = ChangeMessageVisibilityRequest.builder().queueUrl(queueUrl).receiptHandle(message.receiptHandle()).visibilityTimeout(100).build();
                sqsClient.changeMessageVisibility(req);
            }
        } catch (SqsException e) {
            System.err.println(e.awsErrorDetails().errorMessage());
            System.exit(1);
        }
    }

    public void deleteMessages(String queueUrl, List<Message> messages) {
        System.out.println("\nDelete Messages");
        try {
            for (Message message : messages) {
                DeleteMessageRequest deleteMessageRequest = DeleteMessageRequest.builder().queueUrl(queueUrl).receiptHandle(message.receiptHandle()).build();
                sqsClient.deleteMessage(deleteMessageRequest);
            }
        } catch (SqsException e) {
            System.err.println(e.awsErrorDetails().errorMessage());
            System.exit(1);
        }
    }

    public void deleteMessage(String queueUrl, Message message) {
        try {
            DeleteMessageRequest deleteMessageRequest = DeleteMessageRequest.builder().queueUrl(queueUrl).receiptHandle(message.receiptHandle()).build();
            sqsClient.deleteMessage(deleteMessageRequest);
        } catch (SqsException e) {
            System.err.println(e.awsErrorDetails().errorMessage());
            System.exit(1);
        }
    }
}

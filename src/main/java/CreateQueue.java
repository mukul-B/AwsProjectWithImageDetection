public class CreateQueue {
    public static void main(String[] args){
        String queueName = "queueCarImages";
        SQSOperations sw = new SQSOperations();
        String responseUrl=sw.createQueue(queueName);
        System.out.println(responseUrl + " is ready");
        sw.closeConnection();

    }
}

import com.amazonaws.services.s3.model.S3ObjectSummary;

import java.util.List;

public class Instance1 {

    public static void main(String[] args) {

        S3 s3= new S3();

        List<String> labels= s3.ListObject();

        for (String os : labels) {
            System.out.println("* " +os);
            s3.GetObject(os);
            break;

        }


    }
}

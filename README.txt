java -cp /home/muku/IdeaProjects/FirstAwsproject/target/FirstAwsproject-1.0-SNAPSHOT-jar-with-dependencies.jar Instance2
java -cp /home/muku/IdeaProjects/FirstAwsproject/target/FirstAwsproject-1.0-SNAPSHOT-jar-with-dependencies.jar Instance1
ssh -i "instance.pem" ubuntu@ec2-xx-xx-xx-xxx.xx-xx-1.compute.amazonaws.com

scp -i Downloads/instance.pem  /home/muku/IdeaProjects/FirstAwsproject/target/FirstAwsproject-1.0-SNAPSHOT-jar-with-dependencies.jar ubuntu@ec2-xx-xx-xx-xxx.xx-xx-1.compute.amazonaws.com:~
set environment variable
aws_access_key_id =
aws_secret_access_key =

java -cp FirstAwsproject-1.0-SNAPSHOT-jar-with-dependencies.jar Instance2


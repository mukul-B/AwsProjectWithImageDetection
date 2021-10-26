
-------------------------------------------------------------------------------------------------
Both instance setup
-------------------------------------------------------------------------------------------------
connect to ec2
after creating both instance from aws console
copy the command from ssh client
    ssh -i "Downloads/instance.pem" ec2-user@ec2-xx-xx-xx-xxx.xx-xx-1.compute.amazonaws.com
-------------------------------------------------------------------------------------------------
install java
    sudo yum install java-1.8.0-openjdk
-------------------------------------------------------------------------------------------------
set environment variable
copy both variable from local
cat .aws/credentials
export in both ec2 instance
export AWS_ACCESS_KEY_ID=xxxxxxxxxxxxxxxxxxxxxx
export AWS_SECRET_ACCESS_KEY=xxxxxxxxxxxxxxxxxxxxxx
or
scp -i Downloads/instance.pem  .aws/credentials ec2-user@ec2-3xx-xx-xx-xxx.xx-xx.us-west-2.compute.amazonaws.com:~
mand move in .aws directory
-------------------------------------------------------------------------------------------------
Move maven package jar into both ec2
on Intellij : mvn package
command to move jar to ec2 instance
scp -i Downloads/instance.pem  /home/muku/IdeaProjects/FirstAwsproject/target/FirstAwsproject-1.0-SNAPSHOT-jar-with-dependencies.jar ec2-user@ec2-xx-xx-xx-xxx.xx-xx-1.compute.amazonaws.com:~

-------------------------------------------------------------------------------------------------

-------------------------------------------------------------------------------------------------
Running Application
-------------------------------------------------------------------------------------------------
run application on Instane 1:
java -cp FirstAwsproject-1.0-SNAPSHOT-jar-with-dependencies.jar Instance1CarRec

run application on Instance 2:
java -cp FirstAwsproject-1.0-SNAPSHOT-jar-with-dependencies.jar Instance2TextRec

if the queue is not created , on any instance following command can be run
java -cp FirstAwsproject-1.0-SNAPSHOT-jar-with-dependencies.jar CreateQueue
-------------------------------------------------------------------------------------------------

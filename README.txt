Both instance setup
connect through
ssh -i "Downloads/instance.pem" ec2-user@ec2-xx-xx-xx-xxx.xx-xx-1.compute.amazonaws.com
install
sudo yum install java-1.8.0-openjdk

set environment variable
copy both variable from local
cat .aws/credentials

export in ec2 ionstance
export AWS_ACCESS_KEY_ID=xxxxxxxxxxxxxxxxxxxxxx
export AWS_SECRET_ACCESS_KEY=xxxxxxxxxxxxxxxxxxxxxx

Move maven package jar into both ec2
mvn package
scp -i Downloads/instance.pem  /home/muku/IdeaProjects/FirstAwsproject/target/FirstAwsproject-1.0-SNAPSHOT-jar-with-dependencies.jar ec2-user@ec2-xx-xx-xx-xxx.xx-xx-1.compute.amazonaws.com:~

run application on Instane 1:
java -cp /home/muku/IdeaProjects/FirstAwsproject/target/FirstAwsproject-1.0-SNAPSHOT-jar-with-dependencies.jar Instance1CarRec

run application on Instance2TextRec:
java -cp /home/muku/IdeaProjects/FirstAwsproject/target/FirstAwsproject-1.0-SNAPSHOT-jar-with-dependencies.jar Instance2TextRec


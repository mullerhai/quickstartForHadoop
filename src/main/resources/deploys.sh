#!/bin/sh
echo "deploy jar"

AWS_PEM='/Users/linkedmemuller/Documents/AWS_Consel/hadoopCluster.pem'
AWS_USER_SERVER=ec2-user@ec2-52-80-67-242.cn-north-1.compute.amazonaws.com.cn
#LOCAL_JAR=/Users/linkedmemuller/Documents/githubdown/quickstart/out/artifacts/quickstart_jar/quickstart.jar
#RUN_SCRIPT=/Users/linkedmemuller/Documents/run.sh
LOCAL_JAR=../../../out/artifacts/quickstart_jar/quickstart.jar
RUN_SCRIPT=run.sh
APP_NAME=quickstart
echo "waitting some minutes jar and  shell script  upload  to AWS"
scp -i   $AWS_PEM  $LOCAL_JAR     $RUN_SCRIPT     $AWS_USER_SERVER:/hadoopJars/

echo "deploy jar  complete !"
sleep 5
echo "grant the ec2-user execute   run sh  privelege "
ssh -i $AWS_PEM  $AWS_USER_SERVER 'sudo -i chmod 777  /hadoopJars/run.sh'

sleep 3
echo " run  jar begining"
ssh -i $AWS_PEM  $AWS_USER_SERVER '/hadoopJars/run.sh'


#!/bin/sh
echo "deploy jar"
# 三个系统  userlocal  aws—hadoop——cluster    hdfs
#从本地上传 jar包到  亚马逊服务器
alias  APP_NAME=quickstart

ssh -i "/Users/linkedmemuller/Documents/AWS_Consel/hadoopCluster.pem" ec2-user@ec2-52-80-67-242.cn-north-1.compute.amazonaws.com.cn
sudo su hadoop
mkdir  -p  /hadoopJars/$APP_NAME
exit

scp -i "/Users/linkedmemuller/Documents/AWS_Consel/hadoopCluster.pem"  ../target/quickstart-1.0-SNAPSHOT.jar  ec2-user@ec2-52-80-67-242.cn-north-1.compute.amazonaws.com.cn:/hadoopJars/$APP_NAME/

#登录执行 mapreduce 命令
ssh -i "/Users/linkedmemuller/Documents/AWS_Consel/hadoopCluster.pem" ec2-user@ec2-52-80-67-242.cn-north-1.compute.amazonaws.com.cn
sudo su hadoop

echo "deploy run.sh"



scp  hadoop@121.42.36.80:~/test/
scp -i "/Users/linkedmemuller/Documents/AWS_Consel/hadoopCluster.pem" run.sh  ec2-user@ec2-52-80-67-242.cn-north-1.compute.amazonaws.com.cn
echo "start run.sh"
ssh -i "/Users/linkedmemuller/Documents/AWS_Consel/hadoopCluster.pem" ec2-user@ec2-52-80-67-242.cn-north-1.compute.amazonaws.com.cn



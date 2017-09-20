#!/bin/sh

echo "run script is running"

sleep 1
APP_NAME=quickstart
echo -e  'application: $APP_NAME'
mkdir -p /hadoopJars/$APP_NAME
sudo -i chown -R hadoop:hadoop /hadoopJars/$APP_NAME
echo "ok change the application directory user to :hadoop"
sudo -i chmod o+rw  /hadoopJars/$APP_NAME
JAR=quickstart.jar
cd  /hadoopJars
mv ./$JAR     /hadoopJars/$APP_NAME
echo "ok move run.sh to the appliction  directory "
mv ./run.sh   /hadoopJars/$APP_NAME

sleep 3

echo "run task.sh"
HADOOP_BIN_PATH=/usr/local/hadoop-2.8.1/bin/hadoop

JARPATH=/hadoopJars/$APP_NAME/$JAR
MAIN_CLASS=HadoopStart
INPUT_DIR=/testdata/
OUTPUT_DIR=/testouttmpsd

sleep 2
echo "task is running jar"

sudo su hadoop  $HADOOP_BIN_PATH   jar    $JARPATH   $MAIN_CLASS  $INPUT_DIR  $OUTPUT_DIR

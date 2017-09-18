echo "run hadoop task"
sudo su hadoop
/usr/local/hadoop/hadoop-2.8.1/bin/hadoop jar /hadoopJars/quickstart-1.0-SNAPSHOT.jar HadoopStart   /input/*  /output/

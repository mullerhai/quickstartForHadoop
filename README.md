# quickstartForHadoop
java and  scala
##Maven  AWS EC2 shell Script 
搭建 JAVA _HADOOP_MAVEN本地项目 ，通过项目中的shell 脚本远程 链接 远程的Amazon AWS EC-2 实例 hadoop 集群，由于AWS 苛刻 的登录模式，普通shell 脚本需要
绕过一些坑才能实现

1. 大家根基自己实际的AWS ec2   hadoop_master  地址去配置  ，还有你的项目实际路径，AWS登录需要有 pem 凭证文件，
2. 大家需要配置 可执行jar包文件生成路径，而不是使用mvn自己打包产生 的
其他的自由变量 值设定还是要根据知己的实际情况为准

首先 maven package  验证 程序无 编译错误
`
mvn package
`
之后  简单运行 生成 可执行jar包

然后执行  mvn verify 命令 ，console   则会输出  remote  hadoop  cluster 执行结果

这里 一共两个脚本 ，deploys.sh 在 本地  terminal 运行，并上传 run.sh 到服务器端，
run.sh 是在服务端 运行的脚本，执行服务端集群 命令
服务端 需要有一个jar 存储的多用户共享的文件夹 ，并且权限 777  最好

出现  权限问题  文件 目录 问题  大家就自己 慢慢看， 大部分是自己没有配置好，


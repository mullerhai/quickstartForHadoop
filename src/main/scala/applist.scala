
import org.apache.hadoop.conf.Configuration
import org.apache.hadoop.fs.Path
import org.apache.hadoop.io.{IntWritable, Text}
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat
import org.apache.hadoop.mapreduce.Job
import org.apache.hadoop.mapreduce. Mapper
import org.apache.hadoop.mapreduce.Reducer
import java.io.IOException
import java.util.StringTokenizer

object  applist {

  object appMap {


    private val one=new IntWritable(1)
  }

  class appMap extends Mapper[AnyRef, Text, Text, IntWritable] {

    private val word :Text=new Text()

    @throws[IOException]
    @throws[InterruptedException]
    override def map(key: scala.Any, value: Text, context:Mapper[AnyRef,Text,Text,IntWritable]#Context): Unit ={
     val toker =new  StringTokenizer(value.toString)
      if (toker.hasMoreTokens){
        word.set(toker.nextToken())
        context.write(word,appMap.one)
      }
    }
  }

  class appReduce extends  Reducer[Text,IntWritable,Text,IntWritable]{


 private val sum = new IntWritable
/*
    @throws[IOException]
    @throws[InterruptedException]
    override def reduce(key: Text, values: Iterable[IntWritable], context: Reducer[Text,IntWritable,Text,IntWritable]#Context): Unit = {
      var result = 0
      import scala.collection.JavaConversions._
      for (vals <- values) {
        result += vals.get
      }
      sum.set(result)
      context.write(key, sum)
    }
   */
    private val sum = new IntWritable
    @throws[IOException]
    @throws[InterruptedException]
    override def reduce(key: Text, values:Iterable[IntWritable], context:Reducer[Text,IntWritable,Text,IntWritable]#Context): Unit = {
      var result:Int = 0

      import scala.collection.JavaConversions._
    for (value  <- values){
      result += value.get

    }
   sum.set(result)
    context.write(key,sum)
  }

  @throws[Exception]
  def main(args: Array[String]): Unit = {
    val conf :Configuration=new Configuration()
    conf.set("","")

    val job:Job=Job.getInstance(conf,"")
    job.setJar("")
    job.setMapperClass(classOf[appMap])
    job.setCombinerClass(classOf[appReduce])
    job.setReducerClass(classOf[appReduce])
    job.setOutputKeyClass(classOf[Text])
    job.setOutputValueClass(classOf[IntWritable])
    FileInputFormat.addInputPath(job,new Path(args[0]))

    FileOutputFormat.setOutputPath(job,new Path(args[1]))
    System.exit( if (job.waitForCompletion(true)) 1 else 0)
  }
}
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;

import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import java.io.IOException;
import java.util.StringTokenizer;

/**
 * Created by linkedmemuller on 2017/9/15.
 */
public class Applist {

    public static class AppMap extends Mapper<Object,Text,Text,IntWritable> {

        private static final IntWritable one =new IntWritable(1);
        private Text word =new Text();
        public void map(Object key,Text value ,Context context)throws IOException,InterruptedException{

            StringTokenizer sto=new StringTokenizer(value.toString());
            if (sto.hasMoreTokens()){
                word.set(sto.nextToken());
                context.write(word,one);
            }
        }

    }
    public  static  class AppReduce extends Reducer<Text,IntWritable,Text,IntWritable> {

        private  IntWritable sum=new IntWritable();
        public void reduce(Text key ,Iterable<IntWritable> values,Context context)throws IOException,  InterruptedException{

            Integer result =0;
            for (IntWritable  vals:values){
                result+=vals.get();
            }
            sum.set(result);
            context.write(key,sum);

        }
    }

    public static  void main (String [] args) throws  Exception{
        Configuration conf=new Configuration();
        conf.set("","");
        Job job= Job.getInstance(conf,"");
        job.setJar("");
        job.setMapperClass(AppMap.class);
        job.setCombinerClass(AppReduce.class);
        job.setReducerClass(AppReduce.class);
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(IntWritable.class);
        FileInputFormat.addInputPath(job,new Path( args[0]));
        FileOutputFormat.setOutputPath(job,new Path(args[1]));
        System.exit(job.waitForCompletion(true)?0:1);
    }
}

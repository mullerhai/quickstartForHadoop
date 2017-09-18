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
 * Created by linkedmemuller on 2017/9/17.
 */
public class ApplistCount {

    public static class AppMap extends Mapper<Object, Text, Text, IntWritable> {

        private Text word = new Text();
        private static final IntWritable one = new IntWritable();


        @Override
        protected void map(Object key, Text value, Context context)
                throws IOException, InterruptedException {
            StringTokenizer str = new StringTokenizer(value.toString());
            if (str.hasMoreTokens()) {
                word.set(str.nextToken());
                context.write(word, one);
            }

        }
    }

    public static class AppReduce extends Reducer<Text, IntWritable,
            Text, IntWritable> {
        private IntWritable count = new IntWritable();

        @Override
        protected void reduce(Text key, Iterable<IntWritable> values,
                              Context context) throws IOException,
                InterruptedException {
            int sum = 0;

            for (IntWritable val : values) {
                sum += val.get();

            }
            count.set(sum);
            context.write(key, count);

        }
    }
        public static void main(String[] args)throws IOException,
                ClassNotFoundException,InterruptedException {

            Configuration conf=new Configuration();
            conf.set("mapreduce.cluster.local.dir","/home/$user/Documents/hadoopDir");
            Job job=Job.getInstance(conf,"Applistcount");
            job.setJar("ApplistCount");
            job.setMapperClass(AppMap.class);
            job.setCombinerClass(AppReduce.class);
            job.setReducerClass(AppReduce.class);
            job.setOutputKeyClass(Text.class);
            job.setOutputValueClass(IntWritable.class);
            FileInputFormat.addInputPath(job,new Path(args[0]));
            FileOutputFormat.setOutputPath(job,new Path(args[1]));
            System.exit(job.waitForCompletion(true)?1:0);

        }

}

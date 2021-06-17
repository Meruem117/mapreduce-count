package log;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

public class LogJobMain {
    public static void main(String[] args) throws Exception {

        // Windows
        System.setProperty("HADOOP_USER_NAME", "meru");

        // Linux
//        if (args.length < 2) {
//            System.out.println("Usage:hadoop jar Log.jar " + LogJobMain.class.getName() + " input ouput");
//            System.exit(0);
//        }

        Configuration configuration = new Configuration();
        Job job = Job.getInstance(configuration);
        job.setJarByClass(LogJobMain.class);

        job.setMapperClass(LogMapper.class);
        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(Text.class);

        job.setReducerClass(LogReducer.class);
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(Text.class);

        FileSystem fileSystem = FileSystem.get(configuration);

        // Windows
        Path inputPath = new Path("./input/log.log");
        Path outputPath = new Path("./output/");

        // Linux
//        Path inputPath = new Path(args[0]);
//        Path outputPath = new Path(args[1]);

        if (fileSystem.exists(outputPath)) {
            fileSystem.delete(outputPath, true);
        }

        // Windows
        FileInputFormat.setInputPaths(job, inputPath);
        FileOutputFormat.setOutputPath(job, outputPath);

        // Linux
//        FileInputFormat.setInputPaths(job, new Path(args[0]));
//        FileOutputFormat.setOutputPath(job, new Path(args[1]));

        boolean completion = job.waitForCompletion(true);
        System.exit(completion ? 0 : -1);
    }
}

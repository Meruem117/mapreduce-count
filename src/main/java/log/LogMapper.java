package log;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;

public class LogMapper extends Mapper<LongWritable, Text, Text, Text> {

    @Override
    protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
        String[] s = value.toString().split("\\s+");
        int len = s.length;
        if (len == 8 && s[len - 2].equals("from")) {
            String[] video = s[len - 3].split(":");
            // up
            String author = video[0];
            context.write(new Text(author), new Text("1,1"));
            // bvid
            String bv = video[1];
            context.write(new Text(bv), new Text("2,1"));
            // city
            String city = s[len - 1];
            context.write(new Text(city), new Text("3,1"));
        }
    }
}

package log;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;

public class LogMapper extends Mapper<LongWritable, Text, Text, Text> {

    @Override
    protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
        String[] str = value.toString().split("\\s+");
        int len = str.length;
        if (len == 8 && str[len - 2].equals("from")) {
            String[] video = str[len - 3].split(":");
            // up
            String author = video[0];
            context.write(new Text(author), new Text("1,1"));
            // bvid
            String bv = video[1];
            context.write(new Text(bv), new Text("2,1"));
            // city
            String city = str[len - 1];
            context.write(new Text(city), new Text("3,1"));
        }
    }
}

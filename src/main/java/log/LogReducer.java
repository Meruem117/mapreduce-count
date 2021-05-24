package log;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;

public class LogReducer extends Reducer<Text, Text, Text, Text> {

    @Override
    protected void reduce(Text key, Iterable<Text> values, Context context) throws IOException, InterruptedException {
        int sum = 0;
        String type = "0";
        String text = "";
        for (Text v : values) {
            String[] s = v.toString().split(",");
            if (sum == 0) {
                type = s[0];
            }
            int n = Integer.parseInt(s[1]);
            sum += n;
        }
        text = type + "\t" + sum;
        context.write(key, new Text(text));
    }
}

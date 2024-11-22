package log;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

public class LogReducer extends Reducer<Text, Text, Text, Text> {
    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
    String date = df.format(new Date());
    int sumTotal = 0;
    int sumLogin = 0;
    int sumNotLogin = 0;

    @Override
    protected void reduce(Text key, Iterable<Text> values, Context context) throws IOException, InterruptedException {
        int sum = 0;
        String type = "0";
        for (Text value : values) {
            String[] str = value.toString().split(",");
            if (sum == 0) {
                type = str[0];
            }
            int n = Integer.parseInt(str[1]);
            sum += n;

            if (type.equals("3")) {
                sumTotal++;
                if (!key.toString().equals("暂无")) {
                    sumLogin++;
                } else {
                    sumNotLogin++;
                }
            }
        }

        String text = type + "\t" + sum + "\t" + date;
        context.write(key, new Text(text));
    }

    @Override
    protected void cleanup(Context context) throws IOException, InterruptedException {
        context.write(new Text("访问量"), new Text("4" + "\t" + sumTotal + "\t" + date));
        context.write(new Text("登录"), new Text("4" + "\t" + sumLogin + "\t" + date));
        context.write(new Text("未登录"), new Text("4" + "\t" + sumNotLogin + "\t" + date));
    }
}
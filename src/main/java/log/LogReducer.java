package log;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

public class LogReducer extends Reducer<Text, Text, Text, Text> {

    ArrayList<String> list = new ArrayList<String>();
    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
    String date = df.format(new Date());

    @Override
    protected void reduce(Text key, Iterable<Text> values, Context context) throws IOException, InterruptedException {
        int sum = 0;
        String type = "0";
        for (Text v : values) {
            String[] s = v.toString().split(",");
            if (sum == 0) {
                type = s[0];
            }
            int n = Integer.parseInt(s[1]);
            sum += n;
        }
        if (type.equals("2")) {
            String a = key + "," + sum;
            list.add(a);
        } else {
            String text = type + "\t" + sum + "\t" + date;
            context.write(key, new Text(text));
        }
    }

    @Override
    protected void cleanup(Context context) throws IOException, InterruptedException {

        Collections.sort(list, new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                int n1 = Integer.parseInt(o1.split(",")[1]);
                int n2 = Integer.parseInt(o2.split(",")[1]);
                if (n1 < n2) {
                    return 1;
                } else {
                    return -1;
                }
            }
        });

        int len = Math.min(list.size(), 40);
        for (int i = 0; i < len; i++) {
            String[] str = list.get(i).split(",");
            String text = "2" + "\t" + str[1] + "\t" + date;
            context.write(new Text(str[0]), new Text(text));
        }
    }

}
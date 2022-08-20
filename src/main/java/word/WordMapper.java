package word;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;
import java.util.StringTokenizer;

public class WordMapper extends Mapper<Object, Text, Text, IntWritable> {
    public static Text word = new Text();

    @Override
    protected void map(Object key, Text value, Context context) throws IOException, InterruptedException  //抛出异常
    {
        StringTokenizer tokenizer = new StringTokenizer(value.toString(), "\t");
        word.set(tokenizer.nextToken());
        context.write(word, new IntWritable(1));
    }
}

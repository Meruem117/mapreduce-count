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
        //StringTokenizer是Java工具包中的一个类，用于将字符串进行拆分
        word.set(tokenizer.nextToken());
        //返回当前位置到下一个分隔符之间的字符串
        context.write(word, new IntWritable(1));
        //将word存到容器中，记一个数
    }
}

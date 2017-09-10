package com.orienit.hadoop.training;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;

public class WordCountJob implements Tool {
	// Declare Configuration Object
	private Configuration conf;

	@Override
	public Configuration getConf() {
		return conf; // Getting the configuration
	}

	@Override
	public void setConf(Configuration conf) {
		this.conf = conf; // Setting the configuration
	}

	@Override
	public int run(String[] args) throws Exception {

		// initializing the job object with configuration
		Job job = new Job(getConf());

		// setting the job name
		job.setJobName("Orien IT WordCount Job");

		// Set the Jar by finding where a given class came from
		job.setJarByClass(this.getClass());

		// setting custom mapper class
		job.setMapperClass(WordCountMapper.class);

		// setting custom reducer class
		job.setReducerClass(WordCountReducer.class);

		// setting custom combiner class
		// job.setCombinerClass(WordCountCombiner.class);

		// setting no of reducers
		// job.setNumReduceTasks(6);

		// setting custom partitioner class
		// job.setPartitionerClass(WordCountPartitioner.class);

		// setting mapper output key class: K2
		job.setMapOutputKeyClass(Text.class);

		// setting mapper output value class: V2
		job.setMapOutputValueClass(LongWritable.class);

		// setting final output key class: K3
		job.setOutputKeyClass(Text.class);

		// setting final output value class: V3
		job.setOutputValueClass(LongWritable.class);

		// setting the input format class ,i.e for K1, V1
		job.setInputFormatClass(TextInputFormat.class);

		// setting the output format class
		job.setOutputFormatClass(TextOutputFormat.class);

		// define the input & output paths
		Path input = new Path(args[0]);
		Path output = new Path(args[1]);

		// setting the input path
		FileInputFormat.addInputPath(job, input);

		// setting the output path
		FileOutputFormat.setOutputPath(job, output);

		// delete the output path if exists
		output.getFileSystem(conf).delete(output, true);

		// to execute the job and return the status
		return job.waitForCompletion(true) ? 0 : -1;

	}

	public static void main(String[] args) throws Exception {
		// if `status == 0` then `Job Success`
		// if `status == -1` then `Job Failure`
		int status = ToolRunner.run(new Configuration(), new WordCountJob(), args);

		System.out.println("Job Status: " + status);
	}
}














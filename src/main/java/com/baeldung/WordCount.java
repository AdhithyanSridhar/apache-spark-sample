package com.baeldung;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

import org.apache.spark.SparkConf;
import org.apache.spark.SparkContext;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;

import scala.Tuple2;

public class WordCount {

	private static final Pattern SPACE = Pattern.compile(" ");

	public static void main(String[] args) throws Exception {

		// if (args.length < 1) {
		// System.err.println("Usage: JavaWordCount <file>");
		// System.exit(1);
		// }
		SparkConf sparkConf = new SparkConf().setAppName("JavaWordCount")
				.setMaster("local");

		try (JavaSparkContext ctx = new JavaSparkContext(sparkConf)) {
			Thread.sleep(10000);
			SparkContext sc = ctx.sc();
			JavaRDD<String> lines = ctx.textFile(
					"C:\\Users\\sriba\\Downloads\\tutorials-master\\apache-spark\\src\\main\\resources\\spark_example.txt",
					1);

			JavaRDD<String> words = lines
					.flatMap(s -> Arrays.asList(SPACE.split(s)).iterator());

			JavaPairRDD<String, Integer> wordAsTuple = words
					.mapToPair(word -> new Tuple2<>(word, 1));

			JavaPairRDD<String, Integer> wordWithCount = wordAsTuple
					.reduceByKey((Integer i1, Integer i2) -> i1 + i2);

			List<Tuple2<String, Integer>> output = wordWithCount.collect();

			for (Tuple2<?, ?> tuple : output) {
				System.out.println(tuple._1() + ": " + tuple._2());
			}

			Thread.sleep(2000000);

			ctx.close();
			ctx.stop();
		}
	}
}

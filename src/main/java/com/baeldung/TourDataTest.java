package com.baeldung;

import java.util.HashMap;

import org.apache.spark.sql.Column;
import org.apache.spark.sql.DataFrameReader;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Encoders;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;

public class TourDataTest {

	public static void main(String[] args) {

		SparkSession sparkSession = SparkSession.builder().appName("tour-test")
				.master("local[*]").getOrCreate();

		DataFrameReader dataFrameReader = sparkSession.read();

		Dataset<Row> data = dataFrameReader.option("header", "true")
				.csv("data/Tourist.csv");

		data.select("country", "year", "value").show(10);

		/*
		 * To map each of our records to the specified type we will need to use an
		 * Encoder. Encoders translate between Java objects and Spark's internal binary
		 * format:
		 */

		Dataset<Row> dataset = data.select(new Column("region"), new Column("country"),
				new Column("year"), new Column("series"),
				new Column("value").cast("double"), new Column("footnotes"),
				new Column("source"));

		Dataset<TouristData> typedDataset = dataset.as(Encoders.bean(TouristData.class));

		// show norway only
		typedDataset.filter(row -> row.getCountry().equals("Norway")).show(false);

		// groupBy country
		typedDataset.groupBy(typedDataset.col("country")).count().show(false);

		// filter by year
		typedDataset.filter(record -> record.getYear() != null
				&& (Long.valueOf(record.getYear()) > 2010
						&& Long.valueOf(record.getYear()) < 2017))
				.show();

		// aggregation
		HashMap<String, String> hashMap = new HashMap<String, String>();
		hashMap.put("value", "sum");
		typedDataset
				.filter(record -> record.getValue() != null
						&& record.getSeries().contains("expenditure"))
				.groupBy("year").agg(hashMap).show();

		System.out.println(typedDataset.logicalPlan().toString());

		sleep();
	}

	private static void sleep() {
		try {
			Thread.sleep(200000);
		}
		catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}

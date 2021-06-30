package com.baeldung;

public class TouristData {
	private String region;
	private String country;
	private String year;
	private String series;
	private Double value;
	private String footnotes;
	private String source;

	// ... getters and setters
	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public String getSeries() {
		return series;
	}

	public void setSeries(String series) {
		this.series = series;
	}

	public Double getValue() {
		return value;
	}

	public void setValue(Double value) {
		this.value = value;
	}

	public String getFootnotes() {
		return footnotes;
	}

	public void setFootnotes(String footnotes) {
		this.footnotes = footnotes;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}
}
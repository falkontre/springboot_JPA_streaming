package com.example.demo.csv;

import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.stream.Stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.example.demo.entity.City;
import com.opencsv.CSVWriter;
import com.opencsv.bean.ColumnPositionMappingStrategy;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import com.opencsv.exceptions.CsvException;

public class CityCsvWriter {

	private static final Logger LOGGER = LoggerFactory.getLogger(CityCsvWriter.class);
	private ColumnPositionMappingStrategy<City> mapStrategy;
	private StatefulBeanToCsv<City> bean2Csv;

	public CityCsvWriter(Writer writer) {
		this.mapStrategy = new ColumnPositionMappingStrategy<>();
		mapStrategy.setType(City.class);
		String[] columns = new String[] { "name", "id", "population" };
		mapStrategy.setColumnMapping(columns);
		bean2Csv = new StatefulBeanToCsvBuilder<City>(writer).withQuotechar(CSVWriter.NO_QUOTE_CHARACTER)
				.withMappingStrategy(mapStrategy).withSeparator(',').build();
	}

	public CityCsvWriter(OutputStream out) {
		OutputStreamWriter writer = new OutputStreamWriter(out);
		this.mapStrategy = new ColumnPositionMappingStrategy<>();
		mapStrategy.setType(City.class);
		String[] columns = new String[] { "name", "id", "population" };
		mapStrategy.setColumnMapping(columns);
		bean2Csv = new StatefulBeanToCsvBuilder<City>(writer).withQuotechar(CSVWriter.NO_QUOTE_CHARACTER)
				.withMappingStrategy(mapStrategy).withSeparator(',').build();
	}

	public void writeCities(Stream<City> cities) {
		try {
			bean2Csv.write(cities);
		} catch (CsvException ex) {
			LOGGER.error("Error mapping Bean to CSV", ex);
		}
	}

	public void writeCity(City city) {
		try {
			bean2Csv.write(city);
		} catch (CsvException ex) {
			LOGGER.error("Error mapping Bean to CSV", ex);
		}

	}
}
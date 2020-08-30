package com.example.demo.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.stream.Stream;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.csv.CityCsvWriter;
import com.example.demo.entity.City;
import com.example.demo.repository.CityRepository;

@RestController
@RequestMapping("/api")
public class DownloadController {

	@Autowired
	CityRepository repo;

	@PersistenceContext
	EntityManager entityManager;

	private CityCsvWriter csvWriter;

	private final Logger LOGGER = LoggerFactory.getLogger(DownloadController.class);

	@Transactional(readOnly = true) // this is important, because Streams can only be opened in a transaction
	@GetMapping(value = "/download", produces = MediaType.APPLICATION_JSON_VALUE)
	public void download(final HttpServletResponse response) {

		response.setContentType("text/csv");
		response.setHeader("Content-Disposition", "attachment;filename=output.csv");
		response.setCharacterEncoding("UTF-8");
		try (Stream<City> todoStream = repo.findAllByStream()) {
			PrintWriter writer = response.getWriter();
			csvWriter = new CityCsvWriter(writer);
			todoStream.forEach(city -> {
				csvWriter.writeCity(city);
				entityManager.detach(city);
			});
			writer.flush();
		} catch (IOException e) {
			LOGGER.info("Exception occurred " + e.getMessage(), e);
			throw new RuntimeException("Exception occurred while exporting results", e);
		}
	}
}
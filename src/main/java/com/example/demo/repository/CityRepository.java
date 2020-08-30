package com.example.demo.repository;

import java.util.stream.Stream;

import javax.persistence.QueryHint;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;

import com.example.demo.entity.City;

public interface CityRepository extends JpaRepository<City, Long> {

	@QueryHints(value = @QueryHint(name = org.hibernate.jpa.QueryHints.HINT_FETCH_SIZE, value = "" + Integer.MIN_VALUE))
	@Query(value = "select * from city ct", nativeQuery = true)
	Stream<City> findAllByStream();

}

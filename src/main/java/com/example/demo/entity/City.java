package com.example.demo.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "city")
@AllArgsConstructor
@NoArgsConstructor
public class City {

	@Id
	private Long id;
	@Column(name = "Name")
	private String name;
	@Column(name = "CountryCode")
	private String countryCode;
	@Column(name = "District")
	private String district;
	@Column(name = "Population")
	private Integer population;

}

package org.ybi.cinema.entities;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
@Entity
@Data @AllArgsConstructor @NoArgsConstructor @ToString

public class Projection implements Serializable {
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id ;
	private Date dateProjection;
	private Double prix;
	@ManyToOne
	@JsonProperty(access = Access.WRITE_ONLY)
	private Salle salle;
	@ManyToOne
	private Film film;
	@OneToMany(mappedBy = "projection")
	@JsonProperty(access = Access.WRITE_ONLY)

	private Collection<Ticket> tickets;
	@ManyToOne
	private Seance seance;

	
	

}

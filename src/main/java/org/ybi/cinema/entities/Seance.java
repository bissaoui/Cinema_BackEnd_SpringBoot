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
import jakarta.persistence.OneToMany;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Data @AllArgsConstructor @NoArgsConstructor @ToString

public class Seance implements Serializable{
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id ;
	@Temporal(TemporalType.TIME)
	private Date heureDebut;
	@OneToMany(mappedBy = "seance")
	@JsonProperty(access = Access.WRITE_ONLY)
	private Collection<Projection> projections;
	
	
	

}

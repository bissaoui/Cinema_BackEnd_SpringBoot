package org.ybi.cinema.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.ybi.cinema.entities.Cinema;
import org.ybi.cinema.entities.Salle;
@RepositoryRestResource
public interface SalleRepository extends JpaRepository<Salle, Long> {

}

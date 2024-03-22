package org.ybi.cinema.controller;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import javax.print.attribute.standard.Media;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.ybi.cinema.dao.FilmRepository;
import org.ybi.cinema.dao.TicketRepository;
import org.ybi.cinema.entities.Film;
import org.ybi.cinema.entities.Ticket;

import jakarta.transaction.Transactional;
import lombok.Data;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;



@RestController
public class CinemaRestController  {
	
	@Autowired
	private FilmRepository filmRepository ;
	@Autowired
	private TicketRepository ticketRepository;
	
	
	@GetMapping(path = "/imageFilm/{id}", produces = MediaType.IMAGE_JPEG_VALUE)
	public byte [] getFilmImage(@PathVariable (name="id") Long id ) throws Exception {
		
		Film film = filmRepository.findById(id).get();
		String image = film.getPhoto();
		File file = new File(System.getProperty("user.home")+"/cinema/images/"+image );
		Path path = Paths.get(file.toURI());
		
		return Files.readAllBytes(path);
	}
	
	@PostMapping("/payerTickets")
	@Transactional
	public List<Ticket> payerTickets(@RequestBody TicketForm ticketForm) {
		
		List<Ticket> list = new ArrayList<>();
		ticketForm.getTickets().forEach(t->{
			Ticket ticket = ticketRepository.findById(t).get();
			ticket.setReservee(true);
			ticket.setNomClient(ticketForm.getNomClient());
			ticket.setCodePayement(ticketForm.getCodePayement());
			System.out.println("formticket : "+ticketForm.getCodePayement());
			ticketRepository.save(ticket);
			System.out.println("ticket : "+ticket.getCodePayement());

			list.add(ticket);
			
		});
		
		
		return list;
	}
	
	
	
	
}



@Data
class TicketForm{
	private String nomClient;
	private int codePayement ;
	private List<Long> tickets = new ArrayList<>();
}



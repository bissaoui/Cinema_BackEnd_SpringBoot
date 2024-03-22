package org.ybi.cinema.service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.stream.Stream;

import org.antlr.v4.runtime.misc.ParseCancellationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.ybi.cinema.dao.CategorieRepository;
import org.ybi.cinema.dao.CinemaRepository;
import org.ybi.cinema.dao.FilmRepository;
import org.ybi.cinema.dao.PlaceRepository;
import org.ybi.cinema.dao.ProjectionRepository;
import org.ybi.cinema.dao.SalleRepository;
import org.ybi.cinema.dao.SeanceRepository;
import org.ybi.cinema.dao.TicketRepository;
import org.ybi.cinema.dao.VilleRepository;
import org.ybi.cinema.entities.Categorie;
import org.ybi.cinema.entities.Cinema;
import org.ybi.cinema.entities.Film;
import org.ybi.cinema.entities.Place;
import org.ybi.cinema.entities.Projection;
import org.ybi.cinema.entities.Salle;
import org.ybi.cinema.entities.Seance;
import org.ybi.cinema.entities.Ticket;
import org.ybi.cinema.entities.Ville;

import jakarta.transaction.Transactional;


@Service
@Transactional
public class CinemaServiceImpl implements ICinemaInitService {

	@Autowired
	private VilleRepository villeRepository;
	
	@Autowired
	private CinemaRepository cinemaRepository;

	@Autowired
	private SalleRepository salleRepository ;

	@Autowired
	private PlaceRepository placeRepository;

	@Autowired
	private FilmRepository filmRepository;
	
	@Autowired
	private SeanceRepository seanceRepository;
	
	@Autowired
	private CategorieRepository categorieRepository;
	
	@Autowired
	private TicketRepository ticketRepository;
	
	@Autowired
	private ProjectionRepository projectionRepository;





	@Override
	public void initVilles() {

		Stream.of("Casablanca", "Rabat", "Marrakech", "Fès", "Tanger", "Agadir", 
				"Safi").forEach(nameVille->{
					Ville ville = new Ville();
					ville.setName(nameVille);
					villeRepository.save(ville);
				});		
	}

	@Override
	public void initCinemas() {
		villeRepository.findAll().forEach(ville->{
			Stream.of("Megarama", "IMAX", "Cinema Colisée").forEach(nomCinema->{
						Cinema cinema = new Cinema();
						cinema.setName(nomCinema);
						cinema.setVille(ville);
						cinema.setNombreSalles(3 +(int)(Math.random()*5));
						cinemaRepository.save(cinema);
			});
		});
	}

	@Override
	public void initSalles() {
		cinemaRepository.findAll().forEach(c->{
			
			for (int i =1 ; i<=c.getNombreSalles();i++) {
				
				Salle salle = new  Salle();
				salle.setCinema(c);
				salle.setName("Salle"+i);
				salle.setNombrePlaces(10 +(int)(Math.random()*15));
				salleRepository.save(salle);

			}
			
		});
		
	}

	@Override
	public void initSeances() {
		DateFormat dateFormat = new SimpleDateFormat("HH:mm");
		Stream.of("12:00", "13:30", "15:00", "17:00", "19:30", "21:00", "22:30").forEach(s->{
			Seance seance = new Seance();
			try {
				seance.setHeureDebut(dateFormat.parse(s));
				seanceRepository.save(seance);

			}catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
		});;

		
	}

	@Override
	public void initPlaces() {
		salleRepository.findAll().forEach(s->{
			
			for (int i =1 ; i<=s.getNombrePlaces();i++) {
				
				Place place = new  Place();
				place.setSalle(s);
				place.setNumero(i);
				placeRepository.save(place);

			}
			
		});

		
		
	}

	@Override
	public void initCategories() {
		// TODO Auto-generated method stub
		Stream.of("Action", "Comédie", "Drame", "Science-fiction",
				"Horreur", "Romance", "Thriller",
				"Animation", "Documentaire", "Aventure").forEach(nameCategorie->{
					Categorie categorie = new Categorie();
					categorie.setName(nameCategorie);
					categorieRepository.save(categorie);
				});		

	}

	@Override
	public void initFilms() {
		// TODO Auto-generated method stub
		double [] durees = new double[] {1,1.5,2,2.5,3};
		List<Categorie> categories = categorieRepository.findAll();
		Stream.of("Le Parrain", "Inception", "La La Land", "Forrest Gump", "Titanic", 
				"Interstellar", "Pulp Fiction", "Fight Club", "Le Seigneur des Anneaux").forEach(titrefilm->{
					 Film film = new Film();
					 film.setTitre(titrefilm);
					 film.setDuree(durees[new Random().nextInt(durees.length)]);
					 film.setPhoto(titrefilm.replace(" ", "")+".jpg");
					 film.setCategorie(categories.get(new Random().nextInt(categories.size())));
					 
					 filmRepository.save(film); 
				});		
	}

	@Override
	public void initProjections() {
		double [] prices = new double[]{35,70,120,140,50};
		villeRepository.findAll().forEach(ville->{
			ville.getCinemas().forEach(cinema->{
				cinema.getSalles().forEach(salle->{
					filmRepository.findAll().forEach(film->{
						seanceRepository.findAll().forEach(seance->{
							Projection projection = new Projection();
							projection.setPrix(prices[new Random().nextInt(prices.length)]);
							projection.setSalle(salle);
							projection.setFilm(film);
							projection.setSeance(seance);
							projectionRepository.save(projection);
						});
					});
				});
			});
		});
	}

	@Override
	public void initTickets() {
		projectionRepository.findAll().forEach(projection->{
			projection.getSalle().getPlaces().forEach(place->{
				
				Ticket ticket = new Ticket();
				ticket.setPlace(place);
				ticket.setProjection(projection);
				ticket.setPrix(projection.getPrix());
				ticket.setReservee(false);
				ticketRepository.save(ticket);
				
			});			
		});
	}

}

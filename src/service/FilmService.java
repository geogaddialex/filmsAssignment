package service;
import model.Film;
import java.util.ArrayList;

import dao.FilmDAO;

public class FilmService {
	
	private FilmDAO dao = new FilmDAO();

	public ArrayList<Film> getAllFilms(){ 
		
	      return dao.getAllFilms(); 
	      
	}  
	
	public ArrayList<Film> getFilmsMatching(String search){ 
		
	      return dao.getFilmsMatching( search ); 
	      
	}
	
	public Film getFilmByID(int ID){ 
		
	      return dao.getFilmByID(ID); 
	      
	}  
	
	public int addFilm(String title, int year, String director, String stars, String review){ 
		
	      return dao.addFilm(title, year, director, stars, review ); 
	      
	}  
	
	public int addFilm(Film film){ 
		
	      return dao.addFilm(film); 
	      
	}  
	
	public int deleteFilmByID(int ID){ 
		
	      return dao.removeFilmByID(ID); 
	      
	}  
}

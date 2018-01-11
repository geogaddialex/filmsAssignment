package dao;

import java.util.ArrayList;

import model.Film;

import java.sql.*;

public class FilmDAO {
	
	Film oneFilm = null;
	Connection conn = null;

	public FilmDAO() {}
	
	private void openConnection(){
		
		try{
			
		    Class.forName("com.mysql.jdbc.Driver");
		    conn = DriverManager.getConnection("jdbc:mysql://mudfoot.doc.stu.mmu.ac.uk:3306/butterwa?user=butterwa&password=grespteR4");
		    
		}catch(Exception e){ 
			
			System.out.println(e);
			
		}

    }
	
	private void closeConnection(){
		
		try {
			
			conn.close();
			
		}catch (SQLException e){
			
			e.printStackTrace();
			
		}
	}

	private Film getNextFilm(ResultSet rs){
		
	    	Film thisFilm = null;
			
	    	try {
			thisFilm = new Film(
				rs.getInt("id"),
				rs.getString("title"),
				rs.getInt("year"),
				rs.getString("director"),
				rs.getString("stars"),
				rs.getString("review")
			);
			
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
	    	
	    	return thisFilm;		
	}
	
   public ArrayList<Film> getAllFilms(){
	   
		ArrayList<Film> allFilms = new ArrayList<Film>();
		openConnection();
		
		try{
			PreparedStatement statement = conn.prepareStatement("select * from films"); 
			ResultSet rs = statement.executeQuery();

		    while( rs.next() ){
			    	oneFilm = getNextFilm( rs );
			    	allFilms.add( oneFilm );
			}

		    closeConnection();
		    
		} catch(SQLException e) { 
			
			System.out.println(e); 
			
		}

	   return allFilms;
   }

   public ArrayList<Film> getFilmsMatching(String title){
	   
	   	ArrayList<Film> films = new ArrayList<Film>();
		openConnection();
		
		try{
			
			PreparedStatement statement = conn.prepareStatement("select * from films where title like ?");    
			statement.setString(1, "%"+title+"%");    
			ResultSet rs = statement.executeQuery();

		    while( rs.next() ){
		    		oneFilm = getNextFilm( rs );
		    		films.add( oneFilm );
		    }

		} catch(SQLException se) { 
			
			System.out.println(se);
			
		} finally {
			
			closeConnection();
		}

	   return films;
   } 
   

	public Film getFilmByID(int ID) {
		
		Film film = null;
		openConnection();
		
		try{
			
			PreparedStatement statement = conn.prepareStatement("select * from films where id = ?");    
			statement.setInt(1, ID);    
			ResultSet rs = statement.executeQuery();

			try {
				film = new Film(
					rs.getInt("id"),
					rs.getString("title"),
					rs.getInt("year"),
					rs.getString("director"),
					rs.getString("stars"),
					rs.getString("review")
				);
				
			} catch (SQLException e) {
				
				e.printStackTrace();
			}

		} catch(SQLException se) { 
			
			System.out.println(se);
			
		} finally {
			
			closeConnection();
		}
		
		return film;
	}
   
   public int addFilm(Film film) {
	   
	   	openConnection();
	   
		String title = film.getTitle();
		int year = film.getYear();
		String director = film.getDirector();
		String stars = film.getStars();
		String review = film.getReview();
		
	   try{
		   
		    PreparedStatement statement = conn.prepareStatement("insert into films (title, year, director, stars, review) values (?,?,?,?,?)");    
			statement.setString(1, title);
			statement.setInt(2, year);   
			statement.setString(3, director);   
			statement.setString(4, stars);   
			statement.setString(5, review);   
						
		    int success = statement.executeUpdate();

		    return success;
		    
		} catch(SQLException e) { 
			
			System.out.println(e);
			return 0;
			
		} finally {
			
			closeConnection();
		}
	   
   }
   
   public int addFilm(String title, int year, String director, String stars, String review) {
	   
	   openConnection();
	   
	   try{
		    PreparedStatement statement = conn.prepareStatement("insert into films (title, year, director, stars, review) values (?,?,?,?,?)");   
		    statement.setString(1, title);
			statement.setInt(2, year);   
			statement.setString(3, director);   
			statement.setString(4, stars);   
			statement.setString(5, review);   
			
			int success = statement.executeUpdate();

		    return success;
		    
		} catch(SQLException e) { 
			
			System.out.println(e);
			return 0;
			
		} finally {
			
		    closeConnection();

		}
	   
   }

	public int removeFilmByID(int ID) {
		openConnection();
		   
		   try{
			    PreparedStatement statement = conn.prepareStatement("delete from films where id = ?");   
			    statement.setInt(1, ID);
				int success = statement.executeUpdate();

			    return success;
			    
			} catch(SQLException e) { 
				
				System.out.println(e);
				return 0;
				
			} finally {
				
			    closeConnection();

			}
		   
	}

   
}

package assignment;

import java.util.ArrayList;
import java.sql.*;

public class FilmDAO {
	
	Film oneFilm = null;
	Connection conn = null;
    Statement stmt = null;

	public FilmDAO() {}
	
	private void openConnection(){
		try{
		    Class.forName("com.mysql.jdbc.Driver");
		}catch(Exception e){ 
			System.out.println(e);
		}

		try{
		    conn = DriverManager.getConnection("jdbc:mysql://localhost/enterprise","root","");
		    //mudfoot.doc.stu.mmu.ac.uk
		    stmt = conn.createStatement();
		}catch(SQLException se){
			System.out.println(se);
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
		    String selectSQL = "select * from films";
		    System.out.println( selectSQL );
		    ResultSet rs = stmt.executeQuery(selectSQL);

		    while( rs.next() ){
			    	oneFilm = getNextFilm( rs );
			    	allFilms.add( oneFilm );
			}

		    stmt.close();
		    closeConnection();
		} catch(SQLException se) { 
			System.out.println(se); 
		}

	   return allFilms;
   }

   public ArrayList<Film> getFilm(String title){
	   
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

		    stmt.close();
		    closeConnection();
		    
		} catch(SQLException se) { System.out.println(se); }

	   return films;
   } 
   
   public int addFilm(Film film) {
	   
	   openConnection();
	   
		String title = film.getTitle();
		int year = film.getYear();
		String director = film.getDirector();
		String stars = film.getStars();
		String review = film.getReview();
		
	   try{
		    String insertSQL = "insert into films (title, year, director, stars, review) values (\"" + title +"\", " + year +", \"" + director + "\", \"" + stars +"\", \"" + review + "\")";
		    int numRows = stmt.executeUpdate(insertSQL);
		    
		    String message = numRows == 1? "Film added successfully" : "Film not added"; 
		    	System.out.println( message );

		    stmt.close();
		    closeConnection();
		    
		    return numRows;
		} catch(SQLException se) { 
			System.out.println(se);
			return 0;
		}
	   
   }
   
}

package controller;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import dao.FilmDAO;
import model.Film;

@WebServlet("/Films")
public class FilmController extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	FilmDAO dao = new FilmDAO(); 

    public FilmController() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
		
		ArrayList<Film> films = null;
		RequestDispatcher view = null;
		String action = request.getParameter("action") == null ? "none" : request.getParameter("action");
		String format = request.getParameter("format") == null ? "json" : request.getParameter("format");
        String search = request.getParameter("search") == null ? "" : request.getParameter("search");
				
		if( action.equals("getFilm") ){
			
	        films = dao.getFilmsMatching(search);
			
		}else if( action.equals("getAllFilms") ){

			films = dao.getAllFilms();
			
		}else{
			
//			Film noFilm = new Film();
//			request.setAttribute("films", noFilm);
			System.out.println("Not a valid action: " + action);
		}
		
        request.setAttribute("films", films);	
		
		switch( format ){
			case "json": view = request.getRequestDispatcher("results-json.jsp");
			break;
			case "xml": view = request.getRequestDispatcher("results-xml.jsp");
			break;
			case "text": view = request.getRequestDispatcher("results-text.jsp");
			break;
			default: view = request.getRequestDispatcher("results-json.jsp");
			break;
		}
		
	    view.forward(request, response); 
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Film film = null;
		String type = request.getContentType();
			
        if( type.equals("application/json") ){
        	
        		try{
        			
                film = new Gson().fromJson( request.getReader(), Film.class );
                
            }catch(JsonSyntaxException e){
            	
                e.printStackTrace();
            }
        		
        }else if( type.equals("text/xml") ){
        	        	
	        	try { 
	        		
	        		XmlMapper mapper = new XmlMapper();
	        		film = mapper.readValue( request.getReader(), Film.class );
	        		
	         } catch(Exception e) { 
	        	 
	        		e.printStackTrace(); 
	         } 
        	
        }else {
        	
			String title = request.getParameter("title") == null ? "" : request.getParameter("title");;
			String director = request.getParameter("director") == null ? "" : request.getParameter("director");;
			String stars = request.getParameter("stars") == null ? "" : request.getParameter("stars");;
			String review = request.getParameter("review") == null ? "" : request.getParameter("review");;
			int id = request.getParameter("id") == null ? 0 : Integer.parseInt(request.getParameter("id"));
			int year = request.getParameter("year") == null ? 0 : Integer.parseInt(request.getParameter("year"));
			
			film = new Film(id, title, year, director, stars, review);
        }
        
        dao.addFilm( film );		
	}

}

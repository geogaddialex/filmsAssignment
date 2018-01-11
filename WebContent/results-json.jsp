<%@ page import="java.util.List" %>
<%@ page import="assignment.Film" %>
<%@ page import="assignment.FilmViewer" %>

<%

List<Film> films = (List<Film>) request.getAttribute("films");
response.getWriter().println( new FilmViewer().toJson( films ) );

%>
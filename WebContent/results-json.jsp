<%@ page import="java.util.List" %>
<%@ page import="model.Film" %>
<%@ page import="viewer.FilmViewer" %>

<%

List<Film> films = (List<Film>) request.getAttribute("films");
response.getWriter().println( new FilmViewer().toJson( films ) );

%>
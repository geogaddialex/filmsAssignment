<%@ page import="java.util.ArrayList" %>
<%@ page import="assignment.Film" %>

<%

ArrayList<Film> films = (ArrayList<Film>) request.getAttribute("films");

for (Film film: films) {
    response.getWriter().println( film.toString() );
}

%>
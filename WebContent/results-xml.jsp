<%@ page import="java.util.List" %>
<%@ page import="model.Film" %>
<%@ page import="viewer.FilmViewer" %>
<%@ page import="com.fasterxml.jackson.core.JsonProcessingException" %>
<%@ page import="com.fasterxml.jackson.databind.ObjectMapper" %>
<%@ page import="com.fasterxml.jackson.databind.SerializationFeature" %>
<%@ page import="com.fasterxml.jackson.dataformat.xml.*" %>
<%@ page contentType="text/xml; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page trimDirectiveWhitespaces="true" %>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<% request.setAttribute("xml", new FilmViewer().toXML( (List<Film>) request.getAttribute("films") ) ); %>
<c:out value="${xml}" escapeXml="false"/>
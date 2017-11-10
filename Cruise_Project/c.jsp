<%@ page language = "java" contentType = "text/html; charset=ISO-8859-1"
	pageEncoding = "ISO-8859-1"%>
<% 
String login=request.getParameter("login");
 String password=request.getParameter("password");
if((login.equals("admin") && password.equals("admin"))) { session.setAttribute("login",login); response.sendRedirect("admin.jsp"); } else response.sendRedirect("login.html");




%>

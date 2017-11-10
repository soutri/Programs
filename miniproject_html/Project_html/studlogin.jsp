<html>

<%@ page import ="java.sql.*" %>
<%@ page import ="javax.sql.*" %>
<%
String userid=request.getParameter("userid"); 
session.putValue("userid",userid); 
session.setAttribute("userid",userid);
String pwd=request.getParameter("password"); 
Class.forName("com.mysql.jdbc.Driver"); 
java.sql.Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/LIBRARY","root",""); 
Statement st= con.createStatement(); 
ResultSet rs=st.executeQuery("select * from Login where user='"+userid+"' and privilege=student"); 
if(rs.next()) 
{ 
if(rs.getString(3).equals(pwd)) 
{ 
%>
<head>

  <meta charset="UTF-8">
  
</head>

<table>
    <tr>
        <td><img src="pict.jpeg" align="middle"></td>
    </tr>
</table>


<br>

  <h1>Welcome
  <%
		out.print(" " + userid );
	%>
	</h1>

<br>

<div class="menu">
	<div class="menu1">
		<a href="profile.jsp">
		<h1>My Profile</h1></a>
	</div>
	
	<br>
	<div class="menu2">
		<a href="my_books.jsp">
		<h1>My books</h1></a>
	</div>

	</div>
	<br>
	<div class="menu3">
		<a href="search.jsp">
		<h1>Search Books</h1></a>
	</div>
</div>
<%
} 
else 
{ 
out.println("Invalid password try again"); 
} 
} 
%>
</body>
</html>

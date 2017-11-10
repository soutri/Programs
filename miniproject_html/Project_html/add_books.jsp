<html>

<%@ page import ="java.sql.*" %>
<%@ page import ="javax.sql.*" %>
<%

Class.forName("com.mysql.jdbc.Driver"); 
java.sql.Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/project","root","root"); 
Statement st= con.createStatement(); 
String usr=(String) session.getAttribute("userid");
%>
<head>

</head>


<table>
    <tr>
        <td><img src="pict.jpeg" align="middle"></td>
    </tr>
</table>

<br>

	<form action=edit1.jsp>
	<h3>
		<label>First Name:</label><input type=text name=fname1 >
		<br>
		<br>
		<label>Last Name:</label><input type=text name=lname1 >
		<br>
		<br>
		<label>Batch:</label>  <input type=text name=batch1>
		<br>
		<br>
		<label>Password</label>  <input type=text name=pass>
		<br>
		<br>
		<input type=submit value=SUBMIT style="position:relative;left:260px;">
	</h3>
	</form>
</div>


</body>

</html>

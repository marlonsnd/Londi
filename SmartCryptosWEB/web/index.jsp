<%-- 
    Document   : index.jsp
    Created on : 08/03/2023, 11:42:44
    Author     : Márlon Schoenardie
--%>

<%@page import="java.util.Date"%>
<%@page import="com.londi.util.Parser"%>
<%@page contentType="text/html" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
        <title>Smart Cryptos</title>
    </head>
    <body>
        <h1>Smart Cryptos - <%=Parser.dateTimeToStr(new Date())%></h1>
    </body>
</html>

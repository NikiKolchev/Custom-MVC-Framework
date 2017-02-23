<%--
  Created by IntelliJ IDEA.
  User: dexter
  Date: 2/23/17
  Time: 8:07 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Beer</title>
</head>
    <body>
        <h1>I am a beer !</h1>
        <form method="post">
            <input type="text" name="beerBrand" placeholder="beer brand"/>
            <input type="submit" value="Submit"/>
            <p>I am coming from a model ${key}</p>
        </form>
    </body>
</html>

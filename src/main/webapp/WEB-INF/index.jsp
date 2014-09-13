<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!doctype html>
<html>
    <head>
        <title>Index</title>
        <link rel="stylesheet" type="text/css" href="/static/css/bootstrap.min.css">
    </head>
    <body>
        <div class="container">
            <h1>Index</h1>
            <span class="glyphicon glyphicon-star"></span>
            <ul>
                <li><a href="/">Index</a></li>
                <li><a href="/page/1">Page 1</a></li>
                <li><a href="/page/hello">Page Hello</a></li>
            </ul>
            <form method="post" action="/">
                <input type="text" name="message" value="">
                <button type="submit">Submit</button>
            </form>
            <c:choose>
                <c:when test="${model.message != null}">
                    <p>Message is: ${model.message}</p>
                </c:when>
                <c:otherwise>
                    <p>No message</p>
                </c:otherwise>
            </c:choose>
        </div>
    </body>
</html>
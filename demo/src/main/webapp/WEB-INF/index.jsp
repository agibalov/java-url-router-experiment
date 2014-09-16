<%@ taglib tagdir="/WEB-INF/tags" prefix="demo" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<demo:layout>
    <jsp:attribute name="content">
        <h1>Index</h1>
        <span class="glyphicon glyphicon-star"></span>
        <demo:menu />
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
        <c:choose>
            <c:when test="${model.page != null}">
                <p>Page is: ${model.page}</p>
            </c:when>
            <c:otherwise>
                <p>No page</p>
            </c:otherwise>
        </c:choose>
    </jsp:attribute>
</demo:layout>

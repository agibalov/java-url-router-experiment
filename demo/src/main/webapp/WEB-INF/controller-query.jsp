<%@ taglib tagdir="/WEB-INF/tags" prefix="demo" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<demo:layout>
    <jsp:attribute name="content">
        <h1>Controller</h1>
        <demo:menu />
        <c:choose>
            <c:when test="${model.id != null}">
                <p>Query param id: ${model.id}</p>
            </c:when>
            <c:otherwise>
                <p>No query param id</p>
            </c:otherwise>
        </c:choose>
    </jsp:attribute>
</demo:layout>

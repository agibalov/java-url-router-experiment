<%@ taglib tagdir="/WEB-INF/tags" prefix="demo" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<demo:layout>
    <jsp:attribute name="content">
        <h1>Controller</h1>
        <demo:menu />
        <p>Path param id: ${model.id}</p>
    </jsp:attribute>
</demo:layout>

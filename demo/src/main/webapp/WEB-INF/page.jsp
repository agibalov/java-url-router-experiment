<%@ taglib tagdir="/WEB-INF/tags" prefix="demo" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<demo:layout>
    <jsp:attribute name="content">
        <h1 class="red-header">Page ${model.pageId}</h1>
        <demo:menu />
    </jsp:attribute>
</demo:layout>

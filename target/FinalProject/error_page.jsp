<%@ include file="WEB-INF/jspf/directive/page.jspf" %>
<%@ include file="WEB-INF/jspf/directive/taglib.jspf" %>

<%@ page isErrorPage="true" %>
<%@ page import="java.io.PrintWriter" %>

<html>
<head>
    <title><fmt:message key="error.title"/></title>
    <%@ include file="WEB-INF/jspf/head.jspf" %>
</head>
<body>
<%@ include file="WEB-INF/jspf/header.jspf" %>
<div class="wrapper">
    <div class="error">
        <table id="main-container">
            <tr>
                <td>
                    <%-- this way we obtain an information about an exception (if it has been occurred) --%>
                    <c:set var="code" value="${requestScope['javax.servlet.error.status_code']}"/>
                    <c:set var="message" value="${requestScope['javax.servlet.error.message']}"/>
                    <c:set var="exception" value="${requestScope['javax.servlet.error.exception']}"/>

                    <c:if test="${not empty code}">
                        <h3>Error code: ${code}</h3>
                    </c:if>

                    <c:if test="${not empty message}">
                        <h3>${message}</h3>
                    </c:if>

                    <c:if test="${not empty exception}">
                        <% exception.printStackTrace(new PrintWriter(out)); %>
                    </c:if>

                </td>
            </tr>
        </table>
    </div>
</div>

<%@ include file="WEB-INF/jspf/footer.jspf" %>
</body>
</html>
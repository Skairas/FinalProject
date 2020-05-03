<table id="main-container">
  <tr>
    <td>
      <%-- CONTENT --%>

      <h2>
        The following error occurred
      </h2>

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

      <%-- if we get this page --%>
      <c:if test="${not empty sessionScope.errorMessage}">
        <h3><fmt:message key="${sessionScope.errorMessage}"/></h3>
      </c:if>

      <%-- CONTENT --%>
    </td>
  </tr>
</table>
<%@ include file="../jspf/directive/page.jspf" %>
<%@ include file="../jspf/directive/taglib.jspf" %>

<html>
<head>
    <title><fmt:message key="periodical.title"/></title>
    <%@ include file="../jspf/head.jspf" %>
</head>
<body>
<div class="wrapper">
    <%@ include file="../jspf/header.jspf" %>

    <div class="info-div">
        <div class="panel panel-info wx-auto">
            <div class="panel-heading">
                <h3 class="panel-title">${requestScope.periodical.name}</h3>
            </div>
            <div class="panel-body">
                <div>
                    <fmt:message key="name.label"/>: ${requestScope.periodical.name}
                </div>
                <div>
                    <fmt:message key="category.label"/>:
                    <c:forEach var="category" items="${sessionScope.categories}">
                        <c:if test="${category.parentCategoryId eq requestScope.periodical.category}">
                            <c:out value="${category.name}"/>
                        </c:if>
                    </c:forEach>
                </div>
                <div>
                    <fmt:message key="price.label"/>: ${requestScope.periodical.price}<i
                        class="fa fa-usd"></i>
                </div>
            </div>
            <br/><br/>
            <div>
            <c:if test="${sessionScope.userRole.name == 'user'}">
                <a href="controller?command=basketAdd&id=${requestScope.periodical.id}">
                    <button class="btn btn-outline-info my-2 my-sm-0 mx-auto">
                        <fmt:message key="buy.button"/>
                    </button>
                </a>
            </c:if>
            <c:if test="${sessionScope.userRole.name == 'admin'}">
                <a href="controller?command=deletePeriodical&id=${requestScope.periodical.id}">
                    <button class="btn btn-outline-info my-2 my-sm-0 mx-auto">
                        <fmt:message key="delete.button"/>
                    </button>
                </a>
                <a href="controller?command=viewEditPeriodical&id=${requestScope.periodical.id}">
                    <button class="btn btn-outline-info my-2 my-sm-0 mx-auto">
                        <fmt:message key="edit.button"/>
                    </button>
                </a>
            </c:if>
            </div>
        </div>
    </div>
</div>
<%@ include file="../jspf/footer.jspf" %>
</body>
</html>

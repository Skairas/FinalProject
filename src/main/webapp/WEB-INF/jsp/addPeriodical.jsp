<%@ include file="../jspf/directive/page.jspf" %>
<%@ include file="../jspf/directive/taglib.jspf" %>

<html>
<head>
    <title><fmt:message key="addPeriodical.title"/></title>
    <%@ include file="../jspf/head.jspf" %>
</head>
<body>
<div class="wrapper">
    <%@ include file="../jspf/header.jspf" %>

    <div class="signin-div">
        <div class=text-center>
            <form class="form-signin" id="edit_form" action="controller" method="post">
                <input type="hidden" name="command" value="addPeriodical"/>
                <h1 class="h3 mb-3 font-weight-normal">${requestScope.periodical.name}</h1>
                <br/><br/>
                <label>
                    <input name="name" placeholder="<fmt:message key="name.label"/>" value="${requestScope.name}"
                           required/>
                </label><br/>
                <label>
                    <input name="price" placeholder="<fmt:message key="price.label"/>"
                           value="${requestScope.price}" required/>
                </label><br/>
                <label>
                    <select class="form-control" name="category" id="category">
                        <c:forEach var="category" items="${sessionScope.categories}">
                            <option>${category.name}</option>
                        </c:forEach>
                    </select>
                </label><br/><br/>
                <button class="btn btn-lg btn-primary btn-block" type="submit"><fmt:message
                        key="addPeriodical.button"/></button>
            </form>
        </div>
    </div>
</div>

<%@ include file="../jspf/footer.jspf" %>
</body>
</html>
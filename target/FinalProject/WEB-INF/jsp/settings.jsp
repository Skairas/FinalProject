<%@ include file="../jspf/directive/page.jspf" %>
<%@ include file="../jspf/directive/taglib.jspf" %>

<html>
<head>
    <title><fmt:message key="settings.menu"/></title>
    <%@ include file="../jspf/head.jspf" %>
</head>
<body>
<div class="wrapper">
    <%@ include file="../jspf/header.jspf" %>

    <div class="signin-div">
        <div class=text-center>
            <form class="form-signin" id="settings_form" action="controller" method="post">
                <input type="hidden" name="command" value="settings"/>
                <h1 class="h3 mb-3 font-weight-normal"><fmt:message key="settings.menu"/></h1>
                <br/><br/>
                <label>
                    <input name="firstName" value="${requestScope.firstName}" placeholder="<fmt:message key="firstName.label"/>"/>
                </label><br/>
                <label>
                    <input name="lastName" value="${requestScope.lastName}" placeholder="<fmt:message key="lastName.label"/>"/>
                </label><br/>
                <label>
                    <select class="form-control" name="language" id="category">
                        <c:forEach var="language" items="${applicationScope.locales}">
                            <option
                                    <c:if test="${sessionScope.lang eq language}">selected="selected"</c:if>
                            ><fmt:message key="${language}.label"/></option>
                        </c:forEach>
                    </select>
                </label><br/>
                <div class="checkbox mb-3">
                    <label>
                        <input type="checkbox" name="prefLang"> <fmt:message key="prefLang.label"/>
                    </label>
                </div>
                <button class="btn btn-lg btn-primary btn-block" type="submit"><fmt:message key="edit.button"/></button>
            </form>
        </div>
    </div>

</div>

<%@ include file="../jspf/footer.jspf" %>
</body>
</html>

<%@ include file="WEB-INF/jspf/directive/page.jspf" %>
<%@ include file="WEB-INF/jspf/directive/taglib.jspf" %>

<html>
<head>
    <title><fmt:message key="registration.title"/></title>
    <%@ include file="WEB-INF/jspf/head.jspf" %>
</head>
<body>
<div class="wrapper">
    <%@ include file="WEB-INF/jspf/header.jspf" %>

    <c:if test="${not empty sessionScope.user}">
        <c:redirect url="controller?command=periodicals"/>
    </c:if>
    <div class="signin-div">
        <div class=text-center>
            <form class="form-signin" id="registration_form" action="controller" method="post">
                <input type="hidden" name="command" value="registration"/>
                <h1 class="h3 mb-3 font-weight-normal"><fmt:message key="signUp.label"/></h1>
                <br/><br/>
                <label>
                    <input name="login" placeholder="<fmt:message key="login.label"/>" required autofocus/>
                </label><br/>
                <label>
                    <input type="password" name="password" placeholder="<fmt:message key="password.label"/>" required/>
                </label><br/>
                <label>
                    <input type="password" name="conf_password" placeholder="<fmt:message key="confPassword.label"/>" required/>
                </label><br/>
                <label>
                    <input name="first_name" placeholder="<fmt:message key="firstName.label"/>" required/>
                </label><br/>
                <label>
                    <input name="last_name" placeholder="<fmt:message key="lastName.label"/>" required/>
                </label><br/><br/>
                <button class="btn btn-lg btn-primary btn-block" type="submit"><fmt:message
                        key="registration.title"/></button>
            </form>
        </div>
    </div>

</div>

<%@ include file="WEB-INF/jspf/footer.jspf" %>
</body>
</html>
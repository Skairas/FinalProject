<%@ include file="WEB-INF/jspf/directive/page.jspf" %>
<%@ include file="WEB-INF/jspf/directive/taglib.jspf" %>

<html>
<head>
    <title><fmt:message key="login.title"/></title>
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
            <form class="form-signin" id="login_form" action="controller" method="post">
                <input type="hidden" name="command" value="login"/>
                <h1 class="h3 mb-3 font-weight-normal"><fmt:message key="signIn.label"/></h1>
                <br/><br/>
                <label>
                    <input name="login" placeholder="<fmt:message key="login.label"/>" required autofocus/>
                </label><br/>
                <label>
                    <input type="password" name="password"
                           placeholder="<fmt:message key="password.label"/>" required/>
                </label><br/><br/>
                <button class="btn btn-lg btn-primary btn-block" type="submit"><fmt:message key="login.title"/></button>
            </form>
            <div class="mx-auto text-center">
                <a href="registration.jsp">
                    <button class="btn btn-lm btn-primary" type="submit"><fmt:message
                            key="registration.title"/></button>
                </a>
            </div>
        </div>
    </div>

</div>

<script>
    function validateForm(_form) {
        var el = _form.getElementById("login");
        var reg = /^[0-9a-zA-Z]+$/;
        var res = validateEl(el, reg);
        el = _form.getElementById("password");
        reg = /^[0-9a-zA-Z@!$&()*+-?_~]+$/;
        var temp = validateEl(el, reg);
        if (res) {
            if (!temp) {
                res = temp;
            }
        }
        return res;
    }

    function validateEl(_el, reg) {
        var temp = _el.value;
        if (temp.match(reg)) {
            _el.style.color = "";
            return true;
        } else {
            _el.style.color = "red";
            return false
        }
    }
</script>

<%@ include file="WEB-INF/jspf/footer.jspf" %>
</body>
</html>

<%@ include file="../jspf/directive/page.jspf" %>
<%@ include file="../jspf/directive/taglib.jspf" %>

<html>
<head>
    <title><fmt:message key="replenish.label"/></title>
    <%@ include file="../jspf/head.jspf" %>
</head>
<body>
<div class="wrapper">
    <%@ include file="../jspf/header.jspf" %>

    <div class="signin-div">
        <div class=text-center>
            <form class="form-signin" id="edit_form" action="controller" method="post">
                <input type="hidden" name="command" value="replenish"/>
                <h1 class="h3 mb-3 font-weight-normal"><fmt:message key="replenish.label"/></h1>
                <br/><br/>
                <label>
                    <input name="amount" placeholder="<fmt:message key="value.label"/>" value="${requestScope.name}"
                           required/>
                </label><br/><br/>
                <button class="btn btn-lg btn-primary btn-block" type="submit"><fmt:message
                        key="replenish.button"/></button>
            </form>
        </div>
    </div>
</div>

<%@ include file="../jspf/footer.jspf" %>
</body>
</html>
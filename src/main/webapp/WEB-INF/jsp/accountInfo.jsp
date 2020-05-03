<%@ include file="../jspf/directive/page.jspf" %>
<%@ include file="../jspf/directive/taglib.jspf" %>

<html>
<head>
    <title><fmt:message key="myAccount.menu"/></title>
    <%@ include file="../jspf/head.jspf" %>
</head>
<body>
<div class="wrapper">
    <%@ include file="../jspf/header.jspf" %>

    <div class="info-div">
        <div class="panel panel-info">
            <div class="panel-heading">
                <h3 class="panel-title">${sessionScope.user.login}</h3>
            </div>
            <div class="panel-body">
                <div>
                    <fmt:message key="firstName.label"/>: ${sessionScope.user.firstName}
                </div>
                <div>
                    <fmt:message key="lastName.label"/>: ${sessionScope.user.lastName}
                </div>
                <div>
                    <fmt:message key="balance.label"/>: ${sessionScope.user.balance}
                </div>
                <br/><br/>
                <div>
                    <c:if test="${sessionScope.userRole.name == 'user'}">
                        <a href="controller?command=viewReplenish">
                            <button class="btn btn-outline-info my-2 my-sm-0 float-right">
                                <fmt:message key="replenish.button"/>
                            </button>
                        </a>
                    </c:if>
                </div>
            </div>
        </div>
    </div>
</div>
<%@ include file="../jspf/footer.jspf" %>
</body>
</html>


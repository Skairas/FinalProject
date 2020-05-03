<%@ include file="../jspf/directive/page.jspf" %>
<%@ include file="../jspf/directive/taglib.jspf" %>

<html>
<head>
    <title><fmt:message key="users.title"/></title>
    <%@ include file="../jspf/head.jspf" %>
</head>
<body>
<div class="wrapper">
    <%@ include file="../jspf/header.jspf" %>

    <div id="accordianId" role="tablist" aria-multiselectable="true" class="mx-auto w-75">
        <c:forEach var="user" items="${requestScope.usersList}">
            <div class="card">
                <div class="card-header" role="tab" id="sectionHeader${user.id}">
                    <h5 class="mb-0 my-auto">
                        <a data-toggle="collapse" data-parent="#accordianId" href="#sectionContent${user.id}"
                           aria-expanded="true"
                           aria-controls="sectionContent${user.id}">
                            <span>${user.login} </span>
                        </a>
                        <span>
    <c:choose>
        <c:when test="${user.banned}">
            <a href="controller?command=ban&ban=false&uId=${user.id}">
                <button class="btn btn-outline-info my-2 my-sm-0 float-right">
                                            <fmt:message key="unBan.button"/>
                                        </button>
            </a>
        </c:when>
        <c:otherwise>
            <a href="controller?command=ban&ban=true&uId=${user.id}">
                <button class="btn btn-outline-info my-2 my-sm-0 float-right">
                                            <fmt:message key="ban.button"/>
                                        </button>
                            </a>
        </c:otherwise>
    </c:choose>
                        </span>
                    </h5>
                </div>
                <div id="sectionContent${user.id}" class="collapse in" role="tabpanel"
                     aria-labelledby="sectionHeader${user.id}">
                    <div class="card-body">
                        <div>
                            <fmt:message key="login.label"/>: ${user.login}
                        </div>
                        <br/>
                        <div>
                            <fmt:message key="firstName.label"/>: ${user.firstName}
                        </div>
                        <br/>
                        <div>
                            <fmt:message key="lastName.label"/>: ${user.lastName}
                        </div>
                        <br/>
                    </div>
                </div>
            </div>
        </c:forEach>
    </div>
</div>
<%@ include file="../jspf/footer.jspf" %>
</body>
</html>

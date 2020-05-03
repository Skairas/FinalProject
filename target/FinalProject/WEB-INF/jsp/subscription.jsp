<%@ include file="../jspf/directive/page.jspf" %>
<%@ include file="../jspf/directive/taglib.jspf" %>

<html>
<head>
    <title><fmt:message key="subscription.title"/></title>
    <%@ include file="../jspf/head.jspf" %>
</head>
<body>
<div class="wrapper">
    <%@ include file="../jspf/header.jspf" %>

    <div class="info-div">
        <div class="panel panel-info wx-auto">
            <div class="panel-heading">
                <h3 class="panel-title">${requestScope.subscription.periodicalName}</h3>
            </div>
            <div class="panel-body">
                <div>
                    <fmt:message key="name.label"/>: ${requestScope.subscription.periodicalName}
                </div>
                <div>
                    ${requestScope.subscription.categoryName}
                </div>
                <div>
                    <fmt:message key="price.label"/>: ${requestScope.subscription.price}<i
                        class="fas fa-dollar-sign"></i>
                </div>
                <div>
                    <fmt:message key="status.label"/>: <fmt:message
                        key="${requestScope.subscription.statusName}.label"/>
                </div>
                <div><fmt:message key="ending.label"/>: ${requestScope.subscription.endingDate} </div>
            </div>
            <br/><br/>
            <div>
                <form class="form-inline" method="post" action="controller">
                    <div class="form-group">
                        <input type="hidden" name="command" value="renewSubscription"/>
                        <input type="hidden" name="id" value="${requestScope.subscription.id}"/>
                        <label>
                            <button class="btn btn-outline-info my-2 my-sm-0 mx-auto" type="submit">
                                <fmt:message key="renew.button"/>
                            </button>
                        </label>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>
<%@ include file="../jspf/footer.jspf" %>
</body>
</html>

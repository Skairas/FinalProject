<%@ include file="../jspf/directive/page.jspf" %>
<%@ include file="../jspf/directive/taglib.jspf" %>

<html>
<head>
    <title><fmt:message key="subscriptions.title"/></title>
    <%@ include file="../jspf/head.jspf" %>
</head>
<body>
<div class="wrapper">
    <%@ include file="../jspf/header.jspf" %>

    <div id="accordianId" role="tablist" aria-multiselectable="true" class="mx-auto w-75">
        <div>
            <c:if test="${not empty requestScope.emptyList}}">
                <h5><fmt:message key="subscriptionsNotExist.message"/></h5>
            </c:if>
            <c:forEach var="subscription" items="${requestScope.subscriptions}">
                <div class="card">
                    <div class="card-header" role="tab" id="sectionHeader${subscription.id}">
                        <h5 class="mb-0  my-auto">
                            <a data-toggle="collapse" data-parent="#accordianId"
                               href="#sectionContent${subscription.id}"
                               aria-expanded="true"
                               aria-controls="sectionContent${subscription.id}">
                                <span>${subscription.periodicalName} </span>
                            </a>
                            <span class="float-right"><fmt:message key="${subscription.statusName}.label"/></span>
                            <span class="float-right" style="padding-right: 10px">${subscription.endingDate} </span>
                        </h5>
                    </div>
                    <div id="sectionContent${subscription.id}" class="collapse in" role="tabpanel"
                         aria-labelledby="sectionHeader${subscription.id}">
                        <div class="card-body">
                            <div>
                                <fmt:message key="name.label"/>: <a
                                    href="controller?command=subscription&id=${subscription.id}">${subscription.periodicalName}</a>
                            </div>
                            <br/>
                            <div>
                                <fmt:message key="price.label"/>: ${subscription.price}<i
                                    class="fas fa-dollar-sign"></i>
                            </div>
                            <br/>
                            <div>
                                <fmt:message key="category.label"/>: ${subscription.categoryName}
                            </div>
                            <br/>
                            <div><fmt:message key="status.label"/>: <fmt:message key="${subscription.statusName}.label"/></div>
                            <br/>
                            <div><fmt:message key="ending.label"/>: ${subscription.endingDate} </div>
                        </div>
                    </div>
                </div>
            </c:forEach>
        </div>
    </div>
</div>
<%@ include file="../jspf/footer.jspf" %>
</body>
</html>

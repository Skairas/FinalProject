<%@ include file="../jspf/directive/page.jspf" %>
<%@ include file="../jspf/directive/taglib.jspf" %>

<html>
<head>
    <title><fmt:message key="basket.menu"/></title>
    <%@ include file="../jspf/head.jspf" %>
</head>
<body>
<div class="wrapper">
    <%@ include file="../jspf/header.jspf" %>

    <div id="accordianId" role="tablist" aria-multiselectable="true" class="mx-auto w-75">
        <c:forEach var="periodical" items="${requestScope.periodicals}">
            <div class="card">
                <div class="card-header" role="tab" id="sectionHeader${periodical.id}">
                    <h5 class="mb-0  my-auto">
                        <a data-toggle="collapse" data-parent="#accordianId" href="#sectionContent${periodical.id}"
                           aria-expanded="true"
                           aria-controls="sectionContent${periodical.id}">
                            <span class="wy-auto">${periodical.name} </span>
                            <span>
                                    <a href="controller?command=basketRem&id=${periodical.id}">
                                        <button class="btn btn-outline-info my-2 my-sm-0 float-right">
                                            <fmt:message key="removeBasket.button"/>
                                        </button>
                                    </a>
                            </span>
                            <span class="float-right wy-auto" style="padding-right: 10px">${periodical.price}<i
                                    class="fas fa-dollar-sign"></i></span>
                        </a>
                    </h5>
                </div>
                <div id="sectionContent${periodical.id}" class="collapse in" role="tabpanel"
                     aria-labelledby="sectionHeader${periodical.id}">
                    <div class="card-body">
                        <div>
                            <fmt:message key="name.label"/>: <a
                                href="controller?command=periodical&id=${periodical.id}">${periodical.name}</a>
                        </div>
                        <br/>
                        <div>
                            <fmt:message key="price.label"/>: ${periodical.price}<i
                                class="fa fa-usd"></i>
                        </div>
                        <br/>
                        <div>
                            <fmt:message key="category.label"/>:
                            <c:forEach var="category" items="${sessionScope.categories}">
                                <c:if test="${category.parentCategoryId eq periodical.category}">
                                    <c:out value="${category.name}"/>
                                </c:if>
                            </c:forEach>
                        </div>
                    </div>
                </div>
            </div>
        </c:forEach>
        <br/><br/>
        <div>
            <span><fmt:message key="summary.label"/>: ${sessionScope.summaryPrice}</span>
            <span class="float-right">
                <form class="form-inline" action="controller" method="POST">
                	<div class="form-group">
                		<input type="hidden" name="command" value="buySubscriptions"/>
                            <button class="btn btn-lm btn-primary" type="submit"><fmt:message
                                    key="buy.button"/></button>
                	</div>
                </form>
            </span>
        </div>
    </div>
</div>
<%@ include file="../jspf/footer.jspf" %>
</body>
</html>

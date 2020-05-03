<%@ include file="../jspf/directive/page.jspf" %>
<%@ include file="../jspf/directive/taglib.jspf" %>

<html>
<head>
    <title><fmt:message key="periodicals.title"/></title>
    <%@ include file="../jspf/head.jspf" %>
</head>
<body>
<div class="wrapper">
    <%@ include file="../jspf/header.jspf" %>

    <div id="ajaxInfo"></div>

    <div class="form-group mx-auto w-75">
        <form class="form-inline" id="search" action="controller" method="POST">
            <input type="hidden" name="command" value="search"/>
            <input type="hidden" name="category" value="${requestScope.category}"/>
            <input type="hidden" name="sort" value="${requestScope.sort}"/>
            <input name="searchQuery" class="form-control mr-sm-2 w-100" type="text"
                   placeholder="<fmt:message key="search.button"/>">
            <button class="btn btn-outline-info my-2 my-sm-0 mx-auto" type="submit"><fmt:message
                    key="search.button"/></button>
        </form>
    </div>

    <c:if test="${not empty requestScope.category}">
        <div class="text-center">
            <h3>
                <c:forEach var="category" items="${sessionScope.categories}">
                    <c:set var="categoryIdAsString" scope="page" value="${category.parentCategoryId}"/>
                    <c:if test="${pageScope.categoryIdAsString eq requestScope.category}">
                        <c:out value="${category.name}"/>
                    </c:if>
                </c:forEach>
            </h3>
        </div>
    </c:if>

    <c:if test="${not empty requestScope.searchQuery}">
        <div class="text-center">
            <h3><fmt:message key="searchQuery.label"/> <c:out value="${requestScope.searchQuery}"/></h3>
        </div>
    </c:if>

    <div id="accordianId" role="tablist" aria-multiselectable="true" class="mx-auto w-75">
        <div>
            <span class="float-left">
                <div class="dropdown">
                	<button class="btn btn-secondary dropdown-toggle" type="button" id="triggerId"
                            data-toggle="dropdown" aria-haspopup="true"
                            aria-expanded="false">
                				<fmt:message key="${requestScope.sort}.sort"/>
                			</button>
                	<div class="dropdown-menu" aria-labelledby="triggerId">
                        <c:forEach var="sortName" items="${applicationScope.sortNames}">
                          <a class="dropdown-item"
                             href="controller?command=periodicals&category=${requestScope.category}&searchQuery=${requestScope.searchQuery}&sort=${sortName}">
                              <fmt:message key="${sortName}.sort"/>
                          </a>
                        </c:forEach>
                	</div>
                </div>
            </span>
            <c:if test="${sessionScope.userRole.name == 'admin'}">
            <span class="float-right">
                <a href="controller?command=addPeriodical">
                    <button class="btn btn-outline-info my-2 my-sm-0 float-right">
                        <fmt:message key="addPeriodical.button"/>
                    </button>
                </a>
            </span>
            </c:if>
        </div>
        <br/><br/>
        <c:if test="${not empty requestScope.emptyList}}">
            <h5><fmt:message key="periodicalsNotExist.message"/></h5>
        </c:if>
        <c:forEach var="periodical" items="${requestScope.periodicals}">
            <div class="card">
                <div class="card-header" role="tab" id="sectionHeader${periodical.id}">
                    <h5 class="mb-0  my-auto">
                        <a data-toggle="collapse" data-parent="#accordianId" href="#sectionContent${periodical.id}"
                           aria-expanded="true"
                           aria-controls="sectionContent${periodical.id}">
                            <span>${periodical.name} </span>
                        </a>
                        <span class="float-right wy-auto" style="padding-right: 10px">${periodical.price}<i
                                class="fas fa-dollar-sign"></i></span>
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
                                class="fas fa-dollar-sign"></i>
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
                        <c:if test="${sessionScope.userRole.name == 'user'}">
                                <span>
                                        <button class="btn btn-outline-info my-2 my-sm-0 float-right" id="btnAdd"
                                                onclick="addBasket(${periodical.id})">
                                            <fmt:message key="addToBasket.button"/>
                                        </button>
                                </span>
                        </c:if>
                    </div>
                </div>
            </div>
        </c:forEach>
    </div>
</div>
<%@ include file="../jspf/footer.jspf" %>

<script>
    function addBasket(id) {
        var data = {id: id, command: "basketAdd"};
        $.ajax({
            type: 'POST',
            data: data,
            url: '/FinalProject/basket',
            success: [function (serverData) {
                console.log(serverData)
                var element = document.getElementById("ajaxInfo");
                var json = JSON.parse(serverData)
                element.className = "";
                element.classList.add("alert");
                if (json["successMessage"]) {
                    element.classList.add("alert-success");
                    element.innerHTML = json["successMessage"];
                }
                if (json["errorMessage"]) {
                    element.classList.add("alert-danger");
                    element.innerHTML = json["errorMessage"];
                }
                element.classList.add("w-50");
                element.classList.add("mx-auto");
                element.classList.add("my-auto");
                element.classList.add("text-center");
            }],
            error: [function (serverData) {
                console.log(serverData)
            }]
        });
    }
</script>
</body>
</html>

<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<tiles:insertDefinition name="layout">
    <tiles:putAttribute name="body">

        <div class="row">
            <div class="col-md-9">
                <div>
                    <h3>
                        <spring:message code="fourniture.list" />
                    </h3>
                    <hr/>
                </div>
                <div class="dropdown pull-right ">
                    <button class="btn btn-default dropdown-toggle" type="button" id="dropdownMenu1" data-toggle="dropdown" aria-expanded="true">
                        <spring:message code="search.taille" />
                        : ${size}&nbsp;
                        <span class="caret"></span>
                    </button>
                    <ul class="dropdown-menu" role="menu" aria-labelledby="dropdownMenu1">
                        <li role="presentation"><a role="menuitem" tabindex="-1" href="?querycategorie=${fourniture.categorie.id}&querydesignation=${fourniture.designation}&queryreference=${fourniture.reference}&size=5">5</a></li>
                        <li role="presentation"><a role="menuitem" tabindex="-1" href="?querycategorie=${fourniture.categorie.id}&querydesignation=${fourniture.designation}&queryreference=${fourniture.reference}&size=10">10</a></li>
                        <li role="presentation"><a role="menuitem" tabindex="-1" href="?querycategorie=${fourniture.categorie.id}&querydesignation=${fourniture.designation}&queryreference=${fourniture.reference}&size=20">20</a></li>
                        <li role="presentation"><a role="menuitem" tabindex="-1" href="?querycategorie=${fourniture.categorie.id}&querydesignation=${fourniture.designation}&queryreference=${fourniture.reference}&size=30">30</a></li>
                        <li role="presentation"><a role="menuitem" tabindex="-1" href="?querycategorie=${fourniture.categorie.id}&querydesignation=${fourniture.designation}&queryreference=${fourniture.reference}&size=40">40</a></li>
                        <li role="presentation"><a role="menuitem" tabindex="-1" href="?querycategorie=${fourniture.categorie.id}&querydesignation=${fourniture.designation}&queryreference=${fourniture.reference}&size=50">50</a></li>
                    </ul>
                </div>
                <table class="table table-condensed table-hover table-bordered">
                    <thead class="text-center btn-primary">
                        <tr>
                            <th> <span class="btn"> <spring:message code="fourniture.reference" />  </span> </th>
                            <th> <span class="btn"> <spring:message code="fourniture.designation" /> </span> </th>
                            <th> <span class="btn"> <spring:message code="fourniture.categorie" /> </span> </th>
                            <th> <span class="btn"> <spring:message code="fourniture.quantite" /> </span> </th>
                            <th> <span class="btn"> <spring:message code="action.titre" /> </span> </th>
                        </tr>
                    </thead>
                    <tbody>
                        <spring:url value="/fourniture/new" var="fournitureNew" />
                        <spring:url value="/" var="accueil" />

                        <c:if test="${fournitures.size() eq 0}">
                            <tr>
                                <td class="text-center label-danger" colspan="5">
                                    <spring:message code="empty.data" />
                                </td>
                            </tr>
                        </tbody>
                    </table>

                    <div class="row">
                        <a href="${fournitureNew}" class="btn btn-primary btn-sm">
                            <span class="glyphicon glyphicon-new-window"></span>
                            <spring:message code="action.nouveau" />
                        </a>


                        <div class="pull-right">
                            <ul class="pager">

                                <li><a href="?query=${query}&page=0&size=${size}" class ="btn btn-sm disabled">
                                        <span class="glyphicon glyphicon-fast-backward"></span>
                                    </a></li>
                                <li><a href="?query=${query}&page=${page-1}&size=${size}"class ="btn btn-sm disabled">
                                        <span class="glyphicon glyphicon-backward"></span>
                                    </a></li>
                                <li><input type="text" class="pager_detail text-center" readonly value="0/0"/></li>
                                <li><a href="?query=${query}&page=${page+1}&size=${size}" class ="btn btn-sm disabled">
                                        <span class="glyphicon glyphicon-forward"></span>
                                    </a></li>
                                <li><a href="?query=${query}&page=${Totalpage-1}&size=${size}" class ="btn btn-sm disabled">
                                        <span class="glyphicon glyphicon-fast-forward"></span>
                                    </a></li>
                            </ul>
                        </div>
                    </div>

                </c:if>

                <c:if test="${fournitures.size() ne 0}">
                    <c:forEach items="${fournitures}" var="fourniture">

                        <spring:url value="/fourniture/${fourniture.id}/show" var="show" />
                        <spring:url value="/fourniture/${fourniture.id}/edit" var="edit" />

                        <c:if test="${fourniture.perte gt 0}">
                            <tr class="text-danger" >
                                <td>${fourniture.reference}</td>
                                <td>${fourniture.designation}</td>
                                <td>${fourniture.categorie.intitule}</td>
                                <td>${fourniture.quantite}</td>
                                <td>
                                    <a href="${show}" class="btn btn-primary btn-sm">
                                        <span class="glyphicon glyphicon-open"></span>
                                        <spring:message code="action.detail" />
                                    </a>
                                    &nbsp; &nbsp;
                                    <a href="${edit}" class="btn btn-primary btn-warning">
                                        <span class="glyphicon glyphicon-edit"></span>
                                        <spring:message code="action.modifier" />
                                    </a>
                                </td>
                            </tr>
                        </c:if>
                        <c:if test="${fourniture.perte eq 0}">
                            <tr>
                                <td>${fourniture.reference}</td>
                                <td>${fourniture.designation}</td>
                                <td>${fourniture.categorie.intitule}</td>
                                <td>${fourniture.quantite}</td>
                                <td>
                                    <a href="${show}" class="btn btn-primary btn-sm">
                                        <span class="glyphicon glyphicon-open"></span>
                                        <spring:message code="action.detail" />
                                    </a>
                                    &nbsp; &nbsp;
                                    <a href="${edit}" class="btn btn-primary btn-warning">
                                        <span class="glyphicon glyphicon-edit"></span>
                                        <spring:message code="action.modifier" />
                                    </a>
                                </td>
                            </tr>
                        </c:if>

                    </c:forEach>

                    </tbody>
                    </table>
                    <div>
                        <a href="${fournitureNew}" class="btn btn-primary btn-sm">
                            <span class="glyphicon glyphicon-new-window"></span>
                            <spring:message code="action.nouveau" />
                        </a>
                        <div class="pull-right">
                            <ul class="pager">

                                <li>
                                    <a href="?querycategorie=${fourniture.categorie.id}&querydesignation=${fourniture.designation}&queryreference=${fourniture.reference}&page=0&size=${size}" <c:if test="${page eq 0}">class ="btn btn-sm disabled"</c:if>>
                                            <span class="glyphicon glyphicon-fast-backward"></span>
                                        </a>
                                    </li>
                                    <li>
                                        <a href="?querycategorie=${fourniture.categorie.id}&querydesignation=${fourniture.designation}&queryreference=${fourniture.reference}&page=${page-1}&size=${size}" <c:if test="${page eq 0}">class ="btn btn-sm disabled"</c:if>>
                                            <span class="glyphicon glyphicon-backward"></span>
                                        </a>
                                    </li>
                                    <li>
                                        <input type="text" class="pager_detail text-center" readonly value="${page+1}/${Totalpage}"/>
                                </li>
                                <li>
                                    <a href="?querycategorie=${fourniture.categorie.id}&querydesignation=${fourniture.designation}&queryreference=${fourniture.reference}&page=${page+1}&size=${size}" <c:if test="${page+1 eq Totalpage}">class ="btn btn-sm disabled"</c:if>>
                                            <span class="glyphicon glyphicon-forward"></span>
                                        </a>
                                    </li>
                                    <li>
                                        <a href="?querycategorie=${fourniture.categorie.id}&querydesignation=${fourniture.designation}&queryreference=${fourniture.reference}&page=${Totalpage-1}&size=${size}" <c:if test="${page+1 eq Totalpage}">class ="btn btn-sm disabled"</c:if>>
                                            <span class="glyphicon glyphicon-fast-forward"></span>
                                        </a>
                                    </li>
                                </ul>
                            </div>
                        </div>
                    </div>
                    <div class="col-md-3">
                        <div>
                            <h3><spring:message code="action.rechercher" /></h3>
                        <hr/>
                    </div>
                    <spring:url value="/fourniture/" var="fourniture_home"
                                htmlEscape="true" />
                    <form:form method="get" commandName="fourniture" action="${fourniture_home}">
                        <div class="form-group">
                            <label>
                                <spring:message code="fourniture.categorie" />
                            </label>

                            <select name="querycategorie" class="form-control input-sm">
                                <option value="">---</option>
                                <c:forEach var="cat" items="${categories}">
                                    <option value="${cat.key}"
                                            <c:if test="${cat.key eq fourniture.categorie.id}">
                                                selected
                                            </c:if>
                                            >
                                        ${cat.value}
                                    </option>
                                </c:forEach>
                            </select>
                        </div>
                        <div class="form-group">
                            <label>
                                <spring:message code="fourniture.reference" />
                            </label>
                            <input type="text" value="${fourniture.reference}" class="form-control input-sm" name="queryreference"/>
                            <input type="hidden" value="${size}" name="size"/>
                        </div>
                        <div class="form-group">
                            <label>
                                <spring:message code="fourniture.designation" />
                            </label>
                            <input type="text" value="${fourniture.designation}" class="form-control input-sm" name="querydesignation"/>
                        </div>
                        <hr/>
                        <button class="btn btn-default btn-sm">
                            <span class="glyphicon glyphicon-search"></span> <spring:message code="search"/></button>
                            <spring:url value="/fourniture/" htmlEscape="true" var="fourniture_home" />
                        <a href="${fourniture_home}" class="btn btn-default btn-sm">
                            <span class="glyphicon glyphicon-refresh"></span>
                            <spring:message code="search.delete" />
                        </a>

                    </form:form>
                </div>
            </c:if>
        </div>

    </tiles:putAttribute>
</tiles:insertDefinition>
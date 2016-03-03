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
                        <spring:message code="departement.list" />
                        <hr/>
                    </h3>
                </div>
                <div class="dropdown pull-right ">
                    <button class="btn btn-default dropdown-toggle" type="button" id="dropdownMenu1" data-toggle="dropdown" aria-expanded="true">
                        <spring:message code="search.taille" />
                        : ${size}&nbsp;
                        <span class="caret"></span>
                    </button>
                    <ul class="dropdown-menu" role="menu" aria-labelledby="dropdownMenu1">
                        <li role="presentation"><a role="menuitem" tabindex="-1" href="?querycode=${departement.code}&queryintitule=${departement.intitule}&size=5">5</a></li>
                        <li role="presentation"><a role="menuitem" tabindex="-1" href="?querycode=${departement.code}&queryintitule=${departement.intitule}&size=10">10</a></li>
                        <li role="presentation"><a role="menuitem" tabindex="-1" href="?querycode=${departement.code}&queryintitule=${departement.intitule}&size=20">20</a></li>
                        <li role="presentation"><a role="menuitem" tabindex="-1" href="?querycode=${departement.code}&queryintitule=${departement.intitule}&size=30">30</a></li>
                        <li role="presentation"><a role="menuitem" tabindex="-1" href="?querycode=${departement.code}&queryintitule=${departement.intitule}&size=40">40</a></li>
                        <li role="presentation"><a role="menuitem" tabindex="-1" href="?querycode=${departement.code}&queryintitule=${departement.intitule}&size=50">50</a></li>
                    </ul>
                </div>
                <table class="table table-condensed table-hover table-bordered">
                    <thead class="text-center btn-primary">
                        <tr>
                            <th> <span class="btn"> <spring:message code="departement.code" /> </span> </th>
                            <th> <span class="btn"> <spring:message code="departement.intitule" /> </span> </th>
                            <th> <span class="btn"> <spring:message code="departement.agence" /> </span> </th>
                            <th> <span class="btn"> <spring:message code="action.titre" /> </span> </th>
                        </tr>
                    </thead>
                    <tbody>
                        <spring:url value="/service/new" var="departementNew" />
                        <spring:url value="/" var="accueil" />

                        <c:if test="${departements.size() eq 0}">
                            <tr>
                                <td class="text-center label-danger" colspan="4">
                                    <spring:message code="empty.data" />
                                </td>
                            </tr>
                        </tbody>
                    </table>

                    <div class="row">
                        <a href="${departementNew}" class="btn btn-primary btn-sm">
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

                <c:if test="${departements.size() ne 0}">
                    <c:forEach items="${departements}" var="departement">

                        <spring:url htmlEscape="true" value="/service/${departement.id}/show" var="show" />
                        <spring:url htmlEscape="true" value="/service/${departement.id}/edit" var="edit" />

                        <tr>
                            <td>${departement.code}</td>
                            <td>${departement.intitule}</td>
                            <td>${departement.agence.intitule}</td>
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
                    </c:forEach>

                    </tbody>
                    </table>
                    <div class="row">
                        <a href="${departementNew}" class="btn btn-primary btn-sm">
                            <span class="glyphicon glyphicon-new-window"></span>
                            <spring:message code="action.nouveau" />
                        </a>
                        <div class="pull-right">
                            <ul class="pager">

                                <li>
                                    <a href="?querycode=${departement.code}&queryintitule=${departement.intitule}&page=0&size=${size}" <c:if test="${page eq 0}">class ="btn btn-sm disabled"</c:if>>
                                            <span class="glyphicon glyphicon-fast-backward"></span>
                                        </a>
                                    </li>
                                    <li>
                                        <a href="?querycode=${departement.code}&queryintitule=${departement.intitule}&page=${page-1}&size=${size}" <c:if test="${page eq 0}">class ="btn btn-sm disabled"</c:if>>
                                            <span class="glyphicon glyphicon-backward"></span>
                                        </a>
                                    </li>
                                    <li>
                                        <input type="text" class="pager_detail text-center" readonly value="${page+1}/${Totalpage}"/>
                                </li>
                                <li>
                                    <a href="?querycode=${departement.code}&queryintitule=${departement.intitule}&page=${page+1}&size=${size}" <c:if test="${page+1 eq Totalpage}">class ="btn btn-sm disabled"</c:if>>
                                            <span class="glyphicon glyphicon-forward"></span>
                                        </a>
                                    </li>
                                    <li>
                                        <a href="?querycode=${departement.code}&queryintitule=${departement.intitule}&page=${Totalpage-1}&size=${size}" <c:if test="${page+1 eq Totalpage}">class ="btn btn-sm disabled"</c:if>>
                                            <span class="glyphicon glyphicon-fast-forward"></span>
                                        </a>
                                    </li>
                                </ul>
                            </div>
                        </div>
                    </div>
                    <div class="col-md-3">
                        <div>
                            <h3>
                            <spring:message code="action.rechercher" />
                        </h3>
                        <hr/>
                    </div>
                    <spring:url value="/service/" var="service_home"
                                htmlEscape="true" />

                    <form:form method="get" commandName="service" action="${service_home}">
                        <div class="form-group">
                            <label>
                                <spring:message code="departement.code" />
                            </label>
                            <input type="text" class="form-control input-sm" name="querycode"/>
                            <input type="hidden" value="${size}" name="size"/>
                        </div>
                        <div class="form-group">
                            <label>
                                <spring:message code="departement.intitule" />
                            </label>
                            <input type="text"  class="form-control input-sm" name="queryintitule"/>
                        </div>
                        <hr/>
                        <button class="btn btn-default btn-sm">
                            <span class="glyphicon glyphicon-search"></span> <spring:message code="search"/></button>
                            <spring:url value="/service/" htmlEscape="true" var="service_home" />
                        <a href="${service_home}" class="btn btn-default btn-sm">
                            <span class="glyphicon glyphicon-refresh"></span>
                            <spring:message code="search.delete" />
                        </a>
                    </form:form>
                </div>
            </c:if>
        </div>

    </tiles:putAttribute>
</tiles:insertDefinition>





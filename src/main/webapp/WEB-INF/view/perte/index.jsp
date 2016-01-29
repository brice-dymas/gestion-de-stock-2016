<%--
    Document   : index
    Created on : 29 jan. 2016, 07:49:47
    Author     : sando
--%>

<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>



<tiles:insertDefinition name="layout">
    <tiles:putAttribute name="body">
        <div class="row">
            <div class="col-md-9">
                <div class="row">
                    <spring:message code="perte.list" />
                    <hr/>
                </div>
                <div class="dropdown pull-right ">
                    <button class="btn btn-default dropdown-toggle" type="button" id="dropdownMenu1" data-toggle="dropdown" aria-expanded="true">
                        <spring:message code="search.taille" />
                        : ${size}&nbsp;
                        <span class="caret"></span>
                    </button>
                    <ul class="dropdown-menu" role="menu" aria-labelledby="dropdownMenu1">
                        <li role="presentation"><a role="menuitem" tabindex="-1" href="?size=5">5</a></li>
                        <li role="presentation"><a role="menuitem" tabindex="-1" href="?size=10">10</a></li>
                        <li role="presentation"><a role="menuitem" tabindex="-1" href="?size=20">20</a></li>
                        <li role="presentation"><a role="menuitem" tabindex="-1" href="?size=30">30</a></li>
                        <li role="presentation"><a role="menuitem" tabindex="-1" href="?size=40">40</a></li>
                        <li role="presentation"><a role="menuitem" tabindex="-1" href="?size=50">50</a></li>
                    </ul>
                </div>
                <table class="table table-condensed table-hover table-bordered">
                    <thead class="text-center btn-primary">
                        <tr>
                            <th> <span class="btn"> <spring:message code="perte.numero" /> </span> </th>
                            <th> <span class="btn"> <spring:message code="perte.datePerte" /> </span> </th>
                            <th> <span class="btn"> <spring:message code="perte.fourniture" /> </span> </th>
                            <th> <span class="btn"> <spring:message code="perte.quantite" /> </span> </th>
                            <th> <span class="btn"> <spring:message code="action.titre" /> </span> </th>
                        </tr>
                    </thead>
                    <tbody>
                        <spring:url value="/perte/new" var="perteNew" />

                        <c:if test="${pertes.size() eq 0}">
                            <tr>
                                <td class="text-center label-danger" colspan="5">
                                    <spring:message code="empty.data" />
                                </td>
                            </tr>
                        </tbody>
                    </table>

                    <div class="row">
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

                <c:if test="${pertes.size() ne 0}">
                    <c:forEach items="${pertes}" var="perte">
                        <tr>
                            <td>${perte.numero}</td>
                            <td>${perte.datePerte}</td>
                            <td>${perte.lot.fourniture.designation}</td>
                            <td>${perte.quantite}</td>
                            <td>
                                <spring:url value="/perte/${perte.id}/edit" htmlEscape="true" var="perte_edit" />
                                <a href="${perte_edit}" class="btn btn-primary btn-warning">
                                    <span class="glyphicon glyphicon-edit"></span>
                                    <spring:message code="action.modifier" />
                                </a>
                                &nbsp; &nbsp;
                                <spring:url value="/perte/${perte.id}/show" htmlEscape="true" var="perte_show" />
                                <a href="${perte_show}" class="btn btn-primary btn-sm">
                                    <span class="glyphicon glyphicon-open"></span>
                                    <spring:message code="action.detail" />
                                </a>
                            </td>
                        </tr>
                    </c:forEach>

                    </tbody>
                    </table>
                    <div class="row">
                        <div class="pull-right">
                            <ul class="pager">
                                <li>
                                    <a href="?page=0&size=${size}" <c:if test="${page eq 0}">class ="btn btn-sm disabled"</c:if>>
                                            <span class="glyphicon glyphicon-fast-backward"></span>
                                        </a>
                                    </li>
                                    <li>
                                        <a href="?page=${page-1}&size=${size}" <c:if test="${page eq 0}">class ="btn btn-sm disabled"</c:if>>
                                            <span class="glyphicon glyphicon-backward"></span>
                                        </a>
                                    </li>
                                    <li>
                                        <input type="text" class="pager_detail text-center" readonly value="${page+1}/${Totalpage}"/>
                                </li>
                                <li>
                                    <a href="?page=${page+1}&size=${size}" <c:if test="${page+1 eq Totalpage}">class ="btn btn-sm disabled"</c:if>>
                                            <span class="glyphicon glyphicon-forward"></span>
                                        </a>
                                    </li>
                                    <li>
                                        <a href="?page=${Totalpage-1}&size=${size}" <c:if test="${page+1 eq Totalpage}">class ="btn btn-sm disabled"</c:if>>
                                            <span class="glyphicon glyphicon-fast-forward"></span>
                                        </a>
                                    </li>
                                </ul>
                            </div>
                        </div>
                    </div>
            </c:if>
        </div>

    </tiles:putAttribute>
</tiles:insertDefinition>

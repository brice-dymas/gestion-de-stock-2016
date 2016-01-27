<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>


<tiles:insertDefinition name="layout">
    <tiles:putAttribute name="body">

        <div class="row">
            <div class="col-md-9">

                <div>
                    <h3>
                        <spring:message code="user.liste" />
                    </h3>
                    <hr/>
                </div>

                <div class="dropdown pull-right ">
                    <button class="btn btn-default dropdown-toggle" type="button" id="dropdownMenu1" data-toggle="dropdown" >
                        <spring:message code="search.taille" />
                        : ${size}&nbsp;
                        <span class="caret"></span>
                    </button>
                    <ul class="dropdown-menu" role="menu" aria-labelledby="dropdownMenu1">
                        <li role="presentation"><a role="menuitem" tabindex="-1" href="?&querynom=${user.user.nom}&size=5">5</a></li>
                        <li role="presentation"><a role="menuitem" tabindex="-1" href="?&querynom=${user.user.nom}&size=10">10</a></li>
                        <li role="presentation"><a role="menuitem" tabindex="-1" href="?&querynom=${user.user.nom}&size=20">20</a></li>
                        <li role="presentation"><a role="menuitem" tabindex="-1" href="?&querynom=${user.user.nom}&size=30">30</a></li>
                        <li role="presentation"><a role="menuitem" tabindex="-1" href="?&querynom=${user.user.nom}&size=40">40</a></li>
                        <li role="presentation"><a role="menuitem" tabindex="-1" href="?&querynom=${user.user.nom}&size=50">50</a></li>
                    </ul>
                </div>
                <table class="table table-condensed table-hover table-bordered">
                    <thead class=" text-center btn-primary" >
                        <tr>
                            <th>
                                <span class="btn">
                                    <spring:message code="user.nom" />
                                </span>
                            </th>
                            <th>
                                <span class="btn">
                                    <spring:message code="user.role" />
                                </span>
                            </th>
                            <th>
                                <span class="btn">
                                    <spring:message code="user.username" />
                                </span>
                            </th>
                            <th>
                                <span class="btn">
                                    <spring:message code="action.titre" />
                                </span>
                            </th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:if test="${users.size() eq 0}">
                            <tr>
                                <td class="text-center" colspan="3">
                                    <spring:message code="empty.data" />
                                </td>
                            </tr>
                        </tbody>
                    </table>

                    <div class="row">
                        <div class="col-lg-12">
                            <hr/>
                            <sec:authorize access="hasRole('ROLE_ADMIN')" >
                                <spring:url value="/user/new" htmlEscape="true" var="user_new" />
                                <a href="${user_new}" class="btn btn-primary btn-sm">
                                    <span class="glyphicon glyphicon-new-window"></span>
                                    <spring:message code="action.nouveau" />
                                </a>
                            </sec:authorize>
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
                    </div>
                </c:if>

                <c:if test="${users.size() ne 0}">
                    <c:forEach items="${users}" var="user_var">
                        <c:if test="${not user_var.user.enabled}" >
                            <tr class="text text-danger">
                                <td>
                                    ${user_var.user.nom}
                                </td>
                                <td>
                                    ${user_var.getFunction(user_var.role)}
                                </td>
                                <td>
                                    ${user_var.user.username}
                                </td>

                                <td class="text-center">
                                    <sec:authorize access="hasRole('ROLE_ADMIN')" >
                                        <spring:url value="/user/${user_var.id}/edit" htmlEscape="true" var="user_edit" />
                                        <a href="${user_edit}" class="btn btn-primary btn-warning">
                                            <span class="glyphicon glyphicon-edit"></span>
                                            <spring:message code="action.modifier" />
                                        </a>
                                        &nbsp;&nbsp;
                                    </sec:authorize>
                                    <spring:url value="/user/${user_var.id}/show" htmlEscape="true" var="user_show" />
                                    <a href="${user_show}" class="btn btn-primary btn-sm">
                                        <span class="glyphicon glyphicon-open"></span>
                                        <spring:message code="action.detail" />
                                    </a>
                                </td>
                            </tr>
                        </c:if>
                        <c:if test="${user_var.user.enabled}" >
                            <tr >
                                <td>
                                    ${user_var.user.nom}
                                </td>
                                <td>
                                    ${user_var.getFunction(user_var.role)}
                                </td>
                                <td>
                                    ${user_var.user.username}
                                </td>

                                <td class="text-center">
                                    <sec:authorize access="hasRole('ROLE_ADMIN')" >
                                        <spring:url value="/user/${user_var.id}/edit" htmlEscape="true" var="user_edit" />
                                        <a href="${user_edit}" class="btn btn-primary btn-warning">
                                            <span class="glyphicon glyphicon-edit"></span>
                                            <spring:message code="action.modifier" />
                                        </a>
                                        &nbsp;&nbsp;
                                    </sec:authorize>
                                    <spring:url value="/user/${user_var.id}/show" htmlEscape="true" var="user_show" />
                                    <a href="${user_show}" class="btn btn-primary btn-sm">
                                        <span class="glyphicon glyphicon-open"></span>
                                        <spring:message code="action.detail" />
                                    </a>
                                </td>
                            </tr>
                        </c:if>

                    </c:forEach>

                    </tbody>
                    </table>
                    <div class="row">
                        <div class="col-lg-12">
                            <hr/>
                            <sec:authorize access="hasRole('ROLE_ADMIN')" >
                                <spring:url value="/user/new" htmlEscape="true" var="user_new" />
                                <a href="${user_new}" class="btn btn-primary btn-sm">
                                    <span class="glyphicon glyphicon-new-window"></span>
                                    <spring:message code="action.nouveau" />
                                </a>
                            </sec:authorize>
                            <div class="pull-right">
                                <ul class="pager">

                                    <li><a href="?querynom=${user.user.nom}&page=0&size=${size}" <c:if test="${page eq 0}">class ="btn btn-sm disabled"</c:if>>
                                                <span class="glyphicon glyphicon-fast-backward"></span>
                                            </a></li>
                                        <li><a href="?querynom=${user.user.nom}&page=${page-1}&size=${size}" <c:if test="${page eq 0}">class ="btn btn-sm disabled"</c:if>>
                                                <span class="glyphicon glyphicon-backward"></span>
                                            </a></li>
                                        <li><input type="text" class="pager_detail text-center" readonly value="${page+1}/${Totalpage}"/></li>
                                    <li><a href="?querynom=${user.user.nom}&page=${page+1}&size=${size}" <c:if test="${page+1 eq Totalpage}">class ="btn btn-sm disabled"</c:if>>
                                                <span class="glyphicon glyphicon-forward"></span>
                                            </a></li>
                                        <li><a href="?querynom=${user.user.nom}&page=${Totalpage-1}&size=${size}" <c:if test="${page+1 eq Totalpage}">class ="btn btn-sm disabled"</c:if>>
                                                <span class="glyphicon glyphicon-fast-forward"></span>
                                            </a></li>
                                    </ul>
                                </div>

                            </div>
                        </div>
                </c:if>
            </div>

            <div class="col-md-3">
                <div>
                    <h3>
                        <spring:message code="action.rechercher" />
                    </h3>
                    <hr/>
                </div>
                <spring:url value="/user/" var="user_home"
                            htmlEscape="true" />
                <form:form method="get" commandName="user" action="${user_home}">
                    <div class="input-group">
                        <label>
                            <spring:message code="user.nom" />
                        </label>
                        <input type="text" value="${user.user.nom}" class="form-control" name="querynom"/>
                    </div>
                    <hr/>
                    <button class="btn btn-default">
                        <span class="glyphicon glyphicon-search"></span> <spring:message code="search"/></button>
                        <spring:url value="/user/" htmlEscape="true" var="user" />
                    <a href="${user}" class="btn btn-default btn-sm">
                        <span class="glyphicon glyphicon-refresh"></span>
                        <spring:message code="search.delete" />
                    </a>

                </form:form>
            </div>
        </div>

    </tiles:putAttribute>
</tiles:insertDefinition>
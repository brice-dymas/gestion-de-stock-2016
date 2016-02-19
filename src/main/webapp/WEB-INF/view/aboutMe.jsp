<%--
    Document   : aboutMe
    Created on : 1 nov. 2015, 14:10:07
    Author     : Brice GUEMKAM <briceguemkam@gmail.com>
--%>

<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<tiles:insertDefinition name="layout">
    <tiles:putAttribute name="body">
        <div class="row">
            <div class="col-md-12 col-md-offset-1">
                <h3>
                    <spring:message code="user.afficher" />
                </h3>
                <div class="text text-danger">
                    <h4>
                        <c:if test="${not user.user.enabled}" >
                            <spring:message code="user.disabled" />
                        </c:if>
                    </h4>
                </div>
                <hr/>
            </div>
        </div>

        <div class="row">
            <div class="col-md-4 col-md-offset-2" id="table_show">
                <table class="table table-bordered">
                    <tbody>
                        <tr>
                            <th>
                                <spring:message code="user.nom" />
                            </th>
                            <td>${user.user.nom}</td>
                        </tr>
                        <tr>
                            <th>
                                <spring:message code="user.role" />
                            </th>
                            <td>${user.getFunction(user.role)}</td>
                        </tr>
                        <tr>
                            <th>
                                <spring:message code="user.username" />
                            </th>
                            <td>${user.user.username}</td>
                        </tr>
                    </tbody>
                </table>

            </div>
        </div>


        <div class="row">
            <div class="col-md-6 col-md-offset-2">
                <hr/>
                <spring:url value="/user/delete" var="user_delete"/>
                <form:form method="post" commandName="user" action="${user_delete}">

                    <form:hidden path="id"/>
                    <spring:url value="/user/${user.id}/editSimpleUser" var="user_edit"/>
                    <a href="${user_edit}" class="btn btn-default  btn-warning">
                        <span class="glyphicon glyphicon-edit"></span>
                        <spring:message code="action.modifier" />
                    </a>
                    <sec:authorize access="hasRole('ROLE_ADMIN')" >
                        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>

                        <spring:url value="/user/" var="user_home"/>
                        <a href="${user_home}" class="btn btn-primary  btn-primary">
                            <span class="glyphicon glyphicon-list"></span>
                            <spring:message code="user.liste" />
                        </a>
                        <c:if test="${not user.user.enabled}" >
                            <button type="submit" class="btn btn-default  btn-success">
                                <span class="glyphicon glyphicon-thumbs-up"></span>
                                <spring:message code="action.activer" />
                            </button>
                        </c:if>
                        <c:if test="${user.user.enabled}" >
                            <button type="submit" class="btn btn-default  btn-danger">
                                <span class="glyphicon glyphicon-remove-sign"></span>
                                <spring:message code="action.effacer" />
                            </button>
                        </c:if>
                    </sec:authorize>
                </form:form>
            </div>
        </div>


    </tiles:putAttribute>
</tiles:insertDefinition>
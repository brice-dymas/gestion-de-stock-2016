<%--
    Document   : show
    Created on : Dec 10, 2014, 9:48:58 AM
    Author     : sando
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
            <div class="col-md-12 col-md-offset-2">
                <h3>
                    <spring:message code="agence.show" />
                </h3>
                <hr/>
            </div>
        </div>

        <div class="row">
            <div class="col-md-4 col-md-offset-3" id="table_show">
                <table class="table table-bordered">
                    <tbody>
                        <tr>
                            <th>
                                <spring:message code="agence.code" />
                            </th>
                            <td>${agence.code}</td>
                        </tr>
                        <tr>
                            <th>
                                <spring:message code="agence.intitule" />
                            </th>
                            <td>${agence.intitule}</td>
                        </tr>
                        <tr>
                            <th>
                                <spring:message code="agence.region" />
                            </th>
                            <td>${agence.region}</td>
                        </tr>
                    </tbody>
                </table>
            </div>
        </div>


        <div class="row">
            <div class="col-md-6 col-md-offset-3">
                <hr/>
                <spring:url value="/agence/${agence.id}/delete" htmlEscape="true" var="agence_delete"/>
                <form:form method="post" commandName="agence" action="${agence_delete}">

                    <spring:url value="/agence/" var="agence_home"/>
                    <a href="${agence_home}" class="btn btn-primary  btn-primary">
                        <span class="glyphicon glyphicon-list"></span>
                        <spring:message code="agence.list" />
                    </a>
                    <form:hidden path="id"/>
                    <spring:url value="/agence/${agence.id}/edit" var="agence_edit"/>
                    <a href="${agence_edit}" class="btn btn-default  btn-warning">
                        <span class="glyphicon glyphicon-edit"></span>
                        <spring:message code="action.modifier" />
                    </a>
                    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>


                    <button type="submit" class="btn btn-default  btn-danger">
                        <span class="glyphicon glyphicon-remove-sign"></span>
                        <spring:message code="action.effacer" />
                    </button>
                </form:form>
            </div>
        </div>
    </tiles:putAttribute>
</tiles:insertDefinition>
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
            <div class="col-md-12">
                <h3>
                    <spring:message code="sortie.show" />
                </h3>
                <hr/>
            </div>
        </div>

        <div class="row">
            <div class="col-md-12 col-md-offset-0" id="table_show">
                <table class="table table-bordered">
                    <tbody>
                        <tr>
                            <th>
                                <spring:message code="operation.numero" />
                            </th>
                            <td>${sortie.numero}</td>
                            <th>
                                <spring:message code="operation.observation" />
                            </th>
                            <td>${sortie.observation}</td>
                            <th>
                                <spring:message code="departement.intitule" />
                            </th>
                            <td>${sortie.departement.intitule}</td>
                            <th>
                                <spring:message code="operation.dateOperation" />
                            </th>
                            <td>${sortie.dateOperation}</td>
                        </tr>
                    </tbody>
                </table>
            </div>
        </div>
        <div class="row">
            <div class="col-md-12">
                <fieldset>
                    <legend>
                        <spring:message code="sortie.ligneOperation" />
                    </legend>
                    <table class="table table-bordered">
                        <thead>
                            <tr class="btn-primary">
                                <th><spring:message code="fourniture.designation" /></th>
                                <th><spring:message code="ligneOperation.quantite" /></th>
                                <th><spring:message code="ligneOperation.observation" /></th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="ligneOperation" items="${ligneOperations}">
                                <tr>
                                    <td>${ligneOperation.fourniture.designation} </td>
                                    <td>${ligneOperation.quantite} </td>
                                    <td>${ligneOperation.observation} </td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </fieldset>
            </div>
        </div>

        <div class="row">
            <div class="col-md-6 col-md-offset-4">
                <hr/>
                <spring:url value="/sortie/${sortie.id}/delete" htmlEscape="true" var="sortie_delete"/>
                <form:form method="post" commandName="sortie" action="${sortie_delete}">

                    <spring:url value="/sortie/" var="sortie_home"/>
                    <a href="${sortie_home}" class="btn btn-primary  btn-primary">
                        <span class="glyphicon glyphicon-list"></span>
                        <spring:message code="sortie.list" />
                    </a>
                    <form:hidden path="id"/>
                    <spring:url value="/sortie/${sortie.id}/edit" var="sortie_edit"/>
                    <a href="${sortie_edit}" class="btn btn-default  btn-warning">
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
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
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<tiles:insertDefinition name="layout">
    <tiles:putAttribute name="body">
        <div class="row">
            <div class="col-md-12">
                <h3>
                    <spring:message code="fourniture.show" />
                </h3>
                <hr/>
            </div>
        </div>

        <div class="row">
            <div class="col-md-10 col-md-offset-1" id="table_show">
                <table class="table table-bordered">
                    <tbody>
                        <tr>
                            <th>
                                <spring:message code="fourniture.reference" />
                            </th>
                            <td>${fourniture.reference}</td>

                            <th>
                                <spring:message code="fourniture.designation" />
                            </th>
                            <td>${fourniture.designation}</td>

                            <th>
                                <spring:message code="fourniture.categorie" />
                            </th>
                            <td>${fourniture.categorie.intitule}</td>

                            <th>
                                <spring:message code="fourniture.quantite" />
                            </th>
                            <td>${fourniture.quantite}</td>

                            <th>
                                <spring:message code="fourniture.seuil" />
                            </th>
                            <td>${fourniture.seuil}</td>
                        </tr>
                    </tbody>
                </table>
            </div>
        </div>
        <hr>
        <div class="row">
            <div class="col-md-12">
                
                <!-- Nav tabs -->
                <ul class="nav nav-tabs" role="tablist">
                    <li role="presentation" class="active"><a href="#entree" aria-controls="home" role="tab" data-toggle="tab">Entrée</a></li>
                    <li role="presentation"><a href="#sortie" aria-controls="profile" role="tab" data-toggle="tab">Sortie</a></li>
                    <li role="presentation"><a href="#audit" aria-controls="messages" role="tab" data-toggle="tab">Audit</a></li>
                    <li role="presentation"><a href="#perte" aria-controls="settings" role="tab" data-toggle="tab">Perte</a></li>
                </ul>

                <!-- Tab panes -->
                <div class="tab-content">
                    <div role="tabpanel" class="tab-pane active" id="entree"><%@include file="partial/showlot.jsp" %></div>
                    <div role="tabpanel" class="tab-pane" id="sortie">...</div>
                    <div role="tabpanel" class="tab-pane" id="audit">...</div>
                    <div role="tabpanel" class="tab-pane" id="perte">...</div>
                </div>
            </div>
        </div>

        <div class="row">
            <div class="col-md-12">
                <hr/>
                <spring:url value="/fourniture/delete" var="fourniture_delete"/>
                <form:form method="post" commandName="fourniture" action="${fourniture_delete}">

                    <spring:url value="/fourniture/" var="fourniture_home"/>
                    <a href="${fourniture_home}" class="btn btn-primary  btn-primary">
                        <span class="glyphicon glyphicon-list"></span>
                        <spring:message code="fourniture.list" />
                    </a>
                    <form:hidden path="id"/>
                    <spring:url value="/fourniture/${fourniture.id}/edit" var="fourniture_edit"/>
                    <a href="${fourniture_edit}" class="btn btn-default  btn-warning">
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
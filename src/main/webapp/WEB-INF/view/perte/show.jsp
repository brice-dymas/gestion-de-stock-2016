<%--
    Document   : show
    Created on : 13 déc. 2015, 12:57:10
    Author     : samuel     <smlfolong@gmail.com>
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
                    <spring:message code="perte.show" />
                </h3>
                <hr/>
            </div>
        </div>

        <div class="row">
            <div class="col-md-4 col-md-offset-4" id="table_show">
                <table class="table table-bordered">
                    <tbody>
                        <tr>
                            <th>
                                <spring:message code="perte.numero" />
                            </th>
                            <td>${perte.numero}</td>
                        </tr>
                        <tr>
                            <th>
                                <spring:message code="perte.datePerte" />
                            </th>
                            <td>${perte.datePerte}</td>
                        </tr>
                        <tr>
                            <th>
                                <spring:message code="perte.fourniture" />
                            </th>
                            <td>${perte.ligneOperation.fourniture.designation}</td>
                        </tr>
                        <tr>
                            <th>
                                <spring:message code="fourniture.categorie" />
                            </th>
                            <td>${perte.ligneOperation.fourniture.categorie.intitule}</td>
                        </tr>
                        <tr>
                            <th>
                                <spring:message code="lot.name" />
                            </th>
                            <td>${perte.lot.numero}</td>
                        </tr>
                        <tr>
                            <th>
                                <spring:message code="fourniture.avantPerte" />
                            </th>
                            <td>${perte.quantite+perte.ligneOperation.fourniture.quantite}</td>
                        </tr>
                        <tr>
                            <th>
                                <spring:message code="fourniture.apresPerte" />
                            </th>
                            <td>${perte.ligneOperation.fourniture.quantite}</td>
                        </tr>
                        <tr>
                            <th>
                                <spring:message code="perte.quantite" />
                            </th>
                            <td>${perte.quantite}</td>
                        </tr>
                    </tbody>
                </table>
            </div>
        </div>


        <div class="row">
            <div class="col-md-6 col-md-offset-4">
                <hr/>
                <spring:url value="/perte/${perte.id}/delete" htmlEscape="true" var="perte_delete"/>
                <form:form method="post" commandName="perte" action="${perte_delete}">

                    <spring:url value="/perte/" var="perte_home"/>
                    <a href="${perte_home}" class="btn btn-primary  btn-primary">
                        <span class="glyphicon glyphicon-list"></span>
                        <spring:message code="perte.list" />
                    </a>
                    <form:hidden path="id"/>
                    <spring:url value="/perte/${perte.id}/edit" var="perte_edit"/>
                    <a href="${perte_edit}" class="btn btn-default  btn-warning">
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
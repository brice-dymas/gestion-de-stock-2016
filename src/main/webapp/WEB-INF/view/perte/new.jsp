<%--
    Document   : new
    Created on : 13 déc. 2015, 12:56:38
    Author     : samuel     < smlfolong@gmail.com >
--%>

<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>


<tiles:insertDefinition name="layout">
    <tiles:putAttribute name="body">
        <div class="row">
            <div class="col-md-12">
                <h3>
                    <spring:message code="perte.infos" />
               	</h3>
                <hr/>
            </div>
        </div>

        <div class="row">
            <div class="col-md-12 col-lg-offset-0" id="table_show">
                <table class="table table-bordered">
                    <tbody>
                        <tr>
                            <th>
                                <spring:message code="fourniture.designation"/>
                            </th>
                            <td>${perte.ligneOperation.fourniture.designation}</td>
                            <th>
                                <spring:message code="perte.quantite"/>
                            </th>
                            <td>${fourniture.quantite}</td>
                            <th>
                                <spring:message code="ligneOperation.quantiteEcart"/>
                            </th>
                            <td>${perte.ligneOperation.quantiteEcart}</td>
                        </tr>
                    </tbody>
                </table>
            </div>
            <hr/>
        </div>

        <spring:url value="/perte/create" var="perte_create" htmlEscape="true" />

        <form:form method="post" commandName="equilibre" action="${perte_create}?${_csrf.parameterName}=${_csrf.token}">
            <div class="row">
                <div class="col-md-12">
                    <fieldset>
                        <legend>
                            <spring:message code="perte.equilibre" />
                        </legend>

                        <div class="col-md-6 col-md-offset-3">
                            <div class="form-group">
                                <form:label path="" for="quantite">
                                    <spring:message code="perte.quantite" />
                                </form:label>
                                <form:input id="quantite" path="quantite" cssClass="form-control input-sm" />
                                <form:errors path="quantite" cssClass="text-danger" />
                            </div>
                        </div>
                    </fieldset>
                </div>
            </div>

            <div class="row">
                <div class="col-md-12">
                    <hr/>
                    <button type="submit" class="btn btn-sm btn-danger">
                        <span class="glyphicon glyphicon-save"></span>
                        <spring:message code="action.enregistrer"/>
                    </button>
                    <spring:url value="/perte/" htmlEscape="true" var="perte_home" />
                    <a href="${perte_home}" class="btn btn-sm btn-default">
                        <span class="glyphicon glyphicon-list"></span>
                        <spring:message code="perte.list" />
                    </a>
                </div>
            </div>
        </form:form>
    </tiles:putAttribute>
</tiles:insertDefinition>
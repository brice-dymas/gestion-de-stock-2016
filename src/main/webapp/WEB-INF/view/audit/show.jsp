<%--
    Document   : show
    Created on : 11 déc. 2015, 09:47:16
    Author     : samuel     < smlfolong@gmail.com >
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
                    <spring:message code="audit.show" />
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
                            <td>${audit.numero}</td>
                            <th>
                                <spring:message code="operation.dateOperation" />
                            </th>
                            <td>${audit.dateOperation}</td>
                        </tr>
                    </tbody>
                </table>
            </div>
        </div>
        <div class="row">
            <div class="col-md-12">
                <fieldset>
                    <legend>
                        <spring:message code="audit.ligneOperations" />
                    </legend>
                    <table class="table table-bordered">
                        <thead>
                            <tr class="btn-primary">
                                <th><spring:message code="fourniture.designation" /></th>
                                <th><spring:message code="ligneOperation.quantite" /></th>
                                <th><spring:message code="ligneOperation.quantitePhysique" /></th>
                                <th><spring:message code="ligneOperation.quantiteEcart" /></th>
                                <th><spring:message code="action.titre" /></th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="ligneOperation" items="${ligneOperations}">
                                <tr>
                                    <td>${ligneOperation.fourniture.designation} </td>
                                    <td>${ligneOperation.fourniture.quantite} </td>
                                    <td>${ligneOperation.quantitePhysique} </td>
                                    <td>${ligneOperation.quantiteEcart*-1} </td>
                                    <td>
                                        <c:choose>
                                            <c:when test="${ligneOperation.quantiteEcart < 0}">
                                                <c:if test="${ligneOperation.fourniture.manque > 0}" >
                                                    <spring:url value="/entree/${ligneOperation.id}/equilibre" htmlEscape="true" var="entree_new" />
                                                    <a href="${entree_new}" class="btn btn-success btn-sm">
                                                        <span class="glyphicon glyphicon-open"></span>
                                                        <spring:message code="action.equilibre" />
                                                    </a>
                                                </c:if>
                                                <c:if test="${ligneOperation.fourniture.manque eq 0}" >
                                                    <spring:message code="stock.ok" />
                                                </c:if>
                                            </c:when>
                                            <c:when test="${ligneOperation.quantiteEcart > 0}">
                                                <c:if test="${ligneOperation.fourniture.perte > 0}" >
                                                    <spring:url value="/perte/${ligneOperation.id}/new" htmlEscape="true" var="perte_new" />
                                                    <a href="${perte_new}" class="btn btn-danger btn-sm">
                                                        <span class="glyphicon glyphicon-open"></span>
                                                        <spring:message code="action.perte" />
                                                    </a>
                                                </c:if>
                                                <c:if test="${ligneOperation.fourniture.perte eq 0}" >
                                                    <spring:message code="stock.ok" />
                                                </c:if>
                                            </c:when>
                                            <c:otherwise>
                                                <spring:message code="stock.ok" />
                                            </c:otherwise>
                                        </c:choose>
                                    </td>
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
                <spring:url value="/audit/${audit.id}/delete" htmlEscape="true" var="audit_delete"/>
                <form:form method="post" commandName="audit" action="${audit_delete}">

                    <spring:url value="/audit/" var="audit_home"/>
                    <a href="${audit_home}" class="btn btn-primary  btn-primary">
                        <span class="glyphicon glyphicon-list"></span>
                        <spring:message code="audit.list" />
                    </a>
                    <form:hidden path="id"/>
                    <spring:url value="/audit/${audit.id}/edit" var="audit_edit"/>
                    <a href="${audit_edit}" class="btn btn-default  btn-warning">
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
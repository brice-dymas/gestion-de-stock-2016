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
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<tiles:insertDefinition name="layout">
    <tiles:putAttribute name="body">
        <div class="row">
            <div class="col-md-12">
                <h3>
                    <spring:message code="entree.show" />
                    <c:if test="${not empty idaudit}">
                       : (Resultat d'un audit)
                    </a>
                    </c:if>
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
                                <spring:message code="entree.numero" />
                            </th>
                            <td>${entree.numero}</td>
                            <th>
                                <spring:message code="entree.categorie" />
                            </th>
                            <td>${entree.categorie.intitule}</td>
                            <th>
                                <spring:message code="entree.dateEntree" />
                            </th>
                            <td> <fmt:formatDate value="${entree.dateEntree}" pattern="dd-MM-yyyy"/> </td>
                            <th>
                                <spring:message code="entree.user" />
                            </th>
                            <td>${entree.user.user.nom}</td>
                        </tr>
                    </tbody>
                </table>
            </div>
        </div>
        <div class="row">
            <div class="col-md-12">
                <fieldset>
                    <legend>
                        <spring:message code="entree.lots" />
                    </legend>
                    <table class="table table-bordered">
                        <thead>
                            <tr class="btn-primary">
                                <th><spring:message code="lot.numero" /></th>
                                <th><spring:message code="fourniture.designation" /></th>
                                <th><spring:message code="lot.dateEntree" /></th>
                                <th><spring:message code="lot.quantite" /></th>
                                <th><spring:message code="lot.prixUnitaire" /></th>
                                <th><spring:message code="lot.prixUnitaireVente" /></th>
                                <th><spring:message code="lot.motantTotal" /></th>
                                <th><spring:message code="lot.etat" /></th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:if test="${0 eq lots.size()}" >
                                <tr>
                                    <td class="text-center label-danger" colspan="8">
                                        <spring:message code="empty.data" />
                                    </td>
                                </tr>
                            </tbody>
                        </table>
                    </c:if>
                    <c:if test="${0 ne lots.size()}" >
                        <c:forEach var="lot" items="${lots}">
                            <tr>
                                <td>${lot.numero}</td>
                                <td>${lot.fourniture.designation} </td>
                                <td> <fmt:formatDate value="${lot.dateEntree}" pattern="dd-MM-yyyy" /> </td>
                                <td> <fmt:formatNumber value="${lot.quantite}" pattern="#,##0" /> </td>
                                <td> <fmt:formatNumber value="${lot.prixUnitaire}" pattern="#,##0" /> </td>
                                <td> <fmt:formatNumber value="${lot.prixVenteUnitaire}" pattern="#,##0" /> </td>
                                <td> <fmt:formatNumber value="${lot.totalMontant}" pattern="#,##0" /> </td>
                                <td>${lot.etat} </td>
                            </tr>
                        </c:forEach>
                        </tbody>
                        </table>
                    </c:if>
                </fieldset>
            </div>
        </div>

        <div class="row">
            <div class="col-md-6 col-md-offset-4">
                <hr/>
                <spring:url value="/entree/${entree.id}/delete" htmlEscape="true" var="entree_delete"/>
                <form:form method="post" commandName="entree" action="${entree_delete}">

                    <spring:url value="/entree/" var="entree_home"/>
                    <a href="${entree_home}" class="btn btn-primary  btn-primary">
                        <span class="glyphicon glyphicon-list"></span>
                        <spring:message code="entree.list" />
                    </a>
                    <form:hidden path="id"/>
                    
                    <c:if test="${not empty idaudit}">
                        <spring:url value="/audit/${idaudit}/show" var="entree_audit"/>
                        <a href="${entree_audit}" class="btn btn-default  btn-warning">
                        <span class="glyphicon glyphicon-edit"></span>
                        Consulter l'audit
                    </a>
                    </c:if>
                    <spring:url value="/entree/${entree.id}/edit" var="entree_edit"/>
                    <a href="${entree_edit}" class="btn btn-default  btn-warning">
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
<%--
    Document   : edit
    Created on : 13 déc. 2015, 12:57:22
    Author     : samuel     <smlfolong@gmail.com>
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
                    <spring:message code="perte.edit" /> : ${ligneOperation.fourniture.designation}
               	</h3>
                <hr/>
            </div>
        </div>

        <spring:url value="/perte/${perte.id}/update" var="perte_update" htmlEscape="true" />

        <form:form method="post" commandName="perteForm" action="${perte_update}?${_csrf.parameterName}=${_csrf.token}">
            <div class="row">
                <div class="col-md-12">
                    <div class="form-group">
                        <form:errors path="*" cssClass="text-danger"/>
                    </div>
                </div>
                <hr/>

                <div class="row">
                    <div class="col-md-12">
                        <fieldset>
                            <legend><spring:message code="perte.lots" />  </legend>

                            <div id="listPerte">
                                <table class="table table-bordered">
                                    <thead>
                                        <tr class="btn-primary">
                                            <th><spring:message code="perte.lot" /></th>
                                            <th><spring:message code="perte.quantite" /></th>
                                            <th><spring:message code="action.effacer" /></th>
                                        </tr>
                                    </thead>
                                    <tbody data-size="${perteForm.listPertes.size()}">

                                        <c:if test="${0 le perteForm.listPertes.size()}">

                                            <c:forEach items="${perteForm.listPertes}" varStatus="status" begin="0">

                                                <tr class="list-listPerte">
                                                    <td>
                                                        <spring:bind path="listPertes[${status.index}].lot.id">
                                                            <form:select path="${status.expression}" cssClass="form-control input-sm" >
                                                                <form:options items="${lots}" />
                                                            </form:select>
                                                            <form:errors path="${status.expression}" cssClass="text-danger"/>
                                                        </spring:bind>
                                                    </td>
                                                    <td>
                                                        <spring:bind path="listPertes[${status.index}].quantite">
                                                            <form:input path="${status.expression}" cssClass="form-control input-sm" />
                                                            <form:errors path="${status.expression}" cssClass="text-danger"/>
                                                        </spring:bind>
                                                    </td>
                                                    <td class="row-align">
                                                        <button type="button" id="removeLignePerteButton" class="btn btn-sm btn-default remove-listPerte" >
                                                            <span class="glyphicon glyphicon-minus-sign"></span>
                                                        </button>
                                                    </td>
                                                </tr>
                                            </c:forEach>
                                        </c:if>

                                        <c:if test="${0 eq perteForm.listPertes.size()}">

                                            <tr class="list-listPerte">
                                                <td>
                                                    <form:select path="listPertes[0].lot.id" cssClass="form-control input-sm" >
                                                        <form:options items="${lots}" />
                                                    </form:select>
                                                </td>
                                                <td>
                                                    <form:input path="listPertes[0].quantite" cssClass="form-control input-sm" />
                                                </td>
                                                <td class="row-align">
                                                    <button type="button" id="removeLignePerteButton"  class="btn btn-sm btn-default remove-listPerte" >
                                                        <span class="glyphicon glyphicon-minus-sign"></span>
                                                    </button>
                                                </td>
                                            </tr>

                                        </c:if>

                                    </tbody>
                                </table>

                                <button type="button" id="addLignePerteButton" class="btn btn-sm btn-default add-listPerte">
                                    <span class="glyphicon glyphicon-plus-sign"></span>
                                    <spring:message code="action.ajouter" />
                                </button>
                            </div>
                        </fieldset>
                    </div>
                </div>

                <div class="row">
                    <div class="col-md-12">
                        <hr/>
                        <button type="submit" class="btn btn-sm btn-danger" >
                            <span class="glyphicon glyphicon-save"></span>
                            <spring:message code="action.enregistrer" />
                        </button>
                        <spring:url value="/perte/" htmlEscape="true" var="perte_home" />
                        <a href="${perte_home}" class="btn btn-sm btn-default">
                            <span class="glyphicon glyphicon-list"></span>
                            <spring:message code="perte.list" />
                        </a>
                    </div>
                </div>
            </form:form>

            <script src="<c:url value="/resources/js/jquery.dynamiclist.min.js" />"></script>
            <script src="<c:url value="/resources/js/jquery-ui.js" />"></script>
            <script src="<c:url value="/resources/js/bootstrap-filestyle.js" />"></script>
            <script type="text/javascript">

                $(document).ready(function () {

                    $("#listPerte").dynamiclist({
                        itemClass: "list-listPerte",
                        addClass: "add-listPerte",
                        removeClass: "remove-listPerte",
                        withEvents: true
                    });
                });

            </script>

        </tiles:putAttribute>
    </tiles:insertDefinition>
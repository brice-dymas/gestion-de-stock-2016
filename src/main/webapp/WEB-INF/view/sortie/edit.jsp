<%--
    Document   : new
    Created on : Nov 07, 2015, 9:20:13 AM
    Author     : sando
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
                    <spring:message code="sortie.new" />
               	</h3>
                <hr/>
            </div>
        </div>

        <spring:url
            value="/sortie/${sortieForm.sortie.id}/update"
            var="sortie_create"
            htmlEscape="true" />


        <form:form method="post" commandName="sortieForm" action="${sortie_create}?${_csrf.parameterName}=${_csrf.token}">
            <div class="row">
                <div class="col-md-6">
                    <div class="form-group">
                        <form:label for="obs" path="">
                            <spring:message code="operation.observation" />
                        </form:label>
                        <form:input id="obs" path="sortie.observation" cssClass="form-control input-sm"/>
                        <form:errors path="sortie.observation" cssClass="text-danger"/>
                    </div>
                </div>
                <div class="col-md-4">
                    <div class="form-group">
                        <form:label for="dept" path="">
                            <spring:message code="operation.departement" />
                        </form:label>
                        <form:select id="dept" path="sortie.departement.id" cssClass="form-control">
                            <form:options items="${departements}" />
                        </form:select>
                        <form:errors path="sortie.departement.id" cssClass="text-danger" />
                        <form:hidden path="sortie.id" id="id"/>
                    </div>
                </div>
            </div>
            <hr/>

            <div class="row">
                <div class="col-md-12">
                    <fieldset>
                        <legend><spring:message code="sortie.ligneOperation" />  </legend>

                        <div id="lot">
                            <table class="table table-bordered">
                                <thead>
                                    <tr class="btn-primary">
                                        <th><spring:message code="operation.fourniture" /></th>
                                        <th><spring:message code="ligneOperation.quantite" /></th>
                                        <th><spring:message code="ligneOperation.observation" /></th>
                                        <th><spring:message code="action.effacer" /></th>
                                    </tr>
                                </thead>
                                <tbody data-size="${sortieForm.ligneOperations.size()}">
                                    <c:if test="${0 le sortieForm.ligneOperations.size()}">

                                        <c:forEach items="${sortieForm.ligneOperations}" varStatus="status" begin="0">
                                            <tr class="list-lot">
                                                <td>
                                                    <spring:bind path="ligneOperations[${status.index}].fourniture.id">
                                                        <form:select path="${status.expression}" cssClass="form-control input-sm" >
                                                            <form:options items="${lots}" />
                                                        </form:select>
                                                        <form:errors path="${status.expression}" cssClass="text-danger"/>
                                                    </spring:bind>
                                                </td>
                                                <td>
                                                    <spring:bind path="ligneOperations[${status.index}].id">
                                                        <form:hidden path="${status.expression}"/>
                                                    </spring:bind>

                                                    <spring:bind path="ligneOperations[${status.index}].quantite">
                                                        <form:input path="${status.expression}" cssClass="form-control input-sm" />
                                                        <form:errors path="${status.expression}" cssClass="text-danger"/>
                                                    </spring:bind>
                                                </td>
                                                <td>
                                                    <spring:bind path="ligneOperations[${status.index}].observation">
                                                        <form:input path="${status.expression}" cssClass="form-control input-sm" />
                                                        <form:errors path="${status.expression}" cssClass="text-danger"/>
                                                    </spring:bind>
                                                </td>
                                                <td class="row-align">
                                                    <button type="button" id="removeLotButton" class="btn btn-sm btn-default remove-lot" >
                                                        <span class="glyphicon glyphicon-minus-sign"></span>
                                                    </button>
                                                </td>
                                            </tr>
                                        </c:forEach>
                                    </c:if>

                                    <c:if test="${0 eq sortieForm.ligneOperations.size()}">
                                        <tr class="list-lot">
                                            <td>
                                                <form:select path="ligneOperations[0].fourniture.id" cssClass="form-control input-sm" >
                                                    <form:options items="${lots}" />
                                                </form:select>
                                            </td>
                                            <td>
                                                <form:input path="ligneOperations[0].quantite" cssClass="form-control input-sm" />
                                            </td>
                                            <td>
                                                <form:input path="ligneOperations[0].observation" cssClass="form-control input-sm" />
                                            </td>
                                            <td class="row-align">
                                                <button type="button" id="removeLotButton"  class="btn btn-sm btn-default remove-lot" >
                                                    <span class="glyphicon glyphicon-minus-sign"></span>
                                                </button>
                                            </td>
                                        </tr>
                                    </c:if>
                                </tbody>
                            </table>

                            <button type="button" id="addLotButton" class="btn btn-sm btn-default add-lot">
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
                    <spring:url value="/sortie/" htmlEscape="true"
                                var="sortie_home" />
                    <a href="${sortie_home}" class="btn btn-sm btn-default">
                        <span class="glyphicon glyphicon-list"></span>
                        <spring:message code="sortie.list" />
                    </a>
                </div>
            </div>
        </form:form>

        <script src="<c:url value="/resources/js/jquery.dynamiclist.min.js" />"></script>
        <script src="<c:url value="/resources/js/jquery-ui.js" />"></script>
        <script src="<c:url value="/resources/js/bootstrap-filestyle.js" />"></script>
        <script type="text/javascript">

            $(document).ready(function () {
                $("#lot").dynamiclist({
                    itemClass: "list-lot",
                    addClass: "add-lot",
                    removeClass: "remove-lot"
                });
            });

        </script>
    </tiles:putAttribute>
</tiles:insertDefinition>
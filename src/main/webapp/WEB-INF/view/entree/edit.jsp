<%--
    Document   : new
    Created on : 2 nov. 2015, 10:10:17
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
            <div>
                <h3>
                    <spring:message code="entree.edit" />
               	</h3>
                <hr/>
            </div>
        </div>

        <spring:url
            value="/entree/${entreeForm.entree.id}/update"
            var="entree_update"
            htmlEscape="true" />


        <form:form method="post" commandName="entreeForm" action="${entree_update}?${_csrf.parameterName}=${_csrf.token}">
            <div class="row">
                <div class="col-md-4">
                    <div class="form-group">
                        <form:label for="date" path="">
                            <spring:message code="entree.dateEntree" />
                        </form:label>
                        <form:input readonly="true" id="date" path="entree.dateEntree" cssClass="form-control input-sm"/>
                        <form:errors path="entree.dateEntree" cssClass="text-danger"/>
                    </div>
                </div>
                <div class="col-md-6">
                    <div class="form-group">
                        <form:label for="categorie" path="">
                            <spring:message code="entree.categorie" />
                        </form:label>
                        <form:input readonly="true" enabled="false" id="date" path="entree.categorie.intitule" cssClass="form-control input-sm"/>
                        <form:errors path="entree.categorie.id" cssClass="text-danger" />
                        <c:if test="${not empty entreeForm.entree.ligneAuditId}">
                            <form:hidden id="ligneAuditId" path="entree.ligneAuditId"/>
                        </c:if>
                    </div>
                </div>
            </div>
            <hr/>

            <div class="row">
                <div class="col-md-12">
                    <fieldset>
                        <legend><spring:message code="entree.lots" />  </legend>

                        <div id="lot">
                            <table class="table table-bordered">
                                <thead>
                                    <tr class="btn-primary">
                                        <th><spring:message code="fourniture.designation" /></th>
                                        <th><spring:message code="lot.quantite" /></th>
                                        <th><spring:message code="lot.prixUnitaire" /></th>
                                        <th><spring:message code="lot.prixUnitaireVente" /></th>
                                        <th><spring:message code="lot.etat" /></th>
                                        <th><spring:message code="action.effacer" /></th>
                                    </tr>
                                </thead>
                                <tbody data-size="${entreeForm.listeLots.size()}">
                                    <c:if test="${0 le entreeForm.listeLots.size()}">

                                        <c:forEach items="${entreeForm.listeLots}" varStatus="status" begin="0">
                                            <tr class="list-lot">
                                                <td>
                                                    <spring:bind path="listeLots[${status.index}].fourniture.id">
                                                        <form:select path="${status.expression}" cssClass="form-control input-sm" >
                                                            <form:options items="${fournitures}" />
                                                        </form:select>
                                                        <form:errors path="${status.expression}" cssClass="text-danger"/>
                                                    </spring:bind>
                                                </td>
                                                <td>
                                                    <spring:bind path="listeLots[${status.index}].id">
                                                        <form:hidden path="${status.expression}"/>
                                                    </spring:bind>

                                                    <spring:bind path="listeLots[${status.index}].quantite">
                                                        <form:input path="${status.expression}"  cssClass="form-control input-sm" />
                                                        <form:errors path="${status.expression}" cssClass="text-danger"/>
                                                    </spring:bind>
                                                </td>
                                                <td>
                                                    <spring:bind path="listeLots[${status.index}].prixUnitaire">
                                                        <form:input path="${status.expression}" cssClass="form-control input-sm" />
                                                        <form:errors path="${status.expression}" cssClass="text-danger"/>
                                                    </spring:bind>
                                                </td>
                                                <td>
                                                    <spring:bind path="listeLots[${status.index}].prixVenteUnitaire">
                                                        <form:input path="${status.expression}" cssClass="form-control input-sm" />
                                                        <form:errors path="${status.expression}" cssClass="text-danger"/>
                                                    </spring:bind>
                                                </td>
                                                <td>
                                                    <spring:bind path="listeLots[${status.index}].etat">
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

                                    <c:if test="${0 eq entreeForm.listeLots.size()}">
                                        <tr class="list-lot">
                                            <td>
                                                <form:select path="listeLots[0].fourniture.id" cssClass="form-control input-sm" >
                                                    <form:options items="${fournitures}" />
                                                </form:select>
                                            </td>
                                            <td>
                                                <form:input path="listeLots[0].quantite" cssClass="form-control input-sm" />
                                            </td>
                                            <td>
                                                <form:input path="listeLots[0].prixUnitaire" cssClass="form-control input-sm" />
                                            </td>
                                            <td>
                                                <form:input path="listeLots[0].prixVenteUnitaire" cssClass="form-control input-sm" />
                                            </td>
                                            <td>
                                                <form:input path="listeLots[0].etat" cssClass="form-control input-sm" />
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
                    <spring:url value="/entree/" htmlEscape="true"
                                var="entree_home" />
                    <a href="${entree_home}" class="btn btn-sm btn-default">
                        <span class="glyphicon glyphicon-list"></span>
                        <spring:message code="entree.list" />
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
                    removeClass: "remove-lot",
                    withEvents: true
                });
            });

        </script>
    </tiles:putAttribute>
</tiles:insertDefinition>
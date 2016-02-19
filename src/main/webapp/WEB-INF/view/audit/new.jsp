<%--
    Document   : new
    Created on : 13 déc. 2015, 06:23:07
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
            <div>
                <h3>
                    <spring:message code="audit.new" />
               	</h3>
                <hr/>
            </div>
        </div>

        <spring:url value="/audit/create" var="audit_create" htmlEscape="true" />

        <form:form method="post" commandName="auditForm" action="${audit_create}?${_csrf.parameterName}=${_csrf.token}">
            <div class="row">
                <div class="col-md-12">
                    <div class="form-group">
                        <form:errors path="*" cssClass="text-danger"/>
                    </div>
                </div>
                <div class="row">
                    <div class="col-md-4">
                        <div class="form-group">
                            <form:label for="date" path="">
                                <spring:message code="operation.dateOperation" />
                            </form:label>
                            <form:input id="date" path="audit.dateOperation" cssClass="form-control input-sm"/>
                            <form:errors path="audit.dateOperation" cssClass="text-danger"/>
                        </div>
                    </div>
                    <div class="col-md-4">
                        <div class="form-group">
                            <form:label for="observation" path="">
                                <spring:message code="operation.observation" />
                            </form:label>
                            <form:input id="date" path="audit.observation" cssClass="form-control input-sm"/>
                            <form:errors path="audit.observation" cssClass="text-danger"/>
                        </div>
                    </div>
                    <div class="col-md-4">
                        <div class="form-group">
                            <form:label for="dept" path="">
                                <spring:message code="operation.departement" />
                            </form:label>
                            <form:select id="dept" path="audit.departement.id" cssClass="form-control">
                                <form:options items="${departements}" />
                            </form:select>
                            <form:errors path="audit.departement.id" cssClass="text-danger" />
                        </div>
                    </div>
                </div>
                <hr/>

                <div class="row">

                    <div class="col-md-12">
                        <fieldset>
                            <legend><spring:message code="audit.ligneOperations" />  </legend>

                            <div id="ligneOperation">
                                <table class="table table-bordered">
                                    <thead>
                                        <tr class="btn-primary">
                                            <th><spring:message code="fourniture.designation" /></th>
                                            <th><spring:message code="ligneOperation.quantitePhysique" /></th>
                                            <th><spring:message code="ligneOperation.observation" /></th>
                                            <th><spring:message code="action.effacer" /></th>
                                        </tr>
                                    </thead>
                                    <tbody data-size="${auditForm.ligneOperations.size()}">

                                        <c:if test="${0 le auditForm.ligneOperations.size()}">

                                            <c:forEach items="${auditForm.ligneOperations}" varStatus="status" begin="0">

                                                <tr class="list-ligneOperation">
                                                    <td>
                                                        <spring:bind path="ligneOperations[${status.index}].fourniture.id">
                                                            <form:select path="${status.expression}" cssClass="form-control input-sm" >
                                                                <form:options items="${fournitures}" />
                                                            </form:select>
                                                            <form:errors path="${status.expression}" cssClass="text-danger"/>
                                                        </spring:bind>
                                                    </td>
                                                    <td>
                                                        <spring:bind path="ligneOperations[${status.index}].quantitePhysique">
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
                                                        <button type="button" id="removeLigneOperationButton" class="btn btn-sm btn-default remove-ligneOperation" >
                                                            <span class="glyphicon glyphicon-minus-sign"></span>
                                                        </button>
                                                    </td>
                                                </tr>
                                            </c:forEach>
                                        </c:if>

                                        <c:if test="${0 eq auditForm.ligneOperations.size()}">

                                            <tr class="list-ligneOperation">
                                                <td>
                                                    <form:select path="ligneOperations[0].fourniture.id" cssClass="form-control input-sm" >
                                                        <form:options items="${fournitures}" />
                                                    </form:select>
                                                </td>
                                                <td>
                                                    <form:input path="ligneOperations[0].quantitePhysique" cssClass="form-control input-sm" />
                                                </td>
                                                <td>
                                                    <form:input path="ligneOperations[0].observation" cssClass="form-control input-sm" />
                                                </td>
                                                <td class="row-align">
                                                    <button type="button" id="removeLigneOperationButton"  class="btn btn-sm btn-default remove-ligneOperation" >
                                                        <span class="glyphicon glyphicon-minus-sign"></span>
                                                    </button>
                                                </td>
                                            </tr>

                                        </c:if>

                                    </tbody>
                                </table>

                                <button type="button" id="addLigneOperationButton" class="btn btn-sm btn-default add-ligneOperation">
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
                        <spring:url value="/audit/" htmlEscape="true" var="audit_home" />
                        <a href="${audit_home}" class="btn btn-sm btn-default">
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

                    function setOnLigneOperationDatePicker() {
                        var i = 0;
                        $(".list-ligneOperation").each(function () {
                            $(this).on("focus", "#ligneOperations" + i + "\\.date", function () {
                                $(this).datepicker({
                                    changeMonth: true,
                                    changeYear: true,
                                    dateFormat: "dd/mm/yy",
                                    showButtonPanel: false
                                });
                                return false;
                            });

                            i++;
                        });


                    }

                    function setLigneOperationDatePicker(item) {

                        $(item).on("focus", "input[name$='dateOperation']", function () {
                            $(this).removeClass("hasDatepicker").datepicker({
                                changeMonth: true,
                                changeYear: true,
                                dateFormat: "dd/mm/yy",
                                showButtonPanel: false
                            });
                            return false;
                        });

                    }

                    $("#ligneOperation").dynamiclist({
                        itemClass: "list-ligneOperation",
                        addClass: "add-ligneOperation",
                        removeClass: "remove-ligneOperation",
                        withEvents: true,
                        addCallbackFn: setLigneOperationDatePicker

                    });

                    $("#date").datepicker({
                        changeMonth: true,
                        changeYear: true,
                        dateFormat: "dd/mm/yy",
                        showButtonPanel: false
                    });

                    setOnLigneOperationDatePicker();

                });

            </script>


        </tiles:putAttribute>
    </tiles:insertDefinition>
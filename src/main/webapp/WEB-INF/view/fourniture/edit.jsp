<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<tiles:insertDefinition name="layout">
    <tiles:putAttribute name="body">

        <div class="row">
            <div class="col-md-12 col-md-offset-1">
                <h3>
                    <spring:message code="fourniture.new" />
                </h3>
                <hr/>
            </div>
            <spring:url htmlEscape="true" value="/fourniture/${fourniture.id}/update" var="fourniture_update" />
            <spring:url value="/fourniture/" var="fournitures" />

            <div class="row">
                <div class="col-md-6 col-md-offset-2">
                    <form:form  method="post" commandName="fourniture" action="${fourniture_update}">
                        <div class="row">
                            <div class="form-group">
                                <form:label path="" for="reference">
                                    <spring:message code="fourniture.reference" />
                                </form:label>
                                <form:input id="reference" path="reference" cssClass="form-control" />
                                <form:errors path="reference" cssClass="text-danger" />
                            </div>
                        </div>
                        <div class="row">
                            <div class="form-group">
                                <form:label path="" for="designation">
                                    <spring:message code="fourniture.designation" />
                                </form:label>
                                <form:input id="designation" path="designation" cssClass="form-control" />
                                <form:errors path="designation" cssClass="text-danger" />
                            </div>
                        </div>
                        <div class="row">
                            <div class="form-group">
                                <form:label path="" for="seuil">
                                    <spring:message code="fourniture.seuil" />
                                </form:label>
                                <form:input id="seuil" path="seuil" cssClass="form-control" />
                                <form:errors path="seuil" cssClass="text-danger" />
                            </div>
                        </div>
                        <div class="row">
                            <div class="form-group">
                                <form:label path="" for="cat">
                                    <spring:message code="fourniture.categorie" />
                                </form:label>
                                <form:select id="cat" path="categorie.id" cssClass="form-control" >
                                    <form:options items="${categories}" />
                                </form:select>
                                <form:errors path="categorie" cssClass="text-danger" />
                            </div>
                        </div>
                        <hr />
                        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                        <div class="panel-footer">
                            <button type="submit" class="btn btn-primary btn-sm">
                                <span class="glyphicon glyphicon-save"></span>
                                <spring:message code="action.enregistrer" />
                            </button>
                            <a href="${fournitures}" class="btn btn-default btn-sm">
                                <span class="glyphicon glyphicon-list"></span>
                                <spring:message code="fourniture.list" />
                            </a>
                        </div>
                    </form:form>
                </div>
            </div>
        </div>
    </tiles:putAttribute>
</tiles:insertDefinition>
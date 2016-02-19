<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<tiles:insertDefinition name="layout">
    <tiles:putAttribute name="body">

        <div class="row">
            <div class="col-md-12 col-md-offset-2">
                <h3>
                    <spring:message code="departement.edit" />
                </h3>
                <hr/>
            </div>
            <spring:url value="/service/${departement.id}/update" var="edit" />
            <spring:url value="/service/" var="departements" />

            <div class="row">
                <div class="col-md-6 col-md-offset-3">
                    <form:form commandName="departement" action="${edit}">
                        <div class="form-group">
                            <form:label path="" for="code">
                                <spring:message code="departement.code" />
                            </form:label>
                            <form:input id="code" path="code" cssClass="form-control" />
                            <form:errors path="code" cssClass="text-danger" />
                        </div>

                        <div class="form-group">
                            <form:label path="" for="intitule">
                                <spring:message code="departement.intitule" />
                            </form:label>
                            <form:input id="intitule" path="intitule" cssClass="form-control" />
                            <form:errors path="intitule" cssClass="text-danger" />
                        </div>

                        <div class="form-group">
                            <form:label path="" for="agence">
                                <spring:message code="departement.agence" />
                            </form:label>
                            <form:select id="agence" path="agence.id" cssClass="form-control" >
                                <form:options items="${listAgence}"  />
                            </form:select>
                            <form:errors path="agence.id" cssClass="text-danger" />
                        </div>

                        <hr />
                        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                        <div class="panel-footer">
                            <button type="submit" class="btn btn-primary btn-sm">
                                <span class="glyphicon glyphicon-save"></span>
                                <spring:message code="action.enregistrer" />
                            </button>

                            <a href="${departements}" class="btn btn-default btn-sm">
                                <span class="glyphicon glyphicon-list"></span>
                                <spring:message code="departement.list" />
                            </a>
                        </div>
                    </form:form>
                </div>
            </div>
        </div>
    </tiles:putAttribute>
</tiles:insertDefinition>
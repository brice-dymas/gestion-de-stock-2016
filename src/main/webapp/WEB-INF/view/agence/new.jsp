<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<tiles:insertDefinition name="layout">
    <tiles:putAttribute name="body">

        <div class="row">
            <div class="col-md-offset-2">
                <h3>
                    <spring:message code="agence.new" />
                </h3>
                <hr/>
            </div>
            <spring:url value="/agence/create" var="agence_create" htmlEscape="true" />
            <spring:url value="/agence/" var="agences" />

            <div class="row">
                <div class="col-md-6 col-md-offset-3">
                    <form:form method="post" commandName="agence" action="${agence_create}">
                        <div class="form-group">
                            <form:label path="" for="code">
                                <spring:message code="agence.code" />
                            </form:label>
                            <form:input id="code" path="code" cssClass="form-control" />
                            <form:errors path="code" cssClass="text-danger" />
                        </div>

                        <div class="form-group">
                            <form:label path="" for="intitule">
                                <spring:message code="agence.intitule" />
                            </form:label>
                            <form:input id="intitule" path="intitule" cssClass="form-control" />
                            <form:errors path="intitule" cssClass="text-danger" />
                        </div>

                        <div class="form-group">
                            <form:label path="" for="region">
                                <spring:message code="agence.region" />
                            </form:label>
                            <form:input id="region" path="region" cssClass="form-control" />
                            <form:errors path="region" cssClass="text-danger" />
                        </div>
                        <hr />
                        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                        <div class="panel-footer">
                            <button type="submit" class="btn btn-primary btn-sm">
                                <span class="glyphicon glyphicon-save"></span>
                                <spring:message code="action.enregistrer" />
                            </button>

                            <a href="${agences}" class="btn btn-default btn-sm">
                                <span class="glyphicon glyphicon-list"></span>
                                <spring:message code="agence.list" />
                            </a>
                        </div>
                    </form:form>
                </div>
            </div>
        </div>
    </tiles:putAttribute>
</tiles:insertDefinition>
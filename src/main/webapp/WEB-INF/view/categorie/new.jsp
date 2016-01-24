<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<tiles:insertDefinition name="layout">
    <tiles:putAttribute name="body">

        <div class="row">
            <div class="col-md-12">
                <h4>
                    <spring:message code="categorie.new" />
                </h4>
                <hr/>
            </div>
            <spring:url htmlEscape="true" value="/categorie/create" var="categorie_create" />
            <spring:url value="/categorie/" var="categories" />

            <div class="row">
                <div class="col-md-6 col-md-offset-3">
                    <form:form  method="post" commandName="categorie" action="${categorie_create}">
                        <div class="form-group">
                            <form:label path="" for="intitule">
                                <spring:message code="categorie.intitule" />
                            </form:label>
                            <form:input id="intitule" path="intitule" cssClass="form-control" />
                            <form:errors path="intitule" cssClass="text-danger" />
                        </div>
                        <hr />
                        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                        <div class="panel-footer">
                            <button type="submit" class="btn btn-primary btn-sm">
                                <span class="glyphicon glyphicon-save"></span>
                                <spring:message code="action.enregistrer" />
                            </button>
                            <a href="${categories}" class="btn btn-default btn-sm">
                                <span class="glyphicon glyphicon-list"></span>
                                <spring:message code="categorie.list" />
                            </a>
                        </div>
                    </form:form>
                </div>
            </div>
        </div>
    </tiles:putAttribute>
</tiles:insertDefinition>
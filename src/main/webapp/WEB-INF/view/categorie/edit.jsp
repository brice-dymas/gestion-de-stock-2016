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
                    <spring:message code="categorie.edit" />
                </h3>
                <hr/>
            </div>
            <spring:url htmlEscape="true" value="/categorie/${categorie.id}/update" var="edit" />
            <spring:url value="/categorie/" var="categories" />

            <div class="row">
                <div class="col-md-6 col-md-offset-2">
                    <form:form method="post" commandName="categorie" action="${edit}">
                        <div class="form-group">
                            <form:label path="" for="intitule">
                                <spring:message code="categorie.intitule" />
                            </form:label>
                            <form:input id="intitule" path="intitule" cssClass="form-control" />
                            <form:errors path="intitule" cssClass="text-danger" />
                        </div>
                        <hr />
                        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                        <div>
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
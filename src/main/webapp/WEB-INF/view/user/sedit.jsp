<%--
    Document   : edit
    Created on : Jul 21, 2015, 3:44:15 AM
    Author     : sando
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>


<tiles:insertDefinition name="layout">
    <tiles:putAttribute name="body">

        <div class="row">
            <div class="col-md-12 col-md-offset-1">
                <h4>
                    <spring:message code="user.modifier" />
                </h4>
                <hr/>
            </div>
            <div class="col-md-12">
                <h4>
                    <form:errors path="*"/>
                </h4>
                <hr/>
            </div>
        </div>

        <div class="row">
            <div class="col-md-6 col-md-offset-2">
                <spring:url value="/user/${user.id}/updateSimpleUser" var="user_update"
                            htmlEscape="true" />
                <form:form method="post" commandName="user" action="${user_update}?${_csrf.parameterName}=${_csrf.token}">
                    <div class="panel panel-default">
                        <div class="panel-body">
                            <hr/>
                            <fieldset class="form-group divider">
                                <legend class=" text-center btn-primary">
                                    <b><spring:message code="security.infos" /></b>
                                </legend>
                                <div class="row">
                                    <div class="form-group">
                                        <form:label for="password" path="">
                                            <spring:message code="user.password" /> :
                                        </form:label>
                                        <form:password id="password" path="user.password" cssClass="form-control"/>
                                        <form:errors path="user.password" cssClass="text-danger"/>
                                    </div>
                                    <div class="form-group">
                                        <form:label for="confirmPassword" path="">
                                            <spring:message code="user.confirmPassword" /> :
                                        </form:label>
                                        <form:password id="confirmPassword" path="user.confirmPassword" cssClass="form-control"/>
                                        <form:errors path="user.confirmPassword" cssClass="text-danger"/>
                                    </div>
                                </div>
                            </fieldset>
                        </div>

                        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                        <div class="panel-footer">
                            <button type="submit" class="btn btn-primary btn-sm">
                                <span class="glyphicon glyphicon-save"></span>
                                <spring:message code="action.enregistrer" />
                            </button>
                            <spring:url value="/user/" htmlEscape="true"
                                        var="user_home" />
                            <a href="${user_home}" class="btn btn-default btn-sm">
                                <span class="glyphicon glyphicon-list"></span>
                                <spring:message code="user.liste" />
                            </a>
                        </div>
                    </div>
                </form:form>
            </div>
        </div>
    </tiles:putAttribute>
</tiles:insertDefinition>
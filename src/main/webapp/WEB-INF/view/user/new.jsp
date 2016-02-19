<%--
    Document   : new
    Created on : Dec 10, 2014, 9:20:13 AM
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
                <h3>
                    <spring:message code="user.nouveau" />
                    <form:errors path="*" />
                </h3>
                <hr/>
            </div>
        </div>

        <div class="row">
            <div class="col-md-6 col-md-offset-2">
                <spring:url value="/user/create" var="user_create"
                            htmlEscape="true" />
                <form:form method="post" commandName="user" action="${user_create}">
                    <div class="panel panel-default">
                        <div class="panel-body">

                            <fieldset class="form-group divider">
                                <legend class=" text-center btn-primary">
                                    <spring:message code="user.infos" />

                                </legend>
                                <div class="row">
                                    <div class="form-group">
                                        <form:label for="nom" path="">
                                            <spring:message code="user.nom" /> :
                                        </form:label>
                                        <form:input id="nom" path="user.nom" cssClass="form-control"/>
                                        <form:errors path="user.nom" cssClass="text-danger"/>
                                    </div>
                                    <div class="form-group">
                                        <form:label for="email" path="">
                                            <spring:message code="user.email" /> :
                                        </form:label>
                                        <form:input id="email" path="user.email" cssClass="form-control"/>
                                        <form:errors path="user.email" cssClass="text-danger"/>
                                    </div>

                                    <div class="form-group">
                                        <form:label for="role" path="">
                                            <spring:message code="user.role" /> :
                                        </form:label>
                                        <form:select id="role" path="role" cssClass="form-control">
                                            <form:option value="NONE" label="+++ Select +++"/>
                                            <form:options  items="${roles}" />
                                        </form:select>
                                        <form:errors path="role" cssClass="text-danger"/>
                                    </div>
                                </div>

                            </fieldset>

                            <hr/>
                            <fieldset class="form-group divider">
                                <legend class=" text-center btn-primary">
                                    <spring:message code="security.infos" />
                                </legend>
                                <div class="row">
                                    <div class="form-group">
                                        <form:label for="username" path="">
                                            <spring:message code="user.username" /> :
                                        </form:label>
                                        <form:input id="username" path="user.username" cssClass="form-control"/>
                                        <form:errors path="user.username" cssClass="text-danger"/>
                                    </div>
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
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<spring:url value="/categorie/" htmlEscape="true" var="categorie_home" />
<spring:url value="/service/" htmlEscape="true" var="service_home" />
<spring:url value="/agence/" htmlEscape="true" var="agence_home" />
<spring:url value="/" htmlEscape="true" var="home" />

<nav class="row">
    <div style="float: right; padding: 0 15px">
        <div class="dropdown" style="display: inline-block !important">
            <button class="btn btn-default dropdown-toogle" id="dropdown-user" data-toggle="dropdown">
                <i class="glyphicon glyphicon-user"></i>
                <c:if test="${pageContext.request.userPrincipal.name != null}">
                    <spring:message code="menu.bienvenu" />

                    ${pageContext.request.userPrincipal.name}
                </c:if>
                <c:if test="${pageContext.request.userPrincipal.name == null}">
                    Bonjour Visiteur!
                </c:if>

                <i class="caret"></i>
            </button>
            <ul class="dropdown-menu" role="menu" aria-labeledby="dropdown-user">
                <li>
                    <a href="<c:url value="/welcome" />">
                        <span class="glyphicon glyphicon-user ">
                            <spring:message code="menu.profil" />
                        </span>
                    </a>
                </li>
                <li>
                    <a href="<c:url value="/" />">
                        <span class="glyphicon glyphicon-dashboard">
                            <spring:message code="menu.tableauDeBord" />
                        </span>
                    </a>
                </li>
                <li class="divider"></li>
                <li>
                    <a href="<c:url value="/logout" />" >
                        <span class="glyphicon glyphicon-log-out">
                            <spring:message code="menu.deconnecter" />
                        </span>
                    </a>
                </li>
            </ul>
        </div>
        <div class="dropdown" style="display: inline-block !important">
            <button class="btn btn-default dropdown-toogle" id="dropdown-language" data-toggle="dropdown">
                <c:if test="${pageContext.response.locale == 'fr' or pageContext.response.locale == 'fr_FR'}">
                    <img alt="<spring:message code="language.user"/> "  src="<c:url value="/resources/flags/16/France.png"/>"/>
                </c:if>
                <c:if test="${pageContext.response.locale == 'en' or pageContext.response.locale == 'en_US' }">
                    <img alt="<spring:message code="language.user"/> " src="<c:url value="/resources/flags/16/England.png"/>"/>
                </c:if>
                <spring:message code="language.title"/>
                <i class="caret"></i>
            </button>
            <ul class="dropdown-menu" role="menu" aria-labeledby="dropdown-language">
                <c:if test="${pageContext.response.locale == 'fr' or pageContext.response.locale == 'fr_FR'}">
                    <li>
                        <a href="?language=en">

                            <img alt="<spring:message code="language.user"/> " src="<c:url value="/resources/flags/16/England.png"/>"/>
                            <spring:message code="language.user"/>
                        </a>
                    </li>
                </c:if>
                <c:if test="${pageContext.response.locale == 'en' or pageContext.response.locale == 'en_US' }">                          <li>
                    <li>
                        <a href="?language=fr">
                            <img alt="<spring:message code="language.user"/> "  src="<c:url value="/resources/flags/16/France.png"/>"/>
                            <spring:message code="language.user"/>
                        </a>
                    </li>
                </c:if>
        </div>
    </div>
</nav>
<div class="row">
    <div class="col-lg-12">
        <c:set var="url" value="${pageContext.request.requestURI}" />
        <c:set var="urlPart" value="${fn:split(url, '/')}" />

        <div>
            <ol class="breadcrumb">
                <li>
                    <a href="<spring:url value="/" />">
                        <spring:message code="fil.accueil" />
                    </a>
                </li>
                <c:forEach var="i" begin="${fn:length(urlPart)-2}" end='${fn:length(urlPart)-1}'>
                    <li>
                        <c:choose>
                            <c:when test="${fn:containsIgnoreCase(urlPart[i], 'jsp')}">
                                <span>
                                    <%--${fn:toLowerCase(fn:substringBefore(urlPart[i], '.'))}--%>
                                    <spring:message code="fil.${(fn:substringBefore(urlPart[i], '.'))}" />
                                </span>
                            </c:when>
                            <c:otherwise>
                                <spring:url   value="/${urlPart[i]}/" var="path_element"  htmlEscape="true" />
                                <a href="${path_element}">
                                    <%--<c:out value="--- ${urlPart[i]} ---" />--%>
                                    <spring:message code="fil.${urlPart[i]}" />
                                    <%--${fn:toLowerCase((urlPart[i]))}--%>
                                </a>
                            </c:otherwise>
                        </c:choose>
                    </li>
                </c:forEach>
            </ol>
        </div>
    </div>
</div>
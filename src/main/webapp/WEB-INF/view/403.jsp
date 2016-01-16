<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>

<tiles:insertDefinition name="layout">
    <tiles:putAttribute name="body">

        <h1>
            <spring:message code="acces.title" />  ${username} 
        </h1>
        <br/>
        <div class="row">

            <h3 class="text text-center text-warning">
                <spring:message code="acces.refuse" />
            </h3>
        </div>
    </tiles:putAttribute>
</tiles:insertDefinition>
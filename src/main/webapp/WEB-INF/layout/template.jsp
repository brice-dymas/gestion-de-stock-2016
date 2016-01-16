<!DOCTYPE html>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<html lang="en">
    <head>
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <title>
            <spring:message code="application.name" />
        </title>

        <!-- Bootstrap -->
        <link href="<c:url value="/resources/css/app.css" />" rel="stylesheet">
        <link href="<c:url value="/resources/css/bootstrap.min.css" />" rel="stylesheet">
        <link href="<c:url value="/resources/css/jquery-ui.min.css" />" rel="stylesheet">
                <link href="<c:url value="/resources/css/jquery.loader.min.css" />" rel="stylesheet">
        
        <link href="<c:url value="/resources/css/jquery.jqplot.min.css" />" rel="stylesheet">
        <link href="<c:url value="/resources/img/cami.png" />" rel="shortcut icon"  type="image/png">

        <script src="<c:url value="/resources/js/jquery.min.js" />"></script>
        <script src="<c:url value="/resources/js/bootstrap.min.js" />"></script>
        <!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
        <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
        <!--[if lt IE 9]>
          <script src="https://oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js"></script>
          <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
        <![endif]-->
    </head>
    <body>
        <div class="container-fluid">
            <div class="row">
                <div class="col-lg-2">
                    <a class="thumbnail" href="<spring:url value="/"/>">
                        <img alt="cami.png"  src="<c:url value="/resources/img/cami.png"/>"/>
                    </a>
                </div>
                <div class="col-lg-10">
                    <tiles:insertAttribute name="header" />
                </div>
            </div>
            <div class="row">
                <div class="col-lg-2">
                    <tiles:insertAttribute name="menu" />
                </div>
                <div class="col-lg-10">

                    <c:if test="${ not  empty info}">
                        <div class="row">
                            <div class="col-lg-6 col-lg-offset-2">
                                <div class="alert alert-success alert-dismissible" role="alert">
                                    <button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                                    <strong><spring:message code="alert.info"/>! &nbsp;&nbsp;</strong> <spring:message code="${info}"/>
                                </div>
                            </div>
                        </div>
                    </c:if>
                    <c:if test="${ not empty error}">
                        <div class="row">
                            <div class="col-lg-6 col-lg-offset-2">
                                <div class="alert alert-danger alert-dismissible" role="alert">
                                    <button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                                    <strong><spring:message code="alert.error"/>! &nbsp;&nbsp;</strong> <spring:message code="alert.danger"/>
                                </div>
                            </div>
                        </div>
                    </c:if>
                    <c:if test="${ not empty exist}">
                        <div class="row">
                            <div class="col-lg-6 col-lg-offset-2">
                                <div class="alert alert-danger alert-dismissible" role="alert">
                                    <button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                                    <strong><spring:message code="alert.error"/>! &nbsp;&nbsp;</strong> <spring:message code="alert.username"/>
                                </div>
                            </div>
                        </div>
                    </c:if>
                    <c:if test="${ not empty password.error}">
                        <div class="row">
                            <div class="col-lg-6 col-lg-offset-2">
                                <div class="alert alert-danger alert-dismissible" role="alert">
                                    <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                                        <span aria-hidden="true">&times;</span>
                                    </button>
                                    <strong>
                                        <spring:message code="alert.error"/> ! &nbsp;&nbsp;
                                    </strong>
                                    <spring:message code="password.error"/>
                                </div>
                            </div>
                        </div>
                    </c:if>
                    <tiles:insertAttribute name="body" />
                </div>

            </div>



            <div class="row">
                <div class="col-lg-12">
                    <hr/>
                </div>
            </div>

        </div> <!-- /container -->



        
    </body>
</html>
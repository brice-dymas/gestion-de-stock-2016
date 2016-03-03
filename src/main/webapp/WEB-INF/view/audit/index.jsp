<%--
    Document   : index
    Created on : 11 déc. 2015, 09:47:40
    Author     : samuel     < smlfolong@gmail.com >
--%>

<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>



<tiles:insertDefinition name="layout">
    <tiles:putAttribute name="body">
        <div class="row">
            <div class="col-md-9">
                <div>
                    <h3>
                        <spring:message code="audit.list" />
                    </h3>
                    <hr/>
                </div>
                <div class="dropdown pull-right ">
                    <button class="btn btn-default dropdown-toggle" type="button" id="dropdownMenu1" data-toggle="dropdown" aria-expanded="true">
                        <spring:message code="search.taille" />
                        : ${size}&nbsp;
                        <span class="caret"></span>
                    </button>
                    <ul class="dropdown-menu" role="menu" aria-labelledby="dropdownMenu1">
                        <li role="presentation"><a role="menuitem" tabindex="-1" href="?querydepartement=${audit.departement.id}&querydateoperation=${audit.dateOperation}&querydesignation=${querydesignation}&size=5">5</a></li>
                        <li role="presentation"><a role="menuitem" tabindex="-1" href="?querydepartement=${audit.departement.id}&querydateoperation=${audit.dateOperation}&querydesignation=${querydesignation}&size=10">10</a></li>
                        <li role="presentation"><a role="menuitem" tabindex="-1" href="?querydepartement=${audit.departement.id}&querydateoperation=${audit.dateOperation}&querydesignation=${querydesignation}&size=20">20</a></li>
                        <li role="presentation"><a role="menuitem" tabindex="-1" href="?querydepartement=${audit.departement.id}&querydateoperation=${audit.dateOperation}&querydesignation=${querydesignation}&size=30">30</a></li>
                        <li role="presentation"><a role="menuitem" tabindex="-1" href="?querydepartement=${audit.departement.id}&querydateoperation=${audit.dateOperation}&querydesignation=${querydesignation}&size=40">40</a></li>
                        <li role="presentation"><a role="menuitem" tabindex="-1" href="?querydepartement=${audit.departement.id}&querydateoperation=${audit.dateOperation}&querydesignation=${querydesignation}&size=50">50</a></li>
                    </ul>
                </div>
                <table class="table table-condensed table-hover table-bordered">
                    <thead class="text-center btn-primary">
                        <tr>
                            <th> <span class="btn"> <spring:message code="operation.numero" /> </span> </th>
                            <th> <span class="btn"> <spring:message code="operation.dateOperation" /> </span> </th>
                            <th> <span class="btn"> <spring:message code="departement.intitule" /> </span> </th>
                            <th> <span class="btn"> <spring:message code="action.titre" /> </span> </th>
                        </tr>
                    </thead>
                    <tbody>
                        <spring:url value="/audit/new" var="auditNew" />
                        <spring:url value="/" var="accueil" />

                        <c:if test="${audits.size() eq 0}">
                            <tr>
                                <td class="text-center label-danger" colspan="5">
                                    <spring:message code="empty.data" />
                                </td>
                            </tr>
                        </tbody>
                    </table>

                    <div>
                        <a href="${auditNew}" class="btn btn-primary btn-sm">
                            <span class="glyphicon glyphicon-new-window"></span>
                            <spring:message code="action.nouveau" />
                        </a>


                        <div class="pull-right">
                            <ul class="pager">

                                <li><a href="?query=${query}&page=0&size=${size}" class ="btn btn-sm disabled">
                                        <span class="glyphicon glyphicon-fast-backward"></span>
                                    </a></li>
                                <li><a href="?query=${query}&page=${page-1}&size=${size}"class ="btn btn-sm disabled">
                                        <span class="glyphicon glyphicon-backward"></span>
                                    </a></li>
                                <li><input type="text" class="pager_detail text-center" readonly value="0/0"/></li>
                                <li><a href="?query=${query}&page=${page+1}&size=${size}" class ="btn btn-sm disabled">
                                        <span class="glyphicon glyphicon-forward"></span>
                                    </a></li>
                                <li><a href="?query=${query}&page=${Totalpage-1}&size=${size}" class ="btn btn-sm disabled">
                                        <span class="glyphicon glyphicon-fast-forward"></span>
                                    </a></li>
                            </ul>
                        </div>
                    </div>

                </c:if>

                <c:if test="${audits.size() ne 0}">
                    <c:forEach items="${audits}" var="audit">
                        <tr>
                            <td>${audit.numero}</td>
                            <td>${audit.dateOperation}</td>
                            <td>${audit.departement.intitule}</td>
                            <td>
                                <spring:url value="/audit/${audit.id}/edit" htmlEscape="true" var="audit_edit" />
                                <a href="${audit_edit}" class="btn btn-primary btn-warning">
                                    <span class="glyphicon glyphicon-edit"></span>
                                    <spring:message code="action.modifier" />
                                </a>
                                &nbsp; &nbsp;
                                <spring:url value="/audit/${audit.id}/show" htmlEscape="true" var="audit_show" />
                                <a href="${audit_show}" class="btn btn-primary btn-sm">
                                    <span class="glyphicon glyphicon-open"></span>
                                    <spring:message code="action.detail" />
                                </a>
                            </td>
                        </tr>
                    </c:forEach>

                    </tbody>
                    </table>
                    <div>
                        <a href="${auditNew}" class="btn btn-primary btn-sm">
                            <span class="glyphicon glyphicon-new-window"></span>
                            <spring:message code="action.nouveau" />
                        </a>
                        <div class="pull-right">
                            <ul class="pager">

                                <li>
                                    <a href="?querydepartement=${audit.departement.id}&querydateoperation=${audit.dateOperation}&querydesignation=${querydesignation}&page=0&size=${size}" <c:if test="${page eq 0}">class ="btn btn-sm disabled"</c:if>>
                                            <span class="glyphicon glyphicon-fast-backward"></span>
                                        </a>
                                    </li>
                                    <li>
                                        <a href="?querydepartement=${audit.departement.id}&querydateoperation=${audit.dateOperation}&querydesignation=${querydesignation}&page=${page-1}&size=${size}" <c:if test="${page eq 0}">class ="btn btn-sm disabled"</c:if>>
                                            <span class="glyphicon glyphicon-backward"></span>
                                        </a>
                                    </li>
                                    <li>
                                        <input type="text" class="pager_detail text-center" readonly value="${page+1}/${Totalpage}"/>
                                </li>
                                <li>
                                    <a href="?querydepartement=${audit.departement.id}&querydateoperation=${audit.dateOperation}&querydesignation=${querydesignation}&page=${page+1}&size=${size}" <c:if test="${page+1 eq Totalpage}">class ="btn btn-sm disabled"</c:if>>
                                            <span class="glyphicon glyphicon-forward"></span>
                                        </a>
                                    </li>
                                    <li>
                                        <a href="?querydepartement=${audit.departement.id}&querydateoperation=${audit.dateOperation}&querydesignation=${querydesignation}&page=${Totalpage-1}&size=${size}" <c:if test="${page+1 eq Totalpage}">class ="btn btn-sm disabled"</c:if>>
                                            <span class="glyphicon glyphicon-fast-forward"></span>
                                        </a>
                                    </li>
                                </ul>
                            </div>
                        </div>
                    </div>
                    <div class="col-md-3">
                        <div>
                            <h3>
                            <spring:message code="action.rechercher" />
                        </h3>
                        <hr/>
                    </div>
                    <spring:url value="/audit/" var="audit_home"
                                htmlEscape="true" />

                    <form:form method="get" commandName="audit" action="${audit_home}">
                        <div class="form-group">
                            <label for="dep">
                                <spring:message code="operation.departement" />
                            </label>
                            <select id="dep" name="querydepartement" class="form-control input-sm">
                                <option value="">---</option>
                                <c:forEach var="dept" items="${departements}">
                                    <option value="${dept.key}"
                                            <c:if test="${dept.key eq audit.departement.id}">
                                                selected
                                            </c:if>
                                            >
                                        ${dept.value}
                                    </option>
                                </c:forEach>
                            </select>
                            <input type="hidden" value="${size}" name="size"/>
                        </div>
                        <div class="form-group">
                            <label for="dateOperation">
                                <spring:message code="operation.dateOperation" />
                            </label>
                            <input id="dateOperation" type="text" value="${audit.dateOperation}" class="form-control input-sm" name="querydateoperation"/>
                        </div>
                        <div class="form-group">
                            <label for="designation">
                                <spring:message code="fourniture.designation" />
                            </label>
                            <input id="designation" type="text" value="${querydesignation}" class="form-control input-sm" name="querydesignation"/>
                        </div>
                        <hr/>
                        <button class="btn btn-default btn-sm">
                            <span class="glyphicon glyphicon-search"></span> <spring:message code="action.rechercher"/>
                        </button>
                        <spring:url value="/audit/" htmlEscape="true" var="audit_home" />
                        <a href="${audit_home}" class="btn btn-default btn-sm">
                            <span class="glyphicon glyphicon-refresh"></span>
                            <spring:message code="search.delete" />
                        </a>
                    </form:form>
                </div>
            </c:if>
        </div>

        <script src="<c:url value="/resources/js/jquery-ui.js" />"></script>
        <script type="text/javascript">
            $(function () {
                $("#dateOperation").datepicker({
                    changeMonth: true,
                    changeYear: true,
                    dateFormat: "dd/mm/yy",
                    showButtonPanel: false
                }).datepicker("option", "showAnim", "clip");
            });
        </script>
    </tiles:putAttribute>
</tiles:insertDefinition>





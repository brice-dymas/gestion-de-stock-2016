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
                        <spring:message code="sortie.list" />
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
                        <li role="presentation"><a role="menuitem" tabindex="-1" href="?querydepartement=${sortie.departement.id}&querydateoperation=${sortie.dateOperation}&querydesignation=${querydesignation}&size=5">5</a></li>
                        <li role="presentation"><a role="menuitem" tabindex="-1" href="?querydepartement=${sortie.departement.id}&querydateoperation=${sortie.dateOperation}&querydesignation=${querydesignation}&size=10">10</a></li>
                        <li role="presentation"><a role="menuitem" tabindex="-1" href="?querydepartement=${sortie.departement.id}&querydateoperation=${sortie.dateOperation}&querydesignation=${querydesignation}&size=20">20</a></li>
                        <li role="presentation"><a role="menuitem" tabindex="-1" href="?querydepartement=${sortie.departement.id}&querydateoperation=${sortie.dateOperation}&querydesignation=${querydesignation}&size=30">30</a></li>
                        <li role="presentation"><a role="menuitem" tabindex="-1" href="?querydepartement=${sortie.departement.id}&querydateoperation=${sortie.dateOperation}&querydesignation=${querydesignation}&size=40">40</a></li>
                        <li role="presentation"><a role="menuitem" tabindex="-1" href="?querydepartement=${sortie.departement.id}&querydateoperation=${sortie.dateOperation}&querydesignation=${querydesignation}&size=50">50</a></li>
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
                        <spring:url value="/sortie/new" var="sortieNew" />
                        <spring:url value="/" var="accueil" />

                        <c:if test="${sorties.size() eq 0}">
                            <tr>
                                <td class="text-center label-danger" colspan="5">
                                    <spring:message code="empty.data" />
                                </td>
                            </tr>
                        </tbody>
                    </table>

                    <div>
                        <a href="${sortieNew}" class="btn btn-primary btn-sm">
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

                <c:if test="${sorties.size() ne 0}">
                    <c:forEach items="${sorties}" var="sortie">
                        <tr>
                            <td>${sortie.numero}</td>
                            <td>${sortie.dateOperation}</td>
                            <td>${sortie.departement.intitule}</td>
                            <td>
                                <spring:url value="/sortie/${sortie.id}/edit" htmlEscape="true" var="sortie_edit" />
                                <a href="${sortie_edit}" class="btn btn-primary btn-warning">
                                    <span class="glyphicon glyphicon-edit"></span>
                                    <spring:message code="action.modifier" />
                                </a>
                                &nbsp; &nbsp;
                                <spring:url value="/sortie/${sortie.id}/show" htmlEscape="true" var="sortie_show" />
                                <a href="${sortie_show}" class="btn btn-primary btn-sm">
                                    <span class="glyphicon glyphicon-open"></span>
                                    <spring:message code="action.detail" />
                                </a>
                            </td>
                        </tr>
                    </c:forEach>

                    </tbody>
                    </table>
                    <div>
                        <a href="${sortieNew}" class="btn btn-primary btn-sm">
                            <span class="glyphicon glyphicon-new-window"></span>
                            <spring:message code="action.nouveau" />
                        </a>
                        <div class="pull-right">
                            <ul class="pager">

                                <li>
                                    <a href="?querydepartement=${sortie.departement.id}&querydateoperation=${sortie.dateOperation}&querydesignation=${querydesignation}&page=0&size=${size}" <c:if test="${page eq 0}">class ="btn btn-sm disabled"</c:if>>
                                            <span class="glyphicon glyphicon-fast-backward"></span>
                                        </a>
                                    </li>
                                    <li>
                                        <a href="?querydepartement=${sortie.departement.id}&querydateoperation=${sortie.dateOperation}&querydesignation=${querydesignation}&page=${page-1}&size=${size}" <c:if test="${page eq 0}">class ="btn btn-sm disabled"</c:if>>
                                            <span class="glyphicon glyphicon-backward"></span>
                                        </a>
                                    </li>
                                    <li>
                                        <input type="text" class="pager_detail text-center" readonly value="${page+1}/${Totalpage}"/>
                                </li>
                                <li>
                                    <a href="?querydepartement=${sortie.departement.id}&querydateoperation=${sortie.dateOperation}&querydesignation=${querydesignation}&page=${page+1}&size=${size}" <c:if test="${page+1 eq Totalpage}">class ="btn btn-sm disabled"</c:if>>
                                            <span class="glyphicon glyphicon-forward"></span>
                                        </a>
                                    </li>
                                    <li>
                                        <a href="?querydepartement=${sortie.departement.id}&querydateoperation=${sortie.dateOperation}&querydesignation=${querydesignation}&page=${Totalpage-1}&size=${size}" <c:if test="${page+1 eq Totalpage}">class ="btn btn-sm disabled"</c:if>>
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
                    <spring:url value="/sortie/" var="sortie_home"
                                htmlEscape="true" />

                    <form:form method="get" commandName="sortie" action="${sortie_home}">
                        <div class="form-group">
                            <label for="deptment">
                                <spring:message code="operation.departement" />
                            </label>
                            <select id="deptment" name="querydepartement" class="form-control input-sm">
                                <option value="">---</option>
                                <c:forEach var="dept" items="${departements}">

                                    <option value="${dept.key}"
                                            <c:if test="${dept.key eq sortie.departement.id}">
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
                            <input id="dateOperation" type="text" value="${sortie.dateOperation}" class="form-control input-sm" name="querydateoperation"/>
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
                        <spring:url value="/sortie/" htmlEscape="true" var="sortie_home" />
                        <a href="${sortie_home}" class="btn btn-default btn-sm">
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





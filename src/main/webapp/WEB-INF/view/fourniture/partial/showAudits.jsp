<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<fieldset>
    <legend>
        <spring:message code="fourniture.audits" />
    </legend>

    <button class="btn btn-default pull-left" data-toggle="modal" data-target="#searchLigneOperationModal">
        <spring:message code="action.rechercher" />
    </button>

    <div class="dropdown pull-right ">
        <button class="btn btn-default dropdown-toggle" type="button" id="dropdownMenu1" data-toggle="dropdown" aria-expanded="true">
            <spring:message code="search.taille" />
            : <span id='size'>${size}</span>&nbsp;
            <span class="caret"></span>
        </button>
        <ul class="dropdown-menu" role="menu" aria-labelledby="dropdownMenu1">
            <li role="presentation"><a role="menuitem" tabindex="-1" id="size_value" href="size=2">2</a></li>
            <li role="presentation"><a role="menuitem" tabindex="-1" id="size_value" href="size=5">5</a></li>
            <li role="presentation"><a role="menuitem" tabindex="-1" id="size_value" href="size=10">10</a></li>
            <li role="presentation"><a role="menuitem" tabindex="-1" id="size_value" href="size=20">20</a></li>
            <li role="presentation"><a role="menuitem" tabindex="-1" id="size_value" href="size=30">30</a></li>
            <li role="presentation"><a role="menuitem" tabindex="-1" id="size_value" href="size=40">40</a></li>
            <li role="presentation"><a role="menuitem" tabindex="-1" id="size_value" href="size=50">50</a></li>
        </ul>
    </div>
    <table class="table table-bordered">
        <thead>
            <tr class="btn-primary">
                <th><spring:message code="operation.numero" /></th>
                <th><spring:message code="operation.dateOperation" /></th>
                <th><spring:message code="operation.departement" /></th>
                <th><spring:message code="ligneOperation.quantite" /></th>
                <th><spring:message code="ligneOperation.quantitePhysique" /></th>
                <th><spring:message code="ligneOperation.quantiteEcart" /></th>
                <th><spring:message code="ligneOperation.observation" /></th>
            </tr>
        </thead>
        <tbody id="ligneOperationContent">
            <c:if test="${0 eq ligneOperations.size()}" >
                <tr>
                    <td class="text-center label-danger" colspan="8">
                        <spring:message code="empty.data" />
                    </td>
                </tr>
            </tbody>
        </table>
    </c:if>
    <c:if test="${0 ne ligneOperations.size()}" >
        <c:forEach var="ligneOperation" items="${ligneOperations}">
            <tr>
                <td>${ligneOperation.operation.numero} </td>
                <td>${ligneOperation.operation.dateOperation} </td>
                <td>${ligneOperation.operation.departement.intitule} </td>
                <td>${ligneOperation.fourniture.quantite} </td>
                <td>${ligneOperation.quantitePhysique} </td>
                <td>${ligneOperation.quantiteEcart} </td>
                <td>${ligneOperation.observation} </td>
            </tr>
        </c:forEach>
    </tbody>
</table>
<div class="row">
    <div class="pull-right">
        <ul class="pager">

            <li>
                <a id="fast_back_ligneOperation" href="page=0&size=${size}" <c:if test="${page eq 0}">class ="btn btn-sm disabled"</c:if>>
                        <span class="glyphicon glyphicon-fast-backward"></span>
                    </a>
                </li>
                <li>
                    <a id="back_ligneOperation" href="page=${page-1}&size=${size}" <c:if test="${page eq 0}">class ="btn btn-sm disabled"</c:if>>
                        <span class="glyphicon glyphicon-backward"></span>
                    </a>
                </li>
                <li>
                    <input type="text" id="pager_detail" class="pager_detail text-center" readonly value="${page+1}/${Totalpage}"/>
            </li>
            <li>
                <a id="forward_ligneOperation" href="page=${page+1}&size=${size}" <c:if test="${page+1 eq Totalpage}">class ="btn btn-sm disabled"</c:if>>
                        <span class="glyphicon glyphicon-forward"></span>
                    </a>
                </li>
                <li>
                    <a id="fast_forward_ligneOperation" href="page=${Totalpage-1}&size=${size}" <c:if test="${page+1 eq Totalpage}">class ="btn btn-sm disabled"</c:if>>
                        <span class="glyphicon glyphicon-fast-forward"></span>
                    </a>
                </li>
            </ul>
        </div>

        <div class="modal fade" tabindex="-1" role="dialog" id="searchLigneOperationModal">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                        <h4 class="modal-title">
                        <spring:message code="fourniture.searchAudits" />
                    </h4>
                </div>
                <div class="modal-body">
                    <div class="row">
                        <div class="col-xs-6 col-xs-offset-3">
                            <form action="" class="form-horizontal">
                                <fieldset>
                                    <div class="form-group">
                                        <label><spring:message code="debut" />:</label>
                                        <input class="form-control" name="dateDebut" id="dateDebut"/>
                                    </div>
                                    <div class="form-group">
                                        <label><spring:message code="fin" />:</label>
                                        <input class="form-control" name="dateFin" id="dateFin"/>
                                    </div>
                                </fieldset>
                            </form>
                        </div>
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal">
                        <spring:message code="action.fermer" />
                    </button>
                    <a href='#' id="searchLigneOperation" class="btn btn-primary">
                        <spring:message code="action.rechercher" />
                    </a>
                </div>
            </div><!-- /.modal-content -->
        </div><!-- /.modal-dialog -->
    </div><!-- /.modal -->
</div>
</c:if>
</fieldset>


<spring:url value="fourniture/${fourniture.id}/searchAudits.json" var="fourniture_search"/>
<script src="<c:url value="/resources/js/jquery-ui.js" />"></script>
<script>
    $(function () {
        $("#dateDebut, #dateFin").datepicker({
            changeMonth: true,
            changeYear: true,
            dateFormat: "dd/mm/yy",
            showButtonPanel: false
        });
        var href = "";
        $("#searchLigneOperation").on("mouseenter", function () {
            href = "";
            href += "dateDebut=" + $("#dateDebut").val();
            href += "&dateFin=" + $("#dateFin").val();
            $("#searchLigneOperation").attr("href", href);
        });
        $("#searchLigneOperation").on("click", function () {
            queryLigneOperation(href);
            return false;
        });

        $("#fast_back_ligneOperation").on("click", function () {
            if (!$("#fast_back_ligneOperation").hasClass("disabled")) {
                queryLigneOperation(href + "&" + $("#fast_back_ligneOperation").attr("href"));
            }
            return false;
        });
        $("#back_ligneOperation").on("click", function () {
            if (!$("#back_ligneOperation").hasClass("disabled")) {
                queryLigneOperation(href + "&" + $("#back_ligneOperation").attr("href"));
            }
            return false;
        });
        $("#forward_ligneOperation").on("click", function () {
            if (!$("#forward_ligneOperation").hasClass("disabled")) {
                queryLigneOperation(href + "&" + $("#forward_ligneOperation").attr("href"));
            }
            return false;
        });
        $("#fast_forward_ligneOperation").on("click", function () {
            if (!$("#fast_forward_ligneOperation").hasClass("disabled")) {
                queryLigneOperation(href + "&" + $("#fast_forward_ligneOperation").attr("href"));
            }
            return false;
        });
        $("a[id=size_value]").each(function (index, value) {
            $(this).on("click", function () {
                if (href === "") {
                    queryLigneOperation($(this).attr("href"));
                } else {

                    queryLigneOperation(href + "&" + $(this).attr("href"));
                }
                return false;
            });

        });

        function queryLigneOperation(href) {
            var url = getBaseURL() + "${fourniture_search}?" + href;
            var row = "";
            console.log(url);
            $.get(url, function (data) {
                $.each(data["ligneOperations"], function (index, value) {
                    row += "<tr><td>" + value["operation"]['numero'] + "</td>";
                    row += "<td>" + value["operation"]['dateOperation'] + "</td>";
                    row += "<td>" + value["operation"]["departement"]['intitule'] + "</td>";
                    row += "<td>" + data["fourniture"]['quantite'] + "</td>";
                    row += "<td>" + value["quantitePhysique"] + "</td>";
                    row += "<td>" + value["quantiteEcart"] + "</td>";
                    row += "<td>" + value["observation"] + "</td></tr>";
                });

                var page = parseInt(data["page"]);
                var totalPage = parseInt(data["Totalpage"]);
                $("#fast_back_ligneOperation").attr("href", "page=0" + "&size=" + data["size"]);
                $("#back_ligneOperation").attr("href", "page=" + (page - 1) + "&size=" + data["size"]);
                $("#forward_ligneOperation").attr("href", "page=" + (page + 1) + "&size=" + data["size"]);
                $("#fast_forward_ligneOperation").attr("href", "page=" + (totalPage - 1) + "&size=" + data["size"]);
                if (page === 0) {
                    if (!$("#fast_back_ligneOperation").hasClass("disabled")) {
                        $("#fast_back_ligneOperation").addClass("disabled");
                        $("#back_ligneOperation").addClass("disabled");
                    }
                } else {
                    if ($("#fast_back_ligneOperation").hasClass("disabled")) {
                        $("#fast_back_ligneOperation").removeClass("disabled");
                        $("#back_ligneOperation").removeClass("disabled");
                    }
                }

                if (page + 1 === totalPage) {
                    if (!$("#fast_forward_ligneOperation").hasClass("disabled")) {
                        $("#forward_ligneOperation").addClass("disabled");
                        $("#fast_forward_ligneOperation").addClass("disabled");
                    }
                } else {
                    if ($("#fast_forward_ligneOperation").hasClass("disabled")) {
                        $("#forward_ligneOperation").removeClass("disabled");
                        $("#fast_forward_ligneOperation").removeClass("disabled");
                    }
                }

                $("#ligneOperationContent").html(row);
                $("#size").html(data["size"]);
                $("#pager_detail").val((page + 1) + "/" + totalPage);
            });
        }

        function getBaseURL() {
            var url = location.href;  // entire url including querystring - also: window.location.href;
            var baseURL = url.substring(0, url.indexOf('/', 14));


            if (baseURL.indexOf('http://localhost') !== -1) {
                // Base Url for localhost
                var url = location.href;  // window.location.href;
                var pathname = location.pathname;  // window.location.pathname;
                var index1 = url.indexOf(pathname);
                var index2 = url.indexOf("/", index1 + 1);
                var baseLocalUrl = url.substr(0, index2);

                return baseLocalUrl + "/";
            } else {
                // Root Url for domain name
                return baseURL + "/";
            }

        }
    });
</script>
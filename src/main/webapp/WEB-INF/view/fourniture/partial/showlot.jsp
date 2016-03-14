<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<fieldset>
    <legend>
        <spring:message code="entree.lots" />
    </legend>

    <button class="btn btn-default pull-left" data-toggle="modal" data-target="#searchLotModal">
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
                <th><spring:message code="lot.numero" /></th>
                <th><spring:message code="fourniture.designation" /></th>
                <th><spring:message code="lot.dateEntree" /></th>
                <th><spring:message code="lot.quantite" /></th>
                <th><spring:message code="lot.prixUnitaire" /></th>
                <th><spring:message code="lot.prixUnitaireVente" /></th>
                <th><spring:message code="lot.motantTotal" /></th>
                <th><spring:message code="lot.etat" /></th>
            </tr>
        </thead>
        <tbody id="lotContent">
            <c:if test="${0 eq lots.size()}" >
                <tr>
                    <td class="text-center label-danger" colspan="8">
                        <spring:message code="empty.data" />
                    </td>
                </tr>
            </tbody>
        </table>
    </c:if>
    <c:if test="${0 ne lots.size()}" >
        <c:forEach var="lot" items="${lots}">
            <tr>
                <td>${lot.numero}</td>
                <td>${lot.fourniture.designation} </td>
                <td> <fmt:formatDate value="${lot.dateEntree}" pattern="dd/MM/yyyy" /> </td>
        <td>${lot.quantite} </td>
        <td>${lot.prixUnitaire} </td>
        <td>${lot.prixVenteUnitaire} </td>
        <td>${lot.totalMontant} </td>
        <td>${lot.etat} </td>
    </tr>
</c:forEach>
</tbody>
</table>
<div class="row">
    <div class="pull-right">
        <ul class="pager">

            <li>
                <a id="fast_back_lot" href="page=0&size=${size}" <c:if test="${page eq 0}">class ="btn btn-sm disabled"</c:if>>
                        <span class="glyphicon glyphicon-fast-backward"></span>
                    </a>
                </li>
                <li>
                    <a id="back_lot" href="page=${page-1}&size=${size}" <c:if test="${page eq 0}">class ="btn btn-sm disabled"</c:if>>
                        <span class="glyphicon glyphicon-backward"></span>
                    </a>
                </li>
                <li>
                    <input type="text" id="pager_detail" class="pager_detail text-center" readonly value="${page+1}/${Totalpage}"/>
            </li>
            <li>
                <a id="forward_lot" href="page=${page+1}&size=${size}" <c:if test="${page+1 eq Totalpage}">class ="btn btn-sm disabled"</c:if>>
                        <span class="glyphicon glyphicon-forward"></span>
                    </a>
                </li>
                <li>
                    <a id="fast_forward_lot" href="page=${Totalpage-1}&size=${size}" <c:if test="${page+1 eq Totalpage}">class ="btn btn-sm disabled"</c:if>>
                        <span class="glyphicon glyphicon-fast-forward"></span>
                    </a>
                </li>
            </ul>
        </div>

        <div class="modal fade" tabindex="-1" role="dialog" id="searchLotModal">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                        <h4 class="modal-title">Search inside Lot</h4>
                    </div>
                    <div class="modal-body">
                        <div class="row">
                            <div class="col-xs-6 col-xs-offset-3">
                                <form action="" class="form-horizontal">
                                    <fieldset>
                                        <div class="form-group">
                                            <label>Debut Entrée:</label>
                                            <input class="form-control" name="debut" id="debut"/>
                                        </div>
                                        <div class="form-group">
                                            <label>Fin Entrée:</label>
                                            <input class="form-control" name="fin" id="fin"/>
                                        </div>
                                    </fieldset>
                                    <div class="form-group">
                                        <label>Quantité:</label>
                                        <input class="form-control" name="quantite" id="quantite"/>
                                    </div>

                                </form>
                            </div>
                        </div>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
                        <a href='#' id="searchLot" class="btn btn-primary">Search</a>
                    </div>
                </div><!-- /.modal-content -->
            </div><!-- /.modal-dialog -->
        </div><!-- /.modal -->
    </div>
</c:if>
</fieldset>


<spring:url value="fourniture/${fourniture.id}/search.json" var="fourniture_search"/>
<script src="<c:url value="/resources/js/jquery-ui.js" />"></script>
<script>
    $(function () {
        $("#debut, #fin").datepicker({
            changeMonth: true,
            changeYear: true,
            dateFormat: "dd/mm/yy",
            showButtonPanel: false
        });
        var href = "";
        $("#searchLot").on("mouseenter", function () {
            href = "";
            href += "debut=" + $("#debut").val();
            href += "&fin=" + $("#fin").val();
            href += "&quantite=" + $("#quantite").val();
            $("#searchLot").attr("href", href);
        });
        $("#searchLot").on("click", function () {
            queryLot(href);
            return false;
        });
        $("#fast_back_lot").on("click", function () {
            if (!$("#fast_back_lot").hasClass("disabled")) {
                queryLot(href + "&" + $("#fast_back_lot").attr("href"));
            }
            return false;
        });
        $("#back_lot").on("click", function () {
            if (!$("#back_lot").hasClass("disabled")) {
                queryLot(href + "&" + $("#back_lot").attr("href"));
            }
            return false;
        });
        $("#forward_lot").on("click", function () {
            if (!$("#forward_lot").hasClass("disabled")) {
                queryLot(href + "&" + $("#forward_lot").attr("href"));
            }
            return false;
        });
        $("#fast_forward_lot").on("click", function () {
            if (!$("#fast_forward_lot").hasClass("disabled")) {
                queryLot(href + "&" + $("#fast_forward_lot").attr("href"));
            }
            return false;
        });
        $("a[id=size_value]").each(function (index, value) {
            $(this).on("click", function () {
                if (href === "") {
                    queryLot($(this).attr("href"));
                } else {
                    queryLot(href + "&" + $(this).attr("href"));
                }
                return false;
            });
        });
        function queryLot(href) {
            var url = getBaseURL() + "${fourniture_search}?" + href;
            var row = "";
            console.log(url);
            $.get(url, function (data) {
                $.each(data["lots"], function (index, value) {
                    row += "<tr><td>" + value['numero'] + "</td>";
                    row += "<td>" + data["fourniture"]['designation'] + "</td>";
                    row += "<td>" + value["dateEntree"] + "</td>";
                    row += "<td>" + value["quantite"] + "</td>";
                    row += "<td>" + value["prixUnitaire"] + "</td>";
                    row += "<td>" + value["prixVenteUnitaire"] + "</td>";
                    row += "<td>" + value["totalMontant"] + "</td>";
                    row += "<td>" + value["etat"] + "</td></tr>";
                });
                var page = parseInt(data["page"]);
                var totalPage = parseInt(data["Totalpage"]);
                $("#fast_back_lot").attr("href", "page=0" + "&size=" + data["size"]);
                $("#back_lot").attr("href", "page=" + (page - 1) + "&size=" + data["size"]);
                $("#forward_lot").attr("href", "page=" + (page + 1) + "&size=" + data["size"]);
                $("#fast_forward_lot").attr("href", "page=" + (totalPage - 1) + "&size=" + data["size"]);
                if (page === 0) {
                    if (!$("#fast_back_lot").hasClass("disabled")) {
                        $("#fast_back_lot").addClass("disabled");
                        $("#back_lot").addClass("disabled");
                    }
                } else {
                    if ($("#fast_back_lot").hasClass("disabled")) {
                        $("#fast_back_lot").removeClass("disabled");
                        $("#back_lot").removeClass("disabled");
                    }
                }
                if (page + 1 === totalPage) {
                    if (!$("#fast_forward_lot").hasClass("disabled")) {
                        $("#forward_lot").addClass("disabled");
                        $("#fast_forward_lot").addClass("disabled");
                    }
                } else {
                    if ($("#fast_forward_lot").hasClass("disabled")) {
                        $("#forward_lot").removeClass("disabled");
                        $("#fast_forward_lot").removeClass("disabled");
                    }
                }
                $("#lotContent").html(row);
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
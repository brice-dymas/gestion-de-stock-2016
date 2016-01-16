<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html lang="en-IN">
    <head>
        <meta charset="utf-8">
        <title></title>
        <!-- Bootstrap -->
        <link href="<c:url value="/resources/css/bootstrap.min.css" />" rel="stylesheet">
        <link href="<c:url value="/resources/css/app.css" />" rel="stylesheet">
        <link href="<c:url value="/resources/css/jquery-ui.css" />" rel="stylesheet">
        <link href="<c:url value="/resources/css/jquery-ui.structure.css" />" rel="stylesheet">
        <link href="<c:url value="/resources/css/jquery-ui.theme.css" />" rel="stylesheet">
        <link href="<c:url value="/resources/img/cami.png" />" rel="shortcut icon"  type="image/png">

        <script src="<c:url value="/resources/js/jquery.min.js" />"></script>
    </head>
    <body class="row">
        <hr>
        <div class="modal-content modal-dialog">
            <div class="text-center modal-header">
                <img alt="cami.png" height="92" width="400" src="<c:url value="/resources/img/cami.png"/>"/>
            </div>
            <div id="login-form">
                <br>
                <div>
                    <c:if test="${not empty error}">
                        <div class="text ui-widget-shadow text-center text-danger ">
                            <h4 class="text-uppercase">
                                <b>${error}</b>
                            </h4>
                        </div>
                    </c:if>
                    <c:if test="${not empty msg}">
                        <div class="text text-danger">${msg}</div>
                    </c:if>
                </div>
            </div>
            <section class="container modal-body">
                <article class="col-xs-4 col-xs-offset-1">
                    <form role="form"
                          method='POST'
                          name='loginForm'
                          class="panel panel-default"
                          action="<c:url value='/login'/>">
                        <header class="panel-heading">
                            <h3 class="text-muted text-center">
                                Connectez-vous...
                            </h3>
                        </header>
                        <div class="panel-body">
                            <div class="form-group">
                                <div class="input-group">
                                    <input required
                                           type="text"
                                           id="username"
                                           name='username'
                                           class="form-control"
                                           placeholder="Votre nom d'utilisateur"/>
                                    <label for="username" class="input-group-addon">
                                        <i class="glyphicon glyphicon-user"></i>
                                    </label>
                                </div>
                            </div>
                            <div class="form-group">
                                <div class="input-group">
                                    <input required
                                           id="password"
                                           type="password"
                                           name='password'
                                           class="form-control"
                                           placeholder="Votre Mot de passe"/>
                                    <label for="password" class="input-group-addon">
                                        <i class="glyphicon glyphicon-lock"></i>
                                    </label>
                                </div>
                            </div>
                        </div>
                        <footer class="panel-footer">
                            <div class="row">
                                <button type="submit" class="btn btn-primary btn-block text-center">
                                    <b>
                                        <span class="glyphicon glyphicon-log-in">
                                            Se connecter
                                        </span>

                                    </b>
                                </button> &nbsp;&nbsp;
                                <button type="reset" class="btn btn-warning btn-block text-center">
                                    <b>
                                        <span class="glyphicon glyphicon-refresh">
                                            Réinitialiser
                                        </span>
                                    </b>
                                </button>
                            </div>
                            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
                        </footer>
                    </form>
                </article>
            </section>
        </div>

    </body>
</html>
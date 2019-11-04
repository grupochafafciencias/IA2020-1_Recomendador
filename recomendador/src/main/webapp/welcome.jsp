<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <title>Recommender</title>
    <link href="${contextPath}/resources/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>


<nav class="navbar navbar-expand-lg navbar-dark fixed-top scrolling-navbar">
    <a class="navbar-brand" href="/welcome">*Insert logo that I didnt had time to make*</a>
    <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
        <span class="navbar-toggler-icon"></span>
    </button>

<div class="container">
    <c:if test="${pageContext.request.userPrincipal.name != null}">
        <form id="logoutForm" method="POST" action="${contextPath}/logout">
            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
        </form>

        <h2>Welcome ${pageContext.request.userPrincipal.name} | <a onclick="document.forms['logoutForm'].submit()">Logout</a></h2>
    </c:if>
</div>
</nav>

<div class="view" style="background-image: url('http://s1.picswalls.com/wallpapers/2015/12/12/halloween-2015-hd-wallpaper_015511727_296.jpg'); background-repeat: no-repeat; background-size: cover; background-position: center center;">
    <div class="mask rgba-gradient align-items-center">
        <div class="container">
            <iframe width="560" height="315" src="https://www.youtube.com/embed/yjavYXmIvxI" frameborder="0" allow="accelerometer; autoplay; encrypted-media; gyroscope; picture-in-picture" allowfullscreen></iframe>
            <h3 class="text-white">
                Recommended for you
                <c:forEach items="${usuario.getWatched()}" var="serie">
                    <p></p>
                </c:forEach>
            </h3>
            <h3 class="text-white">
                General
                <c:forEach items="${series}" var="serie">
                    <p><img src="${ContextPath}${serie.getImage()}" href=height="165" width="117"></p>
                </c:forEach>
            </h3>
        </div>
    </div>
</div>

</body>
</html>
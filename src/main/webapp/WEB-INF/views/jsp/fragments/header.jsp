<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>

<head>
<title>Currency Exchange Converter</title>

<spring:url value="/resources/css/main.css" var="mainCSS" />

<link href="${mainCSS}" rel="stylesheet" />

</head>

<spring:url value="/currency_exchange/new" var="urlNewQuery" />
<spring:url value="/currency_exchange/list" var="urlList" />
<spring:url value="/currency_exchange/current" var="urlCurrent" />
<spring:url value="/login" var="urlLogin" />
<sec:authorize access="isAuthenticated()">
	<sec:authentication property="principal.id" var="userID" />
	<spring:url value="/users/${userID}/update/" var="urlUserUpdate" />
	<spring:url value="/users/${userID}" var="urlUserDetails" />
</sec:authorize>
<nav>
	<div class="title">
		<h2>
			<spring:message code="Common.Application.Title" />
		</h2>
	</div>
	<ul>
		<sec:authorize access="isAnonymous()">
			<li class="floatLeft"><a href="${urlLogin}"><spring:message
						code="Login.SignIn" /></a></li>
		</sec:authorize>

		<sec:authorize access="isAuthenticated()">
			<li class="floatLeft"><a href="${urlList}"><spring:message
						code="CurrencyExchange.Query.Historical" /></a></li>
		</sec:authorize>

		<sec:authorize access="isAuthenticated()">
			<li class="floatLeft"><a href="${urlCurrent}"><spring:message
						code="Common.CurrentExchangeRate" /></a></li>
		</sec:authorize>

		<sec:authorize access="isAuthenticated()">
			<li class="floatLeft"><a href="${urlNewQuery}"><spring:message
						code="CurrencyExchange.Query.NewQuery" /></a></li>
		</sec:authorize>

		<sec:authorize access="isAuthenticated()">
			<li class="floatRight"><a
				href="<c:url value="/perform_logout" />"><spring:message
						code="Login.Logout" /> <span class="entypo-logout"></span></a></li>
		</sec:authorize>

		<c:if test="${pageContext.response.locale == 'de' }">
			<li class="floatRight"><a href="?lang=en">English</a></li>
		</c:if>

		<c:if test="${pageContext.response.locale == 'en' }">
			<li class="floatRight"><a href="?lang=de">Deustch</a></li>
		</c:if>

		<sec:authorize access="isAuthenticated()">
			<li class="floatRight"><a href="${urlUserUpdate}"><spring:message
						code="User.Update" /></a></li>
		</sec:authorize>

		<sec:authorize access="isAuthenticated()">
			<li class="floatRight"><a href="${urlUserDetails}"><spring:message
						code="User.Details" /></a></li>
		</sec:authorize>
	</ul>
</nav>
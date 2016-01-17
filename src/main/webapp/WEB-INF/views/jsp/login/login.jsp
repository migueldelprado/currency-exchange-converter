<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<!DOCTYPE html>
<html>

<jsp:include page="../fragments/header.jsp" />
<spring:url value="/users/add" var="urlAddUser" />

<body>
	<div class="main_small">
		<h2>
			<span class="fontawesome-lock"></span>
			<spring:message code="Login.SignIn" />
		</h2>

		<form action="j_spring_security_check" method="post">
			<fieldset>
				<c:if test="${error eq true}">
					<div class="error" role="error">
						<spring:message code="Login.Error" />
					</div>
				</c:if>
				<c:if test="${logout eq true}">
					<div class="msg">
						<spring:message code="Login.Logout.Success" />
					</div>
				</c:if>

				<p>
					<span class="entypo-mail"> <input type="email"
						placeholder="<spring:message code="User.Email" />" name="username" required /></span>
				</p>

				<p>
					<span class="entypo-key"> <input type="password"
						placeholder="<spring:message code="User.Password" />"
						name="password" required /></span>
				</p>

				<p>
					<input type="submit" value="<spring:message code="Login.SignIn" />">
				</p>

				<a class="link floatRight" href="${urlAddUser}"><span
					class="entypo-user-add"></span> <spring:message
						code="Common.Register" /></a>
			</fieldset>
		</form>
	</div>

	<jsp:include page="../fragments/footer.jsp" />
</body>

</html>
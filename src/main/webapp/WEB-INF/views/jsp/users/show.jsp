<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<!DOCTYPE html>
<html>

<jsp:include page="../fragments/header.jsp" />

<body>
	<div class="main_small">
		<h2>
			<spring:message code="User.Details" />
		</h2>
		<fieldset>
			<c:if test="${not empty msg}">
				<div class="alertNoMargin alert-${css}" role="alert">
					<strong>${msg}</strong>
				</div>
			</c:if>
			<c:if test="${css != 'error'}">
				<input type="text" placeholder="${user.name}" readonly />
				<input type="text" placeholder="${user.email}" readonly />
				<input type="text" placeholder="${user.dateOfBirth}" readonly />
				<input type="text" placeholder="${user.street}" readonly />
				<input type="text" placeholder="${user.zipCode}" readonly />
				<input type="text" placeholder="${user.city}" readonly />
				<input type="text" placeholder="${user.country}" readonly />
			</c:if>

		</fieldset>
	</div>

	<jsp:include page="../fragments/footer.jsp" />

</body>
</html>
<%@ page session="false"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<!DOCTYPE html>
<html lang="en">

<jsp:include page="../fragments/header.jsp" />

<spring:url value="/users" var="userActionUrl" />

<spring:message code="User.ID" var="ID" />
<spring:message code="User.Name" var="Name" />
<spring:message code="User.Email" var="Email" />
<spring:message code="User.DateOfBirth" var="DateOfBirth" />
<spring:message code="User.Password" var="Password" />
<spring:message code="User.ConfirmPassword" var="ConfirmPassword" />
<spring:message code="User.Street" var="Street" />
<spring:message code="User.ZipCode" var="ZipCode" />
<spring:message code="User.City" var="City" />
<spring:message code="User.Country" var="Country" />
<spring:message code="User.Add" var="Add" />
<spring:message code="User.Update" var="Update" />

<body>
	<div class="main_small">
		<c:choose>
			<c:when test="${empty userForm.userID}">
				<h2>${Add}</h2>
			</c:when>
			<c:otherwise>
				<h2>${Update}</h2>
			</c:otherwise>
		</c:choose>

		<fieldset>
			<spring:hasBindErrors name="userForm">
				<div class="error-area">
					<c:forEach var="error" items="${errors.allErrors}">
						<b><spring:message message="${error}" /></b>
						<br />
					</c:forEach>
				</div>
			</spring:hasBindErrors>

			<form:form method="post" modelAttribute="userForm"
				action="${userActionUrl}">

				<c:if test="${not empty userForm.userID}">
					<spring:bind path="userID">
						<form:input path="userID" type="text" placeholder="${ID}"
							class="${status.error ? 'has-error' : ''}" readonly="true" />
					</spring:bind>
				</c:if>

				<spring:bind path="name">
					<form:input path="name" type="text" placeholder="${Name}"
						class="${status.error ? 'has-error' : ''}" />
				</spring:bind>

				<spring:bind path="email">
					<form:input path="email" placeholder="${Email}"
						class="${status.error ? 'has-error' : ''}" />
				</spring:bind>

				<spring:bind path="dateOfBirth">
					<form:input path="dateOfBirth" placeholder="${DateOfBirth}"
						class="${status.error ? 'has-error' : ''}" />
				</spring:bind>

				<spring:bind path="password">
					<form:password path="password" placeholder="${Password}"
						showPassword="true" class="${status.error ? 'has-error' : ''}" />
				</spring:bind>

				<spring:bind path="confirmPassword">
					<form:password path="confirmPassword"
						placeholder="${ConfirmPassword}" showPassword="true"
						class="${status.error ? 'has-error' : ''}" />
				</spring:bind>

				<spring:bind path="street">
					<form:input path="street" placeholder="${Street}"
						class="${status.error ? 'has-error' : ''}" />
				</spring:bind>

				<spring:bind path="zipCode">
					<form:input path="zipCode" placeholder="${ZipCode}"
						class="${status.error ? 'has-error' : ''}" />
				</spring:bind>

				<spring:bind path="city">
					<form:input path="city" placeholder="${City}"
						class="${status.error ? 'has-error' : ''}" />
				</spring:bind>

				<spring:bind path="country">
					<form:select path="country" style="width: 320px;text-align: left;"
						class="${status.error ? 'has-error' : ''}">
						<form:option value="NONE" label="${Country}" />
						<form:options items="${countryList}" />
					</form:select>
				</spring:bind>

				<c:choose>
					<c:when test="${empty userForm.userID}">
						<button type="submit" class="button">${Add}</button>
					</c:when>
					<c:otherwise>
						<button type="submit" class="button">${Update}</button>
					</c:otherwise>
				</c:choose>

			</form:form>
		</fieldset>
	</div>

	<jsp:include page="../fragments/footer.jsp" />

</body>

</html>
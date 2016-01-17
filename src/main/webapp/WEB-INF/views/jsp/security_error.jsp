<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>

<jsp:include page="fragments/header.jsp" />

<body>
	<div class="main_big">
		<h2>
			<spring:message code="Common.ErrorTitle" />
		</h2>
		<fieldset>
			<spring:message code="Common.SecurityError" />
		</fieldset>
	</div>

	<jsp:include page="fragments/footer.jsp" />
</body>
</html>
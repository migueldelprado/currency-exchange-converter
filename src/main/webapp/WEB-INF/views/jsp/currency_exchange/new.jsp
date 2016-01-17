<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<!DOCTYPE html>
<html>

<jsp:include page="../fragments/header.jsp" />
<spring:url value="/currency_exchange/new" var="urlNew" />

<body>
	<div class="main_big">
		<h2>
			<spring:message code="CurrencyExchange.Query.NewQuery" />
		</h2>
		<fieldset>
			<c:if test="${not empty msg}">
				<div class="alert alert-${css}" role="alert">
					<strong>${msg}</strong>
					<button type="button" class="close" style="float:right;"data-dismiss="alert"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
				</div>
			</c:if>
			<form:form method="post" modelAttribute="currencyExchangeQueryForm"
				action="${urlNew}">
				<p>
					<form:input type="number" path="quantityOrigin" id="quantityOrigin"
						step="any" min="0" />
					<form:select path="originCurrency" id="originCurrency">
						<form:options items="${originCurrencyList}" />
					</form:select>
				</p>
				<p>
					<form:input type="number" path="exchangeRate" id="exchangeRate"
						step="any" min="0" />
					<form:select name="destinationCurrency" path="destinationCurrency"
						onchange="submit()">
						<form:options items="${destinationCurrenyList}" />
					</form:select>
				</p>
				<p>
					<button name="add" value="add" type="submit" class="button"
						style="width: 100px; padding: 10px;">
						<spring:message code="Common.Add" />
					</button>
				</p>
			</form:form>
		</fieldset>
	</div>

	<jsp:include page="../fragments/footer.jsp" />

	<script type="text/javascript">
		$(document).ready(function() {
			var rate = ${rate};
			$("#quantityOrigin").change(function() {
				var total = $('#quantityOrigin').val() * rate;
				$('#exchangeRate').val(total);
			});

			$("#exchangeRate").change(function() {
				var total = $('#exchangeRate').val() / rate;
				$('#quantityOrigin').val(total);
			});
		});
	</script>
</body>
</html>
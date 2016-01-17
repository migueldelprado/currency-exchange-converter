<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<!DOCTYPE html>
<html>

<jsp:include page="../fragments/header.jsp" />

<body>
	<div class="main_big">
		<h2>
			<spring:message code="CurrencyExchange.Query.Historical" />
		</h2>

		<c:choose>
			<c:when test="${not empty list}">
				<table>
					<tr>
						<th><spring:message code="CurrencyExchange.Query.CreatedDay" /></th>
						<th><spring:message code="CurrencyExchange.Query.QuantityOrigin" /></th>
						<th><spring:message code="CurrencyExchange.Query.OriginCurrency" /></th>
						<th><spring:message code="CurrencyExchange.Query.ExchangeRate" /></th>
						<th><spring:message code="CurrencyExchange.Query.TargetCurrency" /></th>
						<th><spring:message code="Common.Action" /></th>
					</tr>

					<c:forEach var="element" items="${list}">
						<tr>
							<td>${element.createdDate}</td>
							<td><fmt:formatNumber type="number" maxFractionDigits="6"
							value="${element.quantityOrigin}" /></td>
							<td>${element.originCurrency}</td>
							<td><fmt:formatNumber type="number" maxFractionDigits="6"
							value="${element.exchangeRate}" /></td>
							<td>${element.destinationCurrency}</td>
							<td><spring:url
									value="/currency_exchange/${element.id}/delete" var="deleteUrl" />
								<button class="button"
									onclick="this.disabled=true;post('${deleteUrl}')"><spring:message code="Common.Delete" /></button></td>
						</tr>
					</c:forEach>
				</table>
			</c:when>
			<c:otherwise>
				<fieldset>
					<p><spring:message code="CurrencyExchange.Query.NoQueries" /></p>
				</fieldset>
			</c:otherwise>
		</c:choose>
	</div>

	<jsp:include page="../fragments/footer.jsp" />

</body>
</html>
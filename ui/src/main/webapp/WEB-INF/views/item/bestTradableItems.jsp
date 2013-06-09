<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<section class="bestTradableItems">
	<h2>
		Best Tradable Items
	</h2>
	<article>
		<table>
			<tr>
				<th>Name</th>
				<th>Unitary Price</th>
				<th>Volume</th>
				<th>Resell Ratio</th>
			</tr>
			<c:forEach var="asset" items="${bestTradableItems}">
				<tr>
					<td>${asset.item.name}</td>
					<td>${asset.marketStats.sellStats.avg}</td>
					<td>${asset.marketStats.allStats.volume}</td>
					<td>${asset.marketStats.resellRatio}</td>
				</tr>
			</c:forEach>
		</table>
	</article>
</section>
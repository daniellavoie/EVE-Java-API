<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<script type="text/javascript">
	$(document).ready(function(){
		$(".character select").change(function(){
			alert($(this).val());
			location.href = $(this).val();
		});
	});
</script>
<section class="assets">
	<h2>
		Corporation Assets
	</h2>
	<article class="character">
		<h3>Select Character</h3>
		<select>
			<c:forEach items="${characters}"  var="character">
				<c:url 
					var="characterUrl" 
					value="/corporation/${character.eveId}/asset?sortBy=${sortBy}&ascending=${ascending}" 
				/>
				<c:choose>
					<c:when test="${character.eveId eq characterId}">
						<option 
							value="${characterUrl}" 
							selected="selected"
						>
							${character.name}
						</option>
					</c:when>
					<c:when test="">
						<option value="${characterUrl}">
							${character.name}
						</option>
					</c:when>
				</c:choose>
			</c:forEach>
		</select>
	</article>
	<article>
		<table>
			<tr>
				<th>Name</th>
				<th>Solar System</th>
				<th>Station Name</th>
				<th>Quantity</th>
				<th>Unitary Price</th>
				<th>Whole Price</th>
				<th>Nested elements</th>
			</tr>
			<c:forEach var="asset" items="${assets}">
				<tr>
					<td>${asset.item.name}</td>
					<td>${asset.station.solarSystem.name}
					<td>${asset.station.name}</td>
					<td>${asset.quantity}</td>
					<td>${asset.marketStats.sellStats.avg}</td>
					<td>${asset.marketStats.sellStats.avg * asset.quantity}</td>
				</tr>
					<c:forEach var="level2asset" items="${asset.assetContent}">
					<tr>
						<td>->&nbsp;${level2asset.item.name}</td>
						<td>->&nbsp;${level2asset.station.solarSystem.name}
						<td>->&nbsp;${level2asset.station.name}</td>
						<td>->&nbsp;${level2asset.quantity}</td>
						<td>->&nbsp;${level2asset.marketStats.sellStats.avg}</td>
						<td>->&nbsp;${level2asset.marketStats.sellStats.avg * asset.quantity}</td>
					</tr>
				</c:forEach>
			</c:forEach>
		</table>
	</article>
</section>
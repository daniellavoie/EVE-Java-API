<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<script type="text/javascript">
	$(document).ready(function(){
		$(".addInput button").click(function(){
			$(".newKey").show();
			$(".addInput").empty();
			
			$("tr.addLine button").click(addKey);
		});
		
		$("button.deleteKey").click(function(){
			location.href = $(this).attr("data-url");
		});
		
		$("tr.addLine button").click(addKey);
	});
	
	function addKey(){
		$("#keyID").val($(".keyID").val());
		$("#vCode").val($(".vCode").val());
		
		$(".keyManagement article form").submit();
	}
</script>
<section class="keyManagement">
	<h2>
		API Key Management ${error}
	</h2>
	
	<c:if test="${not empty error}">
		<article class="error">
			${error}
		</article>
	</c:if>
	<article>
		<table>
			<tr>
				<th>KeyID</th>
				<th>Verification Code</th>
				<th>&nbsp;</th>
			</tr>
			<c:forEach var="key" items="${keys}">
				<tr>
					<td>${key.keyID}</td>
					<td>${key.vCode}</td>
					
					<c:url var="deleteUrl" value="/key/${key.keyID}?delete" />
					<td>
						<button class="deleteKey" data-url="${deleteUrl}" type="button">
							DELETE
						</button>
					</td>
				</tr>
			</c:forEach>
			<c:if test="${empty error}">
				<tr class="addInput">
					<td colspan="3">
						<button type="button">ADD KEY</button>
					</td>
				</tr>
			</c:if>
			<c:set var="newKeyClass" value="" />
			<c:if test="${empty error}">
				<c:set var="newKeyClass" value="newKey" />
			</c:if>			
			<tr class="addLine ${newKeyClass}">
				<td>
					<input class="keyID" type="text" value="${key.keyID}" />
				</td>
				<td>
					<input class="vCode" type="text" value="${key.vCode}" />
				</td>
				<td><button type="button">ADD</button></td>
			</tr>
		</table>

		<sf:form method="POST" modelAttribute="key">
			<input type="hidden" name="save" />
			<sf:hidden id="keyID" path="keyID" />
			<sf:hidden id="vCode" path="vCode" />
			<input type="hidden" name="user" value="${key.user.id}" />
		</sf:form>
	</article>
</section>
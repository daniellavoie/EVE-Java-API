<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<script>
	$(document).ready(function(){
		hasAccessTo(
			"CharacterAssetList", 
			function(data){
				if(data == true){
					$("nav .characterAssets").show();
				}
			}
		);
		
		hasAccessTo(
			"CorporationAssetList", 
			function(data){
				if(data == true){
					$("nav .corporationAssets").show();
				}
			}
		);
	});
	
	function hasAccessTo(accessMask, callback){
		$.ajax({
			url: $("nav .key a").attr("href") + "?hasAccessTo=1&json=1",
			success : callback,
			data: accessMask,
			contentType : "application/json",
			dataType : "json",
			type: "POST"
		});
	}
</script>
<nav>
	<ul class="menu">
		<c:url var="characterAssetsUrl" value="/character/asset" />
		<li class="characterAssets"><a href="${characterAssetsUrl}">Character Assets</a></li>
		
		<c:url var="corporationAssetsUrl" value="/corporation/asset" />
		<li class="corporationAssets"><a href="${corporationAssetsUrl}">Corporation Assets</a></li>
		
		<c:url var="keyUrl" value="/key" />
		<li class="key"><a href="${keyUrl}">Key Management</a></li>
	</ul>
</nav>
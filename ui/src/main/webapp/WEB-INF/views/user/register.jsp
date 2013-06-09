<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<script type="text/javascript">
	$(document).ready(function(){
		$(".register button").click(function(){
			if($(".confirmPassword input").val() == $(".password input").val()){
				$(".register form").submit();				
			}else{
				$(".confirmPassword .error").show();
			}
		});
	});
</script>
<section class="register">
	<h2>
		Register Sacrament !
	</h2>
	
	<c:if test="${error}">
		<article class="error">
			${error}
		</article>
	</c:if>
	
	<article>
		<sf:form action="/user?register" method="POST" modelAttribute="user">
			<div>
				<div>Username : </div>
				<div>
					<sf:input path="username" />
				</div>
			</div>
			
			<div class="password">
				<div>Password : </div>
				<div>
					<sf:input path="password"  />
				</div>
			</div>
			
			<div class="confirmPassword">
				<div>Confirm Password : </div>
				<div>
					<input type="text" name="confirmPassword" />
				</div>
				<div class="error confirmPassword">
					Password confirmation does not match.
				</div>
			</div>
			
			
			<div>
				<div>email : </div>
				<div>
					<input type="text" name="email" />
				</div>
			</div>
			
			<div>
				<button type="button">Register</button>
			</div>
		</sf:form>
	</article>
</section>
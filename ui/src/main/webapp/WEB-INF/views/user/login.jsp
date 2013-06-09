<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<script type="text/javascript">
	$(document).ready(function(){
		$(".login button.login").click(function(){
			$(".login form").submit(); 
		});
		
		$(".login button.register").click(function(){
			location.href = $(".registerUrl").attr("data-url");
		});
	});
</script>
<section class="login">
	<h2>
		Formulaire de login esti !
	</h2>
	${login_error}
	<c:if test="${not empty error}">
		<article class="error">
			${error}
		</article>
	</c:if>
	<article>
		<c:url var="registerUrl" value="/user?register" />
		<div class="registerUrl" data-url="${registerUrl}"></div>
		
		<sf:form action="/static/j_spring_security_check">
			<c:url var="registerUrl" value="/user?register" />
			<div class="redirectUrl" data-url="/"></div>	
			<input 
				type="hidden" 
				name="_spring-security-redirect" 
				value="${redirectUrl}"
			/>
			
			<div>
				<div>Username : </div>
				<div>
					<input type="text" name="j_username" />
				</div>
			</div>
			<div>
				<div>Password : </div>
				<div>
					<input type="password" name="j_password" />
				</div>
			</div>
			
			<div>
				<div>Remember me</div>
				<div>
					<input 
						id="remember_me"
						name="_spring_security_remember_me"
						type="checkbox"
					/>
				</div>
			</div>
			
			<div>
				<button class="login" type="button">Login</button>
				<button class="register" type="button">Register</button>
			</div>
		</sf:form>
	</article>
</section>
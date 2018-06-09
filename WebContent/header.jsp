<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>    
<!DOCTYPE html>
<!-- 登录 注册 购物车... -->
<div class="container-fluid">
	<div class="col-md-4"   >
		<img src="images/tubiao.png"  />
	</div>
	<div class="col-md-5">
		<img src="img/header.png" />
	</div>
	<div class="col-md-3" style="padding-top:20px">
		<ol class="list-inline">
		<c:if test="${empty user }">
			<li><a href="login.jsp">登陆</a></li>
			<li><a href="register.jsp">注册</a></li>
		</c:if>
		<c:if test="${!empty user }">
			<li><a href="login.jsp" style="color:red">欢迎您，${user.name }</a></li>
			<li><a href="${pageContext.request.contextPath }/logout">退出</a></li>		
		</c:if>	
			
			<li><a href="cart.jsp">购物车</a></li>
			<li><a href="${pageContext.request.contextPath }/myOrders">我的订单</a></li>
		</ol>
	</div>
</div>

<!-- 导航条 -->
<div class="container-fluid" >
<!-- class="navbar navbar-inverse" -->

	<nav class="navbar navbar" style="background-color: #F0F0F0">
		<div class="container-fluid">
			<!-- Brand and toggle get grouped for better mobile display -->
			<div class="navbar-header">
				<button type="button" class="navbar-toggle " data-toggle="collapse" data-target="#bs-example-navbar-collapse-1" aria-expanded="false">
					<!-- <span class="sr-only">Toggle navigation</span>
					<span class="icon-bar"></span>
					<span class="icon-bar"></span>
					<span class="icon-bar"></span> -->
				</button>
				<a class="navbar-brand" href="${pageContext.request.contextPath }/index">首页</a>
			</div>

			<div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
			
			
				<ul class="nav navbar-nav" id="categoryUI"  >
				
			    
				</ul>
				
				
				<form class="navbar-form navbar-right" role="search">
					<div class="form-group">
						<input type="text" class="form-control" placeholder="Search">
					</div>
					<button type="submit" class="btn btn-default">Submit</button>
				</form>
			</div>
		</div>
		
		<script type="text/javascript" >
			$(function(){
				//ajax获取后台的category数据
				var content = "";
				$.post(
				"${pageContext.request.contextPath}/categoryList", //需要加载的地址 
				function(data){
					for(var i = 0;i<data.length;i++){
					content +="<li ><a href='${pageContext.request.contextPath}/findProductById?cid="+data[i].cid+"'>"+data[i].cname+"</a></li>";
					}
					$("#categoryUI").html(content);
				},
				"json"
				);
			});
			
		</script>
		
	</nav>
</div>
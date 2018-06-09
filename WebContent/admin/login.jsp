<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Login to admin</title>

<style type="text/css">
</style>

<script type="text/javascript">
	function checkFunction(){
		//接收数据
		var trueUsernam = "root";
		var truePassword = "123";
		var username = document.getElementById("username").value;
		var password = document.getElementById("password").value;
		//alert(username+password);
		if(username != "" &&password != ""){
			if(username == trueUsernam&password == truePassword){
				//alert();
				location.href="${pageContext.request.contextPath}/admin/home.jsp";
			}else{
				alert("账号或密码错误");
				return;
			}
		}else{
			alert("账号或密码为空");
			return;
		}
	}

</script>

</head>
<body style="background-image: url(${pageContext.request.contextPath }/img/1.jpg)">
		<div style="text-align:center">
			 <span style="color:gold;font-size: 80px;">
			 请您输入管理员登陆账号和密码
			 </span>
			
			<div >
				<table border="1px;solid:white" style="margin: auto;background-image: url(${pageContext.request.contextPath}/images/error.jpg)"  >
					<tr>
						<td><span style="font-size: 25px;color:black">请您输入管理员账号</span></td>
						<td><input type="text" value="" id="username"></td>
					</tr>
						
					<tr>
						<td><span style="font-size: 25px;color:black">请您输入管理员密码</span></td>
						<td><input type="password" value="" id="password"></td>
					</tr>
					
					<tr>
						<td><span style="font-size: 25px;color:black">确认无误后：</span></td>
						<td><a href="javascript:void(0)" onclick="checkFunction()"><input type="button" value="登陆"></a></td>
					</tr>
				
				
				</table>
				
			
			</div>
			
			
		</div>
</body>
</html>
<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib prefix="sec" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page session="true"%>
<html>
<head>
    <title>Login Page</title>
    <style>
    .error {
        padding: 15px;
        margin-bottom: 20px;
        border: 1px solid transparent;
        border-radius: 4px;
        color: #a94442;
        background-color: #f2dede;
        border-color: #ebccd1;
    }

    .msg {
        padding: 15px;
        margin-bottom: 20px;
        border: 1px solid transparent;
        border-radius: 4px;
        color: #31708f;
        background-color: #d9edf7;
        border-color: #bce8f1;
    }

    #login-box {
        width: 300px;
        padding: 20px;
        margin: 100px auto;
        background: #fff;
        -webkit-border-radius: 2px;
        -moz-border-radius: 2px;
        border: 1px solid #000;
    }
    </style>
    <meta name="_csrf" content="${_csrf.token}"/>
    <meta name="_csrf_header" content="${_csrf.headerName}"/>
</head>
<body>

	<h1>Cake Manager</h1>

	<div>
		<h2>Login with Username and Password</h2>

		<c:if test="${not empty error}">
			<div>${error}</div>
		</c:if>
		<c:if test="${not empty msg}">
			<div>${msg}</div>
		</c:if>

		<form method="post" action="/login" id="login" accept-charset="UTF-8">
            <table>
                <tr>
                    <td>User:</td><td><input type="text" name="username"></td>
                </tr>
                <tr>
                    <td>Password:</td><td><input type="password" name="password" /></td>
                </tr>
                <tr>
                    <td colspan="2"><input name="submit" type="submit" value="submit" /></td>
                </tr>
            </table>

            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
		</form>
	</div>

</body>
</html>
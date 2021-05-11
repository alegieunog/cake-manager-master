<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib prefix="sec" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page session="true"%>
<html>
<head>
    <style>
        div {
            text-align: center;
        }
        table {
            border-collapse: collapse;
            width: 100%;
        }

        table, th, td {
            border: 1px solid black;
        }
    </style>
</head>
<body>
	<h1>${title}</h1>
	<h2>${message}</h2>

    <div>
        <p><a href="/cakes">Add New Cake</a></p>
    </div>

	<table>
        <thead>
            <th>Image</th>
            <th>Title</th>
            <th>Description</th>
        </thead>
        <tbody>
            <c:forEach items="${cakes}" var="cake">
                <tr>
                    <td width="100px">
                        <a href="${pageContext.request.contextPath}/${cake.title}">
                            <img name="icon" sizes="auto" src="${cake.image}" width="100px" height="100px" alt="${cake.title}">
                        </a>
                    </td>
                    <td>
                        <input type="hidden" name="item" value="${cake.title}">
                        <a href="${pageContext.request.contextPath}/${cake.title}">
                            ${cake.title}
                        </a>
                    </td>
                    <td>
                        <div>
                            ${cake.description}
                        </div>
                    </td>
                </tr>
            </c:forEach>
        </tbody>
    </table>
</body>
</html>
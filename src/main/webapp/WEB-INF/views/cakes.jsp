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
	<h2>${add}</h2>
	<form method="post" action="/cake/add" accept-charset="UTF-8">

        <label for="Title">Title</label>
        <input type="text" name="title" id="Title" required autofocus />

        <label for="Description">Description</label>
        <input type="text" name="description" id="Description" required />

        <label for="Image">Image</label>
        <input type="text" name="image" id="Image" size="100" class="" required autocorrect="off" autocapitalize="off" />

        <div>
            <p><input type="submit" value="Add Cake" name="submit"></p>
        </div>

        <input type="hidden" name="_csrf.parameterName" value="${_csrf.token}" />
    </form>

	<h2>${list}</h2>
	<div>
	    <p>${json}</p>
    </div>

	<h2>${table}</h2>
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
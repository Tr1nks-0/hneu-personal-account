<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ include file="../jspf/management-header.jspf" %>
<div class="content-wrapper">
    <section class="content-header">
        <h1>
            Студенти
        </h1>
        <div class="content">
            <div class="box box-primary">
                <div class="box-header with-border">
                    <form method="get" action="/management/students">
                        <div class="input-group">
                            <input type="text" name="search-criteria" placeholder="ПІБ Група" class="form-control" value="${searchCriteria}">
                            <span class="input-group-btn">
                                <input type="submit" class="btn btn-primary" value="Пошук"/>
                            </span>
                        </div>
                    </form>
                </div>
                <div class="box-body table-responsive">
                    <c:choose>
                        <c:when test="${not empty students}">
                            <table class="table table-hover no-margin">
                                <c:forEach items="${students}" var="student">
                                    <tr onclick="document.location = '/management/students/${student.id}';">
                                        <td>
                                            <div class="image">
                                                <c:set var="studentProfileImage" value="/profile/${student.profileImage}"/>
                                                <c:if test="${empty student.profileImage}">
                                                    <c:set var="studentProfileImage" value="/resources/img/student_photo_default.png"/>
                                                </c:if>
                                                <img src="${studentProfileImage}" class="img-circle student-photo" alt="User Image">
                                            </div>
                                        </td>
                                        <td class="text-center">${student.name}</td>
                                        <td class="text-center">${student.surname}</td>
                                        <td class="text-center">${student.faculty}</td>
                                        <td class="text-center">${student.speciality}</td>
                                        <td class="text-center">${student.group}</td>
                                        <td class="text-center"><a class="btn btn-danger" href="/management/removeStudent?id=${student.id}"><i class="fa fa-remove"></i></a></td>
                                    </tr>
                                </c:forEach>
                            </table>
                        </c:when>
                        <c:otherwise>
                            <p class="text-center">Нікого не знайдено за заданним крітерієм</p>
                        </c:otherwise>
                    </c:choose>
                    <c:if test="${pagesCount > 1}">
                        <div class="text-center">
                            <ul class="pagination">
                                <c:if test="${1 < page}">
                                    <li><a href="/management/students?search-criteria=${searchCriteria}&page=${page - 1}">${page - 1}</a></li>
                                </c:if>
                                <li class="disabled"><a>${page}</a></li>
                                <c:if test="${page < pagesCount}">
                                    <li><a href="/management/students?search-criteria=${searchCriteria}&page=${page + 1}">${page + 1}</a></li>
                                </c:if>
                            </ul>
                        </div>
                    </c:if>
                    </div>
                </div>
            </div>
        </div>
    </section>
</div>

<%@ include file="../jspf/management-footer.jspf" %>

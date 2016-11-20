<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ include file="../jspf/management-header.jspf" %>
<div class="content-wrapper">
    <section class="content-header">
        <h1>
            Виберіть семестр ${group}
        </h1>
        <div class="content">
            <div class="box box-primary">
                <div class="box-body table-responsive">
                    <c:choose>
                        <c:when test="${not empty coursesCount}">
                            <table class="table table-hover no-margin">
                                <c:forEach var="courseNumber" begin="1" end="${coursesCount}">
                                    <tr>
                                        <td>
                                            <a href="/management/groups/${group}/${courseNumber}/1">${courseNumber} курс 1 семестр</a>
                                        </td>
                                    </tr>
                                        <td>
                                            <a href="/management/groups/${group}/${courseNumber}/2">${courseNumber} курс 2 семестр</a>
                                        </td>
                                    </tr>
                                </c:forEach>
                            </table>
                        </c:when>
                        <c:otherwise>
                            <p class="text-center">Нічого не знайдено за заданним крітерієм</p>
                        </c:otherwise>
                    </c:choose>
                    </div>
                </div>
            </div>
        </div>
    </section>
</div>

<%@ include file="../jspf/management-footer.jspf" %>

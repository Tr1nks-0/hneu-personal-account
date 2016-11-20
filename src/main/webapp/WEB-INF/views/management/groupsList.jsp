<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ include file="../jspf/management-header.jspf" %>
<div class="content-wrapper">
    <section class="content-header">
        <h1>
            Групи
        </h1>
        <div class="content">
            <div class="box box-primary">
                <div class="box-header with-border">
                    <form method="get" action="/management/special-disciplines">
                        <div class="input-group">
                            <input type="text" name="group" placeholder="Група" class="form-control" value="${group}">
                            <span class="input-group-btn">
                                <input type="submit" class="btn btn-primary" value="Пошук"/>
                            </span>
                        </div>
                    </form>
                </div>
                <div class="box-body table-responsive">
                    <c:choose>
                        <c:when test="${not empty groups}">
                            <table class="table table-hover no-margin">
                                <c:forEach items="${groups}" var="group">
                                    <tr>
                                        <td>
                                            <a href="/management/groups/${group}">${group}</a>
                                        </td>
                                    </tr>
                                </c:forEach>
                            </table>
                        </c:when>
                        <c:otherwise>
                            <p class="text-center">Нікого не знайдено за заданним крітерієм</p>
                        </c:otherwise>
                    </c:choose>
                    </div>
                </div>
            </div>
        </div>
    </section>
</div>

<%@ include file="../jspf/management-footer.jspf" %>

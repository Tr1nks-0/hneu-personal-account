<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" %>

<%@ include file="../jspf/header.jspf" %>

<c:choose>
    <c:when test="${not empty pairs}">
        <div class="content-wrapper schedule-wrapper">
            <section class="content-header">
                <div class="row">
                    <c:if test="${week > 1}">
                        <a class="btn" href="/account/schedule?week=${week-1}">Попередня</a>
                    </c:if>
                    <c:if test="${week < 53}">
                        <a class="btn float-right" href="/account/schedule?week=${week+1}">Наступна</a>
                    </c:if>
                </div>
                <div class="table-responsive">
                    <table class="table schedule-table table-striped table-hover">
                        <thead>
                            <tr>
                                <th></th>
                                <c:forEach items="${days}" var="day">
                                    <th>
                                        <span class="day">${day.name}</span><br>
                                        <span class="date">${day.displayName}</span>
                                    </th>
                                </c:forEach>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach begin="1" end="7" var="pairNumber">
                                <tr>
                                    <td><span class="pair-number">${pairNumber}</span></td>
                                    <c:forEach begin="1" end="7" var="day">
                                            <c:choose>
                                                <c:when test="${not empty pairs[pairNumber] and not empty pairs[pairNumber][day]}">
                                                    <c:set var="pair" value="${pairs[pairNumber][day]}"/>
                                                    <c:choose>
                                                        <c:when test="${not empty pair.scheduleElement and pair.scheduleElement.size() > 0}">
                                                            <td class="pair pair-multy">
                                                                <c:forEach items="${pair.scheduleElement}" var="scheduleElement">
                                                                    <div class="pair-description-multy">
                                                                        <b>${scheduleElement.subject.shortName}</b><br>
                                                                        <span class="info-tag">
                                                                            <i class="fa fa-graduation-cap"></i> ${scheduleElement.teacher.displayName}
                                                                        </span>
                                                                        <div class="row">
                                                                            <div class="col-md-6">
                                                                                <c:forEach items="${scheduleElement.groups.group}" var="group">
                                                                                    <c:if test="${group.main eq 'true'}">
                                                                                        <span class="info-tag"><i class="fa fa-group"></i> ${group.displayName}</span><br>
                                                                                    </c:if>
                                                                                </c:forEach>
                                                                            </div>
                                                                            <div class="col-md-6">
                                                                                <span class="info-tag">
                                                                                    <i class="fa fa-map-marker"></i> ${scheduleElement.room.shortName}
                                                                                </span>
                                                                            </div>
                                                                        </div>
                                                                        <div class="type-small">
                                                                            <span class="label label-info">${scheduleElement.type.value}</span><br>
                                                                        </div>
                                                                    </div>
                                                                </c:forEach>
                                                            </td>
                                                        </c:when>
                                                        <c:otherwise>
                                                            <td class="pair">
                                                                <div class="pair-description">
                                                                    <b>${pair.subject.value}</b><br>

                                                                    <div class="time">
                                                                        ${pair.start} - ${pair.breakStart}<br>
                                                                        ${pair.breakEnd} - ${pair.end}
                                                                    </div>

                                                                    <span class="info-tag"><i class="fa fa-graduation-cap"></i> ${pair.teacher.displayName}</span><br>
                                                                    <span class="info-tag"><i class="fa fa-map-marker"></i> ${pair.room.shortName}</span><br>
                                                                    <c:forEach items="${pair.groups.group}" var="group">
                                                                        <c:if test="${group.main eq 'true'}">
                                                                            <span class="info-tag"><i class="fa fa-group"></i> ${group.displayName}</span><br>
                                                                        </c:if>
                                                                    </c:forEach>
                                                                    <div class="type"><span class="label label-info">${pair.type.value}</span></div>
                                                                </div>
                                                            </td>
                                                        </c:otherwise>
                                                    </c:choose>
                                                </c:when>
                                                <c:otherwise>
                                                    <td></td>
                                                </c:otherwise>
                                            </c:choose>
                                    </c:forEach>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </div>
            </section>
        </div>
    </c:when>
    <c:otherwise>
        <div class="content-wrapper">
            <section class="content-header" style="padding: 0px">
                <iframe class="schedule-frame" src="http://services.ksue.edu.ua:8081/schedule/schedule?group=${groupId}">
                </iframe>
            </section>
        </div>
    </c:otherwise>
</c:choose>


<%@ include file="../jspf/footer.jspf" %>


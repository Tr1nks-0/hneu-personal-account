<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ include file="jspf/header.jspf" %>
<%@ page contentType="text/html;charset=UTF-8" %>

<div class="container" id="main-container">
    <div class="row contacts">
        <c:if test="${success}">
            <div class="alert alert-info col-md-offset-1">
                Повідомлення успішно відправлено!
            </div>
        </c:if>
        <div class="col-md-6">
           <img src="/resources/img/foto_koc.jpg" class="img-thumbnail contacts-photo">
        </div>
        <div class="col-md-6">
            <p>Доцент кафедри інформаційних систем, кандидат економічних наук.</p>
            <hr/>
            <p>61166, Україна, м.Харків, пр-т Науки 9а, ХНЕУ, Головний корпус, ауд. 420.</p>
            <p><b>Тел.</b> +38 (057) 702-18-31</p>
            <p><b>Факс:</b> +38 (057) 702-07-17</p>
            <p><b>Сайт:</b> http://www.ei.hneu.edu.ua/</p>
            <p><b>E-mail:</b> ei@ksue.edu.ua</p>
        </div>
    </div>
    <form class="form-horizontal" role="form" method="post" action="/account/sendEmail">
        <div class="row">
            <div class="col-md-offset-2 col-md-9">
                <p>Текст Вашого питання</p>
                <div class="row form-group">
                    <textarea class="form-control" rows="4" name="message" id="message"
                              required="required" maxlength="250"></textarea>
                </div>
                <div class="row form-group">
                    <input id="submit" name="submit" type="submit" value="Відправити" class="btn btn-primary">
                </div>
            </div>
        </div>
    </form>
</div>

    <%@ include file="jspf/management-footer.jspf" %>
<%@ include file="jspf/footer.jspf" %>
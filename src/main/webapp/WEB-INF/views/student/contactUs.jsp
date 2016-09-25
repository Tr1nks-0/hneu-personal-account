<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" %>

<%@ include file="../jspf/header.jspf" %>

<div class="content-wrapper">
    <section class="content-header">
        <h1>
            Зворотній зв'язок
            <small>Написати листа декану</small>
        </h1>
        <div class="content">
            <c:if test="${success}">
                <div class="alert alert-success">
                    Повідомлення успішно відправлено!
                </div>
            </c:if>

            <div class="box box-info direct-chat">
                <div class="row chat-header">
                    <div class="col-md-3 col-sm-12 col-xs-12">
                        <img class="img-responsive decan-photo direct-chat-img" src="/resources/img/foto_koc.jpg" alt="message user image">
                    </div>
                    <div class="chat-text col-md-8 col-sm-12 col-xs-12">
                        <span>Доцент кафедри інформаційних систем, кандидат економічних наук.</span><br>
                        <span><b>Коц Григорій Павлович</b></span><hr>
                        <span>61166, Україна, м.Харків, пр-т Науки 9а, ХНЕУ, Головний корпус, ауд. 420.</span><br>
                        <span><b>Тел.</b> +38 (057) 702-18-31</span><br>
                        <span><b>Факс:</b> +38 (057) 702-07-17</span><br>
                        <span><b>Сайт:</b> http://www.ei.hneu.edu.ua/</span><br>
                        <span><b>E-mail:</b> dekanstei@gmail.com</span>
                    </div>
                </div>

                <div class="box-footer">
                    <form class="form-horizontal" role="form" method="post" action="/account/sendEmail">
                        <div class="input-group">
                            <textarea class="form-control" rows="1" name="message" id="message" required="required" maxlength="250"></textarea>
                            <span class="input-group-btn">
                                <input id="submit" name="submit" type="submit" value="Відправити" class="btn btn-info btn-flat">
                            </span>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </section>
</div>

<%@ include file="../jspf/footer.jspf" %>
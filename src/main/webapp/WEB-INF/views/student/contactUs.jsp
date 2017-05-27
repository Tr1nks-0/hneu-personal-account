<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" %>

<%@ include file="../jspf/header.jspf" %>

<div class="content-wrapper">
    <div class="content">

        <ol class="breadcrumb panel panel-default">
            <li><a href="/account"><i class="fa fa-home"></i></a></li>
            <li class="active">Написати листа декану</li>
        </ol>

        <c:if test="${success}">
            <div class="alert alert-success">
                Повідомлення успішно відправлено!
            </div>
        </c:if>

        <div class="row">
            <div class="col-md-12">
                <div class="panel panel-default">
                    <div class="panel-body">
                        <ul class="chat">
                            <li class="left clearfix">
                                <div class="hidden-sm  hidden-xs chat-img pull-left">
                                    <img class="img-circle img-contact" src="/resources/img/foto_koc.jpg" alt="message user image">
                                </div>
                                <div class="chat-body clearfix">
                                    <div class="header">
                                        <strong class="primary-font"><b>Коц Григорій Павлович</b></strong>
                                    </div>
                                    <p>Доцент кафедри інформаційних систем, кандидат економічних наук.</p>
                                    <br>
                                    <p>61166, Україна, м.Харків, пр-т Науки 9а, ХНЕУ, Головний корпус, ауд. 420.</p>
                                    <p><b>Тел.</b> +38 (057) 702-18-31</p>
                                    <p><b>Факс:</b> +38 (057) 702-07-17</p>
                                    <p><b>Сайт:</b> <a href="http://www.ei.hneu.edu.ua/">http://www.ei.hneu.edu.ua</a></p>
                                    <p><b>E-mail:</b> dekanstei@gmail.com</p>
                                </div>
                            </li>
                        </ul>
                    </div>
                    <div class="panel-footer">
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
        </div>
    </div>
</div>

<%@ include file="../jspf/footer.jspf" %>
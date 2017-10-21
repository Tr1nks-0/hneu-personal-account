 $("#speciality #faculty").change(function() {
    var faculty = $("#speciality #faculty option:selected").val();
    window.location.href="/management/specialities?facultyId=" + faculty;
});

$(".delete-speciality").click(function() {
    var speciality = $(this).data("speciality");
    var container = $(this).closest("tr");

    $.post("/management/specialities/" + speciality + "/delete")
        .done(function(){
            container.fadeOut(300, function(){
                $(this).remove();
                if(!$(".delete-speciality").length) {
                    $(".specialities-management-panel").html('Нічого не знайдено');
                }
            })
        })
        .fail(function() {
            $("<div />", {
                "class": "alert alert-error alert-dismissible",
                text: 'Спеціальність не можна видалити так, як вона використовується'
            }).hide().fadeIn(300).insertAfter( ".breadcrumb" );
            $(".alert-error").delay(2000).fadeOut(300, function() {
                $(this).remove()
            });
        });
});
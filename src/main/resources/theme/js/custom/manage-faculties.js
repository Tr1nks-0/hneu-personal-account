$(".delete-faculty ").click(function() {
    var faculty = $(this).data("faculty");
    var container = $(this).closest("tr");

    $.post("/management/faculties/" + faculty + "/delete")
        .done(function(){
            container.fadeOut(300, function(){
                $(this).remove();
                if(!$(".delete-faculty").length) {
                    $(".faculties-management-panel").html('Нічого не знайдено');
                }
            })
        })
        .fail(function() {
            $("<div />", {
                "class": "alert alert-error alert-dismissible",
                text: 'Факультет не можна видалити так, як він використовується'
            }).hide().fadeIn(300).insertAfter( ".breadcrumb" );
            $(".alert-error").delay(2000).fadeOut(300, function() {
                $(this).remove()
            });
        });
});

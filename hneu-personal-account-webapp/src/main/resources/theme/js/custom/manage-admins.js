$(".delete-admin").click(function() {
    var adminId = $(this).data("admin");
    var container = $(this).closest(".list-group-item");

    $.post("/management/configs/admins/" + adminId + "/delete")
        .done(function(){
            container.fadeOut(300, function(){
                $(this).remove();
                if($(".delete-admin").length === 1) {
                    $(".delete-admin").closest('div').html('');
                }
            })
        })
        .fail(function() {
            $("<div />", {
                "class": "alert alert-error alert-dismissible",
                text: 'Адміністратора не можна видалити!'
            }).hide().fadeIn(300).insertAfter( ".breadcrumb" );
            $(".alert-error").delay(2000).fadeOut(300, function() {
                $(this).remove()
            });
        });
});
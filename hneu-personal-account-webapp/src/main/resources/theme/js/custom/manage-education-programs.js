$("#educationProgram #faculty").change(function() {
    var faculty = $("#educationProgram #faculty option:selected").val();
    window.location.href="/management/education-programs?facultyId=" + faculty;
});

$("#educationProgram #speciality").change(function() {
    var faculty = $("#educationProgram #faculty option:selected").val();
    var speciality = $("#educationProgram #speciality option:selected").val();
    window.location.href="/management/education-programs?facultyId=" + faculty + "&specialityId=" + speciality;
});

$(".delete-education-program").click(function() {
    var educationProgram = $(this).attr("education-program-id");
    var container = $(this).closest("tr")

    $.post("/management/education-programs/" + educationProgram + "/delete")
        .done(function(){
            container.fadeOut(300, function(){
                $(this).remove();
                if(!$(".delete-education-program").length) {
                    $(".education-programs-management-panel").html('Нічого не знайдено');
                }
            })
        })
        .fail(function() {
            $("<div />", {
                "class": "alert alert-error alert-dismissible",
                text: 'Напрям підготовка не можна видалити так, як він використовується'
            }).hide().fadeIn(300).insertAfter( ".breadcrumb" );
            $(".alert-error").delay(2000).fadeOut(300, function() {
                $(this).remove()
            });
        });
});
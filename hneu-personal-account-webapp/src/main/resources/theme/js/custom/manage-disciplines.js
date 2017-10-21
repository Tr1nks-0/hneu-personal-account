$("#discipline #faculty").change(function() {
    var faculty = $("#discipline #faculty option:selected").val();
    window.location.href="/management/disciplines?facultyId=" + faculty;
});

$("#discipline #speciality").change(function() {
    var faculty = $("#discipline #faculty option:selected").val();
    var speciality = $("#discipline #speciality option:selected").val();
    window.location.href="/management/disciplines?facultyId=" + faculty + "&specialityId=" + speciality;
});

$("#discipline #educationProgram").change(function() {
    var faculty = $("#discipline #faculty option:selected").val();
    var speciality = $("#discipline #speciality option:selected").val();
    var educationProgram = $("#discipline #educationProgram option:selected").val();
    window.location.href="/management/disciplines?facultyId=" + faculty + "&specialityId=" + speciality
        + "&educationProgramId=" +educationProgram;
});


$("#discipline #course").change(function() {
    var faculty = $("#discipline #faculty option:selected").val();
    var speciality = $("#discipline #speciality option:selected").val();
    var educationProgram = $("#discipline #educationProgram option:selected").val();
    var course = $("#discipline #course option:selected").val();
    var semester = $("#discipline #semester option:selected").val();
    window.location.href="/management/disciplines?facultyId=" + faculty + "&specialityId=" + speciality
        + "&educationProgramId=" +educationProgram
        + "&course=" + course
        + "&semester=" + semester;
});

$("#discipline #semester").change(function() {
    var faculty = $("#discipline #faculty option:selected").val();
    var speciality = $("#discipline #speciality option:selected").val();
    var educationProgram = $("#discipline #educationProgram option:selected").val();
    var course = $("#discipline #course option:selected").val();
    var semester = $("#discipline #semester option:selected").val();
    window.location.href="/management/disciplines?facultyId=" + faculty + "&specialityId=" + speciality
        + "&educationProgramId=" +educationProgram
        + "&course=" + course
        + "&semester=" + semester;
});

$(".delete-discipline").click(function() {
    var discipline = $(this).data("discipline");
    var container = $(this).closest("tr");

    $.post("/management/disciplines/" + discipline + "/delete")
        .done(function(){
            container.fadeOut(300, function(){
                $(this).remove();
                if(!$(".delete-discipline").length) {
                    $(".disciplines-management-panel").html('Нічого не знайдено');
                }
            })
        })
        .fail(function() {
            $("<div />", {
                "class": "alert alert-error alert-dismissible",
                text: 'Дисципліну не можна видалити так, як вона використовується'
            }).hide().fadeIn(300).insertAfter( ".breadcrumb" );
            $(".alert-error").delay(2000).fadeOut(300, function() {
                $(this).remove()
            });
        });
});
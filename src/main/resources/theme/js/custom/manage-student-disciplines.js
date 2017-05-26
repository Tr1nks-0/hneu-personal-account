$("#disciplineMark #course").change(redirectToStudentDisciplines);

$("#disciplineMark #semester").change(redirectToStudentDisciplines);

$(".delete-student-discipline").click(function() {
    var student = getStudentId();
    var disciplineMark = $(this).data("discipline");
    $.post( "/management/students/" + student + "/disciplines/" + disciplineMark + "/delete", function() {
        redirectToStudentDisciplines()
    });
});

function redirectToStudentDisciplines() {
    var student = getStudentId();
    var course = $("#disciplineMark #course option:selected").val();
    var semester = $("#disciplineMark #semester option:selected").val();
    window.location.href="/management/students/" + student + "/disciplines?course=" + course + "&semester=" + semester;
}

function getStudentId() {
    return $("#disciplineMark").data("student");
}

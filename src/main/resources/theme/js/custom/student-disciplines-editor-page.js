$("#disciplineMark #course").change(redirectWithPickedCourseAndSemester);

$("#disciplineMark #semester").change(redirectWithPickedCourseAndSemester);

$(".delete-student-discipline").click(function() {
    var student = getStudentId();
    var disciplineMark = $(this).data("discipline");
    $.post( "/management/students/" + student + "/disciplines/" + disciplineMark + "/delete", function() {
        redirectWithPickedCourseAndSemester()
    });
});

function redirectWithPickedCourseAndSemester() {
    var student = getStudentId();
    var course = $("#disciplineMark #course option:selected").val();
    var semester = $("#disciplineMark #semester option:selected").val();
    window.location.href="/management/students/" + student + "/disciplines?course=" + course + "&semester=" + semester;
}

function getStudentId() {
    return $("#disciplineMark").data("student");
}

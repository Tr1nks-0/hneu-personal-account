$("#group-disciplines-panel #course").change(redirectToGroupDisciplinesPageWithCourseAndSemester);
$("#group-disciplines-panel #semester").change(redirectToGroupDisciplinesPageWithCourseAndSemester);

function redirectToGroupDisciplinesPageWithCourseAndSemester() {
    var group = $("#group-disciplines-panel").data("group");
    var course = $("#group-disciplines-panel #course option:selected").val();
    var semester = $("#group-disciplines-panel #semester option:selected").val();
    window.location.href = "/management/groups/" + group + "/disciplines?course=" + course + "&semester=" + semester;
}
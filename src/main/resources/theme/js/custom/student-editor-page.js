$("#save-student").click(function () {
    $("#student").submit();
});

$("#delete-student").click(function() {
    var student = $(this).data("student");
    var group = $(this).data("group");
    $.post( "/management/students/" + student + "/delete", function() {
        window.location.href="/management/students?groupId=" + group;
    });
});

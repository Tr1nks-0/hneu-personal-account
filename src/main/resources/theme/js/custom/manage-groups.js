$("#group #faculty").change(function() {
    var faculty = $("#group #faculty option:selected").val();
    window.location.href="/management/groups?facultyId=" + faculty;
});

$("#group #speciality").change(function() {
    var faculty = $("#group #faculty option:selected").val();
    var speciality = $("#speciality option:selected").val();
    window.location.href="/management/groups?facultyId=" + faculty + "&specialityId=" + speciality;
});

$("#group #educationProgram").change(function() {
    var faculty = $("#group #faculty option:selected").val();
    var speciality = $("#group #speciality option:selected").val();
    var educationProgram = $("#group #educationProgram option:selected").val();
    window.location.href="/management/groups?facultyId=" + faculty + "&specialityId=" + speciality
        + "&educationProgramId=" +educationProgram;
});

$(".delete-groups").click(function() {
    var group = $(this).attr("group-id");
    var container = $(this).closest("tr")
    $.post( "/management/groups/" + group + "/delete", function() {
        container.fadeOut(300, function(){
            $(this).remove();
            if(!$(".delete-groups").length) {
                $(".disciplines-management-panel").html("<spring:message code='items.not.found'/>");
            }
        });
    });
});
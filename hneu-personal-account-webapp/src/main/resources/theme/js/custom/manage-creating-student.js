$("#create-student #faculty").change(function () {
    var faculty = $("#create-student #faculty option:selected").val();
    window.location.href = "/management/students/create?facultyId=" + faculty;
});

$("#create-student #speciality").change(function () {
    var faculty = $("#create-student #faculty option:selected").val();
    var speciality = $("#create-student #speciality option:selected").val();
    window.location.href = "/management/students/create?facultyId=" + faculty + "&specialityId=" + speciality;
});

$("#create-student #educationProgram").change(function () {
    var faculty = $("#create-student #faculty option:selected").val();
    var speciality = $("#create-student #speciality option:selected").val();
    var educationProgram = $("#create-student #educationProgram option:selected").val();
    window.location.href = "/management/students/create?facultyId=" + faculty + "&specialityId=" + speciality
        + "&educationProgramId=" + educationProgram;
});
$(document).ready(function () {
    $('.delete-subject-inList').click(function () {
        $(this).attr("data-toggle", "modal");
        $(this).attr("data-target", "#exampleModal");
        var subject = $(this).parents().siblings('.nameHp').text().trim();
        $('.node-delete').click(function () {
            $.ajax({
                url: "http://localhost:9000/api/v1/list-subject-by-tenHocPhan/show-list-subject-choosen",
                type: 'get',
                data: {nameSubject: subject},
                success: function (response) {
                    location.href = "http://localhost:9000/api/v1/list-subject-by-tenHocPhan/show-list-subject-choosen";
                },
                error: function (xhr) {

                }

            })
        })
    });

    $('.create-timetable-all').click(function () {
        $('#accordion').removeClass('d-none');
        $('.create-my-timetable').addClass('d-none');
        $.ajax({
            url: "http://localhost:9000/api/v1/list-subject-by-tenHocPhan/show-list-subject-choosen",
            type: 'get',
            success: function (response) {
            },
            error: function (xhr) {

            }
        })
    });
    $("#myTimetable").click(function () {
        $('.create-my-timetable').removeClass('d-none');
        $('#accordion').addClass('d-none');
    })

    // $('.btnMyTimeTable').click(function () {
    //     var totalSubject = $(".total").text().trim();
    //     var listIdSub = [];
    //     var i = 0;
    //     totalSubject = parseInt(totalSubject);
    //     while (i < totalSubject) {
    //         listIdSub[i] = $("#id" + i).children('option:selected').val();
    //         ++i;
    //     }
    //
    //     $.ajax({
    //         url:"http://localhost:9000/api/v1/list-subject-by-tenHocPhan/show-list-subject-choosen",
    //         type:'post',
    //         data: {listId: JSON.stringify(listIdSub)},
    //         dataType: "json",
    //         success:function (response){
    //             console.log(response);
    //         },
    //         error: function (xhr){
    //
    //         }
    //     });
    //     $("#thoiKhoaBieu").removeClass('d-none');
    //
    //
    // });


});
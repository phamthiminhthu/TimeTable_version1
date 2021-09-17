const key = "number";
const key1 = "subjects";
const key2 = "subjectsMaHocPhan";
var count = localStorage.getItem(key);
var listSubject = localStorage.getItem(key1);
var listMaHocPhan = localStorage.getItem(key2);
$(document).ready(function () {
    $('.delete-subject-inList').click(function () {
        $(this).attr("data-toggle", "modal");
        $(this).attr("data-target", "#exampleModal");
        var subject = $(this).parents().siblings('.nameHp').text().trim();
        var maHocPhan = $(this).parents().siblings('.ma-hocPhan').text().trim();
        console.log(maHocPhan);
        if (checkJson(listSubject)) {
            listSubject = JSON.parse(listSubject);
        }
        if (checkJson(listMaHocPhan)){
            listMaHocPhan = JSON.parse(listMaHocPhan);
        }
        $('.node-delete').click(function () {
            for (var i = 0; i < listSubject.length; i++) {
                if (listSubject[i].localeCompare(subject) == 0) {
                    listSubject.splice(i, 1);
                }
            }


            for (var i = 0; i < listMaHocPhan.length; i++) {
                console.log(listMaHocPhan[i].localeCompare(maHocPhan));
                if (listMaHocPhan[i].localeCompare(maHocPhan) == 0) {
                    listMaHocPhan.splice(i, 1);
                }
            }
            --count;
            localStorage.setItem(key, count);
            localStorage.setItem(key2, JSON.stringify(listMaHocPhan));
            localStorage.setItem(key1, JSON.stringify(listSubject));
            $.ajax({
                url: "http://localhost:9000/api/v1/list-subject-by-tenHocPhan/show-list-subject-choosen",
                type: 'get',
                data: {nameSubject: subject},
                success: function (response) {
                    location.href = "http://localhost:9000/api/v1/list-subject-by-tenHocPhan/show-list-subject-choosen";
                },
                error: function (xhr) {
                }
            });
        });

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

    $('.btnMyTimeTable').click(function () {
        var totalSubject = $(".total").text().trim();
        var listIdSub = [];
        var i = 0;
        totalSubject = parseInt(totalSubject);
        while (i < totalSubject) {
            listIdSub[i] = $("#id" + i).children('option:selected').val();
            ++i;
        }
        var paints = [];
        $.ajax({
            url:"http://localhost:9000/api/v1/list-subject-by-tenHocPhan/show-list-subject-choosen",
            type:'get',
            data: {listId: JSON.stringify(listIdSub)},
            dataType: "json",
            success:function (response){
               console.log(response.length);
               if($('.time-table-created').children().hasClass('createdTKB')){
                   $('.time-table-created').children().remove('tr');
               }
                for(var thu = 2; thu < 7; thu++){
                    paints += '<tr class="createdTKB"><th scope="row">' + thu + '</th>';
                    for(var index = 0; index < response.length; index++){
                        if(response[index].thu == thu){
                            paints += '<td><span>' + response[index].tenHocPhan + '</span></br>';
                            paints += '<span> Mã học phần : ' + response[index].maHp + '</span></br>';
                            paints += '<span> Thời gian: ' + response[index].thoiGian + '</span></br>';
                            paints += '<span> Phòng: ' + response[index].phong + '</span></br>';
                            paints += '<span> Buổi : ' + response[index].buoi + '</span></td>';
                        }
                    }
                    paints += '</tr>';

                }
                $('.time-table-created').append(paints);

            },
            error: function (xhr){
                console.log("ahihi")
            }

        });
     $("#thoiKhoaBieu").removeClass('d-none');
     paints = [];
    });




});
function checkJson(x) {
    try {
        JSON.parse(x);
    } catch (e) {
        return false;
    }
    return true;
}

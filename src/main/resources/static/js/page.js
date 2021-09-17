var listSubject = [];
var listMaHocPhan = [];
var count = 0;
const key = "number";
const key1 = "subjects";
const key2 = "subjectsMaHocPhan";
$(document).ready(function () {
    $('.add-subject').click(function () {
        var nameSubject = $(this).siblings('.card-title').text().trim();
        var maHocPhan = $(this).siblings('.nav').children('.ma-hoc-phan')
            .children('span').text().trim();
        if (checkJson(listSubject)) {
            listSubject = JSON.parse(listSubject);
        }
        if (checkJson(listMaHocPhan)){
            listMaHocPhan = JSON.parse(listMaHocPhan);
        }
        listSubject.push(nameSubject);
        listMaHocPhan.push(maHocPhan);
        localStorage.setItem(key2, JSON.stringify(listMaHocPhan));
        localStorage.setItem(key1, JSON.stringify(listSubject));

        ++count;
        localStorage.setItem(key, count);
        $('.count').html(count);

        $(this).attr('disabled', 'disabled');
        $(this).siblings('.delete-subject').removeAttr("disabled");

    });
    $('.delete-subject').click(function () {
        if (checkJson(listSubject)) {
            listSubject =  JSON.parse(listSubject);
        }
        if (checkJson(listMaHocPhan)){
            listMaHocPhan = JSON.parse(listMaHocPhan);
        }
        var nameSubjectDelete = $(this).siblings('.card-title').text().trim();
        var maHocPhan = $(this).siblings('.nav').children('.ma-hoc-phan')
            .children('span').text().trim();
        for (var i = 0; i < listSubject.length; i++) {
            if (listSubject[i].localeCompare(nameSubjectDelete) == 0) {
                listSubject.splice(i, 1);
            }
        }

        for (var i = 0; i < listMaHocPhan.length; i++) {
            if (listMaHocPhan[i].localeCompare(maHocPhan) == 0) {
                listMaHocPhan.splice(i, 1);
            }
        }
        --count;
        localStorage.setItem(key, count);
        $('.count').html(count);

        localStorage.setItem(key2, JSON.stringify(listMaHocPhan));
        localStorage.setItem(key1, JSON.stringify(listSubject));

        $(this).siblings('.add-subject').removeAttr("disabled");
        $(this).attr("disabled", "disabled");
    });

    $('.list-subject-choosen').click(function () {
        listSubject = localStorage.getItem(key1);
        if (count > 0) {
            $.ajax({
                url: "http://localhost:9000/api/v1/list-subject-by-tenHocPhan/show-list-subject-choosen",
                type: 'get',
                data: {listSubject: listSubject},
                dataType: "json",
                success: function (response) {
                    location.href = "http://localhost:9000/api/v1/list-subject-by-tenHocPhan/show-list-subject-choosen";

                }, error: function (xhr) {

                }
            });
        }
    })

    $('.count').html(localStorage.getItem(key));
    var subjectChoose = localStorage.getItem(key2);
    if (subjectChoose) {
        subjectChoose = JSON.parse(subjectChoose);
        for (var i = 0; i < subjectChoose.length; i++) {
            var idAdd = "add" + subjectChoose[i];
            $("#" + idAdd).attr('disabled', 'disabled');
            var idDelete = "delete" + subjectChoose[i];
            $("#" + idDelete).removeAttr('disabled');
        }
        listSubject = localStorage.getItem(key1);
        count = localStorage.getItem(key);
        listMaHocPhan = localStorage.getItem(key2);


    }


})

function checkJson(x) {
    try {
        JSON.parse(x);
    } catch (e) {
        return false;
    }
    return true;
}
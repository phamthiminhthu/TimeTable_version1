$(document).ready(function () {
    var listSubject = [];
    var count = 0;
    $('.add-subject').click(function () {
        var nameSubject = $(this).siblings('.card-title').text().trim();
        listSubject.push(nameSubject);
        ++count;
        $('.count').html(count);
        $(this).attr('disabled', 'disabled');
        $(this).siblings('.delete-subject').removeAttr("disabled");
        console.log(listSubject);
    });
    $('.delete-subject').click(function () {
        var nameSubjectDelete = $(this).siblings('.card-title').text().trim();
        for (var i = 0; i < listSubject.length; i++) {;
            if (listSubject[i].localeCompare(nameSubjectDelete) == 0) {
                listSubject.splice(i, 1);
            }
        }
        --count;
        $(this).siblings('.add-subject').removeAttr("disabled");
        $(this).attr("disabled", "disabled");
        $('.count').html(count);
    });

    $('.list-subject-choosen').click(function () {
        if(count > 0) {
            $.ajax({
                url: "http://localhost:9000/api/v1/list-subject-by-tenHocPhan/show-list-subject-choosen",
                type: 'get',
                data: {listSubject: JSON.stringify(listSubject)},
                dataType: "json",
                success: function (response) {
                    location.href = "http://localhost:9000/api/v1/list-subject-by-tenHocPhan/show-list-subject-choosen";
                }, error: function (xhr) {
                    console.log("ahihi loi roi");
                }
            });
        }

    })




})
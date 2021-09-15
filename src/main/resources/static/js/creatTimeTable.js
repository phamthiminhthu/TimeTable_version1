$(document).ready(function (){
    $('.delete-subject-inList').click(function (){
        $(this).attr("data-toggle", "modal");
        $(this).attr("data-target","#exampleModal");
        var subject = $(this).parents().siblings('.nameHp').text().trim();

        $('.node-delete').click(function (){
            $.ajax({
                url: "http://localhost:9000/api/v1/list-subject-by-tenHocPhan/show-list-subject-choosen",
                type:'get',
                data: {nameSubject : subject},
                success: function (response){
                    location.href="http://localhost:9000/api/v1/list-subject-by-tenHocPhan/show-list-subject-choosen";
                },
                error : function (xhr){

                }

            })
        })
    });

    $('.create-timetable-all').click(function (){
        $('#accordion').removeClass('d-none');
        $.ajax({
            url: "http://localhost:9000/api/v1/list-subject-by-tenHocPhan/show-list-subject-choosen",
            type: 'get',
            success:function (response){
            },
            error: function (xhr){
                console.log("loi");
            }
        })
    })

})
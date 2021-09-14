$(document).ready(function () {
    $(".dropdown>.btn").click(function () {
            var index = $(this).data('index');
            var value = $(this).text().trim();
            var result = [];

            $.ajax({
                url: "http://localhost:9000/api/v1/list-subject-by-tenHocPhan",
                type: 'get',
                data: {tenHocPhan: value},
                success: function (response) {

                    for (var i = 0; i < response.length; i++) {
                        result.push(' <a class="dropdown-item" href="#">');
                        result.push(response[i].maLop);
                        result.push('</a>');
                    }
                    $('.dropdown-menu').append($(result.join('')));
                },
                error: function (xhr) {
                    console.log("ahihi loi roi");
                }
            });

            $('.dropdown-menu>a').remove();
        }
    );
    var listSubject = [];

    $('.button-add').click(function () {
        var index = $(this).data('index');
        var value = $(this).siblings('.dropdown').children('.btn').text().trim();
        var check = 0;
        for(var i = 0; i < listSubject.length; i++){
            if(listSubject[i] == value){
                check = 1;
                break;
            }
        }
        if(check == 0) listSubject.push(value);
        console.log(listSubject);

    })

    $('.lenLich').click(function (){
        $.ajax({
            url:"http://localhost:9000/api/v1/list-subject-by-tenHocPhan",
            type:'post',
            data: {listSubject: JSON.stringify(listSubject)},
            dataType: "json",
            success:function (response){
                console.log(response);


            },error: function (xhr) {
                console.log("ahihi loi roi");
            }
        })
    })


})
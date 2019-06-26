$(document).ready(function () {

    let hodnoty = [];
    $('#adminSetsForm input[name="jmeno"]').val('');

    $("#btn").click(function () {
        let param_count = $('#count').val();
        console.log(param_count);
        let servlet = "hutmann";
        let url = `${servlet}?resCount=${param_count}`;
        $.getJSON(url, function (data) {
            $.each(data, function (index, item) {
                $('body').append('<div>' + item['ans'] + '</div>');
            });
        });
    });

    //make post request
    $('.pretty').click(function () {
        $(this).addClass('selectedSet');
        $(this).removeClass('w3-green');
        let hodnota = $(this).text();
        if (hodnoty.indexOf(hodnota) < 0) {
            hodnoty.push(hodnota);
        } else {
            let indexRemove = hodnoty.indexOf(hodnota);
            hodnoty.splice(indexRemove, 1);
            $(this).removeClass('selectedSet');
        }
        console.log(hodnoty);
    });

    $('#displayAll').on("click", function () {
        $('#users tbody').html('');
        getData("all", "admin");
    });

    $('#adminSetsForm').submit(function (event) {
        event.preventDefault();
        let $vec_name = $(this), x = $vec_name.find("input[name='jmeno']").val();

        if (x.length !== 7) {
            alert("Length of user id has to be 7 chars");
            return true;
        }

        if (hodnoty.join(';').length < 1) {
            alert("At least one set has to be selected");
            return true;
        }

        getData("add", x);
    });

    function getData(whatToDo, x) {

        let servlet = "hutmann";
        let posting = $.post(servlet,
                {
                    fce: whatToDo,
                    user: x,
                    sety: hodnoty.join(';')});
        posting.done(function (data) {

            hodnoty = [];
            $('#ssNumbers > span').each(function () {
                $(this).removeClass('selectedSet');
                $(this).addClass('w3-green');
            });


            $.each(data, function (ix, it) {
                let row = '<tr><td>' + it['userId'] + '</td><td>';
                $.each(it['sety'], function (index, set) {
                    let cell = '<span class="w3-tag w3-red w3-margin-right">' + set + '</span>';
                    row += cell;
                });
                row += '</td><td><span class="deleter">Delete</span></td></tr>';
                $('#users tbody').append(row);
                $('#adminSetsForm input[name="jmeno"]').val('');
            });



        });

    }

    $('#users tbody').on("click", 'td span.deleter', function (event) {
        event.stopPropagation();
        $(this).parent().css('color', 'red');
        let el = $(this).parent().parent().get(0);
        let userDelete = $(el).find("td:eq(0)").text();
        console.log(userDelete);

        let servlet = "hutmann";
        let posting = $.post(servlet,
                {
                    fce: "remove",
                    user: userDelete
                });
        posting.done(function (data) {
            $('#userRemoved').html(data[0]);
        });

        $(el).remove();
    });


});
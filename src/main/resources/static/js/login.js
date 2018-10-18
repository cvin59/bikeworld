$(function () {
    $('#formRegister').submit((e) => {
        e.preventDefault();
        $.ajax({
            type: "POST",
            url: "http://localhost:8080/registration",
            dataType: 'json',
            data: $(this).serialize()
        }).done(() => {
            console.log('success');
        }).fail(() => {
            console.log('fail');
        });

    });

    $('#formLoginPortal').submit((e) => {
        e.preventDefault();
        let json = convertFormToJSON($('#formLoginPortal'));
        $.ajax({
            type: "POST",
            url: "http://localhost:8080/login",
            dataType: 'json',
            contentType: 'application/json',
            data: JSON.stringify(json),
            success: function (res) {
                console.log(res);
                window.location.replace('http://localhost:8080/portal');
            },
            error: function (res) {
                console.log(res);
            }
        });
    });


    const convertFormToJSON = (form) => {
        let array = jQuery(form).serializeArray();
        let json = {};
        jQuery.each(array, function () {
            json[this.name] = this.value || '';
        });
        return json;
    }
})
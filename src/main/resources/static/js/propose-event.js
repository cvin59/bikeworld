$(function (){
    //propose event
    $('#formProposeEvent').submit((e) => {
        e.preventDefault();
        let json = convertFormToJSON($('#formProposeEvent'));
        console.log(json);
        let formData = new FormData();
        formData.append('consumeEventString', JSON.stringify(json));
        let image = $("#image").get(0).files[0];
        formData.append('image', image);
        $.ajax({
            type: "POST",
            url: "http://localhost:8080/api/proposal-event",
            dataType: 'json',
            data: formData,
            contentType: false,
            processData: false

        }).done((res) => {
            console.log(res.data);
            console.log(JSON.stringify(res.data.accountUsename));
        }).fail(() => {
            console.log('propose-fail');
        });

    });

    const convertFormToJSON = (form) =>{
        let array = jQuery(form).serializeArray();
        let json = {};
        jQuery.each(array, function() {
            json[this.name] = this.value || '';
        });
        return json;
    }


    $('#datetimepickerStart').datetimepicker({
        format: 'DD/MM/YYYY HH:mm'
    });
    $('#datetimepickerEnd').datetimepicker({
        useCurrent: false,
        format: 'DD/MM/YYYY HH:mm'
    });
    $("#datetimepickerStart").on("dp.change", function (e) {
        $('#datetimepickerEnd').data("DateTimePicker").minDate(e.date);
    });
    $("#datetimepickerEnd").on("dp.change", function (e) {
        $('#datetimepickerStart').data("DateTimePicker").maxDate(e.date);
    });
})
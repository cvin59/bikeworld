$(function () {
    const loadData = (events) => {
        let table = $('#dataTables-example').DataTable({
            data: events,
            columns: [
                {data: "id"},
                {data: "name"},
                {
                    data: "status",
                    render: function (data, type, row) {
                        let ret;
                        switch (row.status) {
                            case 0 :
                                ret = 'Pending';
                                break;
                            case 1 :
                                ret = 'Not Approved';
                                break;
                            case 2 :
                                ret = 'Approved';
                                break;
                            case 3 :
                                ret = 'Processing';
                                break;
                            case 4 :
                                ret = 'Ongoing';
                                break;
                            case 5 :
                                ret = 'Canceled';
                                break;
                            case 6 :
                                ret = 'End';
                                break;
                        }
                        return ret;
                    }
                },
                {
                    data: null,
                    render: function (data, type, row) {
                        console.log(row.status);
                        switch (row.status) {
                            case 0 :
                                ret = '<button class="btn btn-warning">Approve</button>';
                                break;
                            case 1 :
                                ret = 'Not Approved';
                                break;
                            case 2 :
                                ret = 'Approved';
                                break;
                            case 3 :
                                ret = 'Processing';
                                break;
                            case 4 :
                                ret = 'Ongoing';
                                break;
                            case 5 :
                                ret = 'Canceled';
                                break;
                            case 6 :
                                ret = 'End';
                                break;
                        }
                        return ret;
                        // if (row.status == 1 ) {
                        //     return '<button class="btn btn-warning">Delete</button>';
                        // } else if (row.status == 2) {
                        //     return 'Approved';
                        // }
                    }
                }
            ],
            responsive: true
        });

    };

    $.ajax({
        type: "GET",
        url: "/api/event",
        dataType: 'json',
        success: function (data, status) {
            console.log(data);
            console.log(status);
            loadData(data);
        },
        error: function (data, status) {
            alert(status);
        }
    });

    //propose event
    $('#formProposeEvent').submit((e) => {
        e.preventDefault();
        let json = convertFormToJSON($('#formProposeEvent'));
        let formData = new FormData();
        formData.append('consumeEventString', JSON.stringify(json));
        let image = $("#image").get(0).files[0];
        formData.append('image', image);
        $.ajax({
            type: "POST",
            url: "http://localhost:8080/api/event",
            dataType: 'json',
            data: formData,
            contentType: false,
            processData: false

        }).done(() => {
            console.log('propose-success');
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

    //get event detail

    // const loadEventDetail = () => {
    //     let id = $("#eventId").val();
    //     console.log(id);
    //
    //     $.ajax({
    //         type: "GET",
    //         url: `http://localhost:8080/api/event/${id}`,
    //         dataType: 'json'
    //     }).done((data) => {
    //         console.log(data);
    //         $("#eventName").val(data.name);
    //         $('#imgAva').attr('src', `http://localhost:8080/event${data.imageUrl}`);
    //         console.log('load event detail success');
    //     }).fail(() => {
    //         console.log('fail to load event detail');
    //     })
    // }
    //
    // loadEventDetail();
});
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">

<head>

    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta title="viewport" content="width=device-width, initial-scale=1">
    <meta title="description" content="">
    <meta title="author" content="">

    <title>Manage Event</title>
</head>

<body>

<div id="wrapper">

    <!-- Navigation -->
    <div class="header">
        <jsp:include page="portal-header.jsp"/>
    </div>

    <div id="page-wrapper">
        <div class="row">
            <div class="col-lg-12">
                <h1 class="page-header">Proposal Event Management</h1>
            </div>
            <!-- /.col-lg-12 -->
        </div>
        <!-- /.row -->
        <div class="row">
            <div class="col-lg-12">
                <div class="panel panel-default">
                    <div class="panel-heading">
                        Proposal Events
                    </div>
                    <!-- /.panel-heading -->
                    <div class="panel-body">
                        <table width="100%" class="table table-striped table-bordered table-hover"
                               id="dataTables-example">
                            <thead>
                            <tr>
                                <th>ID</th>
                                <th>Name</th>
                                <th>Address</th>
                                <th>Location</th>
                                <th>StartDate</th>
                                <th>EndDate</th>
                                <th>Status</th>
                                <%--<th>Action</th>--%>
                            </tr>
                            </thead>
                        </table>
                        <!-- /.panel-body -->
                    </div>
                    <!-- /.panel -->
                </div>
                <!-- /.col-lg-12 -->
            </div>
        </div>
        <!-- /#page-wrapper -->

    </div>
    <script>
        var eventDataTable = reloadTable();

        console.log(eventDataTable);

        var table = $('#dataTables-example').DataTable({
            data: eventDataTable,
            columns: [
                {data: "id"},
                {data: "title"},
                {data: "address"},
                {data: "location"},
                {data: "startDate"},
                {data: "endDate"},
                {
                    data: null,
                    render: function (data, type, row) {
                        let ret;
                        switch (row.status) {
                            case 0 :
                                ret = '<button id="btnApprove'+row.id+'" onclick="approveEvent('+row.id+')" class="btn btn-warning"><i class="fa fa-times"></i></button>';
                                break;
                            case 1 :
                                ret = '<button id="btnNotApprove'+row.id+'" onclick="notApproveEvent('+row.id+')" class="btn btn-info"><i class="fa fa-check"></i></button>';
                                break;
                        }
                        return ret;
                    }
                }
                // , {
                //     data: null,
                //     render: function (data, type, row) {
                //         ret = '<a id="btnCreate'+row.id+'" href="proposal-event/create-event/'+row.id+'" class="btn btn-primary">Create Event</a>';
                //         return ret;
                //     }
                // },

            ],
            columnDefs: [
                {
                    render: function (data, type, full, meta) {
                        return "<div style='width: 200px;word-wrap: break-word'>" + data + "</div>";
                    },
                    targets: [2,3]
                }
            ],
            responsive: true
        });


        function reloadTable() {
            let data = "";
            $.ajax({
                type: "GET",
                url: "/api/proposal-event",
                dataType: 'json',
                async: false
            }).done((res) => {
                data = res.data;
            }).fail(() => {
                alert(status);
            });
            return data;
        }

        function approveEvent(id) {
            console.log(id);
            $.ajax({
                type: "PUT",
                url: `http://localhost:8080/api/proposal-event/approve-event/` + id,
                dataType: 'json',
                contentType: false,
                processData: false

            }).done((res) => {
                console.log(res.data);

                $('#btnApprove'+id).replaceWith( '<button id="btnNotApprove'+id+'" onclick="notApproveEvent('+id+')" class="btn btn-info"><i class="fa fa-check"></i></button>');
            }).fail(() => {
                console.log('propose-fail');
            });
        }

        function notApproveEvent(id) {
            console.log(id);
            $.ajax({
                type: "PUT",
                url: `http://localhost:8080/api/proposal-event/not-approve-event/` + id,
                dataType: 'json',
                contentType: false,
                processData: false

            }).done((res) => {
                console.log(res.data);

                $('#btnNotApprove'+id).replaceWith( '<button id="btnApprove'+id+'" onclick="approveEvent('+id+')" class="btn btn-warning"><i class="fa fa-times"></i></button>');
            }).fail(() => {
                console.log('propose-fail');
            });
        }
        
        function createEvent(id) {
            
        }
    </script>
</div>
</body>

</html>

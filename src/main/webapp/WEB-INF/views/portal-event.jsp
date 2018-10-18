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
                    <h1 class="page-header">Event Management</h1>
                </div>
                <!-- /.col-lg-12 -->
            </div>
            <!-- /.row -->
            <div class="row">
                <div class="col-lg-12">
                    <div class="panel panel-default">
                        <div class="panel-heading">
                            <a href="../event/create-event" class="btn btn-primary pull-right">Create Event</a>
                        </div>
                        <!-- /.panel-heading -->
                        <div class="panel-body">
                            <table width="100%" class="table table-striped table-bordered table-hover" id="dataTables-example">
                                <thead>
                                <tr>
                                    <th>ID</th>
                                    <th>Name</th>
                                    <th>Status</th>
                                    <th>Action</th>
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
    <!-- /#wrapper -->
    </div>

    <script>
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
                                    ret = 'Processing';
                                    break;
                                case 1 :
                                    ret = 'Ongoing';
                                    break;
                                case 2 :
                                    ret = 'Canceled';
                                    break;
                                case 3 :
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
        }).done((res) => {
            console.log(res);
            loadData(res.data);
        }).fail((res) => {
            alert(res.message);
        });
    </script>
</body>

</html>

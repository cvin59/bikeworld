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
                        Basic Form Elements
                    </div>
                    <div class="panel-body">
                        <div class="row">
                            <div class="col-lg-6">
                                <form role="form" id="formProposeEvent" enctype="multipart/form-data">
                                    <div class="form-group">
                                        <label>Title</label>
                                        <input class="form-control" name="title">
                                    </div>
                                    <div class="form-group">
                                        <label>Location</label>
                                        <input class="form-control" name="location">
                                    </div>
                                    <div class="form-group">
                                        <label>Address</label>
                                        <input class="form-control" name="address">
                                    </div>
                                    <div class="form-group">
                                        <label>Start Register Date</label>
                                        <div class='input-group date' id='datetimepickerRegiStart'>
                                            <input type='text' name="startDate" class="form-control"/>
                                            <span class="input-group-addon">
                                        <span class="fa fa-calendar"></span>
                                         </span>
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <label>End Register Date</label>
                                        <div class='input-group date' id='datetimepickerRegiEnd'>
                                            <input type='text' name="endDate" class="form-control"/>
                                            <span class="input-group-addon">
                                         <span class="fa fa-calendar"></span>
                                        </span>
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <label>Start Date</label>
                                        <div class='input-group date' id='datetimepickerStart'>
                                            <input type='text' name="startDate" class="form-control"/>
                                            <span class="input-group-addon">
                                        <span class="fa fa-calendar"></span>
                                         </span>
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <label>End Date</label>
                                        <div class='input-group date' id='datetimepickerEnd'>
                                            <input type='text' name="endDate" class="form-control"/>
                                            <span class="input-group-addon">
                                         <span class="fa fa-calendar"></span>
                                        </span>
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <label>Description</label>
                                        <textarea class="form-control" name="description"></textarea>
                                    </div>
                                    <div class="form-group">
                                        <label>Image</label>
                                        <input class="form-control" type="file" id="image">
                                    </div>
                                    <button type="submit" class="btn btn-default">Submit Button</button>
                                    <button type="reset" class="btn btn-default">Reset Button</button>
                                </form>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <!-- /#page-wrapper -->

    </div>
    <!-- /#wrapper -->
</div>
<script src="/js/propose-event.js">

</script>
</body>

</html>

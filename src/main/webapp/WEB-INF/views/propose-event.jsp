<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta title="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Document</title>
</head>
<body>
<div class="header">
    <jsp:include page="header.jsp"/>
    <jsp:include page="importjs.jsp"/>
</div>
<div id="page">
    <div class="row">
        <div class="col-lg-12">
            <h1 class="page-header">Forms</h1>
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
                                    <label>Name</label>
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
                                    <div class='input-group date' id='datetimepickerStart'>
                                        <input type='text' name="startDate" class="form-control"/>
                                        <span class="input-group-addon">
                                        <span class="fa fa-calendar"></span>
                                         </span>
                                    </div>
                                </div>
                                <div class="form-group">
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
</div>
<script src="/js/propose-event.js"></script>
</body>
</html>
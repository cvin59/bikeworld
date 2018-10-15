<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Document</title>
</head>
<body>
<div class="header">
    <jsp:include page="header.jsp"/>
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
                                    <input class="form-control" name="name">
                                </div>
                                <div class="form-group">
                                    <label>File input</label>
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
</body>
</html>
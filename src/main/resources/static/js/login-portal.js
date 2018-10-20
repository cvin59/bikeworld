$(function () {
    $(document).ready(function () {
        const backendServer = 'http://localhost:8080';

        var username = $('#signin-username');
        var password = $('#signin-password');

        $('#signin-button').click(function () {
            $.ajax({
                url: backendServer + '/login',
                method: 'POST',
                contentType: 'application/json',
                data: JSON.stringify({username: username.val(), password: password.val()}),
                success: function (JWT) {
                    console.log('123');
                    window.localStorage.setItem('JWT', JWT);
                    window.location.href = '/portal';
                },
                error: function (xhr, status, error) {
                    console.log(xhr);
                    console.log(status);
                    console.log(error);
                }
            });
        });
    });


})
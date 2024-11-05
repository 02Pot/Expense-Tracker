$(document).ready(function() {
    const sign_in_btn = $("#sign-in-btn");
    const sign_up_btn = $("#sign-up-btn");
    const container = $(".container");
    const sign_in_btn2 = $("#sign-in-btn2");
    const sign_up_btn2 = $("#sign-up-btn2");

    sign_up_btn.on("click", () => {
        container.addClass("sign-up-mode");
    });

    sign_in_btn.on("click", () => {
        container.removeClass("sign-up-mode");
    });

    sign_up_btn2.on("click", () => {
        container.addClass("sign-up-mode2");
    });

    sign_in_btn2.on("click", () => {
        container.removeClass("sign-up-mode2");
    });

    $('#loginForm').on('submit', function(e) {
        e.preventDefault();
        const formData = {
            username: $('input[name="username"]').val(),
            password: $('input[name="password"]').val()
        };

        $.ajax({
            url: '/users/login',
            method: 'POST',
            contentType: 'application/json',
            data: JSON.stringify(formData),
            success: function(response) {
                window.location.href = '/home';
            },
        });
    });

    $('#registerForm').on('submit', function(e) {
        e.preventDefault();
        const formData = {
            username: $('input[name="username"]').val(),
            password: $('input[name="password"]').val(),
            firstName: $('input[name="firstName"]').val(),
            lastName: $('input[name="lastName"]').val(),
            phoneNumber: $('input[name="phoneNumber"]').val()
        };
        $.ajax({
            url: '/users/register',
            method: 'POST',
            contentType: 'application/json',
            data: JSON.stringify(formData),
            success: function(response) {
                window.location.href = '/login';
            },
        });
    });
});

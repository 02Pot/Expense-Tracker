document.getElementById("login-form").addEventListener("submit", async function(e) {
    e.preventDefault();

    const email = document.getElementById("email").value;
    const password = document.getElementById("password").value;

    const userDTO = {
        userEmail: email,
        userPassword: password
    };

    try {
        const response = await fetch("http://localhost:8081/users/login", {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify(userDTO)
        });

        const data = await response.json();

        if (response.ok) {
            const token = data.token;
            localStorage.setItem("jwtToken", token);
            window.location.href = "../index.html";
        } else {
            document.getElementById("message").textContent = data.error || "Login failed";
        }

    } catch (err) {
        console.error(err);
        document.getElementById("message").textContent = "An error occurred. Try again.";
    }
});
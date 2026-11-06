
// Login Form Validation and Dashboard Navigation

const loginForm = document.getElementById("loginForm");

loginForm.addEventListener("submit", function(event) {

    event.preventDefault();

    const role = document.getElementById("role").value.trim().toLowerCase();
    const email = document.getElementById("email").value.trim();
    const password = document.getElementById("password").value;

    // Check role
    if (role === "") {
        alert("Please select your role.");
        return;
    }

    // Check email and password
    if (email === "" || password === "") {
        alert("Please enter email and password.");
        return;
    }

    // Navigate according to selected role
    if (role === "admin") {

        alert("Admin login successful!");
        window.location.href = "admin-dashboard.html";

    }
    else if (role === "teacher") {

        alert("Teacher login successful!");
        window.location.href = "teacher-dashboard.html";

    }
    else if (role === "student") {

        alert("Student login successful!");
        window.location.href = "student-dashboard.html";

    }
    else {

        alert("Invalid role selected.");

    }

});

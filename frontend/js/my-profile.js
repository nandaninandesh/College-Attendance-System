
const profileForm = document.getElementById("profileForm");
const message = document.getElementById("message");

// Default teacher details
const defaultProfile = {
    teacherId: "T001",
    name: "Demo Teacher",
    email: "teacher@gmail.com",
    department: "Computer Science and Engineering",
    phone: ""
};

// Load saved profile
let profile = JSON.parse(
    localStorage.getItem("teacherProfile") || "null"
);

if (!profile) {
    profile = defaultProfile;
}

// Display profile details
document.getElementById("teacherId").value = profile.teacherId;
document.getElementById("name").value = profile.name;
document.getElementById("email").value = profile.email;
document.getElementById("department").value = profile.department;
document.getElementById("phone").value = profile.phone;

// Update profile
profileForm.addEventListener("submit", function (event) {
    event.preventDefault();

    const updatedProfile = {
        teacherId: profile.teacherId,
        name: document.getElementById("name").value.trim(),
        email: document.getElementById("email").value.trim(),
        department: document.getElementById("department").value.trim(),
        phone: document.getElementById("phone").value.trim()
    };

    // Validate phone number if entered
    if (
        updatedProfile.phone !== "" &&
        !/^[0-9]{10}$/.test(updatedProfile.phone)
    ) {
        message.style.color = "red";
        message.textContent = "Please enter a valid 10-digit phone number.";
        return;
    }

    // Save updated profile
    localStorage.setItem(
        "teacherProfile",
        JSON.stringify(updatedProfile)
    );

    message.style.color = "green";
    message.textContent = "Profile updated successfully!";

    profile = updatedProfile;
});


const studentProfileForm = document.getElementById("studentProfileForm");
const message = document.getElementById("message");

// Default student details
const defaultProfile = {
    studentId: "101",
    name: "Rahul Kumar",
    email: "rahul@gmail.com",
    department: "Computer Science and Engineering",
    semester: "2",
    phone: ""
};

// Load saved profile
let profile = JSON.parse(
    localStorage.getItem("studentProfile") || "null"
);

if (!profile) {
    profile = defaultProfile;
}

// Display profile details
document.getElementById("studentId").value = profile.studentId;
document.getElementById("name").value = profile.name;
document.getElementById("email").value = profile.email;
document.getElementById("department").value = profile.department;
document.getElementById("semester").value = profile.semester;
document.getElementById("phone").value = profile.phone;

// Update profile
studentProfileForm.addEventListener("submit", function(event) {
    event.preventDefault();

    const updatedProfile = {
        studentId: profile.studentId,
        name: document.getElementById("name").value.trim(),
        email: document.getElementById("email").value.trim(),
        department: document.getElementById("department").value.trim(),
        semester: profile.semester,
        phone: document.getElementById("phone").value.trim()
    };

    // Validate phone number
    if (
        updatedProfile.phone !== "" &&
        !/^[0-9]{10}$/.test(updatedProfile.phone)
    ) {
        message.style.color = "red";
        message.textContent =
            "Please enter a valid 10-digit phone number.";
        return;
    }

    // Save updated profile
    localStorage.setItem(
        "studentProfile",
        JSON.stringify(updatedProfile)
    );

    profile = updatedProfile;

    message.style.color = "green";
    message.textContent = "Profile updated successfully!";
});

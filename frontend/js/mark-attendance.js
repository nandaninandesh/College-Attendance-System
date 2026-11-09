
// Mark Attendance Management

const attendanceForm = document.getElementById("attendanceForm");
const markAttendanceForm = document.getElementById("markAttendanceForm");
const attendanceTableBody = document.getElementById("attendanceTableBody");

let loadedStudents = [];

// Sample student records
const students = [
    {
        id: "101",
        name: "Rahul Kumar",
        department: "CSE",
        semester: "2"
    },
    {
        id: "102",
        name: "Priya Kumari",
        department: "CSE",
        semester: "2"
    },
    {
        id: "103",
        name: "Aman Kumar",
        department: "CSE",
        semester: "2"
    },
    {
        id: "104",
        name: "Neha Singh",
        department: "CSE",
        semester: "3"
    },
    {
        id: "105",
        name: "Rohit Kumar",
        department: "CSE",
        semester: "2"
    }
];

// Load students
attendanceForm.addEventListener("submit", function(event) {

    event.preventDefault();

    const semester = document.getElementById("semester").value;
    const subject = document.getElementById("subject").value;
    const date = document.getElementById("attendanceDate").value;

    if (!semester || !subject || !date) {
        alert("Please select all attendance details.");
        return;
    }

    loadedStudents = students.filter(
        student => student.semester === semester
    );

    attendanceTableBody.innerHTML = "";

    loadedStudents.forEach(student => {

        const row = document.createElement("tr");

        row.innerHTML = `
            <td>${student.id}</td>
            <td>${student.name}</td>
            <td>
                <select class="status">
                    <option value="Present">Present</option>
                    <option value="Absent">Absent</option>
                </select>
            </td>
        `;

        attendanceTableBody.appendChild(row);

    });

    alert("Students loaded successfully!");

});

// Submit attendance
markAttendanceForm.addEventListener("submit", function(event) {

    event.preventDefault();

    if (loadedStudents.length === 0) {
        alert("Please load students first.");
        return;
    }

    const subject = document.getElementById("subject").value;
    const semester = document.getElementById("semester").value;
    const date = document.getElementById("attendanceDate").value;

    const rows = attendanceTableBody.querySelectorAll("tr");

    // Get previously saved records
    let allRecords = JSON.parse(
        localStorage.getItem("attendanceRecords") || "[]"
    );

    const newRecords = [];

    rows.forEach((row, index) => {

        const status = row.querySelector(".status").value;

        newRecords.push({
            studentId: loadedStudents[index].id,
            studentName: loadedStudents[index].name,
            subject: subject,
            semester: semester,
            date: date,
            status: status
        });

    });

    // Remove old records for the same subject, semester and date
    allRecords = allRecords.filter(record =>
        !(record.subject === subject &&
          record.semester === semester &&
          record.date === date)
    );

    // Add new attendance records
    allRecords.push(...newRecords);

    // Save records in Local Storage
    localStorage.setItem(
        "attendanceRecords",
        JSON.stringify(allRecords)
    );

    const presentCount = newRecords.filter(
        record => record.status === "Present"
    ).length;

    const absentCount = newRecords.filter(
        record => record.status === "Absent"
    ).length;

    alert(
        "Attendance submitted successfully!\n" +
        "Present: " + presentCount + "\n" +
        "Absent: " + absentCount
    );

    console.log("Saved Attendance:", allRecords);

});

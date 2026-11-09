
// View Attendance Management

const attendanceTableBody = document.getElementById("attendanceTableBody");
const filterForm = document.getElementById("filterForm");

const attendanceDate = document.getElementById("attendanceDate");
const attendanceSemester = document.getElementById("attendanceSemester");
const attendanceSubject = document.getElementById("attendanceSubject");

// Sample attendance records for testing
const attendanceRecords = [
    {
        id: "101",
        name: "Rahul Kumar",
        semester: "2",
        subject: "Mathematics",
        date: "2026-11-06",
        status: "Present"
    },
    {
        id: "102",
        name: "Priya Kumari",
        semester: "2",
        subject: "Mathematics",
        date: "2026-11-06",
        status: "Absent"
    },
    {
        id: "103",
        name: "Aman Kumar",
        semester: "2",
        subject: "Physics",
        date: "2026-11-06",
        status: "Present"
    },
    {
        id: "104",
        name: "Neha Singh",
        semester: "2",
        subject: "Physics",
        date: "2026-11-06",
        status: "Present"
    },
    {
        id: "105",
        name: "Rohit Kumar",
        semester: "1",
        subject: "Mathematics",
        date: "2026-11-06",
        status: "Absent"
    }
];

// Add subjects to dropdown
const subjects = [
    ...new Set(attendanceRecords.map(record => record.subject))
];

subjects.forEach(subject => {
    const option = document.createElement("option");
    option.value = subject;
    option.textContent = subject;
    attendanceSubject.appendChild(option);
});

// Display attendance records
function displayAttendance(records) {

    attendanceTableBody.innerHTML = "";

    records.forEach(record => {

        const row = document.createElement("tr");

        const values = [
            record.id,
            record.name,
            record.subject,
            record.date,
            record.status
        ];

        values.forEach(value => {
            const cell = document.createElement("td");
            cell.textContent = value;
            row.appendChild(cell);
        });

        attendanceTableBody.appendChild(row);

    });

    updateSummary(records);

}

// Update attendance summary
function updateSummary(records) {

    const total = records.length;

    const present = records.filter(
        record => record.status === "Present"
    ).length;

    const absent = records.filter(
        record => record.status === "Absent"
    ).length;

    document.getElementById("totalStudents").textContent = total;
    document.getElementById("presentCount").textContent = present;
    document.getElementById("absentCount").textContent = absent;

}

// Apply filters
filterForm.addEventListener("submit", function(event) {

    event.preventDefault();

    const selectedDate = attendanceDate.value;
    const selectedSemester = attendanceSemester.value;
    const selectedSubject = attendanceSubject.value;

    const filteredRecords = attendanceRecords.filter(record => {

        const dateMatch =
            selectedDate === "" || record.date === selectedDate;

        const semesterMatch =
            selectedSemester === "" ||
            record.semester === selectedSemester;

        const subjectMatch =
            selectedSubject === "" ||
            record.subject === selectedSubject;

        return dateMatch && semesterMatch && subjectMatch;

    });

    displayAttendance(filteredRecords);

});

// Display all records initially
displayAttendance(attendanceRecords);

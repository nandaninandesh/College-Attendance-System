
// Update Attendance Management

const searchForm = document.getElementById("searchAttendanceForm");
const updateForm = document.getElementById("updateAttendanceForm");
const attendanceTableBody = document.getElementById("attendanceTableBody");

let loadedRecords = [];

// Get records from Local Storage
function getAttendanceRecords() {

    return JSON.parse(
        localStorage.getItem("attendanceRecords") || "[]"
    );

}

// Load attendance records
searchForm.addEventListener("submit", function(event) {

    event.preventDefault();

    const subject = document.getElementById("subject").value;
    const date = document.getElementById("attendanceDate").value;
    const semester = document.getElementById("semester").value;

    const allRecords = getAttendanceRecords();

    loadedRecords = allRecords.filter(record =>
        record.subject === subject &&
        record.date === date &&
        record.semester === semester
    );

    attendanceTableBody.innerHTML = "";

    if (loadedRecords.length === 0) {

        attendanceTableBody.innerHTML =
            '<tr><td colspan="3">No attendance records found.</td></tr>';

        alert("No attendance records found!");
        return;

    }

    loadedRecords.forEach(record => {

        const row = document.createElement("tr");

        row.innerHTML = `
            <td>${record.studentId}</td>
            <td>${record.studentName}</td>
            <td>
                <select class="status">
                    <option value="Present">Present</option>
                    <option value="Absent">Absent</option>
                </select>
            </td>
        `;

        row.querySelector(".status").value = record.status;

        attendanceTableBody.appendChild(row);

    });

    alert("Attendance records loaded successfully!");

});

// Update attendance
updateForm.addEventListener("submit", function(event) {

    event.preventDefault();

    if (loadedRecords.length === 0) {
        alert("Please load attendance records first.");
        return;
    }

    const allRecords = getAttendanceRecords();
    const rows = attendanceTableBody.querySelectorAll("tr");

    loadedRecords.forEach((record, index) => {

        const newStatus = rows[index].querySelector(".status").value;

        const originalIndex = allRecords.findIndex(item =>
            item.studentId === record.studentId &&
            item.subject === record.subject &&
            item.semester === record.semester &&
            item.date === record.date
        );

        if (originalIndex !== -1) {
            allRecords[originalIndex].status = newStatus;
        }

    });

    localStorage.setItem(
        "attendanceRecords",
        JSON.stringify(allRecords)
    );

    alert("Attendance updated successfully!");

    // Reload updated records in table
    loadedRecords = getAttendanceRecords().filter(record =>
        record.subject === document.getElementById("subject").value &&
        record.date === document.getElementById("attendanceDate").value &&
        record.semester === document.getElementById("semester").value
    );

    loadedRecords.forEach((record, index) => {
        rows[index].querySelector(".status").value = record.status;
    });

});

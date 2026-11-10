
const subjectAttendanceBody = document.getElementById("subjectAttendanceBody");

// Get attendance records from localStorage
const attendanceRecords = JSON.parse(
    localStorage.getItem("attendanceRecords") || "[]"
);

// Demo student ID
const studentId = "101";

// Filter records for the logged-in student
const studentRecords = attendanceRecords.filter(record =>
    String(record.studentId) === studentId
);

// Group attendance by subject
const subjectSummary = {};

studentRecords.forEach(record => {

    const subject = record.subject;

    if (!subjectSummary[subject]) {
        subjectSummary[subject] = {
            total: 0,
            present: 0,
            absent: 0
        };
    }

    subjectSummary[subject].total++;

    if (record.status === "Present") {
        subjectSummary[subject].present++;
    } else {
        subjectSummary[subject].absent++;
    }
});

// Display subject-wise attendance
subjectAttendanceBody.innerHTML = "";

if (Object.keys(subjectSummary).length === 0) {

    subjectAttendanceBody.innerHTML = `
        <tr>
            <td colspan="5">No attendance records found.</td>
        </tr>
    `;

} else {

    for (const subject in subjectSummary) {

        const data = subjectSummary[subject];

        const percentage = (
            (data.present / data.total) * 100
        ).toFixed(2);

        const row = document.createElement("tr");

        row.innerHTML = `
            <td>${subject}</td>
            <td>${data.total}</td>
            <td>${data.present}</td>
            <td>${data.absent}</td>
            <td>${percentage}%</td>
        `;

        subjectAttendanceBody.appendChild(row);
    }
}

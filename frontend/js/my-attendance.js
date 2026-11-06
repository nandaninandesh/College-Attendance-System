
const attendanceTableBody = document.getElementById("attendanceTableBody");

// Get attendance records from localStorage
const attendanceRecords = JSON.parse(
    localStorage.getItem("attendanceRecords") || "[]"
);

// Demo student ID
const studentId = "101";

// Filter records for this student
const studentRecords = attendanceRecords.filter(record =>
    String(record.studentId) === studentId
);

// Calculate overall attendance
const totalClasses = studentRecords.length;

const presentClasses = studentRecords.filter(
    record => record.status === "Present"
).length;

const absentClasses = studentRecords.filter(
    record => record.status === "Absent"
).length;

const percentage = totalClasses > 0
    ? ((presentClasses / totalClasses) * 100).toFixed(2)
    : 0;

// Display overall summary
document.getElementById("totalClasses").textContent = totalClasses;
document.getElementById("presentClasses").textContent = presentClasses;
document.getElementById("absentClasses").textContent = absentClasses;
document.getElementById("attendancePercentage").textContent = percentage + "%";

// Subject-wise attendance
attendanceTableBody.innerHTML = "";

if (studentRecords.length === 0) {

    attendanceTableBody.innerHTML = `
        <tr>
            <td colspan="5">No attendance records found.</td>
        </tr>
    `;

} else {

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

    for (const subject in subjectSummary) {

        const data = subjectSummary[subject];

        const subjectPercentage =
            ((data.present / data.total) * 100).toFixed(2);

        const row = document.createElement("tr");

        row.innerHTML = `
            <td>${subject}</td>
            <td>${data.total}</td>
            <td>${data.present}</td>
            <td>${data.absent}</td>
            <td>${subjectPercentage}%</td>
        `;

        attendanceTableBody.appendChild(row);
    }
}

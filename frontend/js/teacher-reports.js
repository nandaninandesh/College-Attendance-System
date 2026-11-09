
const reportForm = document.getElementById("reportForm");
const reportTableBody = document.getElementById("reportTableBody");

reportForm.addEventListener("submit", function (event) {
    event.preventDefault();

    // Get filter values
    const subject = document.getElementById("subject").value;
    const semester = document.getElementById("semester").value;
    const fromDate = document.getElementById("fromDate").value;
    const toDate = document.getElementById("toDate").value;

    // Validate date range
    if (fromDate > toDate) {
        alert("From Date cannot be greater than To Date!");
        return;
    }

    // Get attendance records from localStorage
    const allRecords = JSON.parse(
        localStorage.getItem("attendanceRecords") || "[]"
    );

    // Filter records
    const filteredRecords = allRecords.filter(record => {
        return (
            record.subject === subject &&
            String(record.semester) === semester &&
            record.date >= fromDate &&
            record.date <= toDate
        );
    });

    // Calculate summary
    const total = filteredRecords.length;

    const present = filteredRecords.filter(
        record => record.status === "Present"
    ).length;

    const absent = filteredRecords.filter(
        record => record.status === "Absent"
    ).length;

    // Display summary
    document.getElementById("totalRecords").textContent = total;
    document.getElementById("presentCount").textContent = present;
    document.getElementById("absentCount").textContent = absent;

    // Display attendance records
    reportTableBody.innerHTML = "";

    if (filteredRecords.length === 0) {
        reportTableBody.innerHTML = `
            <tr>
                <td colspan="4">No attendance records found.</td>
            </tr>
        `;
        return;
    }

    filteredRecords.forEach(record => {
        const row = document.createElement("tr");

        row.innerHTML = `
            <td>${record.studentId}</td>
            <td>${record.studentName}</td>
            <td>${record.date}</td>
            <td>${record.status}</td>
        `;

        reportTableBody.appendChild(row);
    });
});

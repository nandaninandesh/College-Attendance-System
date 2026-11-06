
// Attendance Reports Management

const reportForm = document.getElementById("reportForm");
const reportTableBody = document.getElementById("reportTableBody");

// Sample attendance records for testing
const attendanceData = [
    {
        id: "101",
        name: "Rahul Kumar",
        subject: "Mathematics",
        date: "2026-11-06",
        status: "Present"
    },
    {
        id: "102",
        name: "Priya Kumari",
        subject: "Mathematics",
        date: "2026-11-06",
        status: "Absent"
    },
    {
        id: "103",
        name: "Aman Kumar",
        subject: "Physics",
        date: "2026-11-06",
        status: "Present"
    },
    {
        id: "104",
        name: "Neha Singh",
        subject: "Physics",
        date: "2026-11-05",
        status: "Present"
    },
    {
        id: "105",
        name: "Rohit Kumar",
        subject: "Web Technology",
        date: "2026-10-15",
        status: "Absent"
    },
    {
        id: "106",
        name: "Anjali Kumari",
        subject: "Computer Organization",
        date: "2026-10-20",
        status: "Present"
    }
];

// Generate report
reportForm.addEventListener("submit", function(event) {

    event.preventDefault();

    const reportType = document.getElementById("reportType").value;
    const selectedDate = document.getElementById("reportDate").value;
    const selectedMonth = document.getElementById("reportMonth").value;
    const selectedSubject = document.getElementById("reportSubject").value;

    if (reportType === "daily" && selectedDate === "") {
        alert("Please select a date.");
        return;
    }

    if (reportType === "monthly" && selectedMonth === "") {
        alert("Please select a month.");
        return;
    }

    if (reportType === "subject" && selectedSubject === "") {
        alert("Please select a subject.");
        return;
    }

    let filteredRecords = attendanceData;

    // Daily report
    if (reportType === "daily") {

        filteredRecords = filteredRecords.filter(
            record => record.date === selectedDate
        );

    }

    // Monthly report
    else if (reportType === "monthly") {

        filteredRecords = filteredRecords.filter(
            record => record.date.startsWith(selectedMonth)
        );

    }

    // Subject-wise report
    else if (reportType === "subject") {

        filteredRecords = filteredRecords.filter(
            record => record.subject === selectedSubject
        );

    }

    displayReport(filteredRecords);

});

// Display generated report
function displayReport(records) {

    reportTableBody.innerHTML = "";

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

        reportTableBody.appendChild(row);

    });

    updateSummary(records);

}

// Calculate report summary
function updateSummary(records) {

    const total = records.length;

    const present = records.filter(
        record => record.status === "Present"
    ).length;

    const absent = records.filter(
        record => record.status === "Absent"
    ).length;

    const percentage = total === 0
        ? 0
        : ((present / total) * 100).toFixed(2);

    document.getElementById("totalRecords").textContent = total;

    document.getElementById("totalPresent").textContent = present;

    document.getElementById("totalAbsent").textContent = absent;

    document.getElementById("attendancePercentage").textContent =
        percentage + "%";

}

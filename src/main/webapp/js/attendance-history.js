
const historyTableBody = document.getElementById("historyTableBody");
const filterBtn = document.getElementById("filterBtn");

// Demo student ID
const studentId = "101";

// Get attendance records from localStorage
const allRecords = JSON.parse(
    localStorage.getItem("attendanceRecords") || "[]"
);

// Filter and display records
function displayHistory() {

    const subject = document.getElementById("subject").value;
    const date = document.getElementById("attendanceDate").value;

    // Get records of the selected student
    let filteredRecords = allRecords.filter(record =>
        String(record.studentId) === studentId
    );

    // Filter by subject
    if (subject !== "") {
        filteredRecords = filteredRecords.filter(record =>
            record.subject === subject
        );
    }

    // Filter by date
    if (date !== "") {
        filteredRecords = filteredRecords.filter(record =>
            record.date === date
        );
    }

    // Clear previous records
    historyTableBody.innerHTML = "";

    // Check if records exist
    if (filteredRecords.length === 0) {

        historyTableBody.innerHTML = `
            <tr>
                <td colspan="5">No attendance records found.</td>
            </tr>
        `;

        return;
    }

    // Display attendance history
    filteredRecords.forEach(record => {

        const row = document.createElement("tr");

        row.innerHTML = `
            <td>${record.studentId}</td>
            <td>${record.studentName}</td>
            <td>${record.subject}</td>
            <td>${record.date}</td>
            <td>${record.status}</td>
        `;

        historyTableBody.appendChild(row);

    });
}

// Filter button event
filterBtn.addEventListener("click", displayHistory);

// Display all records when page loads
displayHistory();


// Student List Management

const studentTableBody = document.getElementById("studentTableBody");
const studentFilterForm = document.getElementById("studentFilterForm");

// Sample student records
const students = [
    {
        id: "101",
        name: "Rahul Kumar",
        email: "rahul@gmail.com",
        department: "CSE",
        semester: "2"
    },
    {
        id: "102",
        name: "Priya Kumari",
        email: "priya@gmail.com",
        department: "CSE",
        semester: "2"
    },
    {
        id: "103",
        name: "Aman Kumar",
        email: "aman@gmail.com",
        department: "ECE",
        semester: "3"
    },
    {
        id: "104",
        name: "Neha Singh",
        email: "neha@gmail.com",
        department: "CSE",
        semester: "3"
    },
    {
        id: "105",
        name: "Rohit Kumar",
        email: "rohit@gmail.com",
        department: "ME",
        semester: "2"
    },
    {
        id: "106",
        name: "Anjali Kumari",
        email: "anjali@gmail.com",
        department: "CSE",
        semester: "4"
    }
];

// Display students in table
function displayStudents(studentList) {

    studentTableBody.innerHTML = "";

    studentList.forEach(student => {

        const row = document.createElement("tr");

        const values = [
            student.id,
            student.name,
            student.email,
            student.department,
            student.semester
        ];

        values.forEach(value => {

            const cell = document.createElement("td");
            cell.textContent = value;
            row.appendChild(cell);

        });

        studentTableBody.appendChild(row);

    });

    document.getElementById("studentCount").textContent =
        "Total Students: " + studentList.length;

}

// Filter students
studentFilterForm.addEventListener("submit", function(event) {

    event.preventDefault();

    const selectedDepartment =
        document.getElementById("department").value;

    const selectedSemester =
        document.getElementById("semester").value;

    const filteredStudents = students.filter(student => {

        const departmentMatch =
            selectedDepartment === "" ||
            student.department === selectedDepartment;

        const semesterMatch =
            selectedSemester === "" ||
            student.semester === selectedSemester;

        return departmentMatch && semesterMatch;

    });

    displayStudents(filteredStudents);

});

// Display all students initially
displayStudents(students);

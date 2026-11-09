
// Student Management

const studentForm = document.getElementById("studentForm");
const studentTableBody = document.getElementById("studentTableBody");

let students = [];
let editIndex = -1;

// Display students in table
function displayStudents() {

    studentTableBody.innerHTML = "";

    students.forEach((student, index) => {

        const row = document.createElement("tr");

        [
            student.id,
            student.name,
            student.email,
            student.branch,
            student.semester
        ].forEach(value => {
            const cell = document.createElement("td");
            cell.textContent = value;
            row.appendChild(cell);
        });

        const actionCell = document.createElement("td");

        const editButton = document.createElement("button");
        editButton.textContent = "Edit";
        editButton.type = "button";
        editButton.onclick = () => editStudent(index);

        const deleteButton = document.createElement("button");
        deleteButton.textContent = "Delete";
        deleteButton.type = "button";
        deleteButton.style.marginLeft = "5px";
        deleteButton.onclick = () => deleteStudent(index);

        actionCell.append(editButton, deleteButton);
        row.appendChild(actionCell);

        studentTableBody.appendChild(row);
    });
}

// Add or update student
studentForm.addEventListener("submit", function(event) {

    event.preventDefault();

    const student = {
        id: document.getElementById("studentId").value.trim(),
        name: document.getElementById("studentName").value.trim(),
        email: document.getElementById("studentEmail").value.trim(),
        branch: document.getElementById("studentBranch").value.trim(),
        semester: document.getElementById("studentSemester").value
    };

    if (editIndex === -1) {

        const exists = students.some(
            s => s.id.toLowerCase() === student.id.toLowerCase()
        );

        if (exists) {
            alert("Student ID already exists!");
            return;
        }

        students.push(student);
        alert("Student added successfully!");

    } else {

        students[editIndex] = student;
        alert("Student updated successfully!");
        editIndex = -1;

    }

    studentForm.reset();
    displayStudents();

});

// Edit student
function editStudent(index) {

    const student = students[index];

    document.getElementById("studentId").value = student.id;
    document.getElementById("studentName").value = student.name;
    document.getElementById("studentEmail").value = student.email;
    document.getElementById("studentBranch").value = student.branch;
    document.getElementById("studentSemester").value = student.semester;

    editIndex = index;
}

// Delete student
function deleteStudent(index) {

    if (confirm("Are you sure you want to delete this student?")) {

        students.splice(index, 1);
        displayStudents();

        alert("Student deleted successfully!");

    }

}


// Teacher Management

const teacherForm = document.getElementById("teacherForm");
const teacherTableBody = document.getElementById("teacherTableBody");

let teachers = [];
let editIndex = -1;

// Display teachers in table
function displayTeachers() {

    teacherTableBody.innerHTML = "";

    teachers.forEach((teacher, index) => {

        const row = document.createElement("tr");

        [
            teacher.id,
            teacher.name,
            teacher.email,
            teacher.department,
            teacher.phone
        ].forEach(value => {

            const cell = document.createElement("td");
            cell.textContent = value;
            row.appendChild(cell);

        });

        const actionCell = document.createElement("td");

        // Edit button
        const editButton = document.createElement("button");
        editButton.textContent = "Edit";
        editButton.type = "button";
        editButton.onclick = () => editTeacher(index);

        // Delete button
        const deleteButton = document.createElement("button");
        deleteButton.textContent = "Delete";
        deleteButton.type = "button";
        deleteButton.style.marginLeft = "5px";
        deleteButton.onclick = () => deleteTeacher(index);

        actionCell.append(editButton, deleteButton);
        row.appendChild(actionCell);

        teacherTableBody.appendChild(row);

    });
}

// Add or Update teacher
teacherForm.addEventListener("submit", function(event) {

    event.preventDefault();

    const teacher = {

        id: document.getElementById("teacherId").value.trim(),
        name: document.getElementById("teacherName").value.trim(),
        email: document.getElementById("teacherEmail").value.trim(),
        department: document.getElementById("teacherDepartment").value.trim(),
        phone: document.getElementById("teacherPhone").value.trim()

    };

    if (editIndex === -1) {

        const exists = teachers.some(
            t => t.id.toLowerCase() === teacher.id.toLowerCase()
        );

        if (exists) {
            alert("Teacher ID already exists!");
            return;
        }

        teachers.push(teacher);
        alert("Teacher added successfully!");

    } else {

        teachers[editIndex] = teacher;
        alert("Teacher updated successfully!");

        editIndex = -1;

    }

    teacherForm.reset();
    displayTeachers();

});

// Edit teacher
function editTeacher(index) {

    const teacher = teachers[index];

    document.getElementById("teacherId").value = teacher.id;
    document.getElementById("teacherName").value = teacher.name;
    document.getElementById("teacherEmail").value = teacher.email;
    document.getElementById("teacherDepartment").value = teacher.department;
    document.getElementById("teacherPhone").value = teacher.phone;

    editIndex = index;

}

// Delete teacher
function deleteTeacher(index) {

    if (confirm("Are you sure you want to delete this teacher?")) {

        teachers.splice(index, 1);

        displayTeachers();

        alert("Teacher deleted successfully!");

    }

}

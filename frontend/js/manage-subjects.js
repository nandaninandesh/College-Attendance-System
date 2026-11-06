
// Subject Management

const subjectForm = document.getElementById("subjectForm");
const subjectTableBody = document.getElementById("subjectTableBody");

let subjects = [];
let editIndex = -1;

// Display subjects in table
function displaySubjects() {

    subjectTableBody.innerHTML = "";

    subjects.forEach((subject, index) => {

        const row = document.createElement("tr");

        [
            subject.id,
            subject.name,
            subject.code,
            subject.department,
            subject.semester,
            subject.credits
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
        editButton.onclick = () => editSubject(index);

        // Delete button
        const deleteButton = document.createElement("button");
        deleteButton.textContent = "Delete";
        deleteButton.type = "button";
        deleteButton.style.marginLeft = "5px";
        deleteButton.onclick = () => deleteSubject(index);

        actionCell.append(editButton, deleteButton);
        row.appendChild(actionCell);

        subjectTableBody.appendChild(row);

    });

}

// Add or Update subject
subjectForm.addEventListener("submit", function(event) {

    event.preventDefault();

    const subject = {

        id: document.getElementById("subjectId").value.trim(),
        name: document.getElementById("subjectName").value.trim(),
        code: document.getElementById("subjectCode").value.trim(),
        department: document.getElementById("department").value.trim(),
        semester: document.getElementById("semester").value,
        credits: document.getElementById("credits").value

    };

    if (editIndex === -1) {

        const exists = subjects.some(
            s => s.id.toLowerCase() === subject.id.toLowerCase()
        );

        if (exists) {
            alert("Subject ID already exists!");
            return;
        }

        subjects.push(subject);

        alert("Subject added successfully!");

    } else {

        subjects[editIndex] = subject;

        alert("Subject updated successfully!");

        editIndex = -1;

    }

    subjectForm.reset();

    displaySubjects();

});

// Edit subject
function editSubject(index) {

    const subject = subjects[index];

    document.getElementById("subjectId").value = subject.id;
    document.getElementById("subjectName").value = subject.name;
    document.getElementById("subjectCode").value = subject.code;
    document.getElementById("department").value = subject.department;
    document.getElementById("semester").value = subject.semester;
    document.getElementById("credits").value = subject.credits;

    editIndex = index;

}

// Delete subject
function deleteSubject(index) {

    if (confirm("Are you sure you want to delete this subject?")) {

        subjects.splice(index, 1);

        displaySubjects();

        alert("Subject deleted successfully!");

    }

}

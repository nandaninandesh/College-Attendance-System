#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <mysql.h>

MYSQL *conn;

// DATABASE CONNECTION
void connectDB()
{
    conn = mysql_init(NULL);

    if (conn == NULL)
    {
        printf("MySQL initialization failed!\n");
        exit(1);
    }

    if (!mysql_real_connect(conn, "localhost", "root",
                            "123456", "college_attendance",
                            3306, NULL, 0))
    {
        printf("Database connection failed: %s\n",
               mysql_error(conn));
        exit(1);
    }

    printf("\nDatabase Connected Successfully!\n");
}

// ADD STUDENT
void addStudent()
{
    int roll, semester;
    char name[50], dept[50];
    char query[500];

    printf("\nEnter Roll Number: ");
    scanf("%d", &roll);

    printf("Enter Student Name: ");
    scanf(" %49[^\n]", name);

    printf("Enter Department: ");
    scanf(" %49[^\n]", dept);

    printf("Enter Semester: ");
    scanf("%d", &semester);

    snprintf(query, sizeof(query),
        "INSERT INTO students "
        "(roll_no,name,department,semester) "
        "VALUES (%d,'%s','%s',%d)",
        roll, name, dept, semester);

    if (mysql_query(conn, query))
        printf("\nError: %s\n", mysql_error(conn));
    else
        printf("\nStudent Added Successfully!\n");
}

// MARK ATTENDANCE
void markAttendance()
{
    int roll;
    char date[11], status[10];
    char query[300];

    printf("\nEnter Roll Number: ");
    scanf("%d", &roll);

    printf("Enter Date (YYYY-MM-DD): ");
    scanf("%10s", date);

    printf("Enter Status (Present/Absent): ");
    scanf("%9s", status);

    if (strcmp(status, "Present") != 0 &&
        strcmp(status, "Absent") != 0)
    {
        printf("Invalid attendance status!\n");
        return;
    }

    snprintf(query, sizeof(query),
        "INSERT INTO attendance "
        "(roll_no,attendance_date,status) "
        "VALUES (%d,'%s','%s')",
        roll, date, status);

    if (mysql_query(conn, query))
        printf("\nError: %s\n", mysql_error(conn));
    else
        printf("\nAttendance Marked Successfully!\n");
}

// VIEW ATTENDANCE
void viewAttendance()
{
    int roll;
    char query[200];

    printf("\nEnter Roll Number: ");
    scanf("%d", &roll);

    snprintf(query, sizeof(query),
        "SELECT attendance_date,status "
        "FROM attendance WHERE roll_no=%d", roll);

    if (mysql_query(conn, query))
    {
        printf("Error: %s\n", mysql_error(conn));
        return;
    }

    MYSQL_RES *result = mysql_store_result(conn);
    MYSQL_ROW row;

    printf("\n------ ATTENDANCE RECORD ------\n");
    printf("Date\t\tStatus\n");

    while ((row = mysql_fetch_row(result)))
    {
        printf("%s\t%s\n", row[0], row[1]);
    }

    mysql_free_result(result);
}

// ATTENDANCE PERCENTAGE
void attendancePercentage()
{
    int roll;
    char query[300];

    printf("\nEnter Roll Number: ");
    scanf("%d", &roll);

    snprintf(query, sizeof(query),
        "SELECT COUNT(*), "
        "SUM(status='Present') "
        "FROM attendance WHERE roll_no=%d", roll);

    if (mysql_query(conn, query))
    {
        printf("Error: %s\n", mysql_error(conn));
        return;
    }

    MYSQL_RES *result = mysql_store_result(conn);
    MYSQL_ROW row = mysql_fetch_row(result);

    int total = atoi(row[0]);
    int present = row[1] ? atoi(row[1]) : 0;

    if (total > 0)
    {
        float percentage = (float)present / total * 100;

        printf("\nTotal Classes: %d", total);
        printf("\nPresent: %d", present);
        printf("\nAttendance Percentage: %.2f%%\n",
               percentage);
    }
    else
    {
        printf("\nNo attendance records found!\n");
    }

    mysql_free_result(result);
}

// MAIN MENU
void menu()
{
    int choice;

    do
    {
        printf("\n==================================\n");
        printf(" COLLEGE ATTENDANCE MANAGEMENT\n");
        printf("==================================\n");

        printf("1. Add Student\n");
        printf("2. Mark Attendance\n");
        printf("3. View Attendance\n");
        printf("4. Attendance Percentage\n");
        printf("5. Exit\n");

        printf("\nEnter Your Choice: ");
        scanf("%d", &choice);

        switch(choice)
        {
            case 1:
                addStudent();
                break;

            case 2:
                markAttendance();
                break;

            case 3:
                viewAttendance();
                break;

            case 4:
                attendancePercentage();
                break;

            case 5:
                printf("\nThank You!\n");
                break;

            default:
                printf("\nInvalid Choice!\n");
        }

    } while(choice != 5);
}

// MAIN FUNCTION
int main()
{
    connectDB();

    menu();

    mysql_close(conn);

    return 0;
}

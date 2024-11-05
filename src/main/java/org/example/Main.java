package org.example;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        StudentOperations studentOps = new StudentOperations();
        CourseOperations courseOps = new CourseOperations();

        while (true) {
            System.out.println("Menu:");
            System.out.println("1. Student Operations");
            System.out.println("2. Course Operations");
            System.out.println("3. Exit");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    studentMenu(scanner, studentOps);
                    break;
                case 2:
                    courseMenu(scanner, courseOps);
                    break;
                case 3:
                    System.out.println("Exiting the application...");
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private static void studentMenu(Scanner scanner, StudentOperations studentOps) {
        while (true) {
            System.out.println("\nStudent Operations:");
            System.out.println("1. Create Student");
            System.out.println("2. Retrieve All Students");
            System.out.println("3. Retrieve Student by ID");
            System.out.println("4. Update Student");
            System.out.println("5. Delete Student");
            System.out.println("6. Back to Main Menu");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    studentOps.createStudent();
                    break;
                case 2:
                    studentOps.retrieveAllStudents();
                    break;
                case 3:
                    studentOps.retrieveStudentById();
                    break;
                case 4:
                    studentOps.updateStudent();
                    break;
                case 5:
                    studentOps.deleteStudent();
                    break;
                case 6:
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private static void courseMenu(Scanner scanner, CourseOperations courseOps) {
        while (true) {
            System.out.println("\nCourse Operations:");
            System.out.println("1. Create Course");
            System.out.println("2. Retrieve All Courses");
            System.out.println("3. Retrieve Course by ID");
            System.out.println("4. Update Course");
            System.out.println("5. Delete Course");
            System.out.println("6. Back to Main Menu");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    courseOps.createCourse();
                    break;
                case 2:
                    courseOps.retrieveAllCourses();
                    break;
                case 3:
                    courseOps.retrieveCourseById();
                    break;
                case 4:
                    courseOps.updateCourse();
                    break;
                case 5:
                    courseOps.deleteCourse();
                    break;
                case 6:
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
}

package org.example;

import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;
import java.util.Scanner;
import java.util.regex.Pattern;

public class StudentOperations {
    private Scanner sc = new Scanner(System.in);
    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$");

    public void createStudent() {
        Student student = new Student();
        System.out.print("Enter Student Name: ");
        student.setName(sc.next());

        System.out.print("Enter Student Age (18-25): ");
        int age = sc.nextInt();
        if (age < 18 || age > 25) {
            System.out.println("Age must be between 18 and 25.");
            return;
        }
        student.setAge(age);

        System.out.print("Enter Student Email: ");
        String email = sc.next();
        if (!isValidEmail(email)) {
            System.out.println("Invalid email format.");
            return;
        }
        student.setEmail(email);

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            session.save(student);
            transaction.commit();
            System.out.println("Student created successfully with ID: " + student.getId());
        } catch (Exception e) {
            System.out.println("Error creating student: " + e.getMessage());
        }
    }

    public void retrieveAllStudents() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            List<Student> students = session.createQuery("from Student", Student.class).list();
            System.out.println("List of Students:");
            for (Student student : students) {
                System.out.printf("ID: %d, Name: %s, Age: %d, Email: %s, Created At: %s%n",
                        student.getId(), student.getName(), student.getAge(), student.getEmail(), student.getCreatedAt());
            }
        }
    }

    public void retrieveStudentById() {
        System.out.print("Enter Student ID: ");
        long id = sc.nextLong();
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Student student = session.get(Student.class, id);
            if (student != null) {
                System.out.printf("ID: %d, Name: %s, Age: %d, Email: %s, Created At: %s%n",
                        student.getId(), student.getName(), student.getAge(), student.getEmail(), student.getCreatedAt());
            } else {
                System.out.println("No student found with ID: " + id);
            }
        }
    }

    public void updateStudent() {
        System.out.print("Enter Student ID to update: ");
        long id = sc.nextLong();
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            Student student = session.get(Student.class, id);
            if (student != null) {
                System.out.print("Enter new name: ");
                student.setName(sc.next());

                System.out.print("Enter new age (18-25): ");
                int age = sc.nextInt();
                if (age < 18 || age > 25) {
                    System.out.println("Age must be between 18 and 25.");
                    return;
                }
                student.setAge(age);

                System.out.print("Enter new email: ");
                String email = sc.next();
                if (!isValidEmail(email)) {
                    System.out.println("Invalid email format.");
                    return;
                }
                student.setEmail(email);

                session.update(student);
                transaction.commit();
                System.out.println("Student updated successfully!");
            } else {
                System.out.println("No student found with ID: " + id);
            }
        }
    }

    public void deleteStudent() {
        System.out.print("Enter Student ID to delete: ");
        long id = sc.nextLong();
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            Student student = session.get(Student.class, id);
            if (student != null) {
                session.delete(student);
                transaction.commit();
                System.out.println("Student deleted successfully!");
            } else {
                System.out.println("No student found with ID: " + id);
            }
        }
    }
    public void addCourseToStudent(Long studentId, Long courseId) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();

            Student student = session.get(Student.class, studentId);
            Course course = session.get(Course.class, courseId);

            if (student != null && course != null) {
                student.addCourse(course);
                session.update(student);
                transaction.commit();
                System.out.println("Course added to student successfully.");
            } else {
                System.out.println("Student or Course not found.");
            }
        }
    }
    public void removeCourseFromStudent(Long studentId, Long courseId) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();

            Student student = session.get(Student.class, studentId);
            Course course = session.get(Course.class, courseId);

            if (student != null && course != null) {
                if (student.getCourses().contains(course)) {
                    student.removeCourse(course); // Remove the course from the student’s list
                    session.update(student); // Update the student in the database
                    transaction.commit();
                    System.out.println("Course removed from student successfully.");
                } else {
                    System.out.println("The student is not enrolled in this course.");
                }
            } else {
                System.out.println("Student or Course not found.");
            }
        }
    }


    private boolean isValidEmail(String email) {
        return EMAIL_PATTERN.matcher(email).matches();
    }
}
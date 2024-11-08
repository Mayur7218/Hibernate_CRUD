package org.example;

import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;
import java.util.Scanner;

public class CourseOperations {
    private final Scanner sc = new Scanner(System.in);

    public void createCourse() {
        Course course = new Course();
        System.out.print("Enter Course Name: ");
        course.setCourseName(sc.next());

        System.out.print("Enter Course Code: ");
        course.setCourseCode(sc.next());

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            session.save(course);
            transaction.commit();
            System.out.println("Course created successfully with ID: " + course.getId());
        } catch (Exception e) {
            System.out.println("Error creating course: " + e.getMessage());
        }
    }

    public void retrieveAllCourses() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            List<Course> courses = session.createQuery("from Course", Course.class).list();
            System.out.println("List of Courses:");
            for (Course course : courses) {
                System.out.printf("ID: %d, Name: %s, Code: %s, Created At: %s%n",
                        course.getId(), course.getCourseName(), course.getCourseCode(), course.getCreatedAt());
            }
        }
    }

    public void retrieveCourseById() {
        System.out.print("Enter Course ID: ");
        long id = sc.nextLong();
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Course course = session.get(Course.class, id);
            if (course != null) {
                System.out.printf("Course ID: %d, Name: %s, Code: %s, Created At: %s%n",
                        course.getId(), course.getCourseName(), course.getCourseCode(), course.getCreatedAt());
            } else {
                System.out.println("No course found with ID: " + id);
            }
        }
    }

    public void updateCourse() {
        System.out.print("Enter Course ID to update: ");
        long id = sc.nextLong();
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            Course course = session.get(Course.class, id);
            if (course != null) {
                System.out.print("Enter new name: ");
                course.setCourseName(sc.next());

                System.out.print("Enter new course code: ");
                course.setCourseCode(sc.next());

                session.update(course);
                transaction.commit();
                System.out.println("Course updated successfully!");
            } else {
                System.out.println("No course found with ID: " + id);
            }
        }
    }

    public void deleteCourse() {
        System.out.print("Enter Course ID to delete: ");
        long id = sc.nextLong();
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            Course course = session.get(Course.class, id);
            if (course != null) {
                session.delete(course);
                transaction.commit();
                System.out.println("Course deleted successfully!");
            } else {
                System.out.println("No course found with ID: " + id);
            }
        }
    }
    public void addStudentToCourse(Long courseId, Long studentId) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();

            Course course = session.get(Course.class, courseId);
            Student student = session.get(Student.class, studentId);

            if (course != null && student != null) {
                course.addStudent(student);
                session.update(course);
                transaction.commit();
                System.out.println("Student added to course successfully.");
            } else {
                System.out.println("Student or Course not found.");
            }
        }
    }
    public void removeStudentFromCourse(Long courseId, Long studentId) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();

            Course course = session.get(Course.class, courseId);
            Student student = session.get(Student.class, studentId);

            if (course != null && student != null) {
                if (course.getStudents().contains(student)) {
                    course.removeStudent(student); // Remove the student from the course’s list
                    session.update(course); // Update the course in the database
                    transaction.commit();
                    System.out.println("Student removed from course successfully.");
                } else {
                    System.out.println("The course does not have this student enrolled.");
                }
            } else {
                System.out.println("Student or Course not found.");
            }
        }
    }

}
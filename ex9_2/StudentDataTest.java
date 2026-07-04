import java.io.*;
import java.util.ArrayList;
import java.util.List;

class Student {
    private int id;
    private String name;
    private double gpa;

    public Student(int id, String name, double gpa) {
        this.id = id;
        this.name = name;
        this.gpa = gpa;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public double getGpa() {
        return gpa;
    }
}

public class StudentDataTest {

    public static void writeStudentsToFile(List<Student> students, String filename) {
        try (DataOutputStream dos = new DataOutputStream(new FileOutputStream(filename))) {
            for (Student student : students) {
                dos.writeInt(student.getId());
                String name = student.getName();
                dos.writeInt(name.length());
                dos.writeBytes(name);
                dos.writeDouble(student.getGpa());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static List<Student> readStudentsFromFile(String filename) {
        List<Student> students = new ArrayList<>();
        try (DataInputStream dis = new DataInputStream(new FileInputStream(filename))) {
            while (dis.available() > 0) {
                int id = dis.readInt();
                int nameLength = dis.readInt();
                byte[] nameBytes = new byte[nameLength];
                dis.readFully(nameBytes);
                String name = new String(nameBytes);
                double gpa = dis.readDouble();
                students.add(new Student(id, name, gpa));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return students;
    }

    public static void main(String[] args) {
        List<Student> students = new ArrayList<>();
        students.add(new Student(1, "Alice", 3.5));
        students.add(new Student(2, "Bob", 3.8));
        students.add(new Student(3, "Charlie", 3.2));

        String filename = "students.dat";
        
        // Write students to file
        writeStudentsToFile(students, filename);
        
        // Read students from file
        List<Student> readStudents = readStudentsFromFile(filename);
        
        // Print student information
        for (Student student : readStudents) {
            System.out.println("ID: " + student.getId() + ", Name: " + student.getName() + ", GPA: " + student.getGpa());
        }
    }
}
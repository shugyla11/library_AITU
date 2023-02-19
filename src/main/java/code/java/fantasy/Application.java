package code.java.fantasy;

import code.java.DBA.data.bookDBA;
import code.java.DBA.data.libraryDBA;
import code.java.DBA.data.studentDBA;

import java.sql.SQLException;
import java.util.Scanner;

public abstract class Application implements App {

    private Scanner in = new Scanner(System.in);
    private libraryDBA libraryDBA = new libraryDBA();
    private studentDBA studentDBA = new studentDBA();
    private bookDBA bookDBA = new bookDBA();

    @Override
    public void start() throws SQLException {


        System.out.println("welcome to AITU LIBRARY");
        System.out.println("[1] --> manipulate with students");
        System.out.println("[2] --> manipulate with books");
        System.out.println("[3] --> library manipulations");
        System.out.println("[4] --> close app");

        while (true) {
            System.err.println("enter command number: ");
            int command = in.nextInt();
            switch (command) {
                case 1 -> manipulateWithStudents();
                case 2 -> manipulateWithBooks();
                case 3 -> libraryManipulations();
                case 4 -> exit();
                default -> System.out.println("invalid number");
            }

        }
    }
    private void libraryManipulations() throws SQLException{
        System.out.println("welcome to library system");
        System.out.println("[1] --> give book");
        System.out.println("[2] --> return book");
        System.out.println("[3] --> go to main page");
        System.out.println("[4] --> close app");

        while (true){
            System.out.println("enter key number");
            int n = in.nextInt();
            switch (n){
                case 1 -> libraryDBA.giveBook();
                case 2 -> libraryDBA.returnBook();
                case 3 -> start();
                case 4 -> exit();
                default -> System.out.println("in valid number");
            }
        }
    }

    private void manipulateWithStudents() throws SQLException {

        System.out.println("Welcome to student system");
        System.out.println("[1] --> add student");
        System.out.println("[2] --> delete student");
        System.out.println("[3] --> update student");
        System.out.println("[4] --> illustrate all students");
        System.out.println("[5] --> go to main page");
        System.out.println("[6] --> close app");

        while (true) {
            System.err.println("enter number");
            int n = in.nextInt();
            switch (n) {
                case 1 -> studentDBA.add();
                case 2 -> studentDBA.delete();
                case 3 -> studentDBA.update();
                case 4 -> studentDBA.getAll().forEach(System.out::println);
                case 5 -> start();
                case 6 -> exit();
                default -> System.out.println("invalid number");
            }
        }

    }
    private void manipulateWithBooks() throws SQLException{
        System.out.println("welcome to book system");
        System.out.println("[1] --> add book");
        System.out.println("[2] --> delete book");
        System.out.println("[3] --> update book");
        System.out.println("[4] --> illustrate all books");
        System.out.println("[5] --> go to main page");
        System.out.println("[6] --> close app");

        while (true){
            System.err.println("enter key number");
            int n = in.nextInt();
            switch (n){
                case 1 -> bookDBA.add();
                case 2 -> bookDBA.delete();
                case 3 -> bookDBA.update();
                case 4 -> bookDBA.getAll().forEach(System.out::println);
                case 5 -> start();
                case 6 -> exit();
                default -> System.out.println("invalid number");
            }
        }
    }
}

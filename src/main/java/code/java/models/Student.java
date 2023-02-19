package code.java.models;


import java.util.List;

public class Student {
    private int id;
    private int barcode;
    private String name;
    private int age;
    private String group;
    private List<String> borrowedBooks;

    public Student(int barcode,
                   String name,
                   int age,
                   String group,
                   List<String> borrowedBooks) {
        this.barcode = barcode;
        this.name = name;
        this.age = age;
        this.group = group;
        this.borrowedBooks = borrowedBooks;
    }

    public int getBarcode() {
        return barcode;
    }

    public void setBarcode(int barcode) {
        this.barcode = barcode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public List<String> getBorrowedBooks() {
        return borrowedBooks;
    }

    public void setBorrowedBooks(List<String> borrowedBooks) {
        this.borrowedBooks = borrowedBooks;
    }

    @Override
    public String toString() {
        String info =
                "barcode=" + barcode +
                        ", name='" + name + '\'' +
                        ", age=" + age +
                        ", group=" + group;

        if (!borrowedBooks.isEmpty()) {
            info += " registered books --> ";
            for (String str : borrowedBooks) {
                info += str;
            }
        }
        return "\n"
         + info;
    }
}

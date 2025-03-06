public class Main {
    public static void main(String[] args) {
        Author author1 = new Author("Лев", "Толстой", 10);
        Author author2 = new Author("Антуан де", "Сент-Экзюпери", 8);
        Author author3 = new Author("Дж. К.", "Роулинг", 9);

        Book book1 = new Book("Война и мир", 1869, author1, 1225);
        Book book2 = new Book("Маленький принц", 1943, author2, 96);
        Book book3 = new Book("Гарри Поттер и философский камень", 1997, author3, 432);

        book1.displayInfo();
        book2.displayInfo();
        book3.displayInfo();

        System.out.println("Поиск 'Толстой' в книге 'Война и мир': " + book1.matches("Толстой"));
        System.out.println("Поиск 'Гарри' в книге 'Маленький принц': " + book2.matches("Гарри"));
    }
}

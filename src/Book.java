public class Book {
    private static final int MAX_PAGES = 500;
    private static final int PRICE_PER_PAGE = 3;
    private static final int MIN_PRICE = 250;

    private String title;
    private int releaseYear;
    private Author author;
    private int pages;

    public Book(String title, int releaseYear, Author author, int pages) {
        this.title = title;
        this.releaseYear = releaseYear;
        this.author = author;
        this.pages = pages;
    }

    public boolean isBig() {
        return pages > MAX_PAGES;
    }

    public boolean matches(String word) {
        String lowerWord = word.toLowerCase();

        return title.toLowerCase().contains(lowerWord) ||
                author.getName().toLowerCase().contains(lowerWord) ||
                author.getSurname().toLowerCase().contains(lowerWord);
    }

    public int estimatePrice() {
        double price = pages * PRICE_PER_PAGE * Math.sqrt(author.getRating());

        return Math.max((int) Math.floor(price), MIN_PRICE);
    }

    public void displayInfo() {
        System.out.println("Название: " + title);

        System.out.println("Год выпуска: " + releaseYear);

        System.out.println("Автор: " + author.getName() + " " + author.getSurname());

        System.out.println("Страниц: " + pages);

        System.out.println("Большая книга: " + (isBig() ? "Да" : "Нет"));

        System.out.println("Оценочная стоимость: " + estimatePrice() + " руб.");

        System.out.println();
    }
}

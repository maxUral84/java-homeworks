public class Main {
    public static void main(String[] args) {

        // Корректное создание объекта
        Person mom = new PersonBuilder()
                .setName("Анна")
                .setSurname("Вольф")
                .setAge(31)
                .setAddress("Сидней")
                .build();

        Person son = mom.newChildBuilder()
                .setName("Антошка")
                .build();

        System.out.println("У " + mom + " есть сын, " + son);

        // Попытка создать без имени (должна выбросить исключение)
        try {
            new PersonBuilder().setSurname("Иванов").build();
        } catch (IllegalStateException e) {
            System.out.println("Ошибка: " + e.getMessage());
        }

        // Попытка создать с отрицательным возрастом (должна выбросить исключение)
        try {
            new PersonBuilder().setName("Пётр").setSurname("Петров").setAge(-5).build();
        } catch (IllegalArgumentException e) {
            System.out.println("Ошибка: " + e.getMessage());
        }
    }
}

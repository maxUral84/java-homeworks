import java.util.OptionalInt;

public class Person {
    private final String name;
    private final String surname;
    private Integer age;  // null если возраст неизвестен
    private String address; // null если адрес неизвестен

    public Person(String name, String surname) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Имя не может быть пустым");
        }
        if (surname == null || surname.isBlank()) {
            throw new IllegalArgumentException("Фамилия не может быть пустой");
        }
        this.name = name;
        this.surname = surname;
        this.age = null;
        this.address = null;
    }

    public Person(String name, String surname, int age) {
        this(name, surname);
        if (age < 0) {
            throw new IllegalArgumentException("Возраст не может быть отрицательным");
        }
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public OptionalInt getAge() {
        return age == null ? OptionalInt.empty() : OptionalInt.of(age);
    }

    public String getAddress() {
        return address;
    }

    public boolean hasAge() {
        return age != null;
    }

    public boolean hasAddress() {
        return address != null && !address.isBlank();
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void happyBirthday() {
        if (age == null) {
            throw new IllegalStateException("Возраст неизвестен, увеличить нельзя");
        }
        age++;
    }

    @Override
    public String toString() {
        return name + " " + surname +
                (hasAge() ? ", возраст: " + age : ", возраст неизвестен") +
                (hasAddress() ? ", адрес: " + address : ", адрес неизвестен");
    }

    @Override
    public int hashCode() {
        int result = name.hashCode();
        result = 31 * result + surname.hashCode();
        result = 31 * result + (age != null ? age.hashCode() : 0);
        result = 31 * result + (address != null ? address.hashCode() : 0);
        return result;
    }

    // Метод для создания билдера ребенка с фамилией, возр. 0 и адресом родителя
    public PersonBuilder newChildBuilder() {
        PersonBuilder builder = new PersonBuilder();
        builder.setSurname(this.surname);
        builder.setAge(0);
        if (this.hasAddress()) {
            builder.setAddress(this.address);
        }
        return builder;
    }
}

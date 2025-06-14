public class LinkedStack {
    private Node tail; // ссылка на последний добавленный узел (обёртку)
    private int size; // размер стека, т.е. количество элементов в нём

    public void push(int value) {
        Node node = new Node(value); // создаём новый узел

        if (tail != null) { // если в стеке уже есть элементы
            node.setPrev(tail); // связываем новый узел с последним
        }

        tail = node; // назначаем новый узел последним узлом
        size++; // увеличиваем счётчик элементов
    }

    public int pop() {
        if (isEmpty()) {
            throw new IllegalStateException("Stack is empty");
        }

        int value = tail.getValue();
        tail = tail.getPrev();
        size--;
        return value;
    }

    public int getSize() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public String toString() {
        if (isEmpty()) {
            return "EMPTY";
        }

        StringBuilder sb = new StringBuilder();
        Node current = tail;

        while (current != null) {
            sb.append(current.getValue());

            if (current.getPrev() != null) {
                sb.append(" -> ");
            }

            current = current.getPrev();
        }

        return sb.toString();
    }
}

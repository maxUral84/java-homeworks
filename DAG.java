import java.util.*;

public class DAG<T> {
    private List<Vertex<T>> vertices = new ArrayList<>();

    public Vertex<T> createVertex(T value) {
        Vertex<T> v = new Vertex<>(value);
        vertices.add(v);
        return v;
    }

    public void createEdge(Vertex<T> from, Vertex<T> to) {
        from.getAdjacent().add(to);
    }

    public int path(Vertex<T> from, Vertex<T> to) {
        if (from.equals(to)) {
            return 0;
        }

        Map<Vertex<T>, Integer> paths = new HashMap<>();
        paths.put(from, 0);

        Queue<Vertex<T>> queue = new ArrayDeque<>();
        Set<Vertex<T>> added = new HashSet<>();
        queue.add(from);
        added.add(from);

        while (!queue.isEmpty()) {
            Vertex<T> v = queue.poll();

            if (v.equals(to)) {
                return paths.get(v);
            }

            for (Vertex<T> neighbor : v.getAdjacent()) {
                if (!added.contains(neighbor)) {
                    added.add(neighbor);
                    paths.put(neighbor, paths.get(v) + 1);
                    queue.add(neighbor);
                }
            }
        }

        return -1;
    }
}
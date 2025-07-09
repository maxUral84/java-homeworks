public class Worker {
    private static final int TOTAL_TASKS = 100;
    private OnTaskDoneListener callback;

    public Worker(OnTaskDoneListener callback) {
        this.callback = callback;
    }

    public void start() {
        for (int i = 0; i < TOTAL_TASKS; i++) {
            callback.onDone("Task " + i + " is done");
        }
    }
}

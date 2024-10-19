public abstract class Task implements TaskInterface {
    private String title;
    private String description;
    private String dueDate;
    protected Status status;
    private int priority; // 1 - 5 Scale of Importance, Where 1 is the most important
    
    public Task(String title, String description, String dueDate, int priority) {
        this.title = title;
        this.description = description;
        this.dueDate = dueDate;
        this.priority = priority;
        this.status = Status.NOT_STARTED;
    }

    public void startTask() {
        status = Status.IN_PROGRESS;
    }

    public void completeTask() {
        status = Status.FINISHED;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getDueDate() {
        return dueDate;
    }

    public String getStatus() {
        return status.toString();
    }

    public int getPriority() {
        return priority;
    }
}
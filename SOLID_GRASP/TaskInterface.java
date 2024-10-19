public interface TaskInterface {
    String getTitle();
    String getDescription();
    String getDueDate();
    String getStatus();
    int getPriority();
    void startTask();
    void completeTask();
}
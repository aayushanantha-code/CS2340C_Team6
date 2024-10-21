public class RepeatingTask extends Task {
    private int daysInBetweenReocurrence;
    
    public RepeatingTask(String title, String description, String dueDate, String status, int priority, int frequency) {
        super(title, description, dueDate, priority);
        this.daysInBetweenReocurrence = frequency;
    }

    @Override
    public void completeTask() {
        this.status = Status.TEMPORARILY_FINISHED;
    }
}
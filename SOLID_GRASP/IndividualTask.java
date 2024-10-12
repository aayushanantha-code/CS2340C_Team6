public class IndividualTask extends Task {
    private TeamMember member;
    
    public IndividualTask(String title, String description, String dueDate, String status, int priority, TeamMember member) {
        super(title, description, dueDate, status, priority);
        this.member = member;
    }
}
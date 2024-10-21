import java.util.ArrayList;

public class Project implements ManageTasks{
    private String name;
    private String description;
    private String startDate;
    private String endDate;
    private ArrayList<Task> tasks;
    private ArrayList<TeamMember> group;

    public Project(String name, String description, String startDate, String endDate) {
        this.name = name;
        this.description = description;
        this.startDate = startDate;
        this.endDate = endDate;
        this.tasks = new ArrayList<Task>();
        this.group = new ArrayList<TeamMember>();
    }

    @Override
    public void addTask(Task task) {
        tasks.add(task);
    }

    @Override
    public void removeTask(Task task) {
        tasks.remove(task);
    }

    public void addMember(TeamMember member) {
        group.add(member);
    }

    public void removeMember(TeamMember member) {
        group.remove(member);
    }
}

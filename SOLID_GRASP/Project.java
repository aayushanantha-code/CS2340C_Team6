import java.util.List;

public class Project implements ManageTasks{
    private String name;
    private String descrpiton;
    private String startDate;
    private String endDate;
    private List<Task> tasks;
    private List<TeamMembers> group;

    public Project(String name, String descrpiton, String startDate, String endDate) {
        this.name = name;
        this.description = descrpiton;
        this.startDate = startDate;
        this.endDate = endDate;
        this.tasks = new List<Task>();
        this.group = new List<TeamMembers>();
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

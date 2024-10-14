public class MembersWithRoles extends TeamMember {
    private String role;

    public MembersWithRoles(String name, String email, String role) {
        super(name, email);
        this.role = role;
    }
}
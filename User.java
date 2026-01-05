public abstract class User {
    protected final int id;
    protected final String name;
    protected final String email;

    public User(int id, String name, String email) {
        if (id <= 0) throw new IllegalArgumentException("Invalid id");
        if (name == null || name.isBlank()) throw new IllegalArgumentException("Name required");
        if (email == null || email.isBlank()) throw new IllegalArgumentException("Email required");

        this.id = id;
        this.name = name;
        this.email = email;
    }

    public int getId() { return id; }
    public String getName() { return name; }
}

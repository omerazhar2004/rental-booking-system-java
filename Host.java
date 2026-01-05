public class Host extends User {
    private boolean verified;

    public Host(int id, String name, String email) {
        super(id, name, email);
        this.verified = true; // assume verified for demo
    }

    public boolean isVerified() {
        return verified;
    }
}
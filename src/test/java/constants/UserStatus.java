package constants;

public enum UserStatus {
    ACTIVE(0),
    INACTIVE(1),
    SUSPENDED(2);

    private int i;

    UserStatus(int i) {
        this.i = i;
    }
}

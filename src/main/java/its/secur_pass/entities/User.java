package its.secur_pass.entities;

import javax.annotation.Nonnull;

public class User {

    @Nonnull
    private String name;

    @Nonnull
    private String password;

    public User(@Nonnull String name, @Nonnull String password) {
        this.name = name;
        this.password = password;
    }

    @Nonnull
    public String getName() {
        return name;
    }

    @Nonnull
    public String getPassword() {
        return password;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        return name.equals(user.name) && password.equals(user.password);
    }

    @Override
    public int hashCode() {
        int result = name.hashCode();
        result = 31 * result + password.hashCode();
        return result;
    }
}

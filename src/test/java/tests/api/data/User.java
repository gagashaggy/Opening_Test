/**
 * Created on 26 Apr, 2020
 */
package tests.api.data;

import java.io.Serializable;

/**
 * Класс, описывающий данные пользователя
 *
 * @author vmohnachev
 */
public class User implements Serializable {

    private String id;
    private String email;
    private String firstName;
    private String lastName;
    private String avatar;
    private String name;
    private String job;
    private String createdAt;

    public User(String id, String email, String firstName, String lastName, String avatar) {
        this.id = id;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.avatar = avatar;
    }

    public User(String name, String job, String id, String createdAt) {
        this.name = name;
        this.job = job;
        this.id = id;
        this.createdAt = createdAt;
    }

    public String getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getAvatar() {
        return avatar;
    }

    public String getName() {
        return name;
    }

    public String getJob() {
        return job;
    }

    public String getCreatedAt() {
        return createdAt;
    }
}
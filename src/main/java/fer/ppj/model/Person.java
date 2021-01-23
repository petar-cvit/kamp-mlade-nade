package fer.ppj.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public class Person {
    @Id
    @Column(nullable = false)
    private String nickname;

    private String name;
    private String surname;
    private String email;
    private String motivationalLetter;
    private String password;
    private Date birthday;
    private Boolean accepted;
    private String nickHash;
    private String phoneNumber;

    @OneToMany(mappedBy="user", fetch=FetchType.LAZY, cascade=CascadeType.PERSIST, orphanRemoval=true)
    List<Comment> comments = new ArrayList<>();

    public Person() {}

    public Person(String nickname, String name, String surname, String email, Date birthday, String phoneNumber, String motivationalLetter, String nickHash) {
        this.nickname = nickname;
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.birthday = birthday;
        this.accepted = false;
        this.phoneNumber = phoneNumber;
        this.motivationalLetter = motivationalLetter;
        this.nickHash = nickHash;
    }

    public String getNickname() {
        return nickname;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public Date getBirthday() {
        return birthday;
    }

    public Boolean getAccepted() {
        return accepted;
    }

    public String getNickHash() {
        return nickHash;
    }

    public void setAccepted(Boolean accepted) {
        this.accepted = accepted;
    }

    @Override
    public String toString() {
        return "Person{" +
                "nickname='" + nickname + '\'' +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", birthday=" + birthday +
                '}';
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getMotivationalLetter() {
        return motivationalLetter;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }
}

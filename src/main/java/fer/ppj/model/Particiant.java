package fer.ppj.model;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "participant")
public class Particiant extends Person {

    private String custodian;
    private Boolean underage;

    @ManyToOne
    private Group campGroup;

    public Particiant() {
    }

    public Particiant(String nickname, String name, String surname, String email, String contact, String custodian, Boolean underage, String motivationalLetter, Date birthday, String nickHash) {
        super(nickname, name, surname, email, birthday, contact, motivationalLetter, nickHash);
        this.custodian = custodian;
        this.underage = underage;
    }

    public String getName() {
        return super.getName();
    }

    public String getSurname() {
        return super.getSurname();
    }

    public String getEmail() {
        return super.getEmail();
    }

    public String getContact() {
        return super.getEmail();
    }

    public Group getCampGroup() {
        return campGroup;
    }

    public String getPassword() {
        return super.getPassword();
    }

    public void setCampGroup(Group campGroup) {
        this.campGroup = campGroup;
    }

    public String getCustodian() {
        return custodian;
    }

    public Boolean getUnderage() {
        return underage;
    }
}


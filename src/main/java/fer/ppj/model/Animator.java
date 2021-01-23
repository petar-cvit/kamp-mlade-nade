package fer.ppj.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "animator")
public class Animator extends Person {

    @ManyToMany
    @JoinTable(
            name = "activityAnimator",
            joinColumns = @JoinColumn(name = "animator_id"),
            inverseJoinColumns = @JoinColumn(name = "activity_in_time_id")
    )
    private List<ActivityInTime> activities = new ArrayList<>();

    public Animator() {
    }

    public Animator(String nickname, String name, String surname, String email, Date birtday, String phoneNumber, String motivationalLetter, String nickHash) {
        super(nickname, name, surname, email, birtday, phoneNumber, motivationalLetter, nickHash);
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

    public String getPassword() {
        return super.getPassword();
    }

    public List<ActivityInTime> getActivities() {
        return activities;
    }
}

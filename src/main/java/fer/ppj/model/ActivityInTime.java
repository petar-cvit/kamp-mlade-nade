package fer.ppj.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "ActivityInTime")
public class ActivityInTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private Date start;

    @ManyToOne
    private Activity activity;

    public ActivityInTime(String name, Date start, Activity activity, List<Group> groups, List<Animator> animators) {
        this.name = name;
        this.activity = activity;
        this.start = start;
        this.groups = groups;
        this.animators = animators;
    }

    @ManyToMany(mappedBy = "activities")
    private List<Group> groups = new ArrayList<>();

    @ManyToMany(mappedBy = "activities")
    private List<Animator> animators = new ArrayList<>();

    public ActivityInTime() {
    }

    public String getName() {
        return name;
    }

    public Activity getActivity() {
        return activity;
    }

    public Date getStart() {
        return start;
    }

    public List<Animator> getAnimators() {
        return animators;
    }
}

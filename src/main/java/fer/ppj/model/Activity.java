package fer.ppj.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Activity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String activityDescription;
    private ActivityType type;
    private Integer numberOfGroups;
    private int durationInMinutes;

    public Activity(String name, String activityDescription, Integer durationInMinutes, ActivityType type, int numberOfGroups) {
        this.name = name;
        this.activityDescription = activityDescription;
        this.durationInMinutes = durationInMinutes;
        this.type = type;
        this.numberOfGroups = numberOfGroups;
    }
    public Activity() {}

    @OneToMany(mappedBy="activity", fetch=FetchType.LAZY, cascade=CascadeType.PERSIST, orphanRemoval=true)
    private List<ActivityInTime> activitiesInTime = new ArrayList<>();

    @OneToMany(mappedBy="activity", cascade=CascadeType.PERSIST, fetch=FetchType.LAZY, orphanRemoval=true)
    List<Comment> comments = new ArrayList<>();

    public String getName() {
        return name;
    }

    public String getActivityDescription() {
        return activityDescription;
    }

    public ActivityType getType() {
        return type;
    }

    public int getDurationInMinutes() {
        return durationInMinutes;
    }

    public Integer getNumberOfGroups() {
        return numberOfGroups;
    }
}

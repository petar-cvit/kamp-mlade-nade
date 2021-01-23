package fer.ppj.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "campGroup")
public class Group {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @OneToMany(mappedBy="campGroup", fetch=FetchType.EAGER, cascade=CascadeType.PERSIST, orphanRemoval=true)
    private List<Particiant> particiants = new ArrayList<>();

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    @JoinTable(
            name = "activity_group",
            joinColumns = @JoinColumn(name = "group_id"),
            inverseJoinColumns = @JoinColumn(name = "activity_in_time_id")
    )
    private List<ActivityInTime> activities = new ArrayList<>();

    public Group() {
        new Group("");
    }

    public Group(String name) {
        this.particiants = new ArrayList<>();
        this.activities = new ArrayList<>();
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public List<ActivityInTime> getActivities() {
        return activities;
    }

    public List<Particiant> getParticiants() {
        return particiants;
    }

    public String getName() {
        return name;
    }

    public void addParticipant(Particiant particiant) {
        this.particiants.add(particiant);
    }
}

package fer.ppj.model;

import javax.persistence.*;

@Entity
@Table(name = "komentar")
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String text;
    private Integer mark;

    @ManyToOne
    private Person user;

    @ManyToOne
    private Activity activity;

    public Comment() {}

    public Comment(String text, Integer mark, Person user, Activity activity) {
        this.text = text;
        this.mark = mark;
        this.user = user;
        this.activity = activity;
    }

    public String getText() {
        return text;
    }

    public Integer getMark() {
        return mark;
    }

    public Person getUser() {
        return user;
    }
}

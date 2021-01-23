package fer.ppj.service;

import fer.ppj.model.*;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;


public interface IService {

    //Argumenti: pocetak i kraj aktivnosti, tip aktivnosti
    //Vraca listu svih aktivnosti koje se odvijaju u to vrijeme i tog su tipa (treba bit prazna da bi se aktivnost prihvatila)
    public List<ActivityInTime> findAllActivitiesOfSameTypeAndTime(Date start, Date end, Activity activity);

    //Argumenti: lista grupa, vrijeme aktivnosti
    //Vraca listu grupa koje nemaju konflikte u to vrijeme
    public List<Group> findAllFreeGroups(List<Group> allGroups, Date start, Date end);

    //Argumenti: lista grupa i ime aktivnosti
    //Vraca listu grupa koje vec nisu na toj aktivnosti
    public List<Group> findAllValidGroups(List<Group> allGroups, Activity activity);

    //Argumenti: lista animatora, vrijeme aktivnosti
    //Vraca listu animatora koji nemaju konflikta (nisu vec na nekoj aktivnosti u tom vremenu)
    public List<Animator> findAllFreeAnimators(List<Animator> allAnimators, Date start, Date end);

    //Provjera jesu li sve grupe na svakoj aktivnosti tocno jednom
    //queryja sve grupe i za svaku provjerava jel joj broj aktivnosti jednak ukupnom broju aktivnosti
    public boolean allGroupsAllActivities();

    public List<Animator> getAnimators();

    public List<Particiant> getParticipants();

    //Dohvat svih grupa
    public List<Group> getGroups();

    public List<Activity> participatedIn(Animator animator);

    public List<Activity> participatedIn(Particiant particiant);

    //Dohvat svih animatora na cijim aktivnostima je sudionik sudjelovao
    public List<Animator> getAnimatorsForParticipant(Particiant particiant);

    public List<ActivityInTime> getActInTime(Particiant particiant);

    public List<ActivityInTime> getActInTimeAnimator(Animator animator);

    public void insertParticipant(Particiant particiant);

    public void insertGroup(Group group);

    public List<Person> getNotAccpeted();

    public void deletePerson(String nickname);

    public void acceptPerson(String nickname);

    public Person getByNickname(String nickname);

    public Person getByNickHash(String nickHash);

    public void insertAnimator(Animator animator);

    public void insertActivity(Activity activity);

    public void updatePassword(String nickname, String password);

    public List<Activity> allActivities();

    public List<Activity> filteredActivities();

    public void insertOrganizator(Organizator organizator);

    public List<ActivityInTime> allActivitiesInTime();

    Activity getActivityByName(String name);

    void insertActivityInTime(ActivityInTime activityInTime);

    Group getGroupById(Long id);

    List<Activity> getActivitiesAfterTime(LocalDateTime time);

    Animator getAnimator(String nickname);

    List<ActivityInTime> getActivitiesForGroup(Group group);

    void insertComment(Comment comment);

    List<Comment> commentsByActivity(Activity activity);

    List<Comment> searchCommentsByNickname(String s);

    List<Comment> searchCommentsByGroup(String s);

    List<Comment> searchCommentsByActivity(String s);

    Group getGroupByName(String name);

    Particiant getParticipantByName(String name);

    void updateParticipantGroup(Group group, Particiant particiant);

    ActivityInTime activityInTimeByName(String name);
}

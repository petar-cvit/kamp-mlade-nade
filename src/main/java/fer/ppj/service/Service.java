package fer.ppj.service;

import fer.ppj.model.*;
import fer.ppj.repos.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.*;

@org.springframework.stereotype.Service
public class Service implements IService {
    @Autowired
    private ParticipantRepo participantRepo;

    @Autowired
    private AnimatorRepo animatorRepo;

    @Autowired
    private ActivityRepo activityRepo;

    @Autowired
    private ActivityInTimeRepo activityInTimeRepo;

    @Autowired
    private GroupRepo groupRepo;

    @Autowired
    private PersonRepo personRepo;

    @Autowired
    private OrganizatorRepo organizatorRepo;

    @Autowired
    private CommentRepo commentRepo;

    @Override
    public List<ActivityInTime> findAllActivitiesOfSameTypeAndTime(Date start, Date end, Activity activity) {
        return null;
    }

    @Override
    public List<Group> findAllFreeGroups(List<Group> allGroups, Date start, Date end) {
        return null;
    }

    @Override
    public List<Group> findAllValidGroups(List<Group> allGroups, Activity activity) {
        return null;
    }

    @Override
    public List<Animator> findAllFreeAnimators(List<Animator> allAnimators, Date start, Date end) {
        return null;
    }

    @Override
    public boolean allGroupsAllActivities() {
        List<Group> groups = groupRepo.findAll();
        long activites = activityRepo.count();

        for (Group group : groups) {
            if (activites != activityInTimeRepo.countActivitiesByGroup(group)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public List<Animator> getAnimators() {
        return animatorRepo.findAll();
    }

    @Override
    public List<Particiant> getParticipants() { return participantRepo.findAll(); }

    @Override
    public List<Group> getGroups() {
        return groupRepo.findAll();
    }

    @Override
    public List<Activity> participatedIn(Animator animator) {
        List<Animator> animators = new ArrayList<>();
        animators.add(animator);
        List<ActivityInTime> activitiesInTime = activityInTimeRepo.getAllByAnimators(animators);
        List<Activity> activities = new ArrayList<>();

        for (ActivityInTime activityInTime : activitiesInTime) {
            activities.add(activityInTime.getActivity());
        }

        return activities;
    }

    @Override
    public List<Activity> participatedIn(Particiant particiant) {
        return null;
    }

    @Override
    public List<Animator> getAnimatorsForParticipant(Particiant particiant) {
        Group group = particiant.getCampGroup();
        List<ActivityInTime> activities = activityInTimeRepo.getActivitiesForGroup(group);
        Set<Animator> animators = new HashSet<>();

        activities.forEach(activity -> animators.addAll(animatorRepo.getAnimatorByActivitiesContaining(activity)));

        return new ArrayList<>(animators);
    }

    @Override
    public List<ActivityInTime> getActInTime(Particiant participant) {
        Group group = participant.getCampGroup();
        return new ArrayList<>(new HashSet<>(activityInTimeRepo.getActivitiesForGroup(group)));
    }

    @Override
    public List<ActivityInTime> getActInTimeAnimator(Animator animator) {
        return activityInTimeRepo.getAllByAnimators(new ArrayList<Animator>(Arrays.asList(animator)));
    }

    @Override
    public void insertParticipant(Particiant particiant) {
        participantRepo.save(particiant);
    }

    @Override
    public void insertGroup(Group group) {
        groupRepo.save(group);
    }

    @Override
    public List<Person> getNotAccpeted() {
        return personRepo.findAllByAcceptedFalse();
    }


    @Override
    public void deletePerson(String nickname) {
        personRepo.deleteByNickname(nickname);
    }

    @Override
    public void acceptPerson(String nickname) {
        personRepo.acceptPerson(nickname);
    }

    @Override
    public Person getByNickname(String nickname) {
        return personRepo.findAllByNickname(nickname);
    }

    @Override
    public Person getByNickHash(String nickHash) {
        return personRepo.findAllByNickHash(nickHash);
    }

    @Override
    public void insertAnimator(Animator animator) {
        animatorRepo.save(animator);
    }

    @Override
    public void insertActivity(Activity activity) {
        activityRepo.save(activity);
    }

    @Override
    public void updatePassword(String nickname, String password) {
        personRepo.updatePassword(nickname, password);
    }

    @Override
    public List<Activity> allActivities() {
        return activityRepo.findAll();
    }

    @Override
    public List<Activity> filteredActivities() {
        List<Activity> acts = activityRepo.findAll();
        List<String> names = Arrays.asList("campStart", "campEnd", "animatorStart",
                                            "animatorEnd", "participantStart", "participantEnd");
        List<Activity> activities = new ArrayList<>();
        for(Activity a : acts){
            if(!names.contains(a.getName())){
                activities.add(a);
            }
        }
        return activities;
    }

    @Override
    public void insertOrganizator(Organizator organizator) {
        organizatorRepo.save(organizator);
    }

    @Override
    public List<ActivityInTime> allActivitiesInTime() {
        return activityInTimeRepo.findAll();
    }

    @Override
    public Activity getActivityByName(String name) {
        return activityRepo.findByName(name);
    }

    @Override
    public void insertActivityInTime(ActivityInTime activityInTime) {
        activityInTimeRepo.save(activityInTime);
    }

    @Override
    public Group getGroupById(Long id) {
        return groupRepo.getOne(id);
    }

    @Override
    public List<Activity> getActivitiesAfterTime(LocalDateTime time) {
        Set<Activity> activities = new HashSet<>();

        List<ActivityInTime> activitiesInTime = activityInTimeRepo.getAllByStartAfter(time);
        activitiesInTime.forEach(a -> activities.add(a.getActivity()));

        return new ArrayList<>(activities);
    }

    @Override
    public Animator getAnimator(String nickname) {
        return animatorRepo.getOne(nickname);
    }

    @Override
    public List<ActivityInTime> getActivitiesForGroup(Group group) {
        return activityInTimeRepo.getActivitiesForGroup(group);
    }

    @Override
    public void insertComment(Comment comment) {
        commentRepo.save(comment);
    }

    @Override
    public List<Comment> commentsByActivity(Activity activity) {
        return commentRepo.getAllByActivity(activity);
    }

    @Override
    public List<Comment> searchCommentsByActivity(String s) {
        return commentRepo.findAllByActivityIsLike(s);
    }

    @Override
    public List<Comment> searchCommentsByGroup(String s) {
        List<Comment> comments = new ArrayList<>();
        List<Group> groups = groupRepo.findAllByNameIsLike(s);
        for (Group group : groups) {
            List<Particiant> particiants = group.getParticiants();
            for (Particiant particiant : particiants) {
                comments.addAll(commentRepo.findAllByUserIsLike(particiant));
            }
        }

        return comments;
    }

    @Override
    public List<Comment> searchCommentsByNickname(String s) {
        return commentRepo.findAllByUserIsLike(s);
    }

    @Override
    public Group getGroupByName(String name) {
        return groupRepo.findAllByName(name);
    }

    @Override
    public Particiant getParticipantByName(String  nickname) {
        return participantRepo.getOne(nickname);
    }

    @Override
    public void updateParticipantGroup(Group group, Particiant particiant) {
        participantRepo.udpateParticipantGroup(group, particiant);
    }

    @Override
    public ActivityInTime activityInTimeByName(String name) {
        return activityInTimeRepo.findByName(name);
    }
}

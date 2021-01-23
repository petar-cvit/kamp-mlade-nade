package fer.ppj.controllers;

import fer.ppj.model.*;
import fer.ppj.service.IService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
public class ActivityController {

    @Autowired
    IService service;

    @GetMapping("/addActivity")
    public String addActivity() {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("newActivity");

        return "newActivity";
    }

    @PostMapping("/addActivity")
    public ModelAndView addActivity(@RequestParam String name, @RequestParam String desc, @RequestParam int duration, @RequestParam String activityType, @RequestParam Integer numberOfGroups) {
        ModelAndView mv = new ModelAndView();

        if (name == null || desc == null) {
            mv.setViewName("redirect:/error");
        }

        Activity activity = new Activity(name, desc, duration, ActivityType.valueOf(activityType), numberOfGroups);

        service.insertActivity(activity);

        mv.setViewName("redirect:/");

        return mv;
    }

    @GetMapping("/addActivitiesInTime")
    public ModelAndView addActivitiesInTime(HttpSession session) {
        Person user = (Person) session.getAttribute("user");

        if (!(user instanceof Organizator)) {
            return new ModelAndView("/error");
        }

        ModelAndView mv = new ModelAndView("addActivitiesInTime");

        if (session.getAttribute("error") != null) {
            mv.addObject("error", (String) session.getAttribute("error"));
            session.removeAttribute("error");
        }

        //last condition
        List<List<String>> missingActivities = new ArrayList<>();
        List<Group> allGroups = service.getGroups();
        List<Activity> allActivities = service.allActivities();



        for(int i = 0, n = allGroups.size(); i < n; i++){
            List<String> missingActivitiesForIthGroup = new ArrayList<>();
            for(Activity a : allActivities){
                List<Activity> ithGroupActivities = new ArrayList<>();
                allGroups.get(i).getActivities().stream().forEach(activityInTime -> ithGroupActivities.add(activityInTime.getActivity()));
                if(!ithGroupActivities.contains(a)){
                    missingActivitiesForIthGroup.add(a.getName());
                }
            }
            missingActivities.add(missingActivitiesForIthGroup);
        }
        session.setAttribute("missingActivities", filterString(missingActivities));
        //

        mv.addObject("missingActivities", filterString(missingActivities));

        mv.addObject("animators", service.getAnimators());
        mv.addObject("groups", service.getGroups());
        mv.addObject("activities", filterAct(service.filteredActivities()));
        mv.addObject("activitiesInTime", filter(service.allActivitiesInTime()));

        return mv;
    }

    @PostMapping("/addActivitiesInTime")
    public ModelAndView addActivityInTime(HttpSession session,
                                          @RequestParam String activityName,
                                          @RequestParam String start,
                                          @RequestParam(required = false) String[] selectedGroups,
                                          @RequestParam(required = false) String[] selectedAnimators) throws ParseException {
        Person user = (Person) session.getAttribute("user");

        if (!(user instanceof Organizator)) {
            return new ModelAndView("/error");
        }

        if (selectedAnimators == null) {
            session.setAttribute("error", "Nije pridružen nijedan animator.");
            return new ModelAndView("redirect:/addActivitiesInTime");
        }
        if (selectedGroups == null) {
            session.setAttribute("error", "Nije pridružena nijedna grupa.");
            return new ModelAndView("redirect:/addActivitiesInTime");
        }

        Activity activity = service.getActivityByName(activityName);
        if (activity == null) {
            return new ModelAndView("/error");
        }

        if (!validateNumberOfGroups(activity, selectedGroups)) {
            int numberOfGroups = getNumberOfGroupsByType(activity);
            if (activity.getType().equals(ActivityType.MAX_N)){
                session.setAttribute("error", "Maksimalan broj grupa za ovu aktivnost je "+numberOfGroups+", no pridruženo ih je više.");
            }else{
                session.setAttribute("error", "Broj grupa koji treba biti pridružen ovoj aktivnosti : "+numberOfGroups);
            }

            return new ModelAndView("redirect:/addActivitiesInTime");
        }

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm");
        Date date = (Date)formatter.parse(start);

        List<Group> groups = new ArrayList<>();
        for (String groupID : selectedGroups) {
            groups.add(service.getGroupById(Long.parseLong(groupID)));
        }

        if (groupHadActivity(groups, activity)) {
            session.setAttribute("error", "Neka od pridruženih grupa već ima ovu aktivnost u rasporedu.");
            return new ModelAndView("redirect:/addActivitiesInTime");
        }

        List<Animator> animators = new ArrayList<>();
        for (String animatorNick : selectedAnimators) {
            animators.add(service.getAnimator(animatorNick));
        }

        if (activityHasConflict(activity, date)){
            session.setAttribute("error", "U zadanom vremenu postoji konflikt za animatora ili aktivnost.");
            return new ModelAndView("redirect:/addActivitiesInTime");
        }

        if (groupHasConflict(activity, date, groups)){
            session.setAttribute("error", "Neka od pridruženih grupa već ima aktivnost za zadani vremenski period.");
            return new ModelAndView("redirect:/addActivitiesInTime");
        }

        ActivityInTime activityInTime = new ActivityInTime(activityName, date, activity, groups, animators);

        groups.forEach(g -> g.getActivities().add(activityInTime));
        animators.forEach(a -> a.getActivities().add(activityInTime));

        service.insertActivityInTime(activityInTime);

        return new ModelAndView("redirect:/addActivitiesInTime");
    }

    private List<List<String>> filterString(List<List<String>> strings) {
        List<List<String>> filtered = new ArrayList<>();
        List<String> names = Arrays.asList("campStart", "campEnd", "animatorStart",
                "animatorEnd", "participantStart", "participantEnd",
                "doručak", "ručak", "večera");

        for (List<String> l : strings) {
            List<String> filteredAct = new ArrayList<>();
            for (String s : l) {
                if (!names.contains(s)) {
                    filteredAct.add(s);
                }
            }
            filtered.add(filteredAct);
        }

        return filtered;
    }

    private List<ActivityInTime> filter(List<ActivityInTime> activityInTimes) {
        List<ActivityInTime> filtered = new ArrayList<>();

        List<String> names = Arrays.asList("campStart", "campEnd", "animatorStart",
                "animatorEnd", "participantStart", "participantEnd",
                "doručak", "ručak", "večera");
        for(ActivityInTime a : activityInTimes){
            if(!names.contains(a.getActivity().getName())){
                filtered.add(a);
            }
        }
        return filtered;
    }

    private List<Activity> filterAct(List<Activity> activity) {
        List<Activity> filtered = new ArrayList<>();

        List<String> names = Arrays.asList("campStart", "campEnd", "animatorStart",
                "animatorEnd", "participantStart", "participantEnd",
                "doručak", "ručak", "večera");
        for(Activity a : activity){
            if(!names.contains(a.getName())){
                filtered.add(a);
            }
        }
        return filtered;
    }

    private boolean groupHadActivity(List<Group> groups, Activity activity) {
        for (Group group : groups) {
            for (ActivityInTime activityInTime : group.getActivities()) {
                if (activityInTime.getActivity().equals(activity)) {
                    return true;
                }
            }
        }

        return false;
    }

    public boolean activityHasConflict(Activity activity, Date date) {

        return activityGroupTypeConflict(activity, date, service.getGroups(), true);
    }

    public boolean groupHasConflict(Activity activity, Date date, List<Group> groups) {

        return activityGroupTypeConflict(activity, date, groups, false);

    }

    public boolean activityGroupTypeConflict(Activity activity, Date date, List<Group> groups, boolean type) {

        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.MINUTE, activity.getDurationInMinutes());
        Date end = cal.getTime();

        for (Group group : groups){

            for (ActivityInTime activityInTime : group.getActivities()) {

                Date activityStart = activityInTime.getStart();

                Calendar calendar = Calendar.getInstance();
                calendar.setTime(activityStart);
                calendar.add(Calendar.MINUTE, activityInTime.getActivity().getDurationInMinutes());

                Date activityEnd = calendar.getTime();

                if (activityEnd.before(date) || activityStart.after(end)) {
                    continue;
                }

                if (type){
                    if (activityInTime.getActivity().getType().equals(activity.getType())){
                        return true;
                    }
                }else{
                    return true;
                }
            }
        }
        return false;
    }

    public int getNumberOfGroupsByType(Activity activity) {
        switch (activity.getType()) {
            case ONE:
                return 1;
            case N:
            case MAX_N:
                return  activity.getNumberOfGroups();
            case ALL:
                return service.getGroups().size();
            default:
                throw new IllegalArgumentException();
        }
    }

    public boolean validateNumberOfGroups(Activity activity, String[] groups) {
        switch (activity.getType()) {
            case ONE:
                return groups.length == 1;
            case N:
                return groups.length == activity.getNumberOfGroups();
            case ALL:
                return groups.length == service.getGroups().size();
            case MAX_N:
                return groups.length <= activity.getNumberOfGroups();
            default:
                throw new IllegalArgumentException();
        }
    }
}

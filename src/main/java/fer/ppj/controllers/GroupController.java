package fer.ppj.controllers;

import fer.ppj.model.*;
import fer.ppj.service.IService;
import net.bytebuddy.utility.RandomString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;
import java.util.*;
import java.util.concurrent.TimeUnit;

@Controller
public class GroupController {

    @Autowired
    IService service;

    @GetMapping("/createGroups")
    public ModelAndView createGroups(HttpSession session) {
        if (!(session.getAttribute("user") instanceof Organizator)) {
            return new ModelAndView("redirect:/error");
        }

        List<Group> groups = service.getGroups();

        ModelAndView mv = new ModelAndView("createGroups");
        mv.addObject("groups", groups);
        mv.addObject("participants", service.getParticipants());
        return mv;
    }

    @PostMapping("/divideGroups")
    public ModelAndView divideGroups(HttpSession session, @RequestParam int numberOfGroups) {
        if (!(session.getAttribute("user") instanceof Organizator)) {
            return new ModelAndView("redirect:/error");
        }

        List<Group> groups = service.getGroups();
        if (groups.size() != 0) {
            return new ModelAndView("redirect:/error");
        }

        List<Particiant> particiants = service.getParticipants();

        Iterator<Particiant> iterator = particiants.listIterator();

        for (int i = 0;i < numberOfGroups - 1;i++) {
            Group group = new Group(RandomString.make(10));
            for (int j = 0;j < (int) (Math.floor(particiants.size() / (numberOfGroups - 1)));j++) {
                if (iterator.hasNext()) {
                    iterator.next().setCampGroup(group);
                }
            }

            service.insertGroup(group);
        }

        Group group = new Group(RandomString.make(10));
        while(iterator.hasNext()) {
            iterator.next().setCampGroup(group);
        }
        service.insertGroup(group);

        meals(numberOfGroups);

        return new ModelAndView("redirect:/createGroups");
    }

    @Transactional
    @PostMapping("/changeGroup")
    public ModelAndView changeParticipantGroup(HttpSession session, @RequestParam String nickname, @RequestParam String groupName) {
        Person user = (Person) session.getAttribute("user");

        if (!(user instanceof Organizator)) {
            return new ModelAndView("/error");
        }

        Group group = service.getGroupByName(groupName);
        Particiant participant = service.getParticipantByName(nickname);

        service.updateParticipantGroup(group, participant);

        return new ModelAndView("redirect:/createGroups");
    }

    private void meals(int numberOfGroups) {
        List<Group> groups = service.getGroups();
        List<Animator> animators = service.getAnimators();

        Activity breakfast = new Activity("doručak", "obrok", 60, ActivityType.N, numberOfGroups);
        Activity lunch = new Activity("ručak", "obrok", 60, ActivityType.N, numberOfGroups);
        Activity dinner = new Activity("večera", "obrok", 60, ActivityType.N, numberOfGroups);
        service.insertActivity(breakfast);
        service.insertActivity(lunch);
        service.insertActivity(dinner);

        ActivityInTime start = service.activityInTimeByName("campStart");
        ActivityInTime end = service.activityInTimeByName("campEnd");

        long diffInMillies = Math.abs(start.getStart().getTime() - end.getStart().getTime());
        long diff = TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);

        Calendar c = Calendar.getInstance();
        c.setTime(start.getStart());
        //c.setTimeZone(TimeZone.getTimeZone("CET"));
        c.set(Calendar.HOUR, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);

        for (int i = 0;i < diff;i++) {
            c.set(Calendar.AM_PM, Calendar.AM);
            c.add(Calendar.DAY_OF_MONTH, 1);
            c.set(Calendar.HOUR, 8);
            ActivityInTime breakfastTime = new ActivityInTime("doručak", c.getTime(), breakfast, groups, animators);
            animators.forEach(a -> a.getActivities().add(breakfastTime));
            groups.forEach(g -> g.getActivities().add(breakfastTime));
            service.insertActivityInTime(breakfastTime);

            c.set(Calendar.HOUR, 12);
            ActivityInTime lunchTime = new ActivityInTime("ručak", c.getTime(), lunch, groups, animators);
            animators.forEach(a -> a.getActivities().add(lunchTime));
            groups.forEach(g -> g.getActivities().add(lunchTime));
            service.insertActivityInTime(lunchTime);

            c.set(Calendar.AM_PM, Calendar.PM);
            c.set(Calendar.HOUR, 6);
            ActivityInTime dinnerTime = new ActivityInTime("večera", c.getTime(), dinner, groups, animators);
            animators.forEach(a -> a.getActivities().add(dinnerTime));
            groups.forEach(g -> g.getActivities().add(dinnerTime));
            service.insertActivityInTime(dinnerTime);
        }
    }
}

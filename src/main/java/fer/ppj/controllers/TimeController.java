package fer.ppj.controllers;

import fer.ppj.model.Activity;
import fer.ppj.model.ActivityInTime;
import fer.ppj.model.ActivityType;
import fer.ppj.model.Organizator;
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
public class TimeController {

    @Autowired
    IService service;

    @GetMapping("/setTimer")
    public ModelAndView setTimer() {
        ModelAndView mv = new ModelAndView();
        Map<String, Boolean> activities = new TreeMap<>();
        activities.put("campStart", false);
        activities.put("campEnd", false);
        activities.put("animatorStart", false);
        activities.put("animatorEnd", false);
        activities.put("participantStart", false);
        activities.put("participantEnd", false);

        List<ActivityInTime> activitiesInTime = service.allActivitiesInTime();

        for (ActivityInTime activity : activitiesInTime) {
            if (activities.containsKey(activity.getName())) {
                activities.put(activity.getName(), true);
            }
        }

        mv.addObject("activities", activities);
        mv.setViewName("timer");
        return mv;
    }

    @PostMapping("/setTimer")
    public ModelAndView setTimer(HttpSession session, @RequestParam(required = false) String campStart, @RequestParam(required = false) String campEnd,
                                 @RequestParam(required = false) String animatorStart, @RequestParam(required = false) String animatorEnd,
                                 @RequestParam(required = false) String participantStart, @RequestParam(required = false) String participantEnd) throws ParseException {
        if (!(session.getAttribute("user") instanceof Organizator)) {
            return new ModelAndView("redirect:/error");
        }

        if (campStart != null) createActivity("campStart", campStart);
        if (campEnd != null) createActivity("campEnd", campEnd);
        if (animatorStart != null) createActivity("animatorStart", animatorStart);
        if (animatorEnd != null) createActivity("animatorEnd", animatorEnd);
        if (participantStart != null) createActivity("participantStart", participantStart);
        if (participantEnd != null) createActivity("participantEnd", participantEnd);

        return new ModelAndView("redirect:/setTimer");
    }

    private void createActivity(String name, String time) throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm");
        Date date = (Date)formatter.parse(time);
        Activity act = new Activity(name, "", 0, ActivityType.ALL, service.getGroups().size());
        ActivityInTime activityInTime = new ActivityInTime(name, date, act, service.getGroups(), new ArrayList<>());

        service.insertActivity(act);
        service.insertActivityInTime(activityInTime);
    }
}

package fer.ppj.controllers;

import fer.ppj.model.*;
import fer.ppj.service.EmailService;
import fer.ppj.service.IService;
import net.bytebuddy.utility.RandomString;
import org.apache.tomcat.jni.Time;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Controller
public class MainController {

    @Autowired
    IService service;

    @Transactional
    @GetMapping("/")
    public ModelAndView home(HttpSession session){
        ModelAndView mv = new ModelAndView("index");
        mv.addObject("activity", service.filteredActivities());
        List<ActivityInTime> activityInTimes = service.allActivitiesInTime();
        for(ActivityInTime a : activityInTimes){
            if (a.getName().equals("campStart"))  {
                mv.addObject("kampActivity", a);
                break;
            }
        }

        mv.addObject("campStart", service.activityInTimeByName("campStart"));
        mv.addObject("campEnd", service.activityInTimeByName("campEnd"));

        Date now = new Date(System.currentTimeMillis());
        if(service.activityInTimeByName("campStart") != null &&
                service.activityInTimeByName("campEnd") != null) {
            if(session.getAttribute("user") instanceof Particiant || session.getAttribute("user") instanceof Animator) {

                if (service.activityInTimeByName("campEnd").getStart().compareTo(now) <= 0) {
                    ModelAndView mv2 = new ModelAndView("redirect:/campEnd");
                    session.setAttribute("activityName", "campStart");
                    //mv2.addObject("activityName", service.activityInTimeByName("campStart"));
                    return mv2;

                } else if (service.activityInTimeByName("campStart").getStart().compareTo(now) <= 0) {

                    if (session.getAttribute("user") instanceof Particiant) {
                        Particiant p = (Particiant) session.getAttribute("user");
                        List<ActivityInTime> activities = service.getActInTime(p);
                        actInTimeComparator(mv, activities);
                    } else if (session.getAttribute("user") instanceof Animator) {
                        Animator a = (Animator) session.getAttribute("user");
                        List<ActivityInTime> activities = service.getActInTimeAnimator(a);
                        actInTimeComparator(mv, activities);
                    }
                }
            }
        }
        return mv;
    }

    private void actInTimeComparator(ModelAndView mv, List<ActivityInTime> activities) {
        activities.sort(new Comparator<ActivityInTime>() {
            @Override
            public int compare(ActivityInTime o1, ActivityInTime o2) {
                if(o1.getStart().after(o2.getStart())){
                    return 1;
                } else {
                    return -1;
                }
            }
        });
        mv.addObject("activities", activities);
    }

    @GetMapping("/error")
    public String getError(){
        return "error";
    }

    @GetMapping("/signUpOrganizer")
    public ModelAndView signUpOrganizerGet(HttpSession session) {
        if (!(session.getAttribute("user") instanceof Organizator)) {
            return new ModelAndView("/error");
        }
        return new ModelAndView("signUpOrganizer");
    }

    @PostMapping("/signUpOrganizer")
    public ModelAndView signUpOrganizerPost(HttpSession session, @RequestParam String username, @RequestParam String password) {
        if (!(session.getAttribute("user") instanceof Organizator)) {
            return new ModelAndView("/error");
        }

        Organizator organizator = new Organizator(username, getHash(password));

        try {
            service.insertOrganizator(organizator);
        } catch (Exception e) {
            ModelAndView mv = new ModelAndView("signUpOrganizer");
            mv.addObject("dberror", true);
        }

        return new ModelAndView("redirect:/");
    }

    public static String getHash(String s){
        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            md5.update(s.getBytes());
            BigInteger hash = new BigInteger(1, md5.digest());
            return hash.toString(16);
        }catch(NoSuchAlgorithmException ex){
            throw new RuntimeException();
        }
    }
}
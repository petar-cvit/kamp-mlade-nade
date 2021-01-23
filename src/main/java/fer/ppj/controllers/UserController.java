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

@Controller
public class UserController {
    @Autowired
    IService service;

    @GetMapping("/signUp")
    public String getSignUp(){
        return "signUp";
    }

    @GetMapping("/login")
    public String getLoginAnimator(){
        return "login";
    }

    @GetMapping("/logout")
    public ModelAndView logout(HttpSession session) {
        ModelAndView mv = new ModelAndView("redirect:/");
        session.invalidate();

        return mv;
    }

    @GetMapping("/signUpAnimator")
    public ModelAndView getSignUpAnimator() {
        ModelAndView mv = new ModelAndView("registerAnimator");
        if (!validateSignUpDate("animator")) {
            mv.addObject("disabled", "disabled");
        }
        return mv;
    }

    @GetMapping("/signUpParticipant")
    public ModelAndView getSignUpParticipant() {
        ModelAndView mv = new ModelAndView("registerParticipant");
        if (!validateSignUpDate("participant")) {
            mv.addObject("disabled", "disabled");
        }
        return mv;
    }

    @PostMapping("/signUpAnimator")
    public ModelAndView signUpAnimator(@RequestParam String name, @RequestParam String surname, @RequestParam String email,
                                       @RequestParam String birthday, @RequestParam String motivation, @RequestParam String contact) throws ParseException {
        ModelAndView mv = new ModelAndView();

        if (name == null || surname == null || email == null || birthday == null) {
            mv.setViewName("registerAnimator");
            mv.addObject("error", true);

            return mv;
        }

        if (!validateEmail(email)) {
            mv.setViewName("registerAnimator");
            mv.addObject("validEmail", true);

            return mv;
        }

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
        Date date = formatter.parse(birthday);
        String nickname = uniqueNickname(name, surname);

        Animator a = new Animator(nickname, name, surname, email, date, contact, motivation, getHash(nickname));

        service.insertAnimator(a);

        mv.setViewName("redirect:/");
        mv.addObject("animators", service.getAnimators());

        return mv;
    }

    @PostMapping("/signUpParticipant")
    public ModelAndView signUpParticipant(@RequestParam String name, @RequestParam String surname, @RequestParam String email, @RequestParam String contact,
                                          @RequestParam String birthday, @RequestParam(required = false) String custodian, @RequestParam(required = false) String underage, @RequestParam String motivation) throws ParseException {
        ModelAndView mv = new ModelAndView();

        if (name == null || surname == null || email == null || birthday == null || contact == null) {
            mv.setViewName("registerParticipant");
            mv.addObject("error", true);

            return mv;
        }

        if (!validateEmail(email)) {
            mv.setViewName("registerParticipant");
            mv.addObject("validEmail", true);

            return mv;
        }

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
        Date date = formatter.parse(birthday);
        String nickname = uniqueNickname(name, surname);

        Particiant p = new Particiant(nickname, name, surname, email, contact, custodian, underage != null, motivation, date, getHash(nickname));
        service.insertParticipant(p);

        mv.setViewName("redirect:/");
        mv.addObject("participants", service.getParticipants());

        return mv;
    }

    @GetMapping("/initial")
    public ModelAndView initial(@RequestParam String nickHash, HttpSession session) {
        ModelAndView mv = new ModelAndView();

        Person person = service.getByNickHash(nickHash);
        if (person == null) {
            mv.setViewName("error");
            return mv;
        }

        session.setAttribute("user", person);

        mv.setViewName("initialLogin");

        return mv;
    }

    @Transactional
    @PostMapping("/setPassword")
    public ModelAndView setPassword(@RequestParam String password, @RequestParam String confirmPass, HttpSession session) {
        ModelAndView mv = new ModelAndView();

        if (!password.equals(confirmPass)) {
            mv.setViewName("initialLogin");
            mv.addObject("error", true);

            return mv;
        }

        Person p = (Person) session.getAttribute("user");
        service.updatePassword(p.getNickname(), getHash(password));

        session.setAttribute("user", p);

        mv.setViewName("redirect:/");

        return mv;
    }

    @PostMapping("/login")
    public ModelAndView loginAnim(@RequestParam String username, @RequestParam String password, HttpSession session) {
        if(username == null || password == null){
            ModelAndView mv = new ModelAndView();
            mv.setViewName("login");
            mv.addObject("error", true);
            return mv;
        }
        var user = service.getByNickname(username);

        if(user == null){
            ModelAndView mv = new ModelAndView();
            mv.setViewName("login");
            mv.addObject("invalidUsername", true);
            return mv;
        }

        if (!user.getAccepted()) {
            ModelAndView mv = new ModelAndView();
            mv.setViewName("login");
            mv.addObject("notAccepted", true);
            return mv;
        }

        if(user.getPassword().equals(getHash(password))){
            ModelAndView mv = new ModelAndView();
            var animators = service.getAnimators();
            mv.addObject("animators", animators);
            mv.setViewName("redirect:/");
            session.setAttribute("user", user);
            return mv;
        } else {
            ModelAndView mv = new ModelAndView();
            mv.setViewName("login");
            mv.addObject("invalidPassword", true);
            return mv;
        }
    }

    @GetMapping("/acceptParticipants")
    public ModelAndView acceptParticipants(HttpSession session) {
        if (!(session.getAttribute("user") instanceof Organizator)) {
            return new ModelAndView("/error");
        }

        ModelAndView mv = new ModelAndView();
        mv.setViewName("organizator");
        mv.addObject("notAccepted", service.getNotAccpeted());

        return mv;
    }

    @Transactional
    @GetMapping("/schedule")
    public ModelAndView getSchedule(HttpSession session){
        if (session.getAttribute("user") == null) {
            return new ModelAndView("redirect:/error");
        }

        ModelAndView mv = new ModelAndView();
        if (session.getAttribute("user") instanceof Particiant) {
            Particiant particiant = (Particiant) session.getAttribute("user");

            Set<Activity> activities = new HashSet<>();
            service.getActInTime(particiant).forEach(a -> activities.add(a.getActivity()));

            service.getActivitiesForGroup(particiant.getCampGroup());
            mv.addObject("userActivities", filterDone(service.getActivitiesForGroup(particiant.getCampGroup())));

            Set<Animator> animators = new HashSet<>(service.getAnimatorsForParticipant(particiant));

            List<Animator> animatorsList = new ArrayList<>(animators);
            animatorsList.sort(Comparator.comparing(Animator::getNickname));

            mv.addObject("participantAnimators", animatorsList);
            mv.addObject("participantGroup", particiant.getCampGroup());
            mv.setViewName("schedule");

        } else if (session.getAttribute("user") instanceof Animator) {
            Animator animator = (Animator) session.getAttribute("user");

            Set<Activity> activities = new HashSet<>();
            service.getActInTimeAnimator(animator).forEach(a -> activities.add(a.getActivity()));

            mv.addObject("userActivities", filterDone(service.getActInTimeAnimator(animator)));

            var allGroups = service.getGroups();
            var allAnimators = service.getAnimators();

            mv.addObject("allGroups", allGroups);
            allAnimators.remove(animator);
            mv.addObject("allAnimators", allAnimators);
            mv.setViewName("schedule");
        }
        return mv;
    }

    private List<Activity> filterDone(List<ActivityInTime> activitiesInTime) {
        activitiesInTime.sort(new Comparator<ActivityInTime>() {
            @Override
            public int compare(ActivityInTime o1, ActivityInTime o2) {
                if(o1.getStart().after(o2.getStart())){
                    return 1;
                } else {
                    return -1;
                }
            }
        });

        List<Activity> activities = new ArrayList<>();

        Date now = java.util.Date.from(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant());
        for (ActivityInTime activityInTime : activitiesInTime) {
            if (activityInTime.getStart().before(now)) {
                activities.add(activityInTime.getActivity());
            }
        }

        return activities;
    }

    private boolean validateSignUpDate(String role) {
        ActivityInTime start = new ActivityInTime();
        ActivityInTime end = new ActivityInTime();

        if (role.equals("animator")) {
            start = service.activityInTimeByName("animatorStart");
            end = service.activityInTimeByName("animatorEnd");
        }
        if (role.equals("participant")) {
            start = service.activityInTimeByName("participantStart");
            end = service.activityInTimeByName("participantEnd");
        }

        if (start == null || end == null) {
            return false;
        }

        Date now = java.util.Date.from(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant());

        return now.after(start.getStart()) && now.before(end.getStart());
    }

    public boolean validateEmail(String email) {
        Pattern pattern = Pattern.compile("^(.+)@(.+)$");
        Matcher matcher = pattern.matcher(email);

        return matcher.matches();
    }

    private static HashMap<String,String> croatianToEnglishChars = new HashMap<String,String>() {{
        put("č","c");
        put("ć","c");
        put("đ","d");
        put("ž","z");
        put("š","s");
    }};

    private String uniqueNickname(String name, String surname) {
        String nickname = name.toLowerCase().charAt(0) + surname.toLowerCase();
        int len = nickname.length();
        int index = 1;

        Person p = service.getByNickname(nickname);
        while (p != null) {
            nickname = nickname.substring(0, len) + index++;
            p = service.getByNickname(nickname);
        }
        StringBuilder sb = new StringBuilder();
        for(int i = 0, n = nickname.length(); i < n; i++){
            String currentChar = Character.toString(nickname.charAt(i));
            if(croatianToEnglishChars.containsKey(currentChar)){
                sb.append(croatianToEnglishChars.get(currentChar));
            }
            else {
                sb.append(currentChar);
            }
        }

        nickname = sb.toString();

        return nickname;
    }

    private static String getHash(String s){
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

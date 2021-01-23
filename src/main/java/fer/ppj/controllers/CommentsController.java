package fer.ppj.controllers;

import fer.ppj.model.Activity;
import fer.ppj.model.Comment;
import fer.ppj.model.Person;
import fer.ppj.service.IService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

@Controller
public class CommentsController {

    @Autowired
    IService service;

    @GetMapping("/review")
    public ModelAndView reviewActivity(HttpSession session, @RequestParam String name) {
        ModelAndView mv = new ModelAndView();

        Activity activity = service.getActivityByName(name);
        session.setAttribute("activityName", name);

        mv.addObject("activity", activity);
        return new ModelAndView("review");
    }

    @PostMapping("/review")
    public ModelAndView storeReview(HttpSession session, @RequestParam String commentText, @RequestParam Integer mark) {
        Person user = (Person) session.getAttribute("user");
        if (user == null) {
            return new ModelAndView("redirect:/error");
        }

        Activity activity = service.getActivityByName((String) session.getAttribute("activityName"));
        if (activity == null) {
            return new ModelAndView("redirect:/error");
        }

        session.removeAttribute("activityName");

        Comment comment = new Comment(commentText, mark, user, activity);
        service.insertComment(comment);
        return new ModelAndView("redirect:/");
    }

    @GetMapping("/searchComments")
    public ModelAndView searchComments(HttpSession session) {
        List<Activity> activities = service.allActivities();
        Map<Activity, List<Comment>> commentsByActivities = new TreeMap<>((a1, a2) -> a1.getName().compareTo(a2.getName()));

        activities.forEach(a -> {
            List<Comment> comments = service.commentsByActivity(a);
            comments.sort(Comparator.comparing(c -> c.getUser().getNickname()));
            commentsByActivities.put(a, comments);
        });

        ModelAndView mv = new ModelAndView("searchComments");
        mv.addObject("commentsByActivities", commentsByActivities);
        mv.addObject("searchedComments", session.getAttribute("searchedComments"));
        session.removeAttribute("searchedComments");

        return mv;
    }

    @PostMapping("/searchComments")
    public ModelAndView getSearchedComments(HttpSession session, @RequestParam String category, @RequestParam String searchBy) {
        session.setAttribute("searchedComments", getSearched(category, searchBy));
        return new ModelAndView("redirect:/searchComments");
    }

    @GetMapping("/default")
    public String getDefault(){
        return "default";
    }

    @GetMapping("/campEnd")
    public String getCampEnd(){
        return "campEnd";
    }

    @PostMapping("/campEnd")
    public ModelAndView storeCampReview(HttpSession session, @RequestParam String commentText, @RequestParam Integer mark) {
        Person user = (Person) session.getAttribute("user");
        if (user == null) {
            return new ModelAndView("redirect:/error");
        }

        Activity activity = service.getActivityByName((String) session.getAttribute("activityName"));
        if (activity == null) {
            return new ModelAndView("redirect:/error");
        }

        session.removeAttribute("activityName");

        Comment comment = new Comment(commentText, mark, user, activity);
        service.insertComment(comment);
        return new ModelAndView("redirect:/default");
    }

    public List<Comment> getSearched(String category, String searchBy) {
        switch (category){
            case "user":
                return service.searchCommentsByNickname(searchBy);
            case "group":
                return service.searchCommentsByGroup(searchBy);
            case "activity":
                return service.searchCommentsByActivity(searchBy);
            default:
                throw new IllegalArgumentException("fix me");
        }
    }
}

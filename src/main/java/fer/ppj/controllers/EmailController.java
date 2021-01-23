package fer.ppj.controllers;

import fer.ppj.model.Organizator;
import fer.ppj.model.Person;
import fer.ppj.service.EmailService;
import fer.ppj.service.IService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;

@Controller
public class EmailController {

    @Autowired
    IService service;

    @Autowired
    EmailService emailService;

    @PostMapping("/delete")
    @Transactional
    public ModelAndView deleteParticipants(HttpSession session, @RequestParam String nickname) {
        if (!(session.getAttribute("user") instanceof Organizator)) {
            return new ModelAndView("/error");
        }

        Person person = service.getByNickname(nickname);
        emailService.reject(person.getEmail());
        service.deletePerson(nickname);

        return new ModelAndView("redirect:/acceptParticipants");
    }

    @PostMapping("/accept")
    @Transactional
    public ModelAndView acceptParticipants(HttpSession session, @RequestParam String nickname) {
        if (!(session.getAttribute("user") instanceof Organizator)) {
            return new ModelAndView("/error");
        }

        Person person = service.getByNickname(nickname);
        emailService.accept(person.getEmail(), person.getNickHash());
        service.acceptPerson(person.getNickname());

        return new ModelAndView("redirect:/acceptParticipants");
    }
}

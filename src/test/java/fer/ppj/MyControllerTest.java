package fer.ppj;

import fer.ppj.controllers.ActivityController;
import fer.ppj.controllers.CommentsController;
import fer.ppj.controllers.MainController;
import fer.ppj.controllers.UserController;
import fer.ppj.model.Activity;
import fer.ppj.model.ActivityInTime;
import fer.ppj.model.ActivityType;
import fer.ppj.model.Animator;
import org.junit.Test;
import org.springframework.web.servlet.ModelAndView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MyControllerTest {

    @Test(expected = IllegalArgumentException.class)
    public void testGetSearchedThrowsIllegalArgumentException(){
        String category = "neka kategorija";
        String search = "search";
        CommentsController controller = new CommentsController();
        controller.getSearched(category, search);
    }

    @Test
    public void testEmailValidation(){
        String notEmail = "asfafsgsdga";
        String email = "ivo.peric@gmail.com";
        UserController controller = new UserController();
        assert  controller.validateEmail(notEmail) == false;
        assert controller.validateEmail(email) == true;
    }

    @Test
    public void testHashingFunction(){
        String str = "Abeceda";
        String strHash = "3d356b73b7e08f0190ee6071c5b9b75f";
        MainController controller = new MainController();
        assert  strHash.equals(controller.getHash(str));
    }

    @Test
    public void testHashingFunction2(){
        String str = "Zivotinje";
        String strHash = "3d356b73b7e08f0190ee6071c5b9b75f";
        MainController controller = new MainController();
        assert  !strHash.equals(controller.getHash(str));
    }

    @Test
    public void testActivityComparator(){
        String sDate1="31/12/2020";
        String sDate2="31/12/2021";
        try {
            Date date1=new SimpleDateFormat("dd/MM/yyyy").parse(sDate1);
            Date date2=new SimpleDateFormat("dd/MM/yyyy").parse(sDate2);
            ActivityInTime act1 = new ActivityInTime("Odbojka", date1, null, null, null);
            ActivityInTime act2 = new ActivityInTime("Nogomet", date2, null, null, null);

            assert !act1.getStart().after(act2.getStart());
            assert act1.getStart().before(act2.getStart());
        }catch(ParseException e){
        }
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGroupNumberValidator(){
        Activity act = new Activity("", "", 0, fer.ppj.model.ActivityType.valueOf("la"), 0);
        ActivityController controller = new ActivityController();
        controller.validateNumberOfGroups(act, new String[5]);
    }
}

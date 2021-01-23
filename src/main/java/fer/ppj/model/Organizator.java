package fer.ppj.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Date;

@Entity
public class Organizator extends Person{

    public Organizator(String nickname, String password) {
        super(nickname, null, null, null, null, null, null, null);
        super.setPassword(password);
        super.setAccepted(true);
    }

    public Organizator() {
    }
}

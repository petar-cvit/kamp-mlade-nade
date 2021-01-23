package fer.ppj.repos;

import fer.ppj.model.ActivityInTime;
import fer.ppj.model.Group;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface GroupRepo extends JpaRepository<Group, Long> {
    List<Group> findAllByNameIsLike(String s);

    Group findAllByName(String s);
}

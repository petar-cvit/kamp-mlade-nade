package fer.ppj.repos;

import fer.ppj.model.ActivityInTime;
import fer.ppj.model.Animator;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AnimatorRepo extends JpaRepository<Animator, String> {
    List<Animator> getAnimatorByActivitiesContaining(ActivityInTime activityInTime);
}

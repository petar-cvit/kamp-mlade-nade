package fer.ppj.repos;

import fer.ppj.model.ActivityInTime;
import fer.ppj.model.Animator;
import fer.ppj.model.Group;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;

public interface ActivityInTimeRepo extends JpaRepository<ActivityInTime, Long> {
    @Query(value = "select count(a) from ActivityInTime a where ?1 member of a.groups")
    long countActivitiesByGroup(Group group);

    List<ActivityInTime> getAllByAnimators(List<Animator> animators);

    List<ActivityInTime> getAllByStartAfter(LocalDateTime date);

    @Query(value = "select a from ActivityInTime a where ?1 member of a.groups")
    List<ActivityInTime> getActivitiesForGroup(Group group);

    ActivityInTime findByName(String name);
}
package fer.ppj.repos;

import fer.ppj.model.Activity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ActivityRepo extends JpaRepository<Activity, Long> {
    Activity findByName(String name);
}

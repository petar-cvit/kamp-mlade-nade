package fer.ppj.repos;

import fer.ppj.model.Group;
import fer.ppj.model.Particiant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface ParticipantRepo extends JpaRepository<Particiant, String> {
    @Modifying
    @Query("update Particiant p set p.campGroup = ?1 where p = ?2")
    void udpateParticipantGroup(Group group, Particiant particiant);
}

package fer.ppj.repos;

import fer.ppj.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PersonRepo extends JpaRepository<Person, String> {
    public List<Person> findAllByAcceptedFalse();
    public void deleteByNickname(String nickname);

    @Modifying
    @Query("update Person p set p.accepted = true where p.nickname = ?1")
    void acceptPerson(String nickname);

    public Person findAllByNickname(String nickname);

    public Person findAllByNickHash(String nickHash);

    @Modifying
    @Query("update Person p set p.password = ?2 where p.nickname = ?1")
    public void updatePassword(String nickname, String password);
}

package fer.ppj.repos;

import fer.ppj.model.Activity;
import fer.ppj.model.Comment;
import fer.ppj.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CommentRepo extends JpaRepository<Comment, Long> {
    List<Comment> getAllByActivity(Activity activity);

    @Query("select c from Comment c where c.user.nickname LIKE CONCAT('%', ?1 ,'%')")
    List<Comment> findAllByUserIsLike(String s);

    @Query("select c from Comment c where c.activity.name LIKE CONCAT('%', ?1 ,'%')")
    List<Comment> findAllByActivityIsLike(String s);

    List<Comment> findAllByUserIsLike(Person user);
}

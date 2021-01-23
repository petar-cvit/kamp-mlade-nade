package fer.ppj.repos;

import fer.ppj.model.Particiant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MyRepository extends JpaRepository<Particiant, Long>{
    List<Particiant> findAllByName(String name);
}

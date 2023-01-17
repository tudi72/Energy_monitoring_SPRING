package ro.tuc.ds2020.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ro.tuc.ds2020.entities.MyUser;

public interface MyUserRepository extends JpaRepository<MyUser,Long> {

    MyUser findMyUserById(Long id);
    MyUser findByUsername(String username);

    @Query(
            value = "SELECT * FROM my_user u " +
                    "WHERE  u.name " + 
                    "REGEXP ?1 " + 
                    "LIMIT 1",
            nativeQuery = true
    )
    MyUser findMyUserByName(String regex);


}

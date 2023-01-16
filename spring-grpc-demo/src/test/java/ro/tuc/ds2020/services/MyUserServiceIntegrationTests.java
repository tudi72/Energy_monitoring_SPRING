//package ro.tuc.ds2020.services;
//
//import org.junit.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.test.context.jdbc.Sql;
//import ro.tuc.ds2020.Ds2020TestConfig;
//import ro.tuc.ds2020.dtos.MyUserDTO;
//import ro.tuc.ds2020.dtos.MyUserDetailsDTO;
//import ro.tuc.ds2020.dtos.builders.MyUserBuilder;
//import ro.tuc.ds2020.entities.MyUser;
//
//import static org.springframework.test.util.AssertionErrors.assertEquals;
//
//import java.util.List;
//import java.util.UUID;
//
//@Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = "classpath:/test-sql/create.sql")
//@Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, scripts = "classpath:/test-sql/delete.sql")
//public class MyUserServiceIntegrationTests extends Ds2020TestConfig {
//
//    @Autowired
//    UserService userService;
//
//    @Test
//    public void testGetCorrect() {
//        List<MyUser> personDTOList = userService.getUsers();
//        System.out.print("we're here with size "  + personDTOList.size());
//        assertEquals("Test Insert Person", 1, personDTOList.size());
//    }
//
//    @Test
//    public void testInsertCorrectWithGetById() {
//        MyUserDetailsDTO p = new MyUserDetailsDTO(0l,"John", "Somewhere Else street", 22);
//         MyUser insertedUser = userService.saveUser(MyUser
//                .builder()
//                .address(p.getAddress())
//                .age(p.getAge())
//                .name(p.getName())
//                .build()
//        );
//        Long insertedID = insertedUser.getId();
//        MyUserDetailsDTO insertedPerson = new MyUserDetailsDTO(insertedID, p.getName(),p.getAddress(), p.getAge());
//        MyUserDetailsDTO fetchedPerson = MyUserBuilder.toMyUserDetailsDTO(userService.getUser(insertedID));
//
//        assertEquals("Test Inserted Person", insertedPerson, fetchedPerson);
//    }
//
//    @Test
//    public void testInsertCorrectWithGetAll() {
//        MyUserDetailsDTO p = new MyUserDetailsDTO(0l,"John", "Somewhere Else street", 22);
//        userService.saveUser(MyUserBuilder.toEntity(p));
//
//        List<MyUser> personDTOList = userService.getUsers();
//        assertEquals("Test Inserted Persons", 2, personDTOList.size());
//    }
//}

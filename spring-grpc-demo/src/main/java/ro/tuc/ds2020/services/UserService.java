package ro.tuc.ds2020.services;

import ro.tuc.ds2020.dtos.MyUserDTO;
import ro.tuc.ds2020.dtos.MyUserDetailsDTO;
import ro.tuc.ds2020.entities.Device;
import ro.tuc.ds2020.entities.MyUser;
import ro.tuc.ds2020.entities.Role;

import java.util.List;

/**
 * Interface with business use cases of any user
 */
public interface UserService {
    /**
     * Method for saving an user to the database
     * @param user
     * @return the created user
     */
    MyUser saveUser(MyUser user);

    /**
     * Method for saving a role to database
     * @param role
     * @return the role
     */
    Role saveRole(Role role);

    /**
     * Method for adding a role to an user such as ADMIN etc.
     * @param user
     * @param role
     * @return
     */
    boolean addRoleToUser(MyUser user, Role role);

    /**
     * Method for retrieving an user based on its username
     * @param name
     * @return the user object
     */
    MyUser getUser(String name);

    MyUser getUserByUsername(String username);

    /**
     * Method for retrieving an user based on its id
     * @param id
     * @return the user object
     */
    MyUser getUser(Long id);

    /**
     * Method for retrieving all users from database
     * @return list of user objects
     */
    List<MyUser> getUsers();

    /**
     * method for retrieving a role based on its name
     * @param roleName
     * @return
     */
    Role getRole(String roleName);

    /**
     * Method for updating the user with some new values
     * @param user
     * @param myUserDTO
     * @return
     */
    MyUser updateUser(MyUser user, MyUserDetailsDTO myUserDTO);

    /**
     * Deleting an user based on its ID
     * @param name
     * @return the id deleted
     */
    Long deleteUser(String name);


}


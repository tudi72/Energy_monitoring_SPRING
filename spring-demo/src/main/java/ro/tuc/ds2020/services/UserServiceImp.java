package ro.tuc.ds2020.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ro.tuc.ds2020.dtos.MyUserDetailsDTO;
import ro.tuc.ds2020.entities.MyUser;
import ro.tuc.ds2020.entities.Role;
import ro.tuc.ds2020.repositories.MyUserRepository;
import ro.tuc.ds2020.repositories.RoleRepository;
import ro.tuc.ds2020.security.JWTTokenHelper;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class UserServiceImp implements UserService, UserDetailsService {

    private final MyUserRepository userRepository;
    private final RoleRepository roleRepository;

    private final PasswordEncoder passwordEncoder;
    private final JWTTokenHelper jwtTokenHelper;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        MyUser user =  userRepository.findByUsername(username);
        if(user == null){
            log.error("User : " + username+" not found in the database");
            throw new UsernameNotFoundException("User not found in the database");
        }
        else{
            log.info("User : " + username +" found in the database");
            Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
            user.getRoles().forEach(role -> {authorities.add(new SimpleGrantedAuthority(role.getName()));
            });
            return new org.springframework.security.core.userdetails.User(user.getUsername(),user.getPassword(),authorities);
        }
    }

    @Override
    public MyUser saveUser(MyUser user) {
        log.info("Saving new user "+ user.getName()+" to the database");
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    @Override
    public Role saveRole(Role role) {
        log.info("Saving new role "+role.getName()+" to the database");
        return roleRepository.save(role);
    }

    @Override
    public boolean addRoleToUser(MyUser user, Role role) {

        try{
            user.getRoles().add(role);
            return true;
        }catch (Exception ex){
            log.info("ERROR: Adding to user "+ user.getName()+" role "+ role.getName()+" into database");
            return false;
        }
    }

    @Override
    public MyUser getUser(String name) {
        if(name.length() >= 200) 
        {
            try{

                String username = jwtTokenHelper.getUsernameFromToken(name);
                log.info("[UserServiceImp.getUserFromToken]" + username);
                return userRepository.findByUsername(username);
            
            }
            catch(Exception ex){
                log.error("[UserServiceImp.ERROR] : "+ ex.getMessage());
                return null;
            }
        }
        else{
            try{
                log.info("[UserServiceImp.getUser]: Fetching user by name: " + name);
                String regex = "[a-z]*[:space:]?" + name.toLowerCase() + "[:space:]?[a-z]*";
                return userRepository.findMyUserByName(regex);
            }
            catch(Exception ex){
                log.info("[UserServiceImp.getUser] : ERROR name = " + name);
                return null;
            }
        }
    }

    @Override
    public MyUser getUser(Long id) {
        log.info("Retrieving user based on its id " + id);
        return userRepository.findMyUserById(id);
    }

    @Override
    public List<MyUser> getUsers() {
        log.info("Fetching all users");
        return userRepository.findAll();
    }

    @Override
    public Role getRole(String roleName) {

        log.info("UserServiceImpl: getRole " + roleName);
        return roleRepository.findByName(roleName);
    }

    @Override
    public MyUser updateUser(MyUser user, MyUserDetailsDTO myUserDTO) {

        if(!myUserDTO.getAddress().isEmpty()) user.setAddress(myUserDTO.getAddress());
        if(!myUserDTO.getName().isEmpty()) user.setName(myUserDTO.getName());
        if(!myUserDTO.getUsername().isEmpty()) user.setUsername(myUserDTO.getUsername());
        if(myUserDTO.getAge() != 0) user.setAge(myUserDTO.getAge());
        log.info("Update user " + user.getName());
        return userRepository.save(user);
    }

    @Override
    public Long deleteUser(String name) {
        try{
            String regex =  "[a-z]*[:space:]?" + name + "[:space:]?[a-z]*";
            MyUser user = userRepository.findMyUserByName(regex);
            userRepository.delete(user);
            return user.getId();
        }
        catch(Exception ex){
            log.error("[UserServiceIm]: Cannot delete user");
            return 0L;
        }
        
    }

    @Override
    public MyUser getUserByUsername(String username) {
        try{
            // log.info("[UserServiceImp.getByUsername]: username" + username);

            return userRepository.findByUsername(username);
        }
        catch(Exception ex){
            log.error("[UserServiceIm]: Cannot delete user");
            return null;
        }    
    }

}


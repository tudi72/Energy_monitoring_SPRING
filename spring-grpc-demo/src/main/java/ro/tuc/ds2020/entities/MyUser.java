package ro.tuc.ds2020.entities;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

@Entity(name="MY_USER")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MyUser implements Serializable {

        private static final long serialVersionUID = 1L;

        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        private Long id;

        @Column(name = "name", nullable = false)
        private String name;

        @Column(name = "username", nullable = false)
        private String username;

        @Column(name = "password", nullable = false)
        private String password;

        @Column(name = "address", nullable = false)
        private String address;

        @Column(name = "age", nullable = false)
        private int age;

        @ManyToMany()
        private Collection<Role> roles = new ArrayList<>();

        @OneToMany(fetch = FetchType.EAGER)
        private Collection<Device> devices = new ArrayList<>();

        public MyUser(String name,String username,String password,String address,int age){
                this.name = name;
                this.username= username;
                this.password= password;
                this.address = address;
                this.age = age;
        }
}

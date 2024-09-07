package com.exam.entity;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;


@Data
@Entity
@Getter
@Setter

@Table(name="users")
@SequenceGenerator(name = "user_seq", sequenceName = "users_seq", allocationSize = 1)


public class User{

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_seq")
    private Long id;

    private String password;

    @Column(unique = true, nullable = false)
    private String username;

    private String firstName;

    private String lastName;

    private String middleName;
    
    private String isActive;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
        name = "user_email_map",
        joinColumns = @JoinColumn(name = "user_id"),
        inverseJoinColumns = @JoinColumn(name = "email_id")
    )
    private Set<Email> emails = new HashSet<>();
    
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
        name = "user_role_map",
        joinColumns = @JoinColumn(name = "user_id"),
        inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<Role> roles= new HashSet<>();
    
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
        name = "user_telephone_map",
        joinColumns = @JoinColumn(name = "user_id"),
        inverseJoinColumns = @JoinColumn(name = "telephone_id")
    )
    private Set<Telephone> telephone= new HashSet<>();
	

}

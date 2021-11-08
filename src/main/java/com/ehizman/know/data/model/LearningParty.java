package com.ehizman.know.data.model;

import com.ehizman.know.exception.Know_E_LearningException;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.ArrayList;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Entity
@AllArgsConstructor
@Builder
@Data
@NoArgsConstructor
public class LearningParty extends AuditModel{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    @NotBlank
    private String email;

    @Column(nullable = false)
    @NotBlank
    @JsonIgnoreProperties
    private String password;

    private boolean enabled;

    @OneToMany(cascade = CascadeType.ALL)
    private List<Authority> authorities;

    private String token;

    public LearningParty(String email, String password, Authority authority){
        this.email = email;
        this.password = password;
        this.enabled = false;
        addAuthority(authority);
    }
    private void addAuthority(Authority authority){
        if (this.authorities == null){
            this.authorities = new ArrayList<>();
        }
        this.authorities.add(authority);
    }
}

package com.ehizman.know.security.user;

import com.ehizman.know.data.model.Authority;
import com.ehizman.know.data.model.LearningParty;
import com.ehizman.know.data.repository.LearningPartyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class LearningPartyServiceImpl implements UserDetailsService {
    private LearningPartyRepository repository;

    @Autowired
    public LearningPartyServiceImpl(LearningPartyRepository repository) {
        this.repository = repository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        LearningParty user = repository.findUserByEmail(email);
        if (user == null){
            throw new UsernameNotFoundException("user with email not found");
        }
        List<SimpleGrantedAuthority> authorities = getAuthorities(user.getAuthorities());
        return new User(user.getEmail(), user.getPassword(), authorities);
    }

    private List<SimpleGrantedAuthority> getAuthorities(List<Authority> authorities) {
        return authorities.stream()
                        .map(authority -> new SimpleGrantedAuthority(authority.getAuthority().name()))
                        .collect(Collectors.toList());
    }
}

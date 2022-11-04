package fr.kirikou.Dashboard.service;

import fr.kirikou.Dashboard.model.User;
import fr.kirikou.Dashboard.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
public class DashboardUserDetailsService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<User> user = userRepository.getUserByEmail(email);
        if (user.isEmpty())
            throw new UsernameNotFoundException(email);
        return new DashboardUserDetails(user.get());
    }

    public class DashboardUserDetails implements UserDetails {
        private User user;

        public DashboardUserDetails(User user) {
            this.user = user;
        }

        @Override
        public Collection<? extends GrantedAuthority> getAuthorities() {
            List<? extends GrantedAuthority> list = new ArrayList<>();

            return list;
        }

        @Override
        public String getUsername() {
            return user.getEmail();
        }

        @Override
        public String getPassword() {
            return user.getPassword();
        }

        @Override
        public boolean isAccountNonExpired() {
            return true;
        }

        @Override
        public boolean isAccountNonLocked() {
            return true;
        }

        @Override
        public boolean isCredentialsNonExpired() {
            return user.getPassword() != null;
        }

        @Override
        public boolean isEnabled() {
            return true;
        }

        public User getUser() { return user; }
    }
}

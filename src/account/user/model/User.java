package account.user.model;

import account.admin.AdminOperation;
import account.payment.model.Payment;
import account.user.RoleChangeValidator;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Fetch;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.server.ResponseStatusException;

import javax.persistence.*;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
    private String lastname;
    private String email;
    private String password;
    @ElementCollection(fetch = FetchType.EAGER, targetClass = UserRole.class)
    @Enumerated(EnumType.STRING)
    private Set<UserRole> roles;
    @OneToMany(mappedBy = "id")
    private List<Payment> payments;

    private boolean enabled;
    private boolean locked;

    public User(String name, String lastname, String email, String password, Set<UserRole> roles, List<Payment> payments, boolean enabled, boolean locked) {
        this.name = name;
        this.lastname = lastname;
        this.email = email;
        this.password = password;
        this.roles = roles;
        this.payments = payments;
        this.enabled = enabled;
        this.locked = locked;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        return roles.stream()
                .map(r -> new SimpleGrantedAuthority("ROLE_" + r.name()))
                .collect(Collectors.toSet());

    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return !locked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }

    public void addRole(UserRole role) {
        RoleChangeValidator.validateRequest(this, role, AdminOperation.GRANT);
        roles.add(role);
    }

    public void removeRole(UserRole role) {
        RoleChangeValidator.validateRequest(this, role, AdminOperation.REMOVE);
        roles.remove(role);
    }

    public void lock() {
        if (roles.contains(UserRole.ADMINISTRATOR)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Can't lock the ADMINISTRATOR!");
        }

        locked = true;
    }

    public void unlock() {
        locked = false;
    }

}

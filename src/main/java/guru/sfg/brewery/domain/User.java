package guru.sfg.brewery.domain;

import lombok.*;
import org.springframework.security.core.CredentialsContainer;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class User implements UserDetails, CredentialsContainer {
    @Id
    @GeneratedValue(strategy =
            GenerationType.AUTO)
    private Long id;
    private String username;
    private String password;
    @Builder.Default
    private Boolean accountNonExpired = true;
    @Builder.Default
    private Boolean accountNonLocked = true;
    @Builder.Default
    private Boolean credentialsNonExpired = true;
    @Builder.Default
    private Boolean enabled = true;
    @ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @Singular
    @JoinTable(name = "user_roles",
            joinColumns = {@JoinColumn(name = "user_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "role_id", referencedColumnName = "id")})
    private Set<Role> roles;

    @ManyToOne(cascade = {CascadeType.ALL})
    @JoinColumn
    private Customer customer;

    @Transient
    private Set<GrantedAuthority> authorities;

    public Set<GrantedAuthority> getAuthorities() {
        return this.roles.stream().map(Role::getAuthorities).flatMap(Set::stream).map(authority -> new SimpleGrantedAuthority(authority.getPermission())).collect(Collectors.toSet());
    }

    @Override
    public boolean isAccountNonExpired() {
        return this.getAccountNonExpired();
    }

    @Override
    public boolean isAccountNonLocked() {
        return this.getAccountNonLocked();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return this.getCredentialsNonExpired();
    }

    @Override
    public boolean isEnabled() {
        return this.getEnabled();
    }


    @Override
    public void eraseCredentials() {
        this.password = null;
    }
}

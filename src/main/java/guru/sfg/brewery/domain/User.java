package guru.sfg.brewery.domain;

import lombok.*;

import javax.persistence.*;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class User {
    @Id
    @GeneratedValue(strategy =
            GenerationType.AUTO)
    private Long id;
    private String username;
    private String password;
    @ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @Singular
    @JoinTable(name = "user_roles",
            joinColumns = {@JoinColumn(name = "user_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "role_id", referencedColumnName = "id")})
    private Set<Role> roles;

    @Transient
    private Set<Authority> authorities;

    public Set<Authority> getAuthorities() {
        return this.roles.stream().map(Role::getAuthorities).flatMap(Set::stream).collect(Collectors.toSet());
    }

    @Builder.Default
    private Boolean accountNonExpired = true;
    @Builder.Default
    private Boolean accountNonLocked = true;
    @Builder.Default
    private Boolean credentialsNonExpired = true;
    @Builder.Default
    private Boolean enabled = true;
}

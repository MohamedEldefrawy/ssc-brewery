package guru.sfg.brewery.domain.security;

import guru.sfg.brewery.domain.User;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Role implements Serializable {
    @Id
    @GeneratedValue(strategy =
            GenerationType.AUTO)
    private Long id;
    private String name;
    @ManyToMany(mappedBy = "roles", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private Set<User> users;

    @ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name = "role_authorities",
            joinColumns = @JoinColumn(name = "role_id"))
    private Set<Authority> authorities;


}

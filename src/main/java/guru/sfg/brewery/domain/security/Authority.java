package guru.sfg.brewery.domain.security;

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
public class Authority implements Serializable {
    @Id
    @GeneratedValue(strategy =
            GenerationType.AUTO)
    private Long id;
    String permission;
    @ManyToMany(mappedBy = "authorities", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private Set<Role> roles;


}

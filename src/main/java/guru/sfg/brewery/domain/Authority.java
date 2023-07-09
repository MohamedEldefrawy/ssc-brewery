package guru.sfg.brewery.domain;

import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Authority {
    @Id
    @GeneratedValue(strategy =
            GenerationType.AUTO)
    private Long id;
    String permission;
    @ManyToMany(mappedBy = "authorities")
    Set<Role> roles;


}

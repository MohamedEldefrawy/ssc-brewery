package guru.sfg.brewery.domain;

import javax.persistence.*;
import java.util.Set;

@Entity
public class Authorities {
    @Id
    @GeneratedValue(strategy =
            GenerationType.AUTO)
    private Long id;
    String role;
    @ManyToMany(mappedBy = "authorities")
    Set<User> users;


}

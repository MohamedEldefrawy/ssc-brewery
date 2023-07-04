package guru.sfg.brewery.domain;

import lombok.*;
import org.springframework.transaction.annotation.Transactional;

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
    String role;
    @ManyToMany(mappedBy = "authorities")
    Set<User> users;


}
package guru.sfg.brewery.web.security.annotations.beer;

import org.springframework.security.access.prepost.PreAuthorize;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
@PreAuthorize("hasAuthority('delete.beer')")
public @interface DeleteBeerPermission {
}

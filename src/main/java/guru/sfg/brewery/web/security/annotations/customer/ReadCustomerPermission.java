package guru.sfg.brewery.web.security.annotations.customer;

import org.springframework.security.access.prepost.PreAuthorize;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
@PreAuthorize("hasAuthority('customer.read')")
public @interface ReadCustomerPermission {
}
package guru.sfg.brewery.web.security.annotations.order;

import org.springframework.security.access.prepost.PreAuthorize;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
@PreAuthorize("{hasAuthority('order.update') OR hasAuthority('customer.order.update')}")
public @interface UpdateOrderPermission {
}

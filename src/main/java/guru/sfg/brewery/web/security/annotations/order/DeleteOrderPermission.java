package guru.sfg.brewery.web.security.annotations.order;

import org.springframework.security.access.prepost.PreAuthorize;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
@PreAuthorize("{hasAuthority('order.delete') OR hasAuthority('customer.order.delete')}")
public @interface DeleteOrderPermission {
}

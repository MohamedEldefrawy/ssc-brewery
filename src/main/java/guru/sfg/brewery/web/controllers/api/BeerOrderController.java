package guru.sfg.brewery.web.controllers.api;

import guru.sfg.brewery.domain.User;
import guru.sfg.brewery.services.BeerOrderService;
import guru.sfg.brewery.web.model.BeerOrderDto;
import guru.sfg.brewery.web.model.BeerOrderPagedList;
import guru.sfg.brewery.web.security.annotations.order.CreateOrderPermission;
import guru.sfg.brewery.web.security.annotations.order.ReadOrderPermission;
import guru.sfg.brewery.web.security.annotations.order.UpdateOrderPermission;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RequestMapping("/api/v1/customers")
@RestController
@RequiredArgsConstructor
public class BeerOrderController {

    private static final Integer DEFAULT_PAGE_NUMBER = 0;
    private static final Integer DEFAULT_PAGE_SIZE = 25;

    private final BeerOrderService beerOrderService;

    @GetMapping("orders")
    @ReadOrderPermission
    public BeerOrderPagedList listOrders(@AuthenticationPrincipal User user,
                                         @RequestParam(value = "pageNumber", required = false) Integer pageNumber,
                                         @RequestParam(value = "pageSize", required = false) Integer pageSize) {
        if (pageNumber == null || pageNumber < 0) {
            pageNumber = DEFAULT_PAGE_NUMBER;
        }

        if (pageSize == null || pageSize < 1) {
            pageSize = DEFAULT_PAGE_SIZE;
        }

        if (user.getCustomer() != null)
            return beerOrderService.listOrders(user.getCustomer().getId(), PageRequest.of(pageNumber, pageSize));
        else
            return beerOrderService.listOrders(PageRequest.of(pageNumber, pageSize));
    }

    @PostMapping("orders")
    @CreateOrderPermission
    @ResponseStatus(HttpStatus.CREATED)
    public BeerOrderDto placeOrder(@AuthenticationPrincipal User user, @RequestBody BeerOrderDto beerOrderDto) {
        if (user.getCustomer() != null)
            return beerOrderService.placeOrder(user.getCustomer().getId(), beerOrderDto);
        return beerOrderService.placeOrder(beerOrderDto);
    }

    @GetMapping("orders/{orderId}")
    @ReadOrderPermission
    public BeerOrderDto getOrder(@AuthenticationPrincipal User user, @PathVariable("orderId") UUID orderId) {
        if (user.getCustomer()  != null)
            return beerOrderService.getOrderById(user.getCustomer().getId(), orderId);
        return beerOrderService.getOrderById(orderId);
    }

    @PutMapping("/orders/{orderId}/pickup")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @UpdateOrderPermission
    public void pickupOrder(@AuthenticationPrincipal User user, @PathVariable("orderId") UUID orderId) {
            beerOrderService.pickupOrder(user.getCustomer().getId(), orderId);
    }
}
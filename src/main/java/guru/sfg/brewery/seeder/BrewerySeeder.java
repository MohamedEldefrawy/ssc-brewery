/*
 *  Copyright 2020 the original author or authors.
 *
 * This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */
package guru.sfg.brewery.seeder;

import guru.sfg.brewery.domain.*;
import guru.sfg.brewery.repositories.*;
import guru.sfg.brewery.web.model.BeerStyleEnum;
import guru.sfg.brewery.web.security.CustomPasswordEncoder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;


/**
 * Created by jt on 2019-01-26.
 */
@RequiredArgsConstructor
@Component
@Slf4j
public class BrewerySeeder implements CommandLineRunner {
    public static final String TASTING_ROOM = "Tasting Room";
    public static final String ST_PETE_DISTRIBUTING = "St Pete Distributing";
    public static final String DUNEDIN_DISTRIBUTING = "Dunedin Distributing";
    public static final String KEY_WEST_DISTRIBUTORS = "Key West Distributors";
    public static final String STPETE_USER = "stpete";
    public static final String DUNEDIN_USER = "dunedin";
    public static final String KEYWEST_USER = "keywest";

    public static final String BEER_1_UPC = "0631234200036";
    public static final String BEER_2_UPC = "0631234300019";
    public static final String BEER_3_UPC = "0083783375213";

    @Value("${raw.password}")
    private String rawPassword;

    private final BreweryRepository breweryRepository;
    private final BeerRepository beerRepository;
    private final BeerInventoryRepository beerInventoryRepository;
    private final BeerOrderRepository beerOrderRepository;
    private final CustomerRepository customerRepository;
    private final AuthorityRepository authorityRepository;
    private final RoleRepository roleRepository;
    private final UserRepository userRepository;

    @Override
    public void run(String... args) {
        loadSecurityData();
        loadBreweryData();
        loadTastingRoomData();
        loadCustomerData();
    }

    private void loadCustomerData() {
        Role customerRole = roleRepository.findByName("CUSTOMER").orElseThrow();

        //create customers
        Customer stPeteCustomer = customerRepository.save(Customer.builder()
                .customerName(ST_PETE_DISTRIBUTING)
                .apiKey(UUID.randomUUID())
                .build());

        Customer dunedinCustomer = customerRepository.save(Customer.builder()
                .customerName(DUNEDIN_DISTRIBUTING)
                .apiKey(UUID.randomUUID())
                .build());

        Customer keyWestCustomer = customerRepository.save(Customer.builder()
                .customerName(KEY_WEST_DISTRIBUTORS)
                .apiKey(UUID.randomUUID())
                .build());

        //create users
        userRepository.save(User.builder().username(STPETE_USER)
                .password(CustomPasswordEncoder.createDelegatingPasswordEncoder().encode(rawPassword))
                .customer(stPeteCustomer)
                .role(customerRole).build());

        userRepository.save(User.builder().username(DUNEDIN_USER)
                .password(CustomPasswordEncoder.createDelegatingPasswordEncoder().encode(rawPassword))
                .customer(dunedinCustomer)
                .role(customerRole).build());

        userRepository.save(User.builder().username(KEYWEST_USER)
                .password(CustomPasswordEncoder.createDelegatingPasswordEncoder().encode(rawPassword))
                .customer(keyWestCustomer)
                .role(customerRole).build());

        //create orders
        createOrder(stPeteCustomer);
        createOrder(dunedinCustomer);
        createOrder(keyWestCustomer);

        log.debug("Orders Loaded: " + beerOrderRepository.count());
    }

    private void createOrder(Customer customer) {
        beerOrderRepository.save(BeerOrder.builder()
                .customer(customer)
                .orderStatus(OrderStatusEnum.NEW)
                .beerOrderLines(Set.of(BeerOrderLine.builder()
                        .beer(beerRepository.findByUpc(BEER_1_UPC))
                        .orderQuantity(2)
                        .build()))
                .build());
    }


    private void loadTastingRoomData() {
        Customer tastingRoom = Customer.builder()
                .customerName(TASTING_ROOM)
                .apiKey(UUID.randomUUID())
                .build();

        customerRepository.save(tastingRoom);

        beerRepository.findAll().forEach(beer -> beerOrderRepository.save(BeerOrder.builder()
                .customer(tastingRoom)
                .orderStatus(OrderStatusEnum.NEW)
                .beerOrderLines(Set.of(BeerOrderLine.builder()
                        .beer(beer)
                        .orderQuantity(2)
                        .build()))
                .build()));
    }

    private void loadBreweryData() {
        if (breweryRepository.count() == 0) {
            breweryRepository.save(Brewery
                    .builder()
                    .breweryName("Cage Brewing")
                    .build());

            Beer mangoBobs = Beer.builder()
                    .beerName("Mango Bobs")
                    .beerStyle(BeerStyleEnum.IPA)
                    .minOnHand(12)
                    .quantityToBrew(200)
                    .upc(BEER_1_UPC)
                    .build();

            beerRepository.save(mangoBobs);
            beerInventoryRepository.save(BeerInventory.builder()
                    .beer(mangoBobs)
                    .quantityOnHand(500)
                    .build());

            Beer galaxyCat = Beer.builder()
                    .beerName("Galaxy Cat")
                    .beerStyle(BeerStyleEnum.PALE_ALE)
                    .minOnHand(12)
                    .quantityToBrew(200)
                    .upc(BEER_2_UPC)
                    .build();

            beerRepository.save(galaxyCat);
            beerInventoryRepository.save(BeerInventory.builder()
                    .beer(galaxyCat)
                    .quantityOnHand(500)
                    .build());

            Beer pinball = Beer.builder()
                    .beerName("Pinball Porter")
                    .beerStyle(BeerStyleEnum.PORTER)
                    .minOnHand(12)
                    .quantityToBrew(200)
                    .upc(BEER_3_UPC)
                    .build();

            beerRepository.save(pinball);
            beerInventoryRepository.save(BeerInventory.builder()
                    .beer(pinball)
                    .quantityOnHand(500)
                    .build());

        }
    }

    private void loadSecurityData() {
        // Beer Authorities
        Authority createBeer = this.authorityRepository.saveAndFlush(Authority.builder().permission("create.beer").build());
        Authority deleteBeer = this.authorityRepository.saveAndFlush(Authority.builder().permission("delete.beer").build());
        Authority updateBeer = this.authorityRepository.saveAndFlush(Authority.builder().permission("update.beer").build());
        Authority getBeer = this.authorityRepository.saveAndFlush(Authority.builder().permission("get.beer").build());

        // Brewery Authorities
        Authority createBrewery = this.authorityRepository.saveAndFlush(Authority.builder().permission("create.brewery").build());
        Authority deleteBrewery = this.authorityRepository.saveAndFlush(Authority.builder().permission("delete.brewery").build());
        Authority updateBrewery = this.authorityRepository.saveAndFlush(Authority.builder().permission("update.brewery").build());
        Authority getBrewery = this.authorityRepository.saveAndFlush(Authority.builder().permission("get.brewery").build());


        // Customer Authorities
        Authority createCustomer = this.authorityRepository.saveAndFlush(Authority.builder().permission("create.customer").build());
        Authority deleteCustomer = this.authorityRepository.saveAndFlush(Authority.builder().permission("delete.customer").build());
        Authority updateCustomer = this.authorityRepository.saveAndFlush(Authority.builder().permission("update.customer").build());
        Authority getCustomer = this.authorityRepository.saveAndFlush(Authority.builder().permission("get.customer").build());

        // Order Authorities
        Authority createOrder = this.authorityRepository.saveAndFlush(Authority.builder().permission("create.order").build());
        Authority deleteOrder = this.authorityRepository.saveAndFlush(Authority.builder().permission("delete.order").build());
        Authority updateOrder = this.authorityRepository.saveAndFlush(Authority.builder().permission("update.order").build());
        Authority getOrder = this.authorityRepository.saveAndFlush(Authority.builder().permission("get.order").build());

        // Customer Order Authorities
        Authority customerCreateOrder = this.authorityRepository.saveAndFlush(Authority.builder().permission("customer.create.order").build());
        Authority customerDeleteOrder = this.authorityRepository.saveAndFlush(Authority.builder().permission("customer.delete.order").build());
        Authority customerUpdateOrder = this.authorityRepository.saveAndFlush(Authority.builder().permission("customer.update.order").build());
        Authority customerGetOrder = this.authorityRepository.saveAndFlush(Authority.builder().permission("customer.get.order").build());


        Role adminRole = roleRepository.save(Role.builder().name("ADMIN").build());
        Role customerRole = roleRepository.save(Role.builder().name("CUSTOMER").build());
        Role userRole = roleRepository.save(Role.builder().name("USER").build());

        adminRole.setAuthorities(new HashSet<>(Set.of(createBeer, updateBeer, getBeer, deleteBeer, createCustomer, getCustomer,
                updateCustomer, deleteCustomer, createBrewery, getBrewery, updateBrewery, deleteBrewery,
                createOrder, getOrder, updateOrder, deleteOrder)));

        customerRole.setAuthorities(new HashSet<>(Set.of(getBeer, getCustomer, getBrewery, customerCreateOrder, customerGetOrder,
                customerUpdateOrder, customerDeleteOrder)));

        userRole.setAuthorities(new HashSet<>(Set.of(getBeer)));

        roleRepository.saveAll(Arrays.asList(adminRole, customerRole, userRole));


        // Create users
        this.userRepository.save(User.builder().username("spring").password("{bcrypt}$2a$10$l/zJs2Mz/UDMxTxoHCrBiebKmlobzr2CIHs1zE.IA6BXSWRkVHCzK").role(adminRole).build());
        this.userRepository.save(User.builder().username("user").password("{sha256}17cf776648dce8a2065ba5b6085901740a7f54939985bb887686c29050f19ad682cced7908ccfcd9").role(userRole).build());
        this.userRepository.save(User.builder().username("scott").password("{sha256}17cf776648dce8a2065ba5b6085901740a7f54939985bb887686c29050f19ad682cced7908ccfcd9").role(customerRole).build());


        log.debug("Users Loaded: " + userRepository.count());
    }
}

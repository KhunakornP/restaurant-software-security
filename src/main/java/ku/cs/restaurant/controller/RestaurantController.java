package ku.cs.restaurant.controller;

import jakarta.validation.Valid;
import ku.cs.restaurant.dto.RestaurantRequest;
import ku.cs.restaurant.entities.Restaurant;
import ku.cs.restaurant.service.RestaurantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api")
public class RestaurantController {

    private RestaurantService service;

    @Autowired
    public RestaurantController(RestaurantService service){
        this.service = service;
    }

    @GetMapping("/restaurants")
    public Page<Restaurant> getAllRestaurants(
            @RequestParam(value = "offset", required = false) Integer offset,
            @RequestParam(value = "pageSize", required = false) Integer pageSize,
            @RequestParam(value = "sortBy", required = false) String sortBy) {
        if(null == offset) offset = 0;
        if(null == pageSize) pageSize = 10;
        if(StringUtils.isEmpty(sortBy)) sortBy ="name";
        return service.getRestaurantPage(PageRequest.of(offset, pageSize, Sort.by(sortBy)));
    }

    @PostMapping("/restaurants")
    public Restaurant create(@Valid  @RequestBody RestaurantRequest restaurant_dto){
        Restaurant restaurant = new Restaurant();
        restaurant.setName(restaurant_dto.getName());
        restaurant.setRating(restaurant_dto.getRating());
        restaurant.setLocation(restaurant_dto.getLocation());

        restaurant.setCreatedAt(  Instant.now()  );
        return service.create(restaurant);
    }

    @GetMapping("/restaurants/{id}")
    public Restaurant getRestaurantById(@Valid @PathVariable UUID id) {
        return service.getRestaurantById(id);
    }

    @PutMapping("/restaurants")
    public Restaurant update(@Valid @RequestBody Restaurant restaurant) {
        return service.update(restaurant);
    }

    @DeleteMapping("/restaurants/{id}")
    public Restaurant delete(@Valid @PathVariable UUID id) {
        return service.delete(id);
    }

    @GetMapping("/restaurants/name/{name}")
    public Restaurant getRestaurantByName(@Valid @PathVariable String name) {
        return service.getRestaurantByName(name);
    }


    @GetMapping("/restaurants/location/{location}")
    public List<Restaurant> getRestaurantByLocation(@Valid @PathVariable String location) {
        return service.getRestaurantByLocation(location);
    }

}

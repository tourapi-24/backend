package tourapi24.backend.direction.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import tourapi24.backend.direction.service.DirectionService;

@RestController
public class DirectionController {

    @Autowired
    private DirectionService directionService;

    @GetMapping("/legs")
    public ResponseEntity<?> getRouteLegs(@RequestParam("origin") String origin, @RequestParam("destination") String destination) {
        return directionService.getRoute(origin, destination);
    }

}

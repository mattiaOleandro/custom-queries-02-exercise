package co.develhope.customqueries02exercise.controllers;

import co.develhope.customqueries02exercise.entities.Flight;
import co.develhope.customqueries02exercise.entities.Status;
import co.develhope.customqueries02exercise.repositories.I_FlightRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static co.develhope.customqueries02exercise.entities.Status.values;

@RestController
@RequestMapping("/flights")
public class FlightController {

    @Autowired
    private I_FlightRepository i_flightRepository;

    public String generateRandomValueForFlight(){
        int leftLimit = 97; // letter 'a'
        int rightLimit = 122; // letter 'z'
        int targetStringLength = 10;
        Random random = new Random();

        String generatedString = random.ints(leftLimit, rightLimit + 1)
                .limit(targetStringLength)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();

        return generatedString;
    }

    public Status generateRandomValueForFlightStatus(){
        Random random = new Random();
        Status[] status = values();
        return status[random.nextInt(status.length)];

    }

    @PostMapping("/provisioning")
    public String provisionFlights(@RequestParam(required = false) Integer n){
        if(n == null)
            n=100;
        List<Flight> flights = new ArrayList<>();
        for(int i = 0; i < n; i++){
            Flight flight = new Flight();
            flight.setDescription(generateRandomValueForFlight());
            flight.setFromAirport(generateRandomValueForFlight());
            flight.setToAirport(generateRandomValueForFlight());
            flight.setStatus(generateRandomValueForFlightStatus());
            flights.add(flight);
        }
        i_flightRepository.saveAll(flights);
        return "Provisioning updated!";
    }

    @GetMapping("/getAllFlights")
    public Page<Flight> getAllFlight(@RequestParam int pag, @RequestParam int size){
        return i_flightRepository.findAll(PageRequest.of(pag, size, Sort.by("fromAirport").ascending()));
    }

    @GetMapping("/getFlightsByStatus")
    public List<Flight> getFlightsByStatusOnTime(){
        List<Flight> flightsStatusOnTime = new ArrayList<>();
        for (int i = 0; i < i_flightRepository.findAll().size(); i++) {
            if (i_flightRepository.findAll().get(i).getStatus() == Status.ON_TIME){
                flightsStatusOnTime.add(i_flightRepository.findAll().get(i));
            }
        }
        return flightsStatusOnTime;
    }

    @GetMapping("/getCustomFlight")
    public List<Flight> getCustomFlight(@RequestParam Status p1, @RequestParam Status p2){
        return i_flightRepository.getCustomFlight(p1, p2);
    }
}

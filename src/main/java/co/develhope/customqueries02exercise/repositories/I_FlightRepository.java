package co.develhope.customqueries02exercise.repositories;

import co.develhope.customqueries02exercise.entities.Flight;
import co.develhope.customqueries02exercise.entities.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface I_FlightRepository extends JpaRepository<Flight, Integer> {

    @Query("SELECT flight FROM Flight flight WHERE flight.status = :p1 OR flight.status = :p2")
    List<Flight> getCustomFlight(@Param("p1") Status p1, @Param("p2") Status p2);
}

package com.example.ioproject.repository;

import com.example.ioproject.models.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    @Query("SELECT r FROM Reservation r WHERE r.vehicle_id = :vehicleId AND " +
            "((r.start_date <= :endDate AND r.end_date >= :startDate))")
    List<Reservation> findConflictingReservations(@Param("vehicleId") int vehicleId,
                                                  @Param("startDate") String startDate,
                                                  @Param("endDate") String endDate);
}

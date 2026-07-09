package com.bruno.medconnectcenter.repositories;
import com.bruno.medconnectcenter.entities.DoctorSchedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

public interface DoctorScheduleRepository extends JpaRepository<DoctorSchedule, Long> {

    List<DoctorSchedule> findByDoctorId(Long doctorId);

    @Query("SELECT CASE WHEN COUNT(s) > 0 THEN true ELSE false END " +
            "FROM DoctorSchedule s " +
            "WHERE s.doctor.id = :doctorId " +
            "AND s.dayOfWeek = :dayOfWeek " +
            "AND s.startTime <= :localTime " +
            "AND s.endTime > :localTime")
    boolean existsAvailableSchedule(
            @Param("doctorId") Long doctorId,
            @Param("dayOfWeek") DayOfWeek dayOfWeek,
            @Param("localTime") LocalTime localTime
    );

    List<DoctorSchedule> findByDoctorIdAndSpecialtyIdAndDayOfWeek(Long doctorId, Long specialtyId, DayOfWeek dayOfWeek);
}

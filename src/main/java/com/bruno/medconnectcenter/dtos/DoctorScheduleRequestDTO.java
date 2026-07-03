package com.bruno.medconnectcenter.dtos;
import com.bruno.medconnectcenter.entities.DayOfWeek;
import java.time.LocalTime;

public record DoctorScheduleRequestDTO(
        DayOfWeek dayOfWeek,
        LocalTime startTime,
        LocalTime endTime,
        Long doctorId
) {
}

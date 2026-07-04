package com.bruno.medconnectcenter.dtos;
import java.time.DayOfWeek;
import java.time.LocalTime;

public record DoctorScheduleRequestDTO(
        DayOfWeek dayOfWeek,
        LocalTime startTime,
        LocalTime endTime,
        Long doctorId
) {
}

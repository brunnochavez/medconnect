package com.bruno.medconnectcenter.dtos;
import jakarta.validation.constraints.NotNull;
import java.time.DayOfWeek;
import java.time.LocalTime;

public record DoctorScheduleRequestDTO(

        @NotNull
        DayOfWeek dayOfWeek,

        @NotNull
        LocalTime startTime,

        @NotNull
        LocalTime endTime,

        @NotNull
        Long doctorId,

        @NotNull
        Long specialtyId
) {
}

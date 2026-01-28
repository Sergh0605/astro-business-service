package com.spuzakov.astro.astrobusinessservice.model;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

public record OrderRequestDto(
    UUID telegramUserId,
    LocalDate birthDate,
    LocalTime birthTime,
    UUID cityId
) {}

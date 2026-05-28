package com.example.notifications.DTO;

import lombok.Data;

import java.time.LocalDate;
@Data
public class AnalyzedMedicationDTO {

    private String name;
    private int dosagePerDay;
    private LocalDate endDate;
}

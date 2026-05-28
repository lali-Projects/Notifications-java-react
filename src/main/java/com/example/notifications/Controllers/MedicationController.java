package com.example.notifications.Controllers;

import com.example.notifications.DTO.AnalyzedMedicationDTO;
import com.example.notifications.Services.ImageAnalysisService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.example.notifications.Entities.Medication;
import com.example.notifications.Services.MedicationService;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/medications")
public class MedicationController {

    @Autowired
    private MedicationService medicationService;
    @Autowired
    private ImageAnalysisService imageAnalysisService;

    @PostMapping("/analyze-image")
    public AnalyzedMedicationDTO analyzeImage(@RequestParam("file") MultipartFile file) {
        // בדיקת תקינות ראשונית - אם הקובץ ריק, נזרוק שגיאה שתטופל ב-GlobalExceptionHandler שלך
        if (file == null || file.isEmpty()) {
            throw new IllegalArgumentException("חובה להעלות קובץ תמונה תקין שאינו ריק.");
        }

        // קריאה לשירות ומסירת התמונה, התוצאה המפוענחת תחזור ישירות ל-Frontend
        return imageAnalysisService.analyzeMedicationImage(file);
    }

    @PostMapping
    public Medication createMedication(@Valid @RequestBody Medication medication) {
        return medicationService.createMedication(medication);
    }

    @GetMapping
    public List<Medication> getAllMedications() {
        return medicationService.getAllMedications();
    }

    @GetMapping("/{id}")
    public Medication getMedicationById(@PathVariable Long id) {
        return medicationService.getMedicationById(id);
    }

    @GetMapping("/user/{userId}")
    public List<Medication> getMedicationsByUserId(@PathVariable Long userId) {
        return medicationService.getMedicationsByUserId(userId);
    }

    @PutMapping("/{id}")
    public Medication updateMedication(@PathVariable Long id, @RequestBody Medication medication) {
        return medicationService.updateMedication(id, medication);
    }

    @DeleteMapping("/{id}")
    public void deleteMedication(@PathVariable Long id) {
        medicationService.deleteMedication(id);
    }
}
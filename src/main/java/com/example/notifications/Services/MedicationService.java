package com.example.notifications.Services;
import com.example.notifications.Exceptions.ConflictException;
import com.example.notifications.Exceptions.ResourceNotFoundException;
import com.example.notifications.Repositories.MedicationRepository;
import com.example.notifications.Entities.Medication;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class MedicationService {

    @Autowired
    private MedicationRepository medicationRepository;

    public Medication createMedication(Medication newMedication)
    {

        // 1. חיפוש תרופה קיימת למשתמש הספציפי עם אותו שם
        Optional<Medication> existingMedicationOpt = medicationRepository
                .findByUserAndName(newMedication.getUser(), newMedication.getName());

        if (existingMedicationOpt.isPresent())
        {
            Medication existingMedication = existingMedicationOpt.get();

            // 2. בדיקה האם כל הנתונים זהים (מלבד התאריך)
            // נבדוק את המינון (dosagePerDay) ואת סוג לוח הזמנים (fixedSchedule)
            if (existingMedication.getDosage() == newMedication.getDosage() &&existingMedication.getFrequency() == newMedication.getFrequency() &&
                    existingMedication.isFixedSchedule() == newMedication.isFixedSchedule())
            {

                // עדכון התאריך בלבד ושמירה
                //האם להוסיף גם עידכון של תאריך הוספה
                existingMedication.setEndDate(newMedication.getEndDate());
                existingMedication.setCreatedAt(newMedication.getCreatedAt());
                return medicationRepository.save(existingMedication);
            }


        }

        // 3. אם לא נמצאה תרופה או שהנתונים האחרים שונים - יצירת תרופה חדשה
        return medicationRepository.save(newMedication);
    }

    public List<Medication> getAllMedications() {
        return medicationRepository.findAll();
    }

    public Medication getMedicationById(Long id) {
        return medicationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Medication not found"));
    }

    public List<Medication> getMedicationsByUserId(Long userId) {
        List<Medication> medications = medicationRepository.findByUser_Id(userId);

//        if (medications.isEmpty()) {
//            throw new ResourceNotFoundException("No medications found for user id: " + userId);        }

        return medications;
    }

    public Medication updateMedication(Long id, Medication updatedMedication) {
        Medication existingMedication = getMedicationById(id);

        existingMedication.setName(updatedMedication.getName());
        existingMedication.setDosage(updatedMedication.getDosage());
        existingMedication.setFrequency(updatedMedication.getFrequency());
        existingMedication.setEndDate(updatedMedication.getEndDate());
        existingMedication.setFixedSchedule(updatedMedication.isFixedSchedule());

        return medicationRepository.save(existingMedication);
    }

    public void deleteMedication(Long id) {

        if (!medicationRepository.existsById(id)) {
            throw new ResourceNotFoundException("Cannot delete: Medication not found with id: " + id);
        }
        medicationRepository.deleteById(id);
    }
    @Transactional
    public void cleanupExpiredMedications() {
        // מחיקת כל התרופות שתאריך הסיום שלהן עבר (קטן מהיום)
        LocalDate today = LocalDate.now();
        medicationRepository.deleteByEndDateBefore(today);
    }
}

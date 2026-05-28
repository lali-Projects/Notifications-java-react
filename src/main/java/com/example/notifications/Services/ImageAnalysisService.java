package com.example.notifications.Services;

import com.example.notifications.DTO.AnalyzedMedicationDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.client.MultipartBodyBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

@Service
public class ImageAnalysisService {
    private final WebClient webClient;

    public ImageAnalysisService(@Value("${python.ocr.url:http://localhost:5000}") String pythonUrl) {
        this.webClient = WebClient.builder()
                .baseUrl(pythonUrl)
                .build();
    }

    public AnalyzedMedicationDTO analyzeMedicationImage(MultipartFile file) {
        // בניית גוף הבקשה כ-Multipart
        MultipartBodyBuilder builder = new MultipartBodyBuilder();
        builder.part("image", file.getResource())
                .filename(file.getOriginalFilename());

        try {
            // ביצוע הפנייה לשרת הפייתון לנקודת הקצה /analyze
            return webClient.post()
                    .uri("/analyze")
                    .contentType(MediaType.MULTIPART_FORM_DATA)
                    .body(BodyInserters.fromMultipartData(builder.build()))
                    .retrieve()
                    .bodyToMono(AnalyzedMedicationDTO.class)
                    .block(); // קריאה סינכרונית חוסמת שממתינה לתשובת ה-AI

        } catch (WebClientResponseException e) {
            // שרת הפייתון החזיר קוד שגיאה (למשל 400 או 500) - נזרוק שגיאה עם הפירוט
            throw new IllegalArgumentException("שגיאה בעיבוד התמונה בשרת ה-AI: " + e.getResponseBodyAsString());
        } catch (Exception e) {
            // שרת הפייתון כבוי או שיש בעיית רשת/חיבור
            throw new IllegalStateException("שרת פענוח התמונות אינו זמין כרגע, אנא נסה שוב מאוחר יותר.");
        }
    }
}

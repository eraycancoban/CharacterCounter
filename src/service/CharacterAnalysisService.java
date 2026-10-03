package service;

import model.AnalysisRequest;
import model.AnalysisResult;

public class CharacterAnalysisService {

    public AnalysisRequest validateAndBuildRequest(String limitStr, String text, String targetCharStr, boolean caseSensitive) {
        // 1. Limit Doğrulaması
        if (limitStr == null || limitStr.trim().isEmpty()) {
            throw new IllegalArgumentException("Lütfen geçerli bir maksimum karakter sayısı giriniz!");
        }

        int maxLimit;
        try {
            maxLimit = Integer.parseInt(limitStr.trim());
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Lütfen geçerli bir maksimum karakter sayısı giriniz!");
        }

        if (maxLimit <= 0) {
            throw new IllegalArgumentException("Maksimum karakter sayısı 0'dan büyük bir tamsayı olmalıdır!");
        }

        // 2. Metin & Limit Aşımı Kontrolü
        if (text == null || text.trim().isEmpty()) {
            throw new IllegalArgumentException("Lütfen analiz edilecek bir cümle giriniz!");
        }

        if (text.length() > maxLimit) {
            throw new IllegalArgumentException(String.format(
                    "Girdiğiniz cümle belirlenen limiti aşıyor!\nMevcut Uzunluk: %d\nMaksimum İzin Verilen: %d\nLütfen cümleyi kısaltıp tekrar deneyiniz.",
                    text.length(), maxLimit));
        }

        // 3. Aranacak Karakter Kontrolü
        if (targetCharStr == null || targetCharStr.isEmpty()) {
            throw new IllegalArgumentException("Geçerli bir karakter giriniz!");
        }

        if (targetCharStr.length() > 1) {
            throw new IllegalArgumentException("Lütfen sadece tek bir karakter giriniz!");
        }

        char targetChar = targetCharStr.charAt(0);
        return new AnalysisRequest(maxLimit, text, targetChar, caseSensitive);
    }

    public AnalysisResult analyze(AnalysisRequest request) {
        String text = request.getText();
        char targetChar = request.getTargetChar();
        boolean isCaseSensitive = request.isCaseSensitive();

        int count = 0;
        for (int i = 0; i < text.length(); i++) {
            char current = text.charAt(i);
            if (isCaseSensitive) {
                if (current == targetChar) {
                    count++;
                }
            } else {
                if (Character.toLowerCase(current) == Character.toLowerCase(targetChar)) {
                    count++;
                }
            }
        }

        return new AnalysisResult(targetChar, count, isCaseSensitive);
    }
}
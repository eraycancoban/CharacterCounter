# Karakter Frekans Analiz Aracı

Java Swing altyapısıyla geliştirilmiş, metinler üzerinde gerçek zamanlı frekans analizi, sınır kontrolü ve tema özelleştirmesi sunan modern masaüstü uygulaması.

---

## Genel Bakış

**Karakter Frekans Analiz Aracı**, girilen metinlerin kullanıcı tanımlı karakter limitine uygunluğunu anlık olarak izler, hedeflenen tek bir karakterin metin içindeki geçiş sayısını (frekansını) hesaplar ve analiz sonuçlarını görsel olarak raporlar. Katmanlı mimari (Layered Architecture) prensiplerine sadık kalınarak tasarlanmış olup taşınabilir masaüstü paketi (`.exe`) olarak kullanıma hazırdır.

---

## Temel Özellikler

* **Dinamik Karakter Sınırı Kontrolü:**
  * Kullanıcı tarafından ayarlanabilen maksimum karakter limiti.
  * Metin yazıldıkça tetiklenen anlık uzunluk sayacı (`Uzunluk: X`).
  * Limit aşıldığında sayacın kırmızı renge dönerek kullanıcıyı uyarması.
* **Esnek Karakter Frekans Analizi:**
  * Hedef tek bir karakterin (harf, rakam, sembol) metindeki toplam adet hesabı.
  * İsteğe bağlı olarak açılıp kapatılabilen **Büyük / Küçük Harf Duyarlılığı (Case-Sensitivity)**.
* **Gelişmiş Girdi Doğrulama:**
  * Limit alanının sayısal değer denetimi.
  * Hedef karakter alanının boş bırakılmaması ve birden fazla karakter girilmemesi kontrolü.
  * Belirlenen limitin üzerindeki metinler için işlem öncesi doğrulama uyarısı.
* **Modern & Duyarlı (Responsive) Arayüz:**
  * Pencere yeniden boyutlandırıldığında bileşenlerin orantılı esnemesini sağlayan `BorderLayout` ve `BoxLayout` düzeni.
  * Otomatik satır kaydırma (`lineWrap` / `wrapStyleWord`) destekli metin giriş alanı.
* **Gece / Gündüz Modu (Dark / Light Mode):**
  * Tek tuşla geçiş yapılabilen yüksek kontrastlı açık tema ve gözü yormayan koyu tema (`#18181B`).
  * Tema değişiminde tüm etiket, metin kutusu ve buton renklerinin dinamik güncellenmesi.

---

## Mimari Yapı

Proje, sorumlulukların ayrılığı ilkesine uygun olarak katmanlı bir paket mimarisiyle geliştirilmiştir:

```text
src/
├── model/
│   ├── AnalysisRequest.java          # Doğrulanmış kullanıcı girdilerini temsil eden model
│   └── AnalysisResult.java           # Analiz çıktıları ve formatlanmış sonuç modeli
├── service/
│   └── CharacterAnalysisService.java # Girdi doğrulama ve frekans hesaplama iş mantığı
├── ui/
│   └── CharacterAnalyzerFrame.java   # Duyarlı arayüz, olay dinleyicileri ve tema yöneticisi
└── Main.java                         # Uygulama başlangıç noktası (Swing EDT)
```

---

## Kurulum ve Çalıştırma

### 1. Hazır `.exe` Olarak Çalıştırma (Tavsiye Edilen)
Sisteminizde Java veya JDK kurulu olması gerekmez:
1. **Releases** sekmesinden `KarakterAnalizAraci-v1.0.0-windows.zip` arşivini indirin.
2. ZIP dosyasını dilediğiniz bir klasöre çıkartın.
3. Klasör içindeki `KarakterAnalizAraci.exe` dosyasına çift tıklayarak uygulamayı başlatın.

### 2. Kaynak Koddan Çalıştırma
* **Gereksinimler:** JDK 21+ (veya OpenJDK 25)
* Projeyi IntelliJ IDEA veya tercih ettiğiniz bir Java IDE ile açın.
* `src/Main.java` dosyasını çalıştırın.

---

## Bağımsız `.exe` Üretimi (Packaging)

Uygulamanın standalone Windows sürümünü derlemek için `jpackage` adımları:

1. **IntelliJ Artifact Oluşturma & Derleme:**
   ```text
   Build -> Build Artifacts -> Rebuild
   ```
2. **Terminal Üzerinden Paketleme:**
   ```powershell
   Remove-Item -Recurse -Force dist -ErrorAction SilentlyContinue
   & "C:\Users\Eray\.jdks\openjdk-25\bin\jpackage.exe" `
     --type app-image `
     --input "out/artifacts/Character_Counter_jar" `
     --dest dist `
     --name KarakterAnalizAraci `
     --main-jar "Character Counter.jar" `
     --main-class Main
   ```

---

## Kullanım Adımları

1. Üst panelden hedef maksimum karakter limitini belirleyin (Varsayılan: `50`).
2. Metin alanına analiz etmek istediğiniz cümleyi veya paragrafı yazın.
3. "Büyük / Küçük harf duyarlılığı" kutucuğunu ihtiyacınıza göre işaretleyin.
4. "Aranacak Karakter" kutusuna saymak istediğiniz tek karakteri girin.
5. **"Analiz Et ve Say"** butonuna basarak eşleşen karakter sayısını alt sonuç kartında görüntüleyin.

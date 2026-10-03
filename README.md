# Karakter Frekans Analiz Aracı

Metinler üzerinde detaylı karakter sıklığı analizi yapabilen, dinamik limit kontrolü ve duyarlı (responsive) modern Swing arayüzü sunan masaüstü Java uygulaması.

---

## Genel Bakış

**Karakter Frekans Analiz Aracı**, girilen metinlerin belirlenen maksimum karakter limitine uygunluğunu denetler, istenen hedef karakterin metin içinde kaç defa geçtiğini sayar ve analiz sonuçlarını anlık olarak raporlar. Kullanıcı deneyimini ön planda tutan mimarisi; dinamik ekran boyutu uyumluluğu, tema desteği ve katmanlı yazılım mimarisi ilkelerine göre tasarlanmıştır.

---

## Temel Özellikler

* **Dinamik Karakter Limiti:** Kullanıcı tanımlı limit kontrolü ve limit aşımında anlık görsel uyarılar (renkli canlı sayaç).
* **Esnek Karakter Arama:** İsteğe bağlı olarak **büyük/küçük harf duyarlılığı (Case-Sensitive)** açılıp kapatılabilen hedef karakter analizi.
* **Modern ve Responsive Arayüz:** Ekran çözünürlüğüne göre orantılı esneyen, taşmaları ve bozulmaları engelleyen sınır kontrollü Swing tasarımı.
* **Gece / Gündüz Modu (Dark / Light Theme):** Tek tıkla gözü yormayan koyu tema ile yüksek kontrastlı açık tema arasında geçiş imkanı.
* **Bağımsız Çalıştırılabilir (.exe):** Sistemde Java / JDK kurulumu bulunmasa dahi çalışabilen gömülü runtime paketlemesi.

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
│   └── CharacterAnalyzerFrame.java   # Duyarlı arayüz ve tema yönetim bileşenleri
└── Main.java                         # Uygulama başlangıç noktası (EDT yönetimi)

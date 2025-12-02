# Digital Twin Personal Assist

Proyek Android "Digital Twin Personal Assist" — aplikasi contoh yang menampilkan
UI dengan beberapa fragment, grafik detak jantung, serta koneksi singkat ke API
untuk rekap data. Proyek ini menggunakan Kotlin, Retrofit, dan beberapa
library grafik/3D.

Catatan singkat tentang perubahan:
- Saya menambahkan komentar (KDoc/Javadoc-style) pada banyak kelas dan fungsi
  dalam paket `app/src/main/java/com/example/digitaltwinpersonalassist` untuk
  memperjelas tanggung jawab tiap kelas dan API.

Struktur penting:
- `app/` — modul Android aplikasi.
- `engine/` — modul library (jika ada kode bisnis atau shared logic).
- `app/src/main/java/com/example/digitaltwinpersonalassist` — sumber utama.

Instruksi build & run (macOS, zsh):

1. Pastikan Anda memiliki JDK, Android SDK, dan Gradle wrapper terpasang.

2. Membangun aplikasi (debug):

```bash
./gradlew assembleDebug
```

3. Menjalankan unit tests:

```bash
./gradlew test
```

4. Menjalankan di perangkat/emulator terhubung:

```bash
./gradlew installDebug
```

Catatan tambahan:
- Endpoint API dikonfigurasi di `ApiClient` dengan base URL `https://dtcockpit.foxecho.id`.
- File asset `patient_data.json` digunakan untuk simulasi data dalam `RecapFragment`.
- Beberapa kode 3D (Rajawali) dikomentari sebagai referensi (`Heart3DRenderer.kt`).

Jika Anda ingin, saya dapat:
- Menambahkan atau memperbaiki komentar yang Anda inginkan (gaya bahasa, detail),
- Menjalankan `./gradlew assembleDebug` di lingkungan ini dan melaporkan hasilnya,
- Membuat dokumentasi API yang lebih terstruktur (OpenAPI / Swagger).

Mau saya lanjutkan menambahkan komentar ke file lainnya atau menjalankan build sekarang?

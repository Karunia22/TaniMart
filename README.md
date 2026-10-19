# TaniMart

Aplikasi Android marketplace pertanian yang menghubungkan **pembeli** dan **penjual**
produk tani. Dibangun dengan Kotlin dan Jetpack Compose, terhubung ke backend REST API.

## Fitur

### Pembeli
- Autentikasi: login, register, logout, sesi otomatis tersimpan
- Katalog produk: beranda, daftar produk, detail produk, pencarian
- Keranjang: tambah, ubah jumlah, hapus, kosongkan
- Checkout: pilih alamat, ringkasan, beli sekarang (buy now)
- Pembayaran: pembuatan payment, instruksi pembayaran, QR code, simulasi sukses
- Pesanan: daftar pesanan, detail, filter status
- Alamat: tambah, ubah, hapus, jadikan alamat utama
- Akun: ubah nama, ubah password
- Profil: menu akun, alamat, pesanan, toko saya

### Penjual
- Dashboard penjual
- Manajemen produk: daftar, tambah (upload gambar multipart), detail, edit
- Pesanan penjual: daftar dan ubah status pesanan

## Teknologi

| Kategori     | Library |
|--------------|---------|
| Bahasa       | Kotlin 2.2.10 |
| UI           | Jetpack Compose (BOM 2026.02.01), Material 3, Material Icons Extended |
| Navigasi     | navigation-compose 2.10.2 |
| Arsitektur   | ViewModel (`lifecycle-viewmodel-compose`), StateFlow |
| Networking   | Retrofit 3.0.0 + Gson, OkHttp logging-interceptor 4.12.0 |
| Gambar       | Coil Compose 2.7.0 |
| QR Code      | ZXing core 3.5.3 |
| Serialisasi  | kotlinx-serialization-json 1.7.3 |
| Splash       | androidx.core:core-splashscreen 1.0.1 |
| Build        | Android Gradle Plugin 9.2.1 |

Konfigurasi Android: `minSdk 24`, `targetSdk 36`, `compileSdk 37`, `applicationId com.example.tanimart`.

## Arsitektur

Proyek memakai pemisahan lapisan sederhana (MVVM) tanpa dependency injection framework:
dependensi dirakit manual melalui objek singleton `RetrofitClient` dan `ViewModelFactory`.

```
app/src/main/java/com/example/tanimart/
├── MainActivity.kt                # Entry point, install splash + TokenStorage
├── data/                          # Lapisan data (jaringan)
│   ├── network/                   # RetrofitClient, AuthInterceptor, TokenStorage
│   ├── auth/                      # login, register, logout, session, account
│   ├── address/                   # CRUD alamat
│   ├── product/                   # produk (pembeli & penjual)
│   ├── cart/                      # keranjang
│   ├── checkout/                  # checkout & buy-now
│   ├── payment/                   # pembuatan & simulasi pembayaran
│   └── order/                     # pesanan pembeli & penjual
└── ui/                            # Lapisan UI (per fitur)
    ├── navigation/                # AppNavigation, MainNavigation, bottom bar
    ├── auth/                      # login, register, logout, session
    ├── home/                      # beranda pembeli
    ├── product/                   # katalog & detail produk
    ├── cart/                      # keranjang
    ├── checkout/                  # checkout
    ├── payment/                   # pembayaran & instruksi
    ├── order/                     # pesanan pembeli
    ├── address/                   # daftar/tambah/ubah alamat
    ├── account/                   # pengaturan akun
    ├── profile/                   # profil & menu
    ├── seller/                    # dashboard, produk, pesanan penjual
    ├── components/                # komponen UI bersama (button, text field, kartu produk)
    └── theme/                     # ⚠️ TEMA APLIKASI — jangan diubah (lihat catatan)
```

### Pola per domain di `data/`
`XxxApi` (interface Retrofit) → `XxxRepository` → `XxxRequest`/`XxxResponse`/`XxxDto` + `XxxMapper`.

### Pola per fitur di `ui/`
`XxxScreen.kt` + `XxxUiState.kt` + `XxxViewModel.kt` + `ui/` untuk komponen kecil.
UI bersifat stateless (state dari ViewModel), dikumpulkan dengan `collectAsStateWithLifecycle`.

## Alur Aplikasi

**Sesi** — `AppNavigation` memanggil `SessionViewModel`. Jika token kosong → `Unauthenticated`
(mulai di `login`). Jika ada token → `GET user` untuk validasi; gagal berarti token dihapus
dan kembali ke `login`. Token disimpan di SharedPreferences (`tanimart_session`) oleh
`TokenStorage` dan disuntik otomatis sebagai `Authorization: Bearer <token>` melalui `AuthInterceptor`.

**Belanja** — `product_detail/{id}` → (Buy Now) `checkout/{productId}/{quantity}`, atau melalui
`cart` → `checkout` → `payment/{orderId}` → `payment-instruction/{orderId}` → sukses.

**Penjual** — Profil → "Toko Saya" → `seller_dashboard` → `seller_products`
(tambah/detail/edit) dan `seller_orders`.

**Status pesanan** — Status dinormalisasi di `ui/order/data/OrderUiModel.kt`
(`toOrderStatus()`), sehingga format backend `menunggu_pembayaran` maupun
`Menunggu Pembayaran` sama-sama dikenali. Alur status:
`menunggu_pembayaran` → `diproses` → (penjual menandai "Tandai Dikirim") `dikirim`
→ `selesai`, dengan `dibatalkan` sebagai status akhir alternatif.

## Daftar Endpoint API

Base URL: `http://10.192.224.217:8000/api/` (dikonfigurasi di `data/network/RetrofitClient.kt`).

### Auth & Akun
| Metode | Endpoint | Keterangan |
|--------|----------|------------|
| POST   | `login` | Login |
| POST   | `register` | Registrasi |
| POST   | `logout` | Logout |
| GET    | `user` | Validasi sesi / data user |
| GET    | `account` | Detail akun |
| PATCH  | `account/name` | Ubah nama |
| PATCH  | `account/password` | Ubah password |

### Produk
| Metode | Endpoint | Keterangan |
|--------|----------|------------|
| GET    | `products` | Daftar produk |
| GET    | `products/{productId}` | Detail produk |
| GET    | `seller/products` | Produk milik penjual |
| POST   | `seller/products` | Buat produk (multipart) |
| PATCH  | `products/{productId}` | Ubah produk |

### Keranjang
| Metode | Endpoint | Keterangan |
|--------|----------|------------|
| GET    | `cart` | Isi keranjang |
| POST   | `cart/items` | Tambah item |
| PATCH  | `cart/items/{cartItemId}` | Ubah jumlah |
| DELETE | `cart/items/{cartItemId}` | Hapus item |
| DELETE | `cart` | Kosongkan keranjang |

### Checkout & Pesanan
| Metode | Endpoint | Keterangan |
|--------|----------|------------|
| POST   | `orders/checkout` | Checkout dari keranjang |
| POST   | `orders/buy-now` | Beli sekarang |
| GET    | `orders` | Daftar pesanan |
| GET    | `orders/{orderId}` | Detail pesanan |
| GET    | `seller/orders` | Pesanan penjual |
| PATCH  | `seller/orders/{orderId}/status` | Ubah status pesanan |

### Pembayaran
| Metode | Endpoint | Keterangan |
|--------|----------|------------|
| POST   | `orders/{orderId}/payment` | Pembuatan pembayaran |
| POST   | `orders/{orderId}/payment/simulate-success` | Simulasi pembayaran sukses |

### Alamat
| Metode | Endpoint | Keterangan |
|--------|----------|------------|
| GET    | `addresses` | Daftar alamat |
| POST   | `addresses` | Tambah alamat |
| PATCH  | `addresses/{id}` | Ubah alamat |
| DELETE | `addresses/{id}` | Hapus alamat |
| PATCH  | `addresses/{id}/primary` | Jadikan alamat utama |

## Panduan Pengembangan

> **PENTING — area yang tidak boleh diubah**
>
> - **`app/src/main/res/`** (semua resource: `values/`, `drawable/`, `mipmap-*`, `xml/`)
>   berisi aset dan konfigurasi tampilan aplikasi. **Jangan mengubah, menghapus, atau
>   menambah file di folder ini** tanpa izin, karena dapat merusak tampilan aplikasi.
> - **`app/src/main/java/com/example/tanimart/ui/theme/`** (`Color.kt`, `Theme.kt`, `Type.kt`)
>   adalah **definisi tema aplikasi**. **Jangan mengubah** warna, tipografi, atau
>   komposisi tema di sini.

Saat menambah atau mengubah fitur:
- Taruh logika jaringan pada `data/`, dan UI pada `ui/`.
- Ikuti pola yang sudah ada: `Api` → `Repository` → `ViewModel` → `Screen`.
- Daftarkan route baru di `ui/navigation/MainNavigation.kt`.
- Gunakan komponen bersama di `ui/components/` agar tampilan konsisten.
- Jangan menyentuh `res/` dan `ui/theme/`; gunakan komponen & tema yang sudah ada.

## Setup & Menjalankan

### Kebutuhan
- Android Studio
- JDK 11 (project memakai `sourceCompatibility`/`targetCompatibility` Java 11)
- Android SDK dengan `compileSdk 37`
- Backend TaniMart yang berjalan dan dapat diakses perangkat

### Langkah
1. Clone repositori, lalu buka dengan Android Studio.

2. Sesuaikan Base URL pada `app/src/main/java/com/example/tanimart/data/network/RetrofitClient.kt`.
   - Emulator Android: `http://10.0.2.2:8000/api/`
   - Perangkat fisik: gunakan IP LAN komputer backend, mis. `http://192.168.x.x:8000/api/`

3. Build & jalankan:

   ```bash
   ./gradlew assembleDebug
   ```

   atau tekan **Run** di Android Studio.

> Catatan: `android:usesCleartextTraffic="true"` pada `AndroidManifest.xml` aktif untuk
> mendukung backend HTTP saat pengembangan. Untuk produksi, gunakan HTTPS dan matikan opsi ini.

## Daftar Route Navigasi

Semua route didefinisikan di `ui/navigation/MainNavigation.kt`.

| Route | Layar |
|-------|-------|
| `home` | Beranda (bottom nav) |
| `products` | Daftar produk (bottom nav) |
| `profile` | Profil (bottom nav) |
| `product_detail/{productId}` | Detail produk |
| `cart` | Keranjang |
| `checkout` | Checkout dari keranjang |
| `checkout/{productId}/{quantity}` | Checkout beli sekarang |
| `payment/{orderId}` | Pembayaran |
| `payment-instruction/{orderId}` | Instruksi pembayaran |
| `orders` | Daftar pesanan |
| `order-detail/{orderId}` | Detail pesanan |
| `addresses`, `add_address`, `edit_address/{addressId}` | Alamat (dari checkout) |
| `account`, `account/addresses`, `account/add_address`, `account/edit_address/{addressId}` | Akun & alamat |
| `login`, `register` | Autentikasi |
| `seller_dashboard` | Dashboard penjual |
| `seller_products` | Produk penjual |
| `seller_product_form`, `seller_edit_product/{productId}` | Form produk |
| `seller_product_detail/{productId}` | Detail produk penjual |
| `seller_orders` | Pesanan penjual |
| `seller_order_detail/{orderId}` | Detail pesanan penjual |

## Yang Perlu Diketahui

- **Base URL** masih hardcoded ke IP LAN pengembangan di `RetrofitClient.kt`.
- **Tanpa DI framework** — dependensi dirakit manual via `RetrofitClient` dan `ViewModelFactory`.
- Beberapa route navigasi belum memiliki `composable` terdaftar sehingga berisiko
  `IllegalArgumentException` saat diakses: `forgot_password`, `seller_add_product`,
  `seller_sales`, `seller_stock`, `seller_store`, `seller_settings`.
- Terdapat duplikasi route alamat antara alur umum (`addresses`) dan alur akun (`account/addresses`).

## Rencana Pengembangan (Roadmap)

Rencana tetap untuk menyelesaikan proyek hingga tuntas. Urutan fase wajib
diikuti; **Fase 0 & Fase 1 adalah fondasi**.

```
FASE 0 Integrasi ─► FASE 1 Multi-role ─► FASE 2 Daftar Penjual ─► FASE 3 Dashboard Seller
                                                                        │
                                                    FASE 4 Navigasi ◄───┘
                                                        │
                          FASE 5 Alur Pembeli ──────────┤
                                                        ▼
                                                FASE 6 Website Admin
                                                        │
                                                        ▼
                                                FASE 7 QA & Dokumentasi
```

- [ ] **FASE 0 — Integrasi Dasar**
  - Sentralkan `BASE_URL` via `buildConfigField`, samakan host klien & backend.
  - Jalankan `php artisan serve --host=0.0.0.0 --port=8000`, set `APP_URL`.
- [ ] **FASE 1 — Fondasi Multi-Role**
  - Backend: register menautkan role `customer`; API mengirim array `roles`.
  - Klien: simpan `roles`, helper `isSeller()`, gate menu penjual.
- [ ] **FASE 2 — Pendaftaran Penjual**
  - Form pengajuan (nama toko, deskripsi, alamat usaha, no. telepon).
  - Layar status pengajuan (pending/approved/rejected + catatan admin).
- [ ] **FASE 3 — Kelengkapan Dashboard Seller**
  - Penjualan (`GET /seller/sales/summary`), Stok, Toko Saya (`GET /seller/shop`),
    Pengaturan (`PATCH /seller/shop`).
- [ ] **FASE 4 — Perbaikan Navigasi**
  - Perbaiki `seller_add_product`, daftarkan `seller_sales/stock/store/settings`,
    `forgot_password`; hapus rewrite URL gambar.
- [ ] **FASE 5 — Penyempurnaan Alur Pembeli**
  - Batalkan pesanan, alur pesanan selesai, countdown pembayaran.
- [ ] **FASE 6 — Website Admin (Manajemen + Analisa)**
  - Panel web Laravel (Blade + Tailwind), login sesi di `/admin`.
  - Manajemen: pengguna, pengajuan penjual, toko, produk, pesanan, pembayaran.
  - Analisa: `activity_logs`, halaman aktivitas & statistik penggunaan.
- [ ] **FASE 7 — QA & Dokumentasi**
  - Uji end-to-end semua peran, perbarui README.

## Lisensi

Belum ditentukan.

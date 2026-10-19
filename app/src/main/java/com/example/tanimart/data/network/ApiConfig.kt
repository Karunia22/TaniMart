package com.example.tanimart.data.network

import com.example.tanimart.BuildConfig

/**
 * Konfigurasi terpusat untuk akses backend.
 *
 * Nilai [baseUrl] berasal dari `buildConfigField` di `app/build.gradle.kts`
 * sehingga cukup diubah di satu tempat.
 */
object ApiConfig {

    /**
     * Base URL API, selalu diakhiri dengan `/`.
     * Contoh: `http://192.168.1.15:8000/api/`
     */
    val baseUrl: String = BuildConfig.API_BASE_URL

    /**
     * URL host backend tanpa segmen `/api/`.
     * Contoh: `http://192.168.1.15:8000`
     */
    val hostUrl: String = run {
        val withoutApi = baseUrl.removeSuffix("/api/").removeSuffix("/api")
        withoutApi.trimEnd('/')
    }

    /**
     * Mengubah URL gambar dari backend menjadi URL absolut yang dapat dimuat.
     *
     * Backend biasanya sudah mengirim URL absolut (mengikuti `APP_URL`),
     * tetapi helper ini tetap menangani kemungkinan:
     * - path relatif (`/storage/...`),
     * - URL lama yang menunjuk `localhost`.
     */
    fun toAbsoluteImageUrl(url: String): String {
        if (url.isBlank()) return url

        return when {
            url.startsWith("/") -> "$hostUrl$url"

            url.contains("localhost:8000") ->
                url.replace("http://localhost:8000", hostUrl)

            url.contains("localhost") ->
                url.replace("http://localhost", hostUrl)

            else -> url
        }
    }
}

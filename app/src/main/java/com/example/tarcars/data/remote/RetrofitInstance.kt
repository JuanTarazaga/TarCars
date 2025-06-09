package com.example.tarcars.data.remote

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.InputStream
import java.security.KeyStore
import java.security.cert.CertificateFactory
import javax.net.ssl.SSLContext
import javax.net.ssl.TrustManagerFactory

object RetrofitInstance {

    val api: EmailApiService = Retrofit.Builder()
        .baseUrl("https://api.emailjs.com/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(EmailApiService::class.java)

    private const val COCHES_URL = "https://b862-88-27-37-12.ngrok-free.app"

    val apiCoches: CochesApiService by lazy {
        val client = OkHttpClient.Builder().build()

        Retrofit.Builder()
            .baseUrl(COCHES_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
            .create(CochesApiService::class.java)
    }


//    val apiCoches: CochesApiService by lazy {
//        // Cargo el certificado desde la carpeta 'raw'
//        val inputStream: InputStream = javaClass.classLoader?.getResourceAsStream("res/raw/cert.crt")
//            ?: throw IllegalArgumentException("Certificado no encontrado")
//
//        // Creo un KeyStore con el certificado
//        val keyStore = KeyStore.getInstance(KeyStore.getDefaultType()).apply {
//            load(null, null)
//            setCertificateEntry("cert", CertificateFactory.getInstance("X.509").generateCertificate(inputStream))
//        }
//
//        // Creo un TrustManager para validar el certificado
//        val trustManagerFactory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm()).apply {
//            init(keyStore)
//        }
//
//        // Configuro SSLContext con el TrustManager
//        val sslContext = SSLContext.getInstance("TLS").apply {
//            init(null, trustManagerFactory.trustManagers, java.security.SecureRandom())
//        }
//
//        // Creo un cliente OkHttp con el SSLContext configurado
//        val client = OkHttpClient.Builder()
//            .sslSocketFactory(sslContext.socketFactory, trustManagerFactory.trustManagers[0] as javax.net.ssl.X509TrustManager)
//            .hostnameVerifier { _, _ -> true } // Desactivo la verificación del host (solo para pruebas)
//            .build()
//
//        // Configuro Retrofit con el cliente OkHttp
//        Retrofit.Builder()
//            .baseUrl(COCHES_URL)
//            .addConverterFactory(GsonConverterFactory.create())
//            .client(client)
//            .build()
//            .create(CochesApiService::class.java)
//    }
}

package com.ssafy.d101.utils

import android.content.Context
import android.os.Build
import android.security.KeyPairGeneratorSpec
import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyProperties
import android.util.Base64
import android.util.Log
import java.math.BigInteger
import java.security.GeneralSecurityException
import java.security.KeyPairGenerator
import java.security.KeyStore
import java.security.spec.RSAKeyGenParameterSpec
import java.security.spec.RSAKeyGenParameterSpec.F4
import java.util.Calendar
import java.util.Locale
import javax.crypto.Cipher
import javax.security.auth.x500.X500Principal

object AndroidRsaCipherHelper {
    private const val KEY_LENGTH_BIT = 2048
    private const val VALIDATION_YEARS = 25

    private const val KEY_PROVIDER_NAME = "AndroidKeyStore"
    private const val CIPHER_ALGORITHM =
        "${KeyProperties.KEY_ALGORITHM_RSA}/" + "${KeyProperties.BLOCK_MODE_ECB}/" + KeyProperties.ENCRYPTION_PADDING_RSA_PKCS1

    private lateinit var keyEntry: KeyStore.Entry

    private var _isSupported = false

    val isSupported: Boolean
        get() = _isSupported

    private lateinit var appContext: Context

    internal fun init(applicationContext: Context) {
        if (isSupported) {
            Log.w("Security", "Already initialised - Do not attempt to initialise this twice")
            return
        }

        this.appContext = applicationContext
        val alias = "${appContext.packageName}.rsakeypairs"
        val keyStore = KeyStore.getInstance(KEY_PROVIDER_NAME).apply {
            load(null)
        }

        val result: Boolean
        result = if (keyStore.containsAlias(alias)) {
            true
        } else {
            Log.v("Security", "No keypair found for alias $alias")
            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP_MR1 && initAndroidM(alias)) {
                true
            } else {
                initAndroidL(alias)
            }
        }

        this.keyEntry = keyStore.getEntry(alias, null)
        _isSupported = result
    }

    private fun initAndroidM(alias: String): Boolean {
        try {
            with(KeyPairGenerator.getInstance(KeyProperties.KEY_ALGORITHM_RSA, KEY_PROVIDER_NAME)) {
                val spec = KeyGenParameterSpec.Builder(
                    alias,
                    KeyProperties.PURPOSE_ENCRYPT or KeyProperties.PURPOSE_DECRYPT
                )
                    .setAlgorithmParameterSpec(RSAKeyGenParameterSpec(KEY_LENGTH_BIT, F4))
                    .setBlockModes(KeyProperties.BLOCK_MODE_CBC)
                    .setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_RSA_PKCS1)
                    .setDigests(
                        KeyProperties.DIGEST_SHA512,
                        KeyProperties.DIGEST_SHA384,
                        KeyProperties.DIGEST_SHA256
                    )
                    .setUserAuthenticationRequired(false)
                    .build()
                initialize(spec)
                generateKeyPair()
            }
            Log.i("Security", "KeyPair generated successfully")
            return true
        } catch (e: GeneralSecurityException) {
            Log.w("Security", "Failed to generate KeyPair", e)

            return false
        }
    }

    private fun initAndroidL(alias: String): Boolean {
        try {
            with(KeyPairGenerator.getInstance("RSA", KEY_PROVIDER_NAME)) {
                val start = Calendar.getInstance(Locale.ENGLISH)
                val end = Calendar.getInstance(Locale.ENGLISH)
                    .apply { add(Calendar.YEAR, VALIDATION_YEARS) }
                val spec = KeyPairGeneratorSpec.Builder(appContext)
                    .setKeySize(KEY_LENGTH_BIT)
                    .setAlias(alias)
                    .setSubject(X500Principal("CN=d101App, OU=Android dev"))
                    .setSerialNumber(BigInteger.ONE)
                    .setStartDate(start.time)
                    .setEndDate(end.time)
                    .build()
                initialize(spec)
                generateKeyPair()
            }
            Log.i("Security", "Random RSA algorithm keypair is created.")

            return true
        } catch (e: GeneralSecurityException) {
            Log.w("Security", "It seems that this device does not support encryption!!", e)

            return false
        }
    }

    fun encrypt(plainText: String): String {
        if (!_isSupported) {
            return plainText
        }

        val cipher = Cipher.getInstance(CIPHER_ALGORITHM).apply {
            init(Cipher.ENCRYPT_MODE, (keyEntry as KeyStore.PrivateKeyEntry).certificate.publicKey)
        }
        val bytes = plainText.toByteArray(Charsets.UTF_8)
        val encryptedBytes = cipher.doFinal(bytes)
        val base64EncryptedBytes = Base64.encode(encryptedBytes, Base64.DEFAULT)

        return String(base64EncryptedBytes)
    }

    fun decrypt(base64EncryptedCipherText: String): String {
        if (!_isSupported) {
            return base64EncryptedCipherText
        }

        val cipher = Cipher.getInstance(CIPHER_ALGORITHM).apply {
            init(Cipher.DECRYPT_MODE, (keyEntry as KeyStore.PrivateKeyEntry).privateKey)
        }
        val base64EncryptedBytes = base64EncryptedCipherText.toByteArray(Charsets.UTF_8)
        val encryptedBytes = Base64.decode(base64EncryptedBytes, Base64.DEFAULT)
        val decryptedBytes = cipher.doFinal(encryptedBytes)

        return String(decryptedBytes)
    }
}
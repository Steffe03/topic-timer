package com.example.topictimer

import android.content.Context
import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyProperties
import android.util.Base64
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.security.KeyStore
import javax.crypto.Cipher
import javax.crypto.KeyGenerator
import javax.crypto.SecretKey
import javax.crypto.spec.GCMParameterSpec

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "secure_settings")

class SecurityManager(private val context: Context) {

    private val keyStore = KeyStore.getInstance("AndroidKeyStore").apply { load(null) }
    private val keyAlias = "api_key_alias"
    private val encryptionTransformation = "AES/GCM/NoPadding"

    private fun getSecretKey(): SecretKey {
        val entry = keyStore.getEntry(keyAlias, null) as? KeyStore.SecretKeyEntry
        return entry?.secretKey ?: createSecretKey()
    }

    private fun createSecretKey(): SecretKey {
        val keyGenerator = KeyGenerator.getInstance(KeyProperties.KEY_ALGORITHM_AES, "AndroidKeyStore")
        val spec = KeyGenParameterSpec.Builder(
            keyAlias,
            KeyProperties.PURPOSE_ENCRYPT or KeyProperties.PURPOSE_DECRYPT
        )
            .setBlockModes(KeyProperties.BLOCK_MODE_GCM)
            .setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_NONE)
            .build()
        keyGenerator.init(spec)
        return keyGenerator.generateKey()
    }

    private fun encrypt(data: String): String {
        val cipher = Cipher.getInstance(encryptionTransformation)
        cipher.init(Cipher.ENCRYPT_MODE, getSecretKey())
        val iv = cipher.iv
        val encryptedData = cipher.doFinal(data.toByteArray())
        
        // Combine IV and encrypted data for storage (IV is needed for decryption)
        val combined = ByteArray(iv.size + encryptedData.size)
        System.arraycopy(iv, 0, combined, 0, iv.size)
        System.arraycopy(encryptedData, 0, combined, iv.size, encryptedData.size)
        
        return Base64.encodeToString(combined, Base64.NO_WRAP)
    }

    private fun decrypt(encryptedDataWithIv: String): String? {
        return try {
            val combined = Base64.decode(encryptedDataWithIv, Base64.NO_WRAP)
            val cipher = Cipher.getInstance(encryptionTransformation)
            val ivSize = 12 // GCM default IV size
            val iv = ByteArray(ivSize)
            System.arraycopy(combined, 0, iv, 0, ivSize)
            
            val encryptedData = ByteArray(combined.size - ivSize)
            System.arraycopy(combined, ivSize, encryptedData, 0, encryptedData.size)
            
            cipher.init(Cipher.DECRYPT_MODE, getSecretKey(), GCMParameterSpec(128, iv))
            String(cipher.doFinal(encryptedData))
        } catch (_: Exception) {
            null
        }
    }

    private val apiKeyPref = stringPreferencesKey("api_key")

    suspend fun saveApiKey(apiKey: String) {
        val encrypted = encrypt(apiKey)
        context.dataStore.edit { preferences ->
            preferences[apiKeyPref] = encrypted
        }
    }

    suspend fun clearApiKey() {
        context.dataStore.edit { it.remove(apiKeyPref) }
    }

    val apiKeyFlow: Flow<String?> = context.dataStore.data.map { preferences ->
        preferences[apiKeyPref]?.let { decrypt(it) }
    }
}

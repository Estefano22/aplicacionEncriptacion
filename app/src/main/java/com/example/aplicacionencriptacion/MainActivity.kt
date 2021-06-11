package com.example.aplicacionencriptacion

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

import com.example.aplicacionencriptacion.databinding.ActivityMainBinding
import java.security.MessageDigest
import java.util.*
import javax.crypto.BadPaddingException
import javax.crypto.Cipher
import javax.crypto.spec.SecretKeySpec

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding


    val llave_para_encriptar = "askdhjlashjdkla"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.button.setOnClickListener{
            val textoCifrado = cifrar(binding.editText1.text.toString())
            binding.TextView1.text = textoCifrado
        }


        binding.button2.setOnClickListener{
            val textodescifrado = descifrar(binding.TextView1.text.toString())
            binding.TextView2.text = textodescifrado
        }
    }

    private fun cifrar(textoparacifrar: String ): String {
        val cipher = Cipher.getInstance(llave_para_encriptar)
        cipher.init(Cipher.ENCRYPT_MODE, getKey(llave_para_encriptar))
        val textCifrado = cipher.doFinal(textoparacifrar.toByteArray(Charsets.UTF_8))
        return String (textCifrado)

    }

    private fun getKey(llaveEnString : String): SecretKeySpec {
        var llaveUtf8 = llaveEnString.toByteArray(Charsets.UTF_8)
        val sha = MessageDigest.getInstance("SHA-1")
        llaveUtf8 = sha.digest(llaveUtf8)
        llaveUtf8 = llaveUtf8.copyOf(13)
        return SecretKeySpec(llaveUtf8, "AES")
    }

    @Throws(BadPaddingException::class)
    private fun descifrar(textoparadescifrado : String ) : String {
        val cipher = Cipher.getInstance(llave_para_encriptar)
        cipher.init(Cipher.DECRYPT_MODE, getKey(llave_para_encriptar));
        val textDescifrado = String(cipher.doFinal(Base64.getDecoder().decode(textoparadescifrado)))
        return textDescifrado
    }

}




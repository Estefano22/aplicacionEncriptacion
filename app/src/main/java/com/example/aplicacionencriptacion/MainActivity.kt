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

companion object {
    const val llave_para_encriptar = "12wertyu56uhj"
    const val Tipo_cifrado = "AES/ECB/PKCS5Padding"
}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.buttonCifrar.setOnClickListener{
            val textCifrado = cifrar(binding.editText1.text.toString())
            binding.TextView1.text = textCifrado
        }


        binding.buttonDescifrar.setOnClickListener{
            val textDescifrado = descifrar(binding.TextView1.text.toString())
            binding.TextView2.text = textDescifrado
        }
    }

    private fun cifrar(textoparacifrar: String ): String {
        val cipher = Cipher.getInstance(Tipo_cifrado)
        cipher.init(Cipher.ENCRYPT_MODE, getKey(llave_para_encriptar))
        val textCifrado = Base64.getEncoder().encodeToString(cipher.doFinal(textoparacifrar.toByteArray(Charsets.UTF_8)))
        return textCifrado

    }

    private fun getKey(llaveEnString : String): SecretKeySpec {
        var llaveUtf8 = llaveEnString.toByteArray(Charsets.UTF_8)
        val sha = MessageDigest.getInstance("SHA-1")
        llaveUtf8 = sha.digest(llaveUtf8)
        llaveUtf8 = llaveUtf8.copyOf(16)
        return SecretKeySpec(llaveUtf8, "AES")
    }

    @Throws(BadPaddingException::class)
    private fun descifrar(textoparadescifrar : String ) : String {
        val cipher = Cipher.getInstance(Tipo_cifrado)
        cipher.init(Cipher.DECRYPT_MODE, getKey(llave_para_encriptar));
        val textDescifrado = String(cipher.doFinal(Base64.getDecoder().decode(textoparadescifrar)))
        return textDescifrado
    }

}




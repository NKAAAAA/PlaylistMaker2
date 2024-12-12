package com.example.playlistmaker

import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.widget.Toast
import android.util.Log
import android.os.Bundle
import android.widget.Button

class SettingsActivity : BaseActivity() { // Наследуемся от BaseActivity для управления темой
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_settings)

        // Находим кнопки по их ID
        val btnLightTheme = findViewById<Button>(R.id.btnThemeD)
        val btnDarkTheme = findViewById<Button>(R.id.btnThemeN)
        val btnBackToMain = findViewById<Button>(R.id.btnBack)
        val btnWriteToSupport = findViewById<Button>(R.id.btnWriteToSupport)

        btnLightTheme.setOnClickListener {
            if (getCurrentTheme() != THEME_LIGHT) {
                saveThemePreference(THEME_LIGHT) // Сохраняем выбор темы
                recreate() // Применяем изменения
            }
        }

        btnDarkTheme.setOnClickListener {
            if (getCurrentTheme() != THEME_DARK) {
                saveThemePreference(THEME_DARK) // Сохраняем выбор темы
                recreate() // Применяем изменения
            }
        }

        btnBackToMain.setOnClickListener {
            finish() // Закрываем текущую активность и возвращаемся на предыдущую
        }

        btnWriteToSupport.setOnClickListener {
            // Создаем Intent для отправки email
            val intent = Intent(Intent.ACTION_SEND).apply {
                type = "message/rfc822" // Указываем MIME-тип для email
                putExtra(Intent.EXTRA_EMAIL, arrayOf(getString(R.string.support_email))) // Email-адрес
                putExtra(Intent.EXTRA_SUBJECT, getString(R.string.email_subject)) // Тема письма
                putExtra(Intent.EXTRA_TEXT, getString(R.string.email_body)) // Текст письма
            }

            // Проверяем, установлено ли почтовое приложение
            if (intent.resolveActivity(packageManager) != null) {
                startActivity(Intent.createChooser(intent, "Выберите почтовое приложение")) // Запускаем почтовое приложение
            } else {
                // Если почтовый клиент отсутствует, выводим сообщение пользователю
                Toast.makeText(this, "На устройстве нет установленного почтового клиента", Toast.LENGTH_SHORT).show()
            }
        }

        // Находим кнопку Пользовательского соглашения
        val btnPrivacyPolicy = findViewById<Button>(R.id.btnPrivacyPolicy)

        btnPrivacyPolicy.setOnClickListener {
            val url = getString(R.string.privacy_policy_url)
            val intent = Intent(Intent.ACTION_VIEW).apply {
                data = Uri.parse(url)
            }

            try {
                startActivity(Intent.createChooser(intent, "Выберите браузер"))
            } catch (e: Exception) {
                Toast.makeText(this, "Ошибка при открытии браузера", Toast.LENGTH_SHORT).show()
                Log.e("PrivacyPolicy", "Error: ${e.message}")
            }
        }
    }

    private fun getCurrentTheme(): String {
        // Получаем текущую тему из SharedPreferences
        return getSharedPreferences("PlaylistMakerPrefs", MODE_PRIVATE)
            .getString(THEME_PREF_KEY, THEME_LIGHT) ?: THEME_LIGHT
    }
}
package com.example.playlistmaker

import android.content.Intent
import android.os.Bundle
import android.widget.Button

class MainActivity : BaseActivity() { // Наследуемся от BaseActivity
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_screen)

        // Настройка кнопок
        val btnSettings = findViewById<Button>(R.id.btnSettings)
        val btnSearch = findViewById<Button>(R.id.btnSearch)
        val btnMedia = findViewById<Button>(R.id.btnMedia)
        val btnShareApp = findViewById<Button>(R.id.btnShareApp)

        btnSettings.setOnClickListener {
            val settingsIntent = Intent(this, SettingsActivity::class.java)
            startActivity(settingsIntent)
        }

        btnSearch.setOnClickListener {
            val searchIntent = Intent(this, SearchActivity::class.java)
            startActivity(searchIntent)
        }

        // Заглушка для кнопки Media
        btnMedia.setOnClickListener {
            // Реализуйте переход, если нужно
        }

        // Обработчик для кнопки "Поделиться приложением"
        btnShareApp.setOnClickListener {
            shareApp()
        }
    }

    // Метод для запуска диалога шаринга
    private fun shareApp() {
        val shareIntent = Intent(Intent.ACTION_SEND)
        shareIntent.type = "text/plain"
        shareIntent.putExtra(Intent.EXTRA_TEXT, getString(R.string.share_message))
        startActivity(Intent.createChooser(shareIntent, "Share via"))
    }
}
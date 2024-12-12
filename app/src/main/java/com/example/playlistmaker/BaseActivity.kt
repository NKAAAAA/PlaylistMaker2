package com.example.playlistmaker

import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate

abstract class BaseActivity : AppCompatActivity() {

    companion object {
        const val THEME_PREF_KEY = "app_theme"
        const val THEME_LIGHT = "light"
        const val THEME_DARK = "dark"
    }

    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        // Инициализация SharedPreferences
        sharedPreferences = getSharedPreferences("PlaylistMakerPrefs", MODE_PRIVATE)

        // Применяем тему до вызова super.onCreate() и setContentView()
        applySavedTheme()

        super.onCreate(savedInstanceState)
    }

    private fun applySavedTheme() {
        val currentMode = AppCompatDelegate.getDefaultNightMode()
        val savedTheme = when (sharedPreferences.getString(THEME_PREF_KEY, THEME_LIGHT)) {
            THEME_LIGHT -> AppCompatDelegate.MODE_NIGHT_NO
            THEME_DARK -> AppCompatDelegate.MODE_NIGHT_YES
            else -> AppCompatDelegate.MODE_NIGHT_NO
        }

        // Применяем тему только если она изменилась
        if (currentMode != savedTheme) {
            AppCompatDelegate.setDefaultNightMode(savedTheme)
        }
    }

    fun saveThemePreference(theme: String) {
        // Сохраняем тему и перезапускаем активность
        sharedPreferences.edit()
            .putString(THEME_PREF_KEY, theme)
            .apply()
    }
}
package com.example.playlistmaker

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import com.google.android.material.button.MaterialButton  // Импортируем MaterialButton
import android.widget.ImageButton
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class SearchActivity : AppCompatActivity() {

    private lateinit var searchInput: EditText
    private lateinit var clearButton: ImageButton
    private lateinit var backButton: MaterialButton  // Используем MaterialButton для кнопки "Назад"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_search)

        // Устанавливаем отступы для экрана
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.search_screen)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Инициализация элементов
        searchInput = findViewById(R.id.searchInput)
        clearButton = findViewById(R.id.clearButton)
        backButton = findViewById(R.id.btnBack)  // Инициализация кнопки "Назад" как MaterialButton

        // Обработчик изменений в поле ввода
        searchInput.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                // Показываем кнопку очистки, если текст не пустой
                clearButton.visibility = if (!s.isNullOrEmpty()) View.VISIBLE else View.GONE
            }

            override fun afterTextChanged(s: Editable?) {}
        })

        // Обработчик для кнопки очистки
        clearButton.setOnClickListener {
            searchInput.text.clear()
            hideKeyboard()  // Скрыть клавиатуру после очистки текста
        }

        // Обработчик для кнопки "Назад"
        backButton.setOnClickListener {
            onBackPressed()  // Вернуться на предыдущую активность
        }
    }

    // Метод для скрытия клавиатуры
    private fun hideKeyboard() {
        val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(searchInput.windowToken, 0)
    }
}
package com.example.libraryapp

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment

class MainActivity : AppCompatActivity() {
    // Declaramos una variable para el controlador de navegación
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Inicializamos el modo edge-to-edge para que la interfaz se extienda bajo las barras del sistema
        enableEdgeToEdge()
        // Establece el diseño de la actividad
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Configuramos el NavController para que pueda manejar las acciones de navegación
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.container) as NavHostFragment
        navController = navHostFragment.navController

    }
}
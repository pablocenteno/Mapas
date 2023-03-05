package com.example.mapas

import android.Manifest
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.view.isVisible
import com.example.mapas.databinding.ActivityMainBinding
import com.mapbox.common.location.compat.permissions.PermissionsManager

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        binding.button.setOnClickListener{mostrarMarcador()}
        binding.ubicacion.setOnClickListener{mostrarUbi()}

        solicitarPermisos()
        setContentView(binding.root)
    }

    fun mostrarMarcador(){
        val transaccion = supportFragmentManager.beginTransaction()
        val fragmento=Marcador()

        transaccion.replace(R.id.fragmentContainerView2, fragmento)
        transaccion.addToBackStack(null)
        binding.button.isVisible=false
        binding.ubicacion.isVisible=true


        transaccion.commit()
    }
    fun mostrarUbi(){
        val transaccion = supportFragmentManager.beginTransaction()
        val fragmento=Localizacion()

        transaccion.replace(R.id.fragmentContainerView2, fragmento)
        transaccion.addToBackStack(null)
        binding.button.isVisible=true
        binding.ubicacion.isVisible=false
        transaccion.commit()
    }

    fun solicitarPermisos(): Boolean {
        if (!PermissionsManager.areLocationPermissionsGranted(this)) {
            locationPermissionRequest.launch(
                arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.INTERNET
                )
            )
            if (PermissionsManager.areLocationPermissionsGranted(this))
                return true;
            else {
                false
            }
        }
        return true;
    }

    val locationPermissionRequest = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        when {
            permissions.getOrDefault(Manifest.permission.ACCESS_FINE_LOCATION, false) -> {
                // Precise location access granted.
            }
            permissions.getOrDefault(Manifest.permission.ACCESS_COARSE_LOCATION, false) -> {
                // Only approximate location access granted.
            }

            else -> {
                val toast = Toast.makeText(
                    this,
                    "Es necesario acceso a la ubicacion apra cargar MapBox",
                    Toast.LENGTH_SHORT
                )
                toast.show()
            }
        }
    }
}
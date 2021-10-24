package com.miempresa.widget

import android.appwidget.AppWidgetManager
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_datos_widgets.*

private var widgetId = 0
class datosWidgets : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_datos_widgets)


        val recibidowidget = intent
        val parametros = recibidowidget.extras
        if (parametros != null){
            widgetId = parametros.getInt(
                AppWidgetManager.EXTRA_APPWIDGET_ID,
                AppWidgetManager.INVALID_APPWIDGET_ID,

                )
        }
        setResult(RESULT_CANCELED)

        botonenviar.setOnClickListener(View.OnClickListener {
            val datos = getSharedPreferences("DatosWidget", MODE_PRIVATE)//guardado de datos en memoria clave valor
            val editor  = datos.edit()//editara
            editor.putString("ciudad",txtcuidad.getText().toString())//en esta parte estamos editando el txtEnviar
            editor.putString("pais",txtpais.getText().toString())
            editor.putString("temperatura",txttemp.getText().toString())
            editor.commit()//confirmo los cambios

            //llamar a la funcion de mi widget
            val notificarwidget = AppWidgetManager.getInstance(this)
            val usoClaseWidget = widget_tiempo()
            usoClaseWidget.actualizarWidget(this,notificarwidget, widgetId)

            //ejecutar el widget y hacer que se cierre la actividad
            val resultado = Intent()
            resultado.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, widgetId)
            setResult(RESULT_OK,resultado)
            finish()
        })
        botoncancelar.setOnClickListener(View.OnClickListener { finish() }) // puede servir sin el View.OnClickListener

    }
}
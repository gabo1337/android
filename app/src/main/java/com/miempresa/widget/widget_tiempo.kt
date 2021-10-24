package com.miempresa.widget

import android.annotation.SuppressLint
import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.content.Intent
import android.widget.RemoteViews
import androidx.appcompat.app.AppCompatActivity
import java.text.SimpleDateFormat
import java.util.*

/**
 * Implementation of App Widget functionality.
 */

const val control_widget = "control_widget"
class widget_tiempo : AppWidgetProvider() {
    fun actualizarWidget(context: Context, appWidgetManager: AppWidgetManager, widgetId: Int) {

        val datos = context.getSharedPreferences("DatosWidget", Context.MODE_PRIVATE)
        val ciudad = datos.getString("ciudad","Lima")//aca estamos recibiendo lo que el usuario envio
        val pais = datos.getString("pais","Peru")
        val temperatura = datos.getString("temperatura","15°")

        val controles = RemoteViews(context.packageName, R.layout.widget_tiempo)//acceder al diseño de mi widged
        controles.setTextViewText(R.id.ciudad,ciudad)
        val sdfDate = SimpleDateFormat("HH:mm:ss a")
        val now = Date()
        val hora = sdfDate.format(now)
        controles.setTextViewText(R.id.lblHora, hora)

        controles.setTextViewText(R.id.pais,pais)
        controles.setTextViewText(R.id.temperatura,temperatura+"°")

        val fecha = SimpleDateFormat("MM/dd/yy")
        val ahora = Date()
        val fecha2 = fecha.format(ahora)
        controles.setTextViewText(R.id.fecha,fecha2)


        val clickwidget = Intent(context,datosWidgets::class.java)
        val widgetesperado = PendingIntent.getActivity(
            context,
            widgetId,
            clickwidget,
            PendingIntent.FLAG_UPDATE_CURRENT
        )
        controles.setOnClickPendingIntent(R.id.frmWidget,widgetesperado)




        val botonwidget=Intent(context,widget_tiempo::class.java)
        botonwidget.action= control_widget
        botonwidget.putExtra("appWidgetId",widgetId)

        val botonespera = PendingIntent.getBroadcast(
            context,
            0,
            botonwidget,
            PendingIntent.FLAG_UPDATE_CURRENT
        )
        controles.setOnClickPendingIntent(R.id.btnAct,botonespera)
        appWidgetManager.updateAppWidget(widgetId,controles)
        }

    override fun onUpdate(
        context: Context,
        appWidgetManager: AppWidgetManager,
        appWidgetIds: IntArray
    ) {
        for (appWidgetId in appWidgetIds){
            actualizarWidget(context,appWidgetManager,appWidgetId)
        }
    }


    override fun onReceive(context: Context?, intent: Intent?) {
        if (control_widget==intent?.action){
            val widgetId = intent.getIntExtra("appWidgetId",0)
            actualizarWidget(context!!, AppWidgetManager.getInstance(context),widgetId)
        }
        super.onReceive(context, intent)
    }

}

internal fun updateAppWidget(
    context: Context,
    appWidgetManager: AppWidgetManager,
    appWidgetId: Int
) {
    val widgetText = context.getString(R.string.appwidget_text)
    // Construct the RemoteViews object
    val views = RemoteViews(context.packageName, R.layout.widget_tiempo)

    // Instruct the widget manager to update the widget
    appWidgetManager.updateAppWidget(appWidgetId, views)
}
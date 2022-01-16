package com.emirsanchez.mypokedex

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkRequest
import androidx.lifecycle.MutableLiveData

// este LiveData personalizado es para mantener el valor booleano
// para la conexión de red disponible o no disponible.
class ConnectivityLiveData(
    private val connectivityManager: ConnectivityManager
): MutableLiveData<Boolean>() {

    // esta ayuda para obtener Context.CONNECTIVITY_SERVICE directamente dentro de LiveData.
    constructor(
        application: Application
    ) : this(
        application.getSystemService(
            Context
                .CONNECTIVITY_SERVICE
        )
                as ConnectivityManager
    )

    //esto Maneja la disponibilidad de la red que recibe, devuelve una llamada cuando
    // la red está disponible o se pierde.
    private val networkCallback = object : ConnectivityManager.NetworkCallback() {
        override fun onAvailable(network: Network) {
            super.onAvailable(network)
            //4
            postValue(true)
        }

        // Cuando la red no está disponible, actualiza el valor de forma asíncrona para notificar
        // a sus observadores activos con un valor falso.
        override fun onLost(network: Network) {
            super.onLost(network)
            // 5
            postValue(false)
        }
    }

    // Cuando la red está disponible, actualiza el valor de forma asincrónica para notificar
    // a sus observadores activos con el valor real.
    override fun onActive() {
        super.onActive()
        val builder = NetworkRequest.Builder()
        // 6
        connectivityManager.registerNetworkCallback(builder.build(), networkCallback)
    }

    //Se registra para devoluciones de llamada de red cuando hay al menos un observador activo para LiveData.
    override fun onInactive() {
        super.onInactive()
        // 7
        //Cancela los registros para las devoluciones de llamadas de la red cuando
        // no hay observadores activos para LiveData.
        connectivityManager.unregisterNetworkCallback(networkCallback)
    }
}
package com.example.mapbox_demo

import android.annotation.SuppressLint
import android.content.Context
import android.view.View
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import com.mapbox.maps.*
import com.mapbox.maps.plugin.Plugin
import io.flutter.plugin.common.BinaryMessenger
import io.flutter.plugin.common.StandardMessageCodec
import io.flutter.plugin.platform.PlatformView
import io.flutter.plugin.platform.PlatformViewFactory
import com.mapbox.bindgen.Value
import com.mapbox.common.TileDataDomain
import com.mapbox.common.TileStore
import com.mapbox.common.TileStoreOptions
import com.mapbox.maps.ResourceOptions
import com.mapbox.maps.TileStoreUsageMode

class MapViewWrapper(
    context: Context, accessToken: String
) : DefaultLifecycleObserver {
    val mapView: MapView

    init {
        mapView = MapView(
            context, mapInitOptions = MapInitOptions(
                context,
                resourceOptions = ResourceOptions.Builder().accessToken(accessToken)
                    .tileStore(TileStore.create().also {
                        it.setOption(
                            TileStoreOptions.MAPBOX_ACCESS_TOKEN,
                            TileDataDomain.MAPS,
                            Value(accessToken),
                        )
                    }).tileStoreUsageMode(TileStoreUsageMode.READ_AND_UPDATE).build(),
                plugins = listOf(
                    Plugin.Mapbox(Plugin.MAPBOX_CAMERA_PLUGIN_ID),
                    Plugin.Mapbox(Plugin.MAPBOX_GESTURES_PLUGIN_ID),
                )
            )
        )
    }

    // We need to manually implement lifecycle, since FlutterActivity is not really an appcompat
    // activity and the default MapBox lifecycle plugin won't be able to register itself

    @SuppressLint("Lifecycle")
    fun dispose() {
        mapView.onDestroy()
    }

    @SuppressLint("Lifecycle")
    override fun onStart(owner: LifecycleOwner) {
        mapView.onStart()
    }

    @SuppressLint("Lifecycle")
    override fun onStop(owner: LifecycleOwner) {
        mapView.onStop()
    }

    @SuppressLint("Lifecycle")
    override fun onDestroy(owner: LifecycleOwner) {
        mapView.onDestroy()
    }
}

class NativeMapView(
    context: Context, accessToken: String, private val lifecycle: Lifecycle
) : PlatformView {
    private var mapViewWrapper: MapViewWrapper?

    init {
        mapViewWrapper = MapViewWrapper(
            context, accessToken
        )
        lifecycle.addObserver(mapViewWrapper!!)
    }

    override fun getView(): View {
        return mapViewWrapper!!.mapView
    }

    override fun dispose() {
        lifecycle.removeObserver(mapViewWrapper!!)
        mapViewWrapper!!.dispose()

        mapViewWrapper = null
    }

    class Factory(private val lifecycle: Lifecycle) :
        PlatformViewFactory(StandardMessageCodec.INSTANCE) {
        override fun create(context: Context?, viewId: Int, args: Any?): PlatformView {
            val accessToken = args as String
            return NativeMapView(context!!, accessToken, lifecycle)
        }
    }
}

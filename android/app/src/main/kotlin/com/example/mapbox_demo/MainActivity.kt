package com.example.mapbox_demo

import io.flutter.embedding.android.FlutterActivity
import io.flutter.embedding.engine.FlutterEngine

class MainActivity : FlutterActivity() {
      override fun configureFlutterEngine(flutterEngine: FlutterEngine) {
        super.configureFlutterEngine(flutterEngine)
        flutterEngine
            .platformViewsController
            .registry
            .registerViewFactory(
                "NativeMapView",
                NativeMapView.Factory(flutterEngine.dartExecutor.binaryMessenger, lifecycle)
            )
     }
}

# mapbox_demo

Small example showing problem of MapBox integration with flutter platform views.

<!-- ## How to run

1. Add your mapbox access token to [gradle.properties](android/gradle.properties) (by replacing the value of `MAPBOX_DOWNLOADS_TOKEN`) and [main.dart](lib/main.dart) (by replacing the value of `accessToken`)
2. Run on an android device -->

## Reproducing the problem

1. Run the app, you will see a map on the middle of the screen. You can interact with it by dragging the map.
2. Open a secondary screen by pressing the button.
3. Minimize the app, and return to it (pause -> resume).
4. See the map being drawn over everything, but it is not interactive.

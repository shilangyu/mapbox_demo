import 'package:flutter/material.dart';
import 'package:mapbox_demo/native_maps.dart';

void main() {
  runApp(const MyApp());
}

class MyApp extends StatelessWidget {
  const MyApp({super.key});

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: 'Flutter Demo',
      theme: ThemeData(
        primarySwatch: Colors.blue,
      ),
      home: const MyHomePage(
          accessToken:
              'pk.eyJ1IjoibWFyY2luLWxlYW5jb2RlIiwiYSI6ImNsOW8yMmh6ajBjb3gzbm81bzZlNXUyaW4ifQ.JASgajUrZzcvdPXN0gIHQw'),
    );
  }
}

class MyHomePage extends StatelessWidget {
  const MyHomePage({super.key, required this.accessToken});

  final String accessToken;

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: const Text('MapBox'),
      ),
      body: Center(
        child: Column(
          mainAxisAlignment: MainAxisAlignment.center,
          children: [
            SizedBox(
              height: 300,
              child: NativeMaps(
                accessToken: accessToken,
              ),
            ),
            ElevatedButton(
              onPressed: () {
                Navigator.of(context).push(
                  MaterialPageRoute(builder: (context) => const OtherScreen()),
                );
              },
              child: const Text('go to other screen'),
            ),
          ],
        ),
      ),
    );
  }
}

class OtherScreen extends StatelessWidget {
  const OtherScreen({super.key});

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: const Text('other screen'),
      ),
      body: ListView(
        children: [
          for (var i = 0; i < 100; i++) const Center(child: Text('content'))
        ],
      ),
    );
  }
}

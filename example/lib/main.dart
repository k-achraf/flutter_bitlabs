import 'package:flutter/material.dart';
import 'dart:async';

import 'package:flutter/services.dart';
import 'package:bitlabs_plugin/bitlabs_plugin.dart';

void main() {
  runApp(const MyApp());
}

class MyApp extends StatefulWidget {
  const MyApp({Key? key}) : super(key: key);

  @override
  State<MyApp> createState() => _MyAppState();
}

class _MyAppState extends State<MyApp> {
  @override
  void initState() {
    super.initState();
    BitlabsPlugin.instance
        .init(token: 'XXXXXXXXXXXXXXXXXXXXXXXXXX', userId: 'Your user id');

    BitlabsPlugin.instance.onRewarded((reward) {
      print('rewarded: $reward');
    });
  }

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      home: Scaffold(
        appBar: AppBar(
          title: const Text('Bitlabs plugin'),
        ),
        body: Center(
          child: InkWell(
            child: const Text('Open suervey wall'),
            onTap: () {
              BitlabsPlugin.instance.show();
            },
          ),
        ),
      ),
    );
  }
}

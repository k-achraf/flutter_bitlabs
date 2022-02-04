import 'dart:async';

import 'package:flutter/services.dart';

typedef OnRewarded = void Function(double reward);

class BitlabsPlugin {
  static OnRewarded? _onRewarded;

  static BitlabsPlugin get instance => _instance;
  final MethodChannel _channel;

  static final BitlabsPlugin _instance = BitlabsPlugin.private(
    const MethodChannel('bitlabs'),
  );

  BitlabsPlugin.private(MethodChannel channel) : _channel = channel {
    _channel.setMethodCallHandler(_platformCallHandler);
  }

  Future init({required String token, required String userId}) {
    return _channel.invokeMethod(
        'init', <String, String>{'token': token, 'userId': userId});
  }

  Future show() {
    return _channel.invokeMethod('show');
  }

  Future _platformCallHandler(MethodCall call) async {
    if (call.method == 'onRewarded') {
      _onRewarded?.call(call.arguments['payout']);
    }
  }

  void onRewarded(OnRewarded onRewardedListener) {
    _onRewarded = onRewardedListener;
  }
}

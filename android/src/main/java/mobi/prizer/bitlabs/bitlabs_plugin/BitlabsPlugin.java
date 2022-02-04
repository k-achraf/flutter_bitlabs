package mobi.prizer.bitlabs.bitlabs_plugin;

import android.app.Activity;
import android.util.Log;

import androidx.annotation.NonNull;

import java.util.HashMap;
import java.util.Map;

import ai.bitlabs.sdk.BitLabsSDK;
import io.flutter.embedding.engine.plugins.FlutterPlugin;
import io.flutter.embedding.engine.plugins.activity.ActivityAware;
import io.flutter.embedding.engine.plugins.activity.ActivityPluginBinding;
import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;
import io.flutter.plugin.common.MethodChannel.MethodCallHandler;
import io.flutter.plugin.common.MethodChannel.Result;

/** BitlabsPlugin */
public class BitlabsPlugin implements FlutterPlugin, MethodCallHandler, ActivityAware {

  private MethodChannel channel;
  private FlutterPluginBinding binding = null;
  private Activity activity = null;


  @Override
  public void onAttachedToEngine(@NonNull FlutterPluginBinding binding) {
    channel = new MethodChannel(binding.getBinaryMessenger(), "bitlabs");
    channel.setMethodCallHandler(this);
    this.binding = binding;
  }

  @Override
  public void onDetachedFromEngine(@NonNull FlutterPluginBinding binding) {
    channel.setMethodCallHandler(null);
    this.binding = null;
  }

  @Override
  public void onAttachedToActivity(@NonNull ActivityPluginBinding binding) {
    activity = binding.getActivity();
  }

  @Override
  public void onDetachedFromActivityForConfigChanges() {

  }

  @Override
  public void onReattachedToActivityForConfigChanges(@NonNull ActivityPluginBinding binding) {

  }

  @Override
  public void onDetachedFromActivity() {
    activity = null;
  }

  public void initBitlabs(final Activity activity, final String token, final String userId){
    BitLabsSDK.Companion.init(activity, token, userId);
    BitLabsSDK.Companion.onReward(
            payout-> {
              Map<String, Float> result = new HashMap<>();
              result.put("payout", payout);
              channel.invokeMethod("onRewarded", result);
            }
    );
  }

  public void show(final Activity activity){
    BitLabsSDK.Companion.show(activity);
  }

  private void extractParams(Activity activity, MethodCall call, Result result){
    String token = "";
    String userId = "";

    if(call.argument("token") != null){
      token = call.argument("token");
    }
    if(call.argument("userId") != null){
      userId = call.argument("userId");
    }

    if(token == null){
      result.error("n_token", "no token provided", null);
      return;
    }

    if(userId == null){
      result.error("no_user_id", "no user id provided", null);
    }

    if(binding != null){
      initBitlabs(activity, token, userId);
    }
  }

  @Override
  public void onMethodCall(@NonNull MethodCall call, @NonNull Result result) {
    if(call.method.equals("init")){
      if(activity != null){
        extractParams(activity, call, result);
      }
    }
    if(call.method.equals("show")){
      show(activity);
    }
  }
}

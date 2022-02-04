# bitlabs_plugin

Bitlabs Flutter Plugin allows integration of Bitlabs surveys into Android apps.

#Prerequisites

* Null safety
* minSdk: 16

#Quick Guide

* Create bitlabs Account,
* Create application in your account
* Configure your currency and your survey wall design
* Implement your reward callback url

#Installation

Add this to your package's pubspec.yaml file:

```
bitlabs_plugin: ^0.9.0
```

Execute the following command

````
flutter packages get
````

#Initialization
Get your API Token from your bitlabs application dashboard,

````dart
BitlabsPlugin.instance.init(
  token: 'XXXXXXXXXXXXXXXXXXXXXXXXXX',
  userId: 'Your user id'
);
````

#Get notified when a Pollfish survey is completed

You can get notified when a user completed a survey. With this notification, you can also get informed about the money earned from that survey in USD cents.

````dart
BitlabsPlugin.instance.onRewarded((reward) {
  print('rewarded: $reward');
});
````

#Show the survey wall

You need to show the survey wall. this function opens the survey wall and show the serveys are available to this user.

````dart
BitlabsPlugin.instance.show();
````

#More info

You can read more info on how the Native Bitlabs SDKs work on Android

[Bitlabs doucumentation](https://bitlabs.ai/integrations/android-sdk)




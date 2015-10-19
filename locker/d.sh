#!/bin/bash
#jar cvf libs/armeabi.jar lib
VERSION_NAME=`perl -e 'while($line=<>) { if ($line=~ /versionName\s*=\s*"([^"]+)"/) { print "$1\n";}}' < AndroidManifest.xml`
VERSION_CODE=`perl -e 'while($line=<>) { if ($line=~ /versionCode\s*=\s*"([^"]+)"/) { print "$1\n";}}' < AndroidManifest.xml`
gradle asD --parallel-threads 3
rm build/outputs/apk/*unaligned*.apk
mv build/outputs/apk/locker-debug.apk build/outputs/apk/locker-debug-$VERSION_NAME-$VERSION_CODE.apk
adb install -r build/outputs/apk/*-debug-*.apk && adb shell am start com.bear.locker/com.bear.locker.Main

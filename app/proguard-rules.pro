############
# Poprobuy
############

-repackageclasses ru.poprobuy.poprobuy

-keep class ru.poprobuy.poprobuy.dictionary.** { *; }

# Keep ViewBinding methods that are accessed via reflection
-keepclassmembers class * implements androidx.viewbinding.ViewBinding {
   public static *** inflate(...);
   public static *** bind(android.view.View);
}

############
# Crashlytics
############

# Deobfuscated crash reports in Crashlytics
# https://firebase.google.com/docs/crashlytics/get-deobfuscated-reports#android
-keepattributes *Annotation*
-keepattributes SourceFile,LineNumberTable
-keep public class * extends java.lang.Exception

-keep class com.google.firebase.crashlytics.** { *; }
-dontwarn com.google.firebase.crashlytics.**

############
# Crypto
############

-keep class com.google.crypto.tink.proto.** { *;}
-dontwarn com.google.crypto.tink.proto.**

-keep class androidx.security.crypto.** { *;}
-dontwarn androidx.security.crypto.**

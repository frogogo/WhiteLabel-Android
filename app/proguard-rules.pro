############
# Poprobuy
############

-repackageclasses ru.poprobuy

# ViewBinding fragments
-keep class * implements androidx.viewbinding.ViewBinding {
    public static *** bind(android.view.View);
}

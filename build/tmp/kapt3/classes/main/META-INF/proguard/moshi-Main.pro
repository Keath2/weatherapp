-if class Main
-keepnames class Main
-if class Main
-keep class MainJsonAdapter {
    public <init>(com.squareup.moshi.Moshi);
}

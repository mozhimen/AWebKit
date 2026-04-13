# webk x5
# 为了保障X5功能的正常使用，您需要在您的proguard-rules.pro文件中添加如下配置：
-dontwarn dalvik.**
-dontwarn com.tencent.smtt.**
-keep class com.tencent.smtt.** { *; }
-keep class com.tencent.tbs.** { *; }

# https://x5.tencent.com/docs/access.html#13-%E6%B7%B7%E6%B7%86%E9%85%8D%E7%BD%AE
-keepattributes InnerClasses
-keepattributes Signature

-keep class MTT.ThirdAppInfoNew { *; }
#-keep class com.tencent.mtt.MttTraceEvent { *; }
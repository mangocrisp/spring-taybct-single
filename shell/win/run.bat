@echo on
rem "配置控制台显示中文（防止乱码）"
chcp 65001
rem "可以显示当前运行的 bat 位置到黑窗口上面，方便后续找到运行的 jar 包的位置"
TITLE=%~dp0%1
rem "指定jdk的 java 命令，你也可以指定对应的 jdk 版本"
set java=env\win\jdk-17.0.5\bin\java.exe
rem "打包好后的jar包名，每个服务的 jar 包名不一样"
set jar=%1
for /f "tokens=1-5" %%i in ('env\win\jdk-17.0.5\bin\jps ^|findstr "%jar%"') do (
    echo kill the process %%i who use the port
    taskkill /pid %%i -t -f
    goto start
)
:start
rem "配置 VM 参数"
set vm=-Dfile.encoding=utf-8 ^
-Dmaven.wagon.http.ssl.insecure=true ^
-Dmaven.wagon.http.ssl.allowall=true ^
--add-opens java.base/java.util=ALL-UNNAMED ^
--add-opens java.base/java.lang=ALL-UNNAMED ^
--add-opens java.base/java.lang.reflect=ALL-UNNAMED ^
--add-opens java.base/java.lang.invoke=ALL-UNNAMED ^
--add-opens java.base/java.lang.io=ALL-UNNAMED ^
-Xms8g -Xmx8g ^
-XX:MaxMetaspaceSize=512m ^
-XX:+UseZGC ^
-XX:MaxGCPauseMillis=150 ^
-XX:ReservedCodeCacheSize=256m ^
-XX:+UseCodeCacheFlushing ^
-Xlog:gc*,gc+age=trace,safepoint:file=ac/gc.log:time,uptime,level,tags:filecount=10,filesize=10M ^
-XX:+HeapDumpOnOutOfMemoryError ^
-XX:HeapDumpPath=./java_pid%p.hprof ^
-XX:NativeMemoryTracking=detail
rem "配置 Jar 包参数"
set params=--spring.profiles.active=test ^
--spring.cloud.nacos.discovery.server-addr=127.0.0.1:8848 ^
--spring.cloud.nacos.config.server-addr=127.0.0.1:8848 ^
--spring.cloud.nacos.username=nacos ^
--spring.cloud.nacos.password=nacos
rem "组合成启动命令"
echo "启动中：%jar%"
%java% %vm% -jar %jar% %params%
exit
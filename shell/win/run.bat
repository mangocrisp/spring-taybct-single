@echo off
rem "配置控制台显示中文（防止乱码）"
chcp 65001
rem "可以显示当前运行的 bat 位置到黑窗口上面，方便后续找到运行的 jar 包的位置"
set TITLE=%0
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
--add-opens java.base/java.lang.io=ALL-UNNAMED
rem "配置 Jar 包参数"
set params=--spring.profiles.active=test
rem "组合成启动命令"
echo "启动中：%jar%"
%java% %vm% -jar %jar% %params%
rem "这里暂停一下，防止 Jar 包没启动起来，但是黑窗口一闪而过导致找不到错误原因"
exit
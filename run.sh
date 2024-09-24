#!/bin/ash
# 为了方便代理文件夹，这里的 run.sh 是在 $WORKDIR/run 目录下的，所以需要切换到 $WORKDIR/jar 文件文件夹下去执行才能使用 config 里面的配置
cd $1
## 运行 jar 包
java -Dfile.encoding=utf-8 \
-Dmaven.wagon.http.ssl.insecure=true \
-Dmaven.wagon.http.ssl.allowall=true \
--add-opens java.base/java.util=ALL-UNNAMED \
--add-opens java.base/java.lang=ALL-UNNAMED \
--add-opens java.base/java.lang.reflect=ALL-UNNAMED \
--add-opens java.base/java.lang.invoke=ALL-UNNAMED \
--add-opens java.base/java.lang.io=ALL-UNNAMED \
-jar $2


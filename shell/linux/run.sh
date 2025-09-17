java=env/linux/jdk-17.0.5/bin/java
jar=$1
echo "" > $jar.out
echo "*****************start begin*****************"
oldpid=`jps | grep $jar | grep -v "prep" | awk '{print $1}'`
if [ x"$oldpid" != x"" ]; then
    echo "$jar was running..."
    echo "try restart"
    kill -9 $oldpid
    echo "killed PID is $oldpid"
    echo "run $jar ...."
else
    echo "run $jar ...."
fi
vm="-Dfile.encoding=utf-8 \
-Dmaven.wagon.http.ssl.insecure=true \
-Dmaven.wagon.http.ssl.allowall=true \
--add-opens java.base/java.util=ALL-UNNAMED \
--add-opens java.base/java.lang=ALL-UNNAMED \
--add-opens java.base/java.lang.reflect=ALL-UNNAMED \
--add-opens java.base/java.lang.invoke=ALL-UNNAMED \
--add-opens java.base/java.lang.io=ALL-UNNAMED \
-Xms8g -Xmx8g \
-XX:MaxMetaspaceSize=512m \
-XX:+UseZGC \
-XX:MaxGCPauseMillis=150 \
-XX:ReservedCodeCacheSize=256m \
-XX:+UseCodeCacheFlushing \
-Xlog:gc*,gc+age=trace,safepoint:file=ac/gc.log:time,uptime,level,tags:filecount=10,filesize=10M \
-XX:+HeapDumpOnOutOfMemoryError \
-XX:HeapDumpPath=./java_pid%p.hprof \
-XX:NativeMemoryTracking=detail"
params="--spring.profiles.active=test"
nohup $java $vm -jar $jar $params >$jar.out 2>&1 &
nowpid=`jps | grep $jar | grep -v "prep" | awk '{print $1}'`
echo "*****************start success,new PID is $nowpid*****************"

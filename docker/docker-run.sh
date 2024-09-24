#!/bin/bash
# 镜像仓库地址
registry_url='127.0.0.1:5000'
# 定义组名
group_name='spring-taybct-single'
# 定义应用名
#app_name=$1
app_name=run
# 定义应用版本
#app_version=$2
app_version=3.1.0-beta.7
# docker 镜像
docker_image=$registry_url/$group_name/$app_name:$app_version
echo '---- stop container ----'
docker stop $app_name
echo '----- rm container ----'
docker rm $app_name
echo '---- run container ----'
# 可以指定一下 Java 变量
#--env=PATH=/opt/openjdk-17/bin:/usr/local/sbin:/usr/local/bin:/usr/sbin:/usr/bin:/sbin:/bin \
#--env=JAVA_HOME=/opt/openjdk-17 \
#--env=JAVA_VERSION=17-ea+14 \
docker run --hostname=54498263e31f \
-e=TZ=Asia/Shanghai \
-v=D:/dev/tools/docker/spring-taybct-single/jar/config:/taybct/jar/config \
-v=D:/dev/tools/docker/spring-taybct-single/jar/logs:/taybct/jar/logs/spring-taybct-single \
-v=D:/dev/tools/docker/spring-taybct-single/run:/taybct/run \
-p 9102:9102 \
--name $app_name --restart=no \
--runtime=runc \
-d $docker_image
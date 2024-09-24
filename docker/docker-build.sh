#!/bin/bash
# 镜像仓库地址
registry_url='127.0.0.1:5000'
# 定义组名
group_name='spring-taybct-single'
# 定义应用名
app_name=$1
#app_name=run
# 定义应用版本
#app_version=$2
app_version=3.1.0-beta.7
# docker 镜像
docker_image=$registry_url/$group_name/$app_name:$app_version
echo '---- stop container ----'
docker stop $app_name
echo '----- rm container ----'
docker rm $app_name
echo '---- rm image ----'
docker rmi $docker_image
echo '---- build image ----'
docker build -f ./Dockerfile -t $docker_image --build-arg JAR_FILE=target/$app_name.jar .
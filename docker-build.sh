#!/bin/bash
usage() {
  echo "Usage: $0 <OPTIONS>"
  echo "可选参数:"
  echo "-u --registry_url   镜像仓库地址"
  echo "-g --group_name     镜像组名"
  echo "-n --app_name       镜像名称(必须)"
  echo "-v --image_version  镜像版本(必须)"
}
build() {
  u=$([ -n "$registry_url" ] && echo "$registry_url/" || echo "")
  g=$([ -n "$group_name" ] && echo "$group_name/" || echo "")
  v=$([ -n "$image_version" ] && echo ":$image_version" || echo ":latest")
  # docker 镜像
  docker_image=$u$g$image_name$v
  echo "---- stop container ----"
  docker stop $image_name
  echo "----- rm container ----"
  docker rm $image_name
  echo "---- rm image ----"
  docker rmi $docker_image
  echo "---- build image ----"
  echo "docker image name $docker_image"
  docker build -f ./Dockerfile -t $docker_image --build-arg JAR_FILE=target/$image_name.jar .
}
# 镜像仓库地址
registry_url=
# 镜像组名
group_name=
# 镜像名
image_name=
# 镜像版本
image_version=
# 解析命令行参数
options=$(getopt -o u:g:n:v: --long registry_url:,group_name:,image_name:,image_version:, -- "$@")
eval set -- "$options"
# 提取选项和参数
while true; do
  case $1 in
  	-u | --registry_url) shift; registry_url=$1 ; shift ;;
    -g | --group_name) shift; group_name=$1 ; shift ;;
    -n | --image_name) shift; image_name=$1 ; shift ;;
    -v | --image_version) shift; image_version=$1 ; shift ;;
    --) shift ; break ;;
    ?) echo "无效的选项: $1" ; usage ; exit 1 ;;
  esac
done
if [ -z "$image_name" ]; then
    echo "错误: 镜像名是必须的"
    usage
    exit 1
fi
if [ -z "$image_version" ]; then
    echo "错误: 镜像版本是必须的"
    usage
    exit 1
fi
if [ -n "$image_name" ] && [ -n "$image_version" ] ; then
  build
  exit 0
else
  usage
  exit 1
fi

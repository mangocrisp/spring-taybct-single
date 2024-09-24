#!/bin/bash
usage() {
  echo "Usage: $0 <OPTIONS>"
  echo "可选参数:"
  echo "-u --registry_url   镜像仓库地址"
  echo "-g --group_name     镜像组名"
  echo "-n --app_name       镜像名称(必须)"
  echo "-v --image_version  镜像版本(必须)"
  echo "-p --app_port       应用的端口"
  echo "-a --run_args       docker run 的时候的参数"
}
run() {
  u=$([ -n "$registry_url" ] && echo "$registry_url/" || echo "")
  g=$([ -n "$group_name" ] && echo "$group_name/" || echo "")
  v=$([ -n "$image_version" ] && echo ":$image_version" || echo ":latest")
  p=$([ -n "$app_port" ] && echo "$app_port" || echo "8080")
  a=$([ -n "$run_args" ] && echo "$run_args" || echo "")
  # docker 镜像
  docker_image=$u$g$image_name$v
  echo "---- stop container ----"
  docker stop $image_name
  echo "----- rm container ----"
  docker rm $image_name
  echo "---- run container ----"
  echo "docker image name $docker_image"
  # 可以指定一下 Java 变量
  #--hostname=54498263e31f \
  #--env=PATH=/opt/openjdk-17/bin:/usr/local/sbin:/usr/local/bin:/usr/sbin:/usr/bin:/sbin:/bin \
  #--env=JAVA_HOME=/opt/openjdk-17 \
  #--env=JAVA_VERSION=17-ea+14 \
  docker run -e=TZ=Asia/Shanghai \
  -p $p:$p \
  --name $image_name --restart=no \
  --runtime=runc \
  $a -d $docker_image
}
# 镜像仓库地址
registry_url=
# 定义组名
group_name=
# 定义应用名
image_name=
# 定义应用版本
image_version=
# 应用的端口
app_port=
# 运行的参数
run_args=
# 解析命令行参数
options=$(getopt -o u:g:n:v:p:a: --long registry_url:,group_name:,image_name:,image_version:,app_port:,run_args:, -- "$@")
eval set -- "$options"
# 提取选项和参数
while true; do
  case $1 in
  	-u | --registry_url) shift; registry_url=$1 ; shift ;;
    -g | --group_name) shift; group_name=$1 ; shift ;;
    -n | --image_name) shift; image_name=$1 ; shift ;;
    -v | --image_version) shift; image_version=$1 ; shift ;;
    -p | --app_port) shift; app_port=$1 ; shift ;;
    -a | --run_args) shift; run_args=$1 ; shift ;;
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
  run
  exit 0
else
  usage
  exit 1
fi

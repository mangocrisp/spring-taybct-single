#!/bin/bash
# 镜像仓库地址
registry_url="127.0.0.1:5000"
# 镜像组名
group_name="spring-taybct-single"
# 镜像版本
image_version="3.1.0-beta.7"
# 需要 build 哪些 jar 包的名都往这个数组里面放
# 有哪些应用
apps=(run)
# 这些应用的各自的端口,和上面的 jar 包名的顺序对应不要搞错了
port_arr=(9102)
# docker run 运行参数
run_args="--hostname=54498263e31f --volume=D:\dev\tools\docker\spring-taybct-single\jar\config:/taybct/jar/config --volume=D:\dev\tools\docker\spring-taybct-single\jar\logs:/taybct/jar/logs/spring-taybct-single --volume=D:\dev\tools\docker\spring-taybct-single\run:/taybct/run"
for(( i=0;i<${#apps[@]};i++)) ; do
    ./docker-run.sh -u "$registry_url" -g "$group_name" -n "${apps[$i]}" -v "$image_version" -p "${port_arr[$i]}" -a "$run_args"
done

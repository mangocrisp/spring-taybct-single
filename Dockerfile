# 设置镜像基础，jdk17
FROM openjdk:17-jdk-alpine
# 维护人员信息
MAINTAINER <15014633363@163.com>
# maven 打包 buildArgs 指定项目 jar 包位置名称
ARG JAR_FILE
# 设定时区
ENV TZ=Asia/Shanghai
#设置镜像对外暴露端口（因为也不知道 jar 包最终运行的是什么端口这里就不指定了运行的时候自己写命令去运行）
#EXPOSE 9102
# 设置后续指令的工作目录
WORKDIR /taybct
# 将指定的 jar 放置在根目录下，命名为 app.jar，推荐使用绝对路径
ADD $JAR_FILE jar/app.jar
# 执行多条命令的 shell 文件
ADD ./run.sh run/run.sh
# 设置时区 && 创建 spring boot jar 包启动的配置文件夹 && 授权可执行文件   这里尽量一行搞定，因为每多一个 RUN 指令 Docker 就多套一层
RUN ln -sf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ >/etc/timezone && mkdir jar/config && chmod +x run/run.sh
#执行启动命令
ENTRYPOINT ["run/run.sh", "/taybct/jar", "./app.jar"]
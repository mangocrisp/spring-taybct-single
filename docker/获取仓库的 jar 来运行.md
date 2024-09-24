## 从远程仓库获取 docker 镜像并运行

```bash
docker run --hostname=54498263e31f --env=PATH=/opt/openjdk-17/bin:/usr/local/sbin:/usr/local/bin:/usr/sbin:/usr/bin:/sbin:/bin --env=JAVA_HOME=/opt/openjdk-17 --env=JAVA_VERSION=17-ea+14 --env=TZ=Asia/Shanghai  --volume=D:\dev\tools\docker\spring-taybct-single\jar\config:/taybct/jar/config --volume=D:\dev\tools\docker\spring-taybct-single\jar\logs:/taybct/jar/logs/spring-taybct-single --volume=D:\dev\tools\docker\spring-taybct-single\run:/taybct/run -p 9102:9102 --name spring-taybct-single --restart=no --runtime=runc -d 127.0.0.1:5000/spring-taybct-single/run:3.1.0-beta.7
```

1、列出所有镜像
> curl http://127.0.0.1:5000/v2/_catalog
2、查看指定镜像都有哪些tag
> curl http://127.0.0.1:5000/v2/spring-taybct-single/run/tags/list


## Docker Registry 用户和密码配置

> docker login 127.0.0.1:5000


## 用户和密码base64

```bash
$ echo taybct:taybct | base64
eGlhb2JhaTp4aWFvYmFpCg==
$ echo eGlhb2JhaTp4aWFvYmFpCg== | base64 -d
taybct:taybct
```

在 ./docker/config.json 添加如下内容

```json
{
        "auths": {
                "127.0.0.1:5000": {
                  "auth": "eGlhb2JhaTp4aWFvYmFpCg=="
                }
        },
        "credsStore": "desktop",
        "currentContext": "default"
}
```

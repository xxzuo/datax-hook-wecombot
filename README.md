# datax-hook-weComBot
add datax hook(send datax job info to weComBot)


[企业微信机器人配置文档](https://developer.work.weixin.qq.com/document/path/91770)


## 打包
```
mvn clean package
```

## 上传
将打好的 jar 包上传到 datax 目录下
目录结构 如下所示
```
- datax
  - job
  - hook
    - webComBot
      - datax-hook-wecombot-1.0-SNAPSHOT.jar
```


## datax json 参数配置
在 job 下新增 `weComBot` 参数

e.g.
```json
{
  "job":{
    "weComBot":{
        "webhook": "https://qyapi.weixin.qq.com/cgi-bin/webhook/send?key=xxxxxxxxxxxx",
        "title":"datax job title",
        "mode":"ALL"
    }
  }
}
```

### 参数说明

- webhook
  - 描述: 企业微信机器人 webhook 地址
  - 必填: 是
  - 默认值: 无
  
- title
  - 描述: 发送消息的标题
  - 必填: 否
  - 默认值: 无

- mode
  - 描述: 发送模式 `ALL` 或者 `ERROR`, `ERROR` 模式下只有因为脏数据或者其他原因,导致部分数据读写失败时才会发送消息
  - 必填: 否
  - 默认值: `ERROR`







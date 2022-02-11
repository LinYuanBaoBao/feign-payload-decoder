# feign-payload-decoder

通常，我们服务接口统一定义的响应体（ Payload ）格式可能如下：
```json
{
    "code": 200,
    "data": {
      "userId": 1001,
      "username": "linyuan"
    },
    "success": true,
    "timestamp": 1644555576272
}
```
除最主要数据 `data` 字段之外，还包含了一些"基础"字段，如 `success` 用于标记接口是否正常处理，`code` 用于在业务异常时提供标识。

**feign-payload-decoder** 组件目的在于"简化代码"，其作用为直接从响应体（Payload）中提取数据 `data` 字段，以达到如下效果：
```java
@FeignClient(name = "user-server", configuration = FeignConfiguration.class)
public interface UserApi {

    // 使用前...
    // @GetMapping("/user/{userId}")
    // DefaultPayload<UserVO> getUser();
    
    // 使用后...
    @GetMapping("/user/{userId}")
    UserVO getUser();
    
}
```

## 信息
基于 **spring-cloud-openfeign-core：3.1.0** 构建

by：林同学（765371578@qq.com）

## Getting Started

引入依赖：

```xml
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-openfeign</artifactId>
    <version>3.1.0</version>
</dependency>

<dependency>
    <groupId>com.github.LinYuanBaoBao</groupId>
    <artifactId>feign-payload-decoder</artifactId>
    <version>1.0.0-RELEASE</version>
</dependency>
```

定义 Feign Configuration 配置类：

```java
class FeignConfiguration {

    @Autowired
    private ObjectFactory<HttpMessageConverters> messageConverters;

    @Bean
    public Decoder decoder() {
        return new MyDecoder(messageConverters);
    }

    private static class MyDecoder extends AbstractPayloadDecoder<DefaultPayload> {

        public MyDecoder(ObjectFactory<HttpMessageConverters> messageConverters) {
            super(messageConverters);
        }

        @Override
        protected void check(DefaultPayload respBody) {
            // 检查响应结果...
            if (!respBody.getSuccess()) {
                
            }
        }

    }

}
```

在 @FeignClient 中指定使用此配置类：
```java
@FeignClient(name = "user-server", configuration = FeignConfiguration.class)
public interface UserApi {
    ...
}
```
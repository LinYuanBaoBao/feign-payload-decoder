# feign-payload-decoder

一个 SpringBoot-Feign 请求响应解析组件，可以在定义远程接口时简化 "Response 包装"，提取响应对象，如：
```java
@FeignClient(name = "user-server", configuration = FeignConfiguration.class)
public interface UserApi {

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
package com.linyuanbaobao.feign.payload.decoder;

import feign.FeignException;
import feign.Response;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.cloud.openfeign.support.SpringDecoder;
import org.springframework.web.client.HttpMessageConverterExtractor;
import sun.reflect.generics.reflectiveObjects.ParameterizedTypeImpl;

import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.WildcardType;

/**
 * @author linyuan - 765371578@qq.com
 * @since 2022/1/18
 */
public abstract class AbstractPayloadDecoder<T extends Payload> extends SpringDecoder {

    private ObjectFactory<HttpMessageConverters> messageConverters;

    public AbstractPayloadDecoder(ObjectFactory<HttpMessageConverters> messageConverters) {
        super(messageConverters);
        this.messageConverters = messageConverters;
    }

    @Override
    public Object decode(Response response, Type type) throws IOException, FeignException {
        Type payloadClazz = getSuperClass();
        if (!(type instanceof Class) && !(type instanceof ParameterizedType) && !(type instanceof WildcardType)) {
            throw new RuntimeException();
        } else {
            HttpMessageConverterExtractor<?> extractor = null;
            if (payloadClazz != getRawClass(type)) {
                ParameterizedTypeImpl wrapperType = ParameterizedTypeImpl.make(getRawClass(payloadClazz), new Type[]{type}, null);
                extractor = new HttpMessageConverterExtractor<T>(wrapperType, this.messageConverters.getObject().getConverters());
                T r = (T) extractor.extractData(new PayloadFeignResponseAdapter(response));
                this.check(r);
                return r.parseData();
            } else {
                extractor = new HttpMessageConverterExtractor<T>(type, this.messageConverters.getObject().getConverters());
                return extractor.extractData(new PayloadFeignResponseAdapter(response));
            }
        }
    }

    protected abstract void check(T t);

    private Class getRawClass(Type type) {
        Class clazz = null;
        if (type instanceof Class) {
            clazz = (Class) type;
        } else if (type instanceof ParameterizedType) {
            clazz = (Class) ((ParameterizedType) type).getRawType();
        }
        return clazz;
    }

    private Type getSuperClass() {
        return ((ParameterizedType) this.getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    }
}

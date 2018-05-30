package com.example.dubboclient.config;

import com.alibaba.dubbo.config.spring.ReferenceBean;
import com.example.demo.dubbointerface.DubboService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DubboClientConfig {
    @Value(value = "${dubbo.version}")
    private String commonVersion;
    @Bean("dubboService")
    public ReferenceBean<DubboService> businessAccountOperateInterface() {
        return getReferenceBean(DubboService.class);
    }
    private <T> ReferenceBean<T> getReferenceBean(final Class<T> clazz) {
        return getReferenceBean(clazz, false, commonVersion);
    }
    private <T> ReferenceBean<T> getReferenceBean(final Class<T> clazz, final boolean isCheck, final String version) {
        final ReferenceBean<T> referenceBean = new ReferenceBean<>();
        referenceBean.setInterface(clazz);
        referenceBean.setCheck(isCheck);
        referenceBean.setVersion(version);
        return referenceBean;
    }
}

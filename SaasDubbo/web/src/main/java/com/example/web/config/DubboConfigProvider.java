package com.example.web.config;

import com.alibaba.dubbo.config.spring.ServiceBean;
import com.example.demo.dubbointerface.DubboService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;

@Configuration
public class DubboConfigProvider {
    @Value(value = "${dubbo.version}")
    private String version;
   @Resource
   DubboService dubboService;
   @Bean
    public ServiceBean<DubboService> DubboServiceBean(){
       final ServiceBean<DubboService> serviceBean=new ServiceBean<>();
       serviceBean.setInterface(DubboService.class);
       serviceBean.setRef(dubboService);
       setPubProperties(serviceBean);
       return serviceBean;


   }
    private void setPubProperties(ServiceBean<?> serviceBean) {
        serviceBean.setVersion(version);
        serviceBean.setTimeout(3000);
    }



}

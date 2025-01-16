package com.example.springboot.config;

import com.example.springboot.HrServiceImpl;
import jakarta.xml.ws.Endpoint;
import org.apache.cxf.Bus;
import org.apache.cxf.interceptor.LoggingInInterceptor;
import org.apache.cxf.interceptor.LoggingOutInterceptor;
import org.apache.cxf.jaxws.EndpointImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.ws.config.annotation.WsConfigurerAdapter;

@Configuration
public class EndPointConfig extends WsConfigurerAdapter {
    private final Bus bus;

    @Autowired
    public EndPointConfig(Bus bus) {
        this.bus = bus;
    }

    @Bean
    public Endpoint endpoint(){
        EndpointImpl endpoint = new EndpointImpl(bus, new HrServiceImpl());
        endpoint.publish("/hr");
        endpoint.getServer().getEndpoint().getInInterceptors().add(new LoggingInInterceptor());
        endpoint.getServer().getEndpoint().getOutInterceptors().add(new LoggingOutInterceptor());
//        endpoint.getServer().getEndpoint().getOutFaultInterceptors().add(new ErrorInterceptor());
//        endpoint.getBus().getProperties().put("org.apache.cxf.logging.FaultListener", new ErrorInterceptor());
        return endpoint;
    }
}


//package com.example.springboot.config;
//
//import jakarta.websocket.EndpointConfig;
//import org.apache.cxf.Bus;
//import org.apache.cxf.bus.spring.SpringBus;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.context.annotation.Import;
//import org.springframework.ws.soap.server.endpoint.SoapFaultDefinition;
//import org.springframework.ws.soap.server.endpoint.SoapFaultMappingExceptionResolver;
//
//import java.util.Properties;
//
//@Configuration
//@Import(value= EndpointConfig.class)
//public class ServiceConfig {
//
//    @SuppressWarnings("unused")
//    @Bean(name= Bus.DEFAULT_BUS_ID)
//    SpringBus springBus() {
//        return new SpringBus();
//    }
//
//    @Bean
//    public SoapFaultMappingExceptionResolver exceptionResolver(){
//        SoapFaultMappingExceptionResolver exceptionResolver = new FaultResolver();
//
//        SoapFaultDefinition faultDefinition = new SoapFaultDefinition();
//        faultDefinition.setFaultCode(SoapFaultDefinition.SERVER);
//        exceptionResolver.setDefaultFault(faultDefinition);
//
//        Properties errorMappings = new Properties();
//        errorMappings.setProperty(Exception.class.getName(), SoapFaultDefinition.SERVER.toString());
//        errorMappings.setProperty(ErrorWithCode.class.getName(), SoapFaultDefinition.SERVER.toString());
//        exceptionResolver.setExceptionMappings(errorMappings);
//        exceptionResolver.setOrder(1);
//        return exceptionResolver;
//    }

//}


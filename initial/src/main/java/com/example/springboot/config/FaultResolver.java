//package com.example.springboot.config;
//
//import org.springframework.ws.soap.SoapFault;
//import org.springframework.ws.soap.SoapFaultDetail;
//import org.springframework.ws.soap.server.endpoint.SoapFaultMappingExceptionResolver;
//
//import javax.xml.namespace.QName;
//
//public class FaultResolver  extends SoapFaultMappingExceptionResolver {
//
//    private static final QName CODE = new QName("code");
//    private static final QName MESSAGE = new QName("message");
//
//    @Override
//    protected void customizeFault(Object endpoint, Exception ex, SoapFault fault) {
//        if (ex instanceof ErrorWithCode serviceFault) {
//            SoapFaultDetail detail = fault.addFaultDetail();
//            detail.addFaultDetailElement(CODE).addText(String.valueOf(serviceFault.getCode()));
//            detail.addFaultDetailElement(MESSAGE).addText(serviceFault.getMessage());
//        }
//    }
//
//}

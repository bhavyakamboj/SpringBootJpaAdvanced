package com.bhavyakamboj.springboot2.springboot2jpacrudexample;

import com.bhavyakamboj.springboot2.springboot2jpacrudexample.controller.EmployeeResource;
import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.stereotype.Component;

import javax.ws.rs.ApplicationPath;

@Component
@ApplicationPath("/boot-jersey")
public class JerseyConfiguration extends ResourceConfig {
    public JerseyConfiguration(){
        register(EmployeeResource.class);
    }
}

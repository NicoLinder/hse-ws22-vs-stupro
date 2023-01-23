package de.hse.stupro;

import com.google.common.net.HostAndPort;
import com.orbitz.consul.Consul;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;

@ApplicationScoped
public class OrderServiceBeansProducer {

    @ConfigProperty(name = "consul.host")
    String consulHost;

    @ConfigProperty(name = "consul.port")
    String consulPort;

    @Produces
    Consul consulClient = Consul.builder().withHostAndPort(HostAndPort.fromParts("consul", 8500)).build();

}
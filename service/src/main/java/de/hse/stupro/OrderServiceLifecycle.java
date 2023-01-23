package de.hse.stupro;

import com.orbitz.consul.Consul;
import com.orbitz.consul.HealthClient;
import com.orbitz.consul.model.agent.ImmutableRegistration;
import com.orbitz.consul.model.health.ServiceHealth;
import io.quarkus.runtime.Application;
import io.quarkus.runtime.ShutdownEvent;
import io.quarkus.runtime.StartupEvent;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@ApplicationScoped
public class OrderServiceLifecycle {

    private static final Logger LOGGER = LoggerFactory
            .getLogger(OrderServiceLifecycle.class);
    private String instanceId;

    @Inject
    Consul consulClient;
    @ConfigProperty(name = "quarkus.application.name")
    String appName;
    @ConfigProperty(name = "quarkus.application.version")
    String appVersion;
    @ConfigProperty(name = "quarkus.http.port")
    String servicePort;
    @ConfigProperty(name = "service.address")
    String serviceAddress;

    void onStart(@Observes StartupEvent ev) {
        ScheduledExecutorService executorService = Executors
                .newSingleThreadScheduledExecutor();

        executorService.schedule(() -> {
            HealthClient healthClient = consulClient.healthClient();
            List<ServiceHealth> instances = healthClient
                    .getHealthyServiceInstances(appName).getResponse();
            instanceId = appName + "-" + instances.size();
            ImmutableRegistration registration = ImmutableRegistration.builder()
                    .id(instanceId)
                    .name(appName)
                    .address(serviceAddress)
                    .port(Integer.parseInt(servicePort))
                    .putMeta("version", appVersion)
                    .build();
            consulClient.agentClient().register(registration);
            LOGGER.info("Instance registered: id={}", registration.getId());
        }, 5000, TimeUnit.MILLISECONDS);
    }

    void onStop(@Observes ShutdownEvent ev) {
        consulClient.agentClient().deregister(instanceId);
        LOGGER.info("Instance de-registered: id={}", instanceId);
    }
}

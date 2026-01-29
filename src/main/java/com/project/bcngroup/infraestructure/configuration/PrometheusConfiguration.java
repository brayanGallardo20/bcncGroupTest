package com.project.bcngroup.infraestructure.configuration;

import io.micrometer.prometheusmetrics.PrometheusConfig;
import io.micrometer.prometheusmetrics.PrometheusMeterRegistry;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuración de Prometheus para métricas de la aplicación.
 *
 * Esta configuración habilita la recopilación de métricas mediante Micrometer
 * y expone un endpoint en /actuator/prometheus para que Prometheus pueda
 * raspear (scrape) las métricas.
 */
@Configuration
public class PrometheusConfiguration {

    /**
     * Configura el registro de métricas de Prometheus.
     *
     * @return PrometheusMeterRegistry configurado
     */
    @Bean
    public PrometheusMeterRegistry prometheusMeterRegistry() {
        return new PrometheusMeterRegistry(PrometheusConfig.DEFAULT);
    }
}


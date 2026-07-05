package com.ms.orders.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.lang.Nullable;
import java.time.Duration;

import com.azure.core.credential.AzureKeyCredential;
import com.azure.cosmos.CosmosClientBuilder;
import com.azure.cosmos.GatewayConnectionConfig;
import com.azure.cosmos.CosmosDiagnosticsHandler;
import com.azure.cosmos.models.CosmosClientTelemetryConfig;
import com.azure.cosmos.CosmosDiagnosticsThresholds;

import com.azure.spring.data.cosmos.config.AbstractCosmosConfiguration;
import com.azure.spring.data.cosmos.repository.config.EnableCosmosRepositories;
import com.azure.spring.data.cosmos.core.ResponseDiagnostics;
import com.azure.spring.data.cosmos.core.ResponseDiagnosticsProcessor;

import com.azure.spring.data.cosmos.core.mapping.EnableCosmosAuditing;

@Configuration("ordersCosmosConfig")
@EnableCosmosRepositories(basePackages = "com.ms.orders.repository")
@EnableCosmosAuditing
public class CosmosConfig extends AbstractCosmosConfiguration {
    private static final Logger LOGGER = LoggerFactory.getLogger(CosmosConfig.class);

    @Value("${azure.cosmos.uri}")
    private String uri;

    @Value("${azure.cosmos.key}")
    private String key;

    @Value("${azure.cosmos.secondaryKey:}")
    private String secondaryKey;

    @Value("${azure.cosmos.database}")
    private String dbName;

    @Value("${azure.cosmos.queryMetricsEnabled:false}")
    private boolean queryMetricsEnabled;

    @Value("${azure.cosmos.indexMetricsEnabled:false}")
    private boolean indexMetricsEnabled;

    @Value("${azure.cosmos.maxDegreeOfParallelism:0}")
    private int maxDegreeOfParallelism;

    @Value("${azure.cosmos.maxBufferedItemCount:0}")
    private int maxBufferedItemCount;

    @Value("${azure.cosmos.responseContinuationTokenLimitInKb:0}")
    private int responseContinuationTokenLimitInKb;

    @Value("${azure.cosmos.diagnosticsThresholds.pointOperationLatencyThresholdInMS:0}")
    private int pointOperationLatencyThresholdInMS;

    @Value("${azure.cosmos.diagnosticsThresholds.nonPointOperationLatencyThresholdInMS:0}")
    private int nonPointOperationLatencyThresholdInMS;

    @Value("${azure.cosmos.diagnosticsThresholds.requestChargeThresholdInRU:0}")
    private int requestChargeThresholdInRU;

    @Value("${azure.cosmos.diagnosticsThresholds.payloadSizeThresholdInBytes:0}")
    private int payloadSizeThresholdInBytes;

    private AzureKeyCredential azureKeyCredential;

    @Bean
    public CosmosClientBuilder cosmosClientBuilder() {
        this.azureKeyCredential = new AzureKeyCredential(key);
        GatewayConnectionConfig gatewayConnectionConfig = new GatewayConnectionConfig();
        return new CosmosClientBuilder()
                .endpoint(uri)
                .credential(azureKeyCredential)
                .gatewayMode(gatewayConnectionConfig)
                .clientTelemetryConfig(
                        new CosmosClientTelemetryConfig()
                                .diagnosticsThresholds(
                                        new CosmosDiagnosticsThresholds()
                                                .setNonPointOperationLatencyThreshold(
                                                        Duration.ofMillis(nonPointOperationLatencyThresholdInMS))
                                                .setPointOperationLatencyThreshold(
                                                        Duration.ofMillis(pointOperationLatencyThresholdInMS))
                                                .setPayloadSizeThreshold(payloadSizeThresholdInBytes)
                                                .setRequestChargeThreshold(requestChargeThresholdInRU))
                                .diagnosticsHandler(CosmosDiagnosticsHandler.DEFAULT_LOGGING_HANDLER));
    }

    @Override
    public com.azure.spring.data.cosmos.config.CosmosConfig cosmosConfig() {
        return com.azure.spring.data.cosmos.config.CosmosConfig.builder()
                .enableQueryMetrics(queryMetricsEnabled)
                .enableIndexMetrics(indexMetricsEnabled)
                .maxDegreeOfParallelism(maxDegreeOfParallelism)
                .maxBufferedItemCount(maxBufferedItemCount)
                .responseContinuationTokenLimitInKb(responseContinuationTokenLimitInKb)
                .responseDiagnosticsProcessor(new ResponseDiagnosticsProcessorImplementation())
                .build();
    }

    @Bean
    @org.springframework.context.annotation.Primary
    public com.ms.orders.mapper.OrderMapper orderMapper() {
        return org.mapstruct.factory.Mappers.getMapper(com.ms.orders.mapper.OrderMapper.class);
    }

    public void switchToSecondaryKey() {
        this.azureKeyCredential.update(secondaryKey);
    }

    @Override
    protected String getDatabaseName() {
        return dbName;
    }

    private static class ResponseDiagnosticsProcessorImplementation implements ResponseDiagnosticsProcessor {

        @Override
        public void processResponseDiagnostics(@Nullable ResponseDiagnostics responseDiagnostics) {
            LOGGER.info("Response Diagnostics {}", responseDiagnostics);
        }
    }
}

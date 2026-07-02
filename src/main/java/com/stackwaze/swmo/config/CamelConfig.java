package com.stackwaze.swmo.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.stackwaze.swmo.domain.OrderEntity;
import java.time.format.DateTimeFormatter;
import java.time.ZoneId;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CamelConfig extends RouteBuilder {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
            .withZone(ZoneId.systemDefault());

    @Bean
    public ObjectMapper objectMapper() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        return mapper;
    }

    @Override
    public void configure() {
        from("direct:order-logger")
                .process(exchange -> {
                    OrderEntity order = exchange.getIn().getBody(OrderEntity.class);
                    String json = objectMapper().writeValueAsString(new java.util.LinkedHashMap<>() {{
                        put("id", order.getId());
                        put("username", order.getUsername());
                        put("createdAt", FORMATTER.format(order.getCreatedAt()));
                        put("items", order.getItems());
                    }}) + System.lineSeparator();
                    exchange.getMessage().setBody(json);
                })
                .to("file://./logs?fileName=commandes.log&fileExist=Append");
    }
}

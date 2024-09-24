package io.github.mangocrisp.lab.flux;

import io.github.mangocrisp.spring.taybct.tool.core.auto.ControllerRegisterAutoConfigure;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class, ControllerRegisterAutoConfigure.class})
public class FluxApplication {
    public static void main(String[] args) {
        SpringApplication.run(FluxApplication.class);
    }
}

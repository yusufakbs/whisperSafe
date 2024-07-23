package org.yusufakbas.whispersafe;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition( // http://localhost:4747/swagger-ui/index.html#/
        info = @Info(
                title = "WhispareSafe - Chat Project",
                version = "1.0.0",
                description = "This project is only for learning!",
                contact = @Contact(
                        name = "Yusuf",
                        email = "akbasyusuuf@gmail.com"
                )
        )
)
public class WhisperSafeApplication {

    public static void main(String[] args) {
        SpringApplication.run(WhisperSafeApplication.class, args);
    }

}

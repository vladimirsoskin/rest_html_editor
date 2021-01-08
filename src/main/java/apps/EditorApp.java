package apps;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan({"controller","editor", "storage", "history"})
public class EditorApp {
    public static void main(String[] args) {
        SpringApplication.run(EditorApp.class, args);
    }
}

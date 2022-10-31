package fr.kirikou.Dashboard;

import lombok.Getter;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RestTest {
    @GetMapping("/test")
    public TestData getTestData(@RequestParam(value = "name") String name) {
        return new TestData(name);
    }

    @Getter
    public class TestData {
        private String name;

        public TestData(String name) {
            this.name = name;
        }
    }
}

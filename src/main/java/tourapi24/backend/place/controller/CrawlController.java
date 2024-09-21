package tourapi24.backend.place.controller;

import io.swagger.v3.oas.annotations.Hidden;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import tourapi24.backend.place.service.crawl.Crawl;

@RestController
@RequestMapping("/crawl")
@RequiredArgsConstructor
@Hidden
public class CrawlController {

    private final Crawl crawl;

    @Value("${key.chatbot}")
    private String key;

    //TODO: 크롤링 테스트용 코드
    @GetMapping
    public ResponseEntity<?> crawl(@RequestParam String key) {
        if (!this.key.equals(key)) {
            return ResponseEntity.badRequest().build();
        }
        crawl.crawlAndSave(3000);
        return ResponseEntity.ok().build();
    }
}

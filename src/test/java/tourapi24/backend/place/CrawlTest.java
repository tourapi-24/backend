package tourapi24.backend.place;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import tourapi24.backend.place.repository.PlaceRepository;
import tourapi24.backend.place.service.crawl.Crawl;

@SpringBootTest
@Transactional
@Rollback(false)
public class CrawlTest {

    @Autowired
    private Crawl crawl;

    @Autowired
    private PlaceRepository placeRepository;

    @Test
    void 크롤링() {
        crawl.crawlAndSave(3000);
    }
}

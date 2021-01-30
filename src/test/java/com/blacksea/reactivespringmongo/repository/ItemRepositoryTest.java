package com.blacksea.reactivespringmongo.repository;

import com.blacksea.reactivespringmongo.document.ItemDocument;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.ActiveProfiles;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@DataMongoTest
@Log4j2
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@ActiveProfiles("integration")
public class ItemRepositoryTest {

    @Autowired
    private ItemReactiveRepository itemReactiveRepository;
    private String templateId = UUID.randomUUID().toString();
    private String templateDesc = "Akıllı Seyir Füzesi";

    private List<ItemDocument> itemDocumentList = Arrays.asList(
            new ItemDocument(null, "TCG Anadolu", "Amfibik hücüm gemisi", 150.00),
            new ItemDocument(null, "ANKA-S", "Silahlı insansız hava aracı", 50.00),
            new ItemDocument(null, "Bayraktar TB2", "Taktik sınıfı silahlı insansız hava aracı", 40.00),
            new ItemDocument(null, "Akıncı", "Taarruzi Insansız hava aracı", 90.00),
            new ItemDocument(null, "Anka", "Insansız Hava Aracı", 90.00),
            new ItemDocument(templateId, "SOM", templateDesc, 130.00)
    );

    @BeforeAll
    public void setup() {
        this.itemReactiveRepository.deleteAll()
                .thenMany(Flux.fromIterable(itemDocumentList))
                .flatMap(itemReactiveRepository::save)
                .doOnNext((item -> {
                    log.info("inserted item : " + item);
                }))
                .blockLast();
    }

    @AfterAll
    public void completed() {
        this.itemReactiveRepository.deleteAll();
    }

    @Test
    @Order(1)
    public void getAll() {
        StepVerifier.create(this.itemReactiveRepository.findAll().log())
                .expectSubscription()
                .expectNextCount(6)
                .verifyComplete();
    }

    @Test
    @Order(2)
    public void getById() {
        StepVerifier.create(this.itemReactiveRepository.findById(templateId).log())
                .expectSubscription()
                .expectNextCount(1)
                .verifyComplete();
    }

    @Test
    @Order(3)
    public void findItemByDescription() {
        StepVerifier.create(this.itemReactiveRepository.findAllByDescription(templateDesc).log())
                .expectSubscription()
                .expectNextMatches((item -> {
                    log.info("item : " + item);
                    log.info("template id : " + templateId);
                    return item.getId().equals(templateId);
                }))
                .verifyComplete();
    }

    @Test
    @Order(4)
    public void saveItem() {
        Mono<ItemDocument> savedItem = itemReactiveRepository.save(new ItemDocument(null, "Faruk", "Karadeniz", 100.00));
        StepVerifier.create(savedItem.log())
                .expectSubscription()
                .expectNextMatches(item -> item.getName().equals("Faruk"))
                .verifyComplete();
    }

    @Test
    @Order(5)
    public void updateItem() {
        String description = "lazkopat";
        Flux<ItemDocument> updatedItem = itemReactiveRepository.findAllByName("Faruk")
                .map(item -> {
                    item.setDescription(description);
                    return item;
                })
                .flatMap(item -> {
                    return itemReactiveRepository.save(item);
                });

        StepVerifier.create(updatedItem.log())
                .expectSubscription()
                .expectNextMatches(item -> item.getDescription().equals(description))
                .verifyComplete();
    }

    @Test
    @Order(6)
    public void deleteById() {
        Mono<Void> deletedItem = this.itemReactiveRepository.deleteById(templateId);
        StepVerifier.create(deletedItem.log())
                .expectSubscription()
                .verifyComplete();
    }
}

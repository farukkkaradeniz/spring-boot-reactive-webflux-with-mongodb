package com.blacksea.reactivespringmongo.controller;

import com.blacksea.reactivespringmongo.document.ItemDocument;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;
import java.util.Arrays;
import java.util.List;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
@Log4j2
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@ActiveProfiles("integration")
public class ItemDocumentControllerTest {
    private static final String BASE_PATH = "/items";
    @Autowired
    private WebTestClient webTestClient;
    private static ItemDocument tmpItemDoc = new ItemDocument(null,"Web Test Client UnitTest","Web Test Client UnitTest",100.00);

    @Test
    @Order(1)
    public void getAll() {
        List<ItemDocument> response = this.webTestClient.get().uri(BASE_PATH)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBodyList(ItemDocument.class)
                .returnResult()
                .getResponseBody();
        log.info("response : " + (response != null && !response.isEmpty() ? Arrays.toString(response.toArray()) : ""));
    }

    @Test
    @Order(2)
    public void save() {
        ItemDocument saveResponse = this.webTestClient.post().uri(BASE_PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(tmpItemDoc), ItemDocument.class)
                .exchange()
                .expectStatus().isCreated()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody(ItemDocument.class)
                .returnResult()
                .getResponseBody();
        log.info("saved item : " + saveResponse);
        assert saveResponse != null;
        Assertions.assertEquals(tmpItemDoc.getDescription(),saveResponse.getDescription());
        Assertions.assertNotNull(saveResponse.getId(),"Error on saving item document. The id can not be null");
        tmpItemDoc.setId(saveResponse.getId());
    }

    @Test
    @Order(3)
    public void getById() {
        ItemDocument itemResponse = this.webTestClient.get().uri(BASE_PATH + "/" + tmpItemDoc.getId())
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody(ItemDocument.class)
                .returnResult()
                .getResponseBody();
        log.info("item response : " + itemResponse);
        assert itemResponse != null;
        Assertions.assertEquals(itemResponse,tmpItemDoc);
    }

    @Test
    @Order(4)
    public void update() {
        tmpItemDoc.setDescription(tmpItemDoc.getDescription() + "-UPT");
        ItemDocument itemResponse = this.webTestClient.put().uri(BASE_PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(tmpItemDoc), ItemDocument.class)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody(ItemDocument.class)
                .returnResult()
                .getResponseBody();
        log.info("item response : " + itemResponse);
        assert itemResponse != null;
        Assertions.assertEquals(itemResponse,tmpItemDoc);
    }

    @Test
    @Order(5)
    public void deleteItem() {
        this.webTestClient.delete().uri(BASE_PATH + "/" + tmpItemDoc.getId())
                .exchange()
                .expectStatus().isOk()
                .expectBody(Void.class);
    }
}

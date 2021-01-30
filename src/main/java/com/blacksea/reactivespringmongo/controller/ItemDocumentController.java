package com.blacksea.reactivespringmongo.controller;

import com.blacksea.reactivespringmongo.document.ItemDocument;
import com.blacksea.reactivespringmongo.repository.ItemReactiveRepository;
import com.blacksea.reactivespringmongo.service.ItemDocumentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;

@RestController
@RequestMapping("/items")
public class ItemDocumentController {
    @Autowired
    private ItemDocumentService itemDocumentService;

    @GetMapping(produces = MediaType.APPLICATION_STREAM_JSON_VALUE)
    public Flux<ItemDocument> getAll() {
        return this.itemDocumentService.getAll();
    }

    @GetMapping("/{id}")
    public Mono<ItemDocument> getItemById(@PathVariable String id) {
        return this.itemDocumentService.getById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<ItemDocument> save(@RequestBody @Validated ItemDocument itemDocument) {
        return this.itemDocumentService.save(itemDocument);
    }

    @PutMapping
    public Mono<ItemDocument> update(@RequestBody @Validated ItemDocument itemDocument) {
        return this.itemDocumentService.update(itemDocument);
    }

    @DeleteMapping("/{id}")
    public Mono<Void> deleteItemById(@PathVariable String id) {
        return this.itemDocumentService.deleteById(id);
    }
}

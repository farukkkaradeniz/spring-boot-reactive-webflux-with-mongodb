package com.blacksea.reactivespringmongo.service.impl;

import com.blacksea.reactivespringmongo.document.ItemDocument;
import com.blacksea.reactivespringmongo.repository.ItemReactiveRepository;
import com.blacksea.reactivespringmongo.service.ItemDocumentService;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.mongodb.core.CollectionOptions;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.annotation.PostConstruct;

@Service
@Log4j2
public class ItemDocumentServiceImpl implements ItemDocumentService {
    private final ItemReactiveRepository itemReactiveRepository; //findWithTailableCursorBy
    private final ReactiveMongoTemplate reactiveMongoTemplate;

    public ItemDocumentServiceImpl(ItemReactiveRepository itemReactiveRepository, ReactiveMongoTemplate reactiveMongoTemplate) {
        this.itemReactiveRepository = itemReactiveRepository;
        this.reactiveMongoTemplate = reactiveMongoTemplate;
    }

    @PostConstruct
    public void setupCappedCollection() {
        this.reactiveMongoTemplate.dropCollection(ItemDocument.class)
                .then(
                        this.reactiveMongoTemplate.createCollection(ItemDocument.class, CollectionOptions.empty().capped().size(40960000).maxDocuments(1000000))
                ).subscribe();
    }

    @Override
    public Flux<ItemDocument> getAll() {
        log.info("getting all items");
        return itemReactiveRepository.findWithTailableCursorBy();
    }

    @Override
    public Flux<ItemDocument> getAllByDescription(String description) {
        log.info("getting all items by description : " + description);
        return this.itemReactiveRepository.findAllByDescription(description);
    }

    @Override
    public Mono<ItemDocument> getById(String id) {
        log.info("getting item by id : " + id);
        return this.itemReactiveRepository.findById(id);
    }

    @Override
    public Mono<ItemDocument> save(ItemDocument itemDocument) {
        log.info("saving item : " + itemDocument);
        return this.itemReactiveRepository.save(itemDocument);
    }

    @Override
    public Mono<ItemDocument> update(String id, ItemDocument itemDocument) {
        log.info("updating item : " + itemDocument);
        return this.itemReactiveRepository.findById(id)
                .flatMap((item -> this.itemReactiveRepository.save(itemDocument)));
    }

    @Override
    public Mono<Void> deleteById(String id) {
        log.info("deleting item by id : " + id);
        return this.itemReactiveRepository.deleteById(id);
    }
}

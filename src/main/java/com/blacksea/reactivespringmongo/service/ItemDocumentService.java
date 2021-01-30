package com.blacksea.reactivespringmongo.service;

import com.blacksea.reactivespringmongo.document.ItemDocument;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ItemDocumentService {
    Flux<ItemDocument> getAll();
    Flux<ItemDocument> getAllByDescription(String description);
    Mono<ItemDocument> getById(String id);
    Mono<ItemDocument> save(ItemDocument itemDocument);
    Mono<ItemDocument> update(String id,ItemDocument itemDocument);
    Mono<Void> deleteById(String id);
}

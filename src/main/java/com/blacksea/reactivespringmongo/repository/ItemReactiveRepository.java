package com.blacksea.reactivespringmongo.repository;

import com.blacksea.reactivespringmongo.document.ItemDocument;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.data.mongodb.repository.Tailable;
import reactor.core.publisher.Flux;

public interface ItemReactiveRepository extends ReactiveMongoRepository<ItemDocument,String> {
    @Tailable
    Flux<ItemDocument> findAllBy();
    @Tailable
    Flux<ItemDocument> findWithTailableCursorBy();
    Flux<ItemDocument> findAllByName(String name);
    Flux<ItemDocument> findAllByDescription(String description);
}

package com.blacksea.reactivespringmongo.document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "items")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ItemDocument {
    @Id
    private String id;
    private String name;
    private String description;
    private Double price;
}

package com.nfttv.apis.nosql.domain;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.CompoundIndexes;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@Data
@NoArgsConstructor
@CompoundIndexes({
        @CompoundIndex(name = "id_nftInfo", def = "{'id' : 1, 'nftInfo': 1}")
})
public class Nft {

    @Id
    private ObjectId id;

    @Indexed
    private String nftInfo;

}

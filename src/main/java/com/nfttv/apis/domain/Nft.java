package com.nfttv.apis.domain;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.CompoundIndexes;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document
@Data
@NoArgsConstructor
@CompoundIndexes({
        @CompoundIndex(name = "id_nftInfo", def = "{'id' : 1, 'nftInfo': 1}")
})
public class Nft {

    @Id
    ObjectId id;

    @Indexed
    String nftInfo;

}

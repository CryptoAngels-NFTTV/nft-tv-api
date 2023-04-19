package com.nfttv.apis.nosql.domain;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.CompoundIndexes;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Set;

@Document
@Data
@NoArgsConstructor
@CompoundIndexes({@CompoundIndex(name = "id_name_user", def = "{'id' : 1, 'name' : 1, 'user' : 1, 'type' : 1}")})
public class Playlist {

    public enum PlaylistType {
        GENERAL, PUBLIC, PRIVATE
    }

    @Id
    ObjectId id;

    private String name;

    private String user;

    private PlaylistType type;

    private Set<String> items;

}

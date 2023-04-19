package com.nfttv.apis.nosql.repository;

import com.nfttv.apis.nosql.domain.Playlist;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface PlaylistRepository extends MongoRepository<Playlist, ObjectId> {

    List<Playlist> findByUser(final String user);

    List<Playlist> findByType(final Playlist.PlaylistType type);

}

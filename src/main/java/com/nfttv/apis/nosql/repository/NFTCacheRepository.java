package com.nfttv.apis.nosql.repository;


import com.nfttv.apis.nosql.domain.Nft;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Set;

public interface NFTCacheRepository extends MongoRepository<Nft, String> {

    List<Nft> findByIdIn(final Set<String> ids);


}
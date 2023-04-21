package com.nfttv.apis.nosql.repository;


import com.nfttv.apis.nosql.domain.Nft;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Set;

public interface NFTCacheRepository extends MongoRepository<Nft, String> {

//    @Query(value = "SELECT n FROM Nft n WHERE n.id IN :ids")
//    List<Nft> findByIds(@Param("ids") Set<String> ids);
//
    List<Nft> findByIdIn(final Set<String> ids);


}
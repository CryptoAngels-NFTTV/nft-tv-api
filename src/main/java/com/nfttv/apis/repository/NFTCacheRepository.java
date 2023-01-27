package com.nfttv.apis.repository;


import com.nfttv.apis.domain.Nft;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface NFTCacheRepository extends MongoRepository<Nft, String> {

}
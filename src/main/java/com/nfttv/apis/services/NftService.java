package com.nfttv.apis.services;

import com.nfttv.apis.nosql.domain.Nft;
import com.nfttv.apis.nosql.repository.NFTCacheRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
@Slf4j
@AllArgsConstructor
public class NftService {

    private NFTCacheRepository nftCacheRepository;

    public Page<Nft> filterNft(final Pageable pageable) {
        long startTime = System.currentTimeMillis();
        final Page<Nft> nfts = nftCacheRepository.findAll(pageable);
        long timeSpent = System.currentTimeMillis() - startTime;
        log.debug("Time spent - [{}] ", timeSpent);
        return nfts;
    }

    public List<Nft> filterByIds(final Set<String> ids) {
        return nftCacheRepository.findByIdIn(ids);
    }
}

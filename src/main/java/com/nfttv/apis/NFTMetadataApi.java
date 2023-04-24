package com.nfttv.apis;

import com.nfttv.apis.apiresource.NftApiResource;
import com.nfttv.apis.apiresource.NftMetadataApiResource;
import com.nfttv.apis.nosql.domain.Nft;
import com.nfttv.apis.services.NftService;
import com.nfttv.apis.utils.NftUtils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/nftmetada")
@AllArgsConstructor
@Slf4j
@CrossOrigin(origins = "*", maxAge = 3600)
class NFTMetadataApi {

    private NftService nftService;

    private NftUtils nftUtils;

    @GetMapping
    public ResponseEntity<NftApiResource> getNFTs(@RequestParam(required = false) Integer page,
                                                  @RequestParam(required = false) Integer size) {
        Pageable pageable = PageRequest.of(page != null ? page : 0, size != null ? size : 10);
        final Set<NftMetadataApiResource> metadata = nftService.filterNft(pageable).stream().map(nft ->
                this.nftUtils.buildMetadataResource(nft)).collect(Collectors.toSet());
        return ResponseEntity.ok(NftApiResource.builder().nftMetadata(metadata).build());
    }

    @GetMapping("/filter")
    public ResponseEntity<Page<NftMetadataApiResource>> filter(@RequestParam(required = false) Integer page,
                                                               @RequestParam(required = false) Integer size) {
        Pageable pageable = PageRequest.of(page == null ? 0 : page, size == null ? 10 : size);
        final Page<Nft> nfts = nftService.filterNft(pageable);
        return ResponseEntity.ok(new PageImpl<>(nfts.stream().map(nft ->
                this.nftUtils.buildMetadataResource(nft)).collect(Collectors.toList()), pageable, nfts.getTotalPages()));
    }
}

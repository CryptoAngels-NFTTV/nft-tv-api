package com.nfttv.apis;

import com.nfttv.apis.apiresource.NftApiResource;
import com.nfttv.apis.apiresource.NftMetadataApiResource;
import com.nfttv.apis.domain.Nft;
import com.nfttv.apis.repository.NFTCacheRepository;
import com.nfttv.apis.repository.NftQRCodeGenerator;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/nftmetada")
@AllArgsConstructor
class NFTMetadataApi {

    private NFTCacheRepository nftCacheRepository;

    private NftQRCodeGenerator qrCodeGenerator;

    @CrossOrigin(origins = "*", maxAge = 3600)
    @GetMapping
    public ResponseEntity<NftApiResource> filter(@RequestParam(required = false) Integer page,
                                                       @RequestParam(required = false) Integer size) {
        Pageable pageable = PageRequest.of(page != null ? page : 0, size != null ? size : 10);
        final Set<NftMetadataApiResource> metadata = nftCacheRepository.findAll(pageable).stream().map(this::buildMetadataResource).collect(Collectors.toSet());
        return ResponseEntity.ok(NftApiResource.builder().nftMetadata(metadata).build());
    }

    public NftMetadataApiResource buildMetadataResource(final Nft nft) {
        final NftMetadataApiResource.QRCodeInfo pngData = qrCodeGenerator.getQRCodeImage(nft.getId().toHexString());
        return NftMetadataApiResource.builder().qrCode(pngData).uuid(nft.getId().toHexString()).info(nft.getNftInfo()).build();
    }
}

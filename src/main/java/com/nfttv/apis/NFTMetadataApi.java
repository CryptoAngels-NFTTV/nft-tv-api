package com.nfttv.apis;

import com.nfttv.apis.apiresource.NftApiResource;
import com.nfttv.apis.apiresource.NftMetadataApiResource;
import com.nfttv.apis.domain.Nft;
import com.nfttv.apis.repository.NFTCacheRepository;
import com.nfttv.apis.repository.NftQRCodeGenerator;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    ResponseEntity<NftApiResource> getAllTodoList() {
        final Set<NftMetadataApiResource> metadata = nftCacheRepository.findAll().stream().map(this::buildMetadataResource).collect(Collectors.toSet());
        return ResponseEntity.ok(NftApiResource.builder().nftMetadata(metadata).build());
    }

    public  NftMetadataApiResource buildMetadataResource(final Nft nft) {
        final NftMetadataApiResource.QRCodeInfo pngData = qrCodeGenerator.getQRCodeImage(nft.getId().toHexString());
        return NftMetadataApiResource.builder().qrCode(pngData).uuid(nft.getId().toHexString()).metadata(nft.getMetadata()).chain(nft.getChain()).build();
    }
}

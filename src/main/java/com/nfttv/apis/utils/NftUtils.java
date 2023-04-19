package com.nfttv.apis.utils;

import com.nfttv.apis.apiresource.NftMetadataApiResource;
import com.nfttv.apis.nosql.domain.Nft;
import com.nfttv.apis.nosql.repository.NftQRCodeGenerator;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class NftUtils {

    private NftQRCodeGenerator qrCodeGenerator;

    public NftMetadataApiResource buildMetadataResource(final Nft nft) {
        final NftMetadataApiResource.QRCodeInfo pngData = qrCodeGenerator.getQRCodeImage(nft.getId().toHexString());
        return NftMetadataApiResource.builder().qrCode(pngData).uuid(nft.getId().toHexString()).info(nft.getNftInfo()).build();
    }
}

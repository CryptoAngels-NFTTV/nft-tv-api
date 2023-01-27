package com.nfttv.apis.apiresource;

import lombok.Builder;
import lombok.Data;

import java.util.Set;

@Data
@Builder
public class NftApiResource {

    private Set<NftMetadataApiResource> nftMetadata;

}

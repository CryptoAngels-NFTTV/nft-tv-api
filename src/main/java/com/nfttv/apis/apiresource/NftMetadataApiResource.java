package com.nfttv.apis.apiresource;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Builder
@Data
public class NftMetadataApiResource {

    private String uuid;

    private QRCodeInfo qrCode;

    private String chain;

    private List<String> metadata;

    public static class QRCodeInfo {

        private byte[] data;

        public byte[] getData() {
            return data;
        }

        public void setData(byte[] data) {
            this.data = data;
        }
    }

}

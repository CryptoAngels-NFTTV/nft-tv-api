package com.nfttv.apis.nosql.repository;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageConfig;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.nfttv.apis.apiresource.NftMetadataApiResource;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

@Service
public class NftQRCodeGenerator {

    @Cacheable(
            value = "qrCodeCache",
            key = "#uuid")
    public NftMetadataApiResource.QRCodeInfo getQRCodeImage(final String uuid) {
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        BitMatrix bitMatrix = null;
        byte[] pngData = null;
        try {
            bitMatrix = qrCodeWriter.encode(uuid, BarcodeFormat.QR_CODE, 100, 100);
            ByteArrayOutputStream pngOutputStream = new ByteArrayOutputStream();
            MatrixToImageConfig con = new MatrixToImageConfig(0xFF000002, 0xFFFFC041);

            MatrixToImageWriter.writeToStream(bitMatrix, "PNG", pngOutputStream, con);
            pngData = pngOutputStream.toByteArray();
        } catch (WriterException | IOException e) {
            e.printStackTrace();
        }

        final NftMetadataApiResource.QRCodeInfo qrCodeInfo = new NftMetadataApiResource.QRCodeInfo();

        qrCodeInfo.setData(pngData);

        return qrCodeInfo;
    }


}

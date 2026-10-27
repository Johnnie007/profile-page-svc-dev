package com.profile.svc.shared.service;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.profile.svc.shared.model.MultimediaUploadResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.util.Map;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class MultimediaService {
    private final Cloudinary cloudinary;
    private static final Set<String> ALLOWED_IMAGE_TYPES =
            Set.of("image/jpeg", "image/png", "image/jpg");
    private static final Set<String> ALLOWED_PDF_TYPES =
            Set.of("application/pdf");


    public MultimediaUploadResponse saveImage(MultipartFile file) throws IOException {


        if (!ALLOWED_IMAGE_TYPES.contains(file.getContentType())) {
            throw new ResponseStatusException(HttpStatus.UNSUPPORTED_MEDIA_TYPE, "Only JPEG and PNG images are allowed");
        }

        Map<?, ?> result = cloudinary.uploader()
                .upload(
                        file.getBytes(),
                        ObjectUtils.asMap(
                                "resource_type", "image",
                                "folder", "profile_images"
                        )
                );
        return new MultimediaUploadResponse(
                result.get("public_id").toString(),
                result.get("secure_url").toString(),
                String.valueOf(result.get("format")),
                ((Number) result.get("bytes")).longValue());
    }

    public MultimediaUploadResponse savePdf(MultipartFile file) throws IOException {
        if (file == null || file.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "PDF is required");
        }
        if (!ALLOWED_PDF_TYPES.contains(file.getContentType())) {
            throw new ResponseStatusException(HttpStatus.UNSUPPORTED_MEDIA_TYPE, "Only PDF files are allowed");
        }

        Map<?, ?> result = cloudinary.uploader()
                .upload(
                        file.getBytes(),
                        ObjectUtils.asMap(
                                "resource_type", "raw",
                                "folder", "profile_pdfs"
                        ));

        return new MultimediaUploadResponse(
                result.get("public_id").toString(),
                result.get("secure_url").toString(),
                String.valueOf(result.get("format")),
                ((Number) result.get("bytes")).longValue());
    }
}

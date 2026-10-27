package com.profile.svc.shared;

import com.cloudinary.Cloudinary;
import com.cloudinary.Uploader;
import com.profile.svc.shared.model.MultimediaUploadResponse;
import com.profile.svc.shared.service.MultimediaService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyMap;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class MultimediaServiceTest {
    private Cloudinary cloudinary;
    private Uploader uploader;
    private MultimediaService service;

    @BeforeEach
    void setUp() {
        cloudinary = mock(Cloudinary.class);
        uploader = mock(Uploader.class);
        when(cloudinary.uploader()).thenReturn(uploader);
        service = new MultimediaService(cloudinary);
    }

    @Test
    void saveImageUploadsSupportedImageAndReturnsUploadMetadata() throws Exception {
        MockMultipartFile file = new MockMultipartFile(
                "file", "cover.png", "image/png", new byte[]{1, 2, 3});
        when(uploader.upload(any(), anyMap())).thenReturn(uploadResult("image-id", "png"));

        MultimediaUploadResponse response = service.saveImage(file);

        assertEquals(new MultimediaUploadResponse(
                "image-id", "https://cdn.example/image-id", "png", 321L), response);
        verify(uploader).upload(any(), anyMap());
    }

    @Test
    void saveImageRejectsUnsupportedContentTypeBeforeUploading() {
        MockMultipartFile file = new MockMultipartFile(
                "file", "cover.gif", "image/gif", new byte[]{1});

        ResponseStatusException exception = assertThrows(
                ResponseStatusException.class, () -> service.saveImage(file));

        assertEquals(HttpStatus.UNSUPPORTED_MEDIA_TYPE, exception.getStatusCode());
        verify(cloudinary, never()).uploader();
    }

    @Test
    void savePdfUploadsPdfAndReturnsUploadMetadata() throws Exception {
        MockMultipartFile file = new MockMultipartFile(
                "file", "book.pdf", "application/pdf", new byte[]{4, 5});
        when(uploader.upload(any(), anyMap())).thenReturn(uploadResult("pdf-id", "pdf"));

        MultimediaUploadResponse response = service.savePdf(file);

        assertEquals(new MultimediaUploadResponse(
                "pdf-id", "https://cdn.example/pdf-id", "pdf", 321L), response);
        verify(uploader).upload(any(), anyMap());
    }

    @Test
    void savePdfRejectsMissingOrEmptyFile() {
        ResponseStatusException nullFileException = assertThrows(
                ResponseStatusException.class, () -> service.savePdf(null));
        ResponseStatusException emptyFileException = assertThrows(
                ResponseStatusException.class,
                () -> service.savePdf(new MockMultipartFile("file", new byte[0])));

        assertEquals(HttpStatus.BAD_REQUEST, nullFileException.getStatusCode());
        assertEquals(HttpStatus.BAD_REQUEST, emptyFileException.getStatusCode());
        verify(cloudinary, never()).uploader();
    }

    @Test
    void savePdfRejectsNonPdfContentTypeBeforeUploading() {
        MockMultipartFile file = new MockMultipartFile(
                "file", "book.txt", "text/plain", new byte[]{1});

        ResponseStatusException exception = assertThrows(
                ResponseStatusException.class, () -> service.savePdf(file));

        assertEquals(HttpStatus.UNSUPPORTED_MEDIA_TYPE, exception.getStatusCode());
        verify(cloudinary, never()).uploader();
    }

    private Map<String, Object> uploadResult(String publicId, String format) {
        return Map.of(
                "public_id", publicId,
                "secure_url", "https://cdn.example/" + publicId,
                "format", format,
                "bytes", 321);
    }
}

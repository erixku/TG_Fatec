package br.app.harppia.modulo.shared.filestream.endpoint;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import br.app.harppia.modulo.shared.filestream.service.FileStreamService;

import java.io.IOException;

@RestController
@RequestMapping("/v1/files")
public class FileUploadController {

    private final FileStreamService s3Service;

    public FileUploadController(FileStreamService s3Service) {
        this.s3Service = s3Service;
    }

    @PostMapping("/upload")
    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file, String bucketToSave) {
        try {
            String fileUrl = s3Service.uploadFile(file, bucketToSave);
            return ResponseEntity.ok("Arquivo enviado com sucesso: " + fileUrl);
        } catch (IOException e) {
            // Log do erro
            return ResponseEntity.status(500).body("Erro ao enviar o arquivo: " + e.getMessage());
        }
    }
}
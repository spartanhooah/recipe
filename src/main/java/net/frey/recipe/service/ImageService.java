package net.frey.recipe.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public interface ImageService {
    void saveImageFile(Long id, MultipartFile file);
}

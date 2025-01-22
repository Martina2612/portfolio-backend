package com.portfolio.portfolio_backend.controllers;

import com.portfolio.portfolio_backend.model.Artwork;
import com.portfolio.portfolio_backend.repository.artworkRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/artworks")
public class artworkController {

    @Autowired
    private artworkRepository artworkRepository;

    // Endpoint para subir una nueva obra con imagen
    @PostMapping("/upload")
    public Artwork uploadArtwork(
            @RequestParam("title") String title,
            @RequestParam("description") String description,
            @RequestParam("image") MultipartFile image) throws IOException {

        // Convertir el archivo a bytes
        byte[] imageBytes = image.getBytes();

        // Crear y guardar el objeto Artwork
        Artwork artwork = new Artwork();
        artwork.setTitle(title);
        artwork.setDescription(description);
        artwork.setImage(imageBytes);
        return artworkRepository.save(artwork);
    }

    // Endpoint para obtener todas las obras de arte
    @GetMapping
    public List<Artwork> getAllArtworks() {
        return artworkRepository.findAll();
    }

    // Endpoint para obtener la imagen de una obra de arte por ID
    @GetMapping("/{id}/image")
    public ResponseEntity<byte[]> getArtworkImage(@PathVariable Long id) {
        Artwork artwork = artworkRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Artwork no encontrado"));

        // Construir la respuesta con la imagen binaria
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_JPEG); // Cambia según el tipo de archivo que subas
        return new ResponseEntity<>(artwork.getImage(), headers, HttpStatus.OK);
    }

    // Endpoint para obtener una obra específica por ID (sin la imagen)
    @GetMapping("/{id}")
    public Artwork getArtworkById(@PathVariable Long id) {
        return artworkRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Artwork no encontrado"));
    }

    // Endpoint para eliminar una obra de arte por ID
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteArtwork(@PathVariable Long id) {
        artworkRepository.deleteById(id);
        return new ResponseEntity<>("Artwork eliminado con éxito", HttpStatus.OK);
    }
}

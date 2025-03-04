package com.example.besthack_atomdev.controller;
import com.example.besthack_atomdev.dto.LotCardResponse;
import com.example.besthack_atomdev.dto.LotListRequest;
import com.example.besthack_atomdev.dto.LotListResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.data.domain.Page;

import com.example.besthack_atomdev.model.Lot;
import com.example.besthack_atomdev.service.LotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.io.IOException;

@RestController
@RequestMapping("/api/lots")
public class LotController {

    @Autowired
    private LotService lotService;

    // Получить все лоты
    @PostMapping
    public ResponseEntity<Page<LotListResponse>> getAllLots(@RequestBody LotListRequest request) {
        Page<LotListResponse> lots = lotService.getAllLots(request);
        return ResponseEntity.ok(lots);
    }

    // Получить лот по ID
    @GetMapping("/{id}")
    public ResponseEntity<LotCardResponse> getLotById(@PathVariable long id) {
        return lotService.getLotById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Удалить лот
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLot(@PathVariable long id) {
        boolean isDeleted = lotService.deleteLot(id);
        if (isDeleted) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    /// Метод загрузки CSV-файла
    @Operation(
            summary = "Загрузка CSV-файла с лотами",
            description = "Загружает CSV-файл и добавляет лоты в систему. Пропускает строки с ошибками.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Успешная загрузка"),
                    @ApiResponse(responseCode = "207", description = "Частичная загрузка с ошибками", content = @Content(mediaType = "application/json")),
                    @ApiResponse(responseCode = "400", description = "Файл не выбран"),
                    @ApiResponse(responseCode = "500", description = "Ошибка сервера")
            }
    )
    @PostMapping(value = "/upload", consumes = "multipart/form-data")
    public ResponseEntity<?> uploadCsv(
            @Parameter(description = "CSV-файл с лотами", required = true,
                    content = @Content(mediaType = "multipart/form-data",
                            schema = @Schema(type = "string", format = "binary")))
            @RequestParam("file") MultipartFile file) {

        if (file.isEmpty()) {
            return ResponseEntity.badRequest().body("Файл не выбран");
        }

        try {
            // Вызов метода загрузки CSV
            List<String> errors = lotService.uploadLotsFromCsv(file.getInputStream());

            if (errors.isEmpty()) {
                return ResponseEntity.ok("Все лоты успешно загружены");
            } else {
                return ResponseEntity.status(207).body(errors); // 207 - Multi-Status
            }
        } catch (IOException e) {
            return ResponseEntity.status(500).body("Ошибка при обработке файла: " + e.getMessage());
        }
    }
}

package com.penjackasala.climbing_sala.controller;

import com.penjackasala.climbing_sala.dto.IzvestajPosecenostiResponse;
import com.penjackasala.climbing_sala.service.IzvestajService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

/**
 * Controller for reports.
 */
@RestController
@RequestMapping("/api/izvestaji")
public class IzvestajController {

    private final IzvestajService izvestajService;

    public IzvestajController(IzvestajService izvestajService) {
        this.izvestajService = izvestajService;
    }

    /**
     * Generate visit report in JSON.
     * Example:
     * /api/izvestaji/posecenost?start=2026-05-01T00:00:00&end=2026-05-31T23:59:59
     */
    @GetMapping("/posecenost")
    public IzvestajPosecenostiResponse izvestajPosecenosti(
            @RequestParam("start") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime start,
            @RequestParam("end") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime end
    ) {
        return izvestajService.generisiIzvestaj(start, end);
    }

    /**
     * Export report as CSV.
     */
    @GetMapping("/posecenost/csv")
    public ResponseEntity<String> izvestajPosecenostiCsv(
            @RequestParam("start") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime start,
            @RequestParam("end") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime end
    ) {
        String csv = izvestajService.generisiIzvestajCsv(start, end);
        String filename = "izvestaj_posecenost_" + start.toLocalDate() + "_" + end.toLocalDate() + ".csv";
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + filename + "\"")
                .contentType(MediaType.valueOf("text/csv"))
                .body(csv);
    }
}
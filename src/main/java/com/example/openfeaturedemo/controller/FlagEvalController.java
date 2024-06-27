package com.example.openfeaturedemo.controller;

import com.example.openfeaturedemo.dto.BeforeHookEmailCryptoRequestDTO;
import com.example.openfeaturedemo.service.FlagEvalService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "api/v1/flag-eval")
@Tag(name = "OpenFeature Flag Evaluation API", description = "Flag Evaluation Gateway for OpenFeature Server-side.")
public class FlagEvalController {
    private final FlagEvalService flagEvalService;

    @Autowired
    public FlagEvalController(FlagEvalService flagEvalService) {
        this.flagEvalService = flagEvalService;
    }

    //    TODO: 建立相依元件
    @PostMapping(path = "/before-hook-email-crypto")
    @Operation(summary = "Case5: Before Hook Email Crypto Server-side")
    public ResponseEntity<String> beforeHookEmailCrypto(@RequestBody @Valid BeforeHookEmailCryptoRequestDTO flagEvalRequest) {
        flagEvalService.beforeHookEmailCrypto(flagEvalRequest);
        return ResponseEntity.status(HttpStatus.OK).body("Flag evaluated successfully");
    }
}

package org.kirpich.smart_profile.controllers;

import lombok.RequiredArgsConstructor;
import org.kirpich.smart_profile.dto.credit_products.CreateCreditProductRequest;
import org.kirpich.smart_profile.services.CreditProductService;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/credit-products")
@RequiredArgsConstructor
public class CreditProductsController {
    private final CreditProductService creditProductService;

    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    @PostMapping("/create")
    public ResponseEntity<String> createProduct(CreateCreditProductRequest cr){
        try {
            creditProductService.createCreditProduct(cr);
            return ResponseEntity.ok("OK");
        } catch (RuntimeException e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<?> getCreditProducts(Pageable pageable){
        try {
            return ResponseEntity.ok(creditProductService.getCreditProducts(pageable));
        }
        catch (RuntimeException e){
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }
}

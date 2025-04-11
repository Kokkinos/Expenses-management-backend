package com.kokkinos.payments_management_backend.dtos;

public class AuthResponseDTO {
    private String token;
    private long expirationDate;

    public AuthResponseDTO(String token, long expirationDate) {
        this.token = token;
        this.expirationDate = expirationDate;
    }

    public String getToken() {
        return this.token;
    }

    public long getExpirationDate() {
        return this.expirationDate;
    }

}

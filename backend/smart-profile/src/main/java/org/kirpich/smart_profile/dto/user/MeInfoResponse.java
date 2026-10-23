package org.kirpich.smart_profile.dto.user;

public record MeInfoResponse(
        String email,
        String phone
){
    public static MeInfoResponse from(String email, String phone){
        return new MeInfoResponse(
                email, phone
        );
    }
}

package com.example.webCommon.domain.dto.user;

import com.example.webCommon.domain.dto.BaseDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import reactor.util.annotation.Nullable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class RegistryDto extends BaseDto {
    String username;

    String nickname;

    @Nullable
    String phone;

    String password;

    String repeatPassword;
}

package com.sportynote.server.repository.dto;

import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class GymDto {
    private String name;
    private String latitude;
    private String longitude;
    private String si;
    private String gu;
    private String dong;
}

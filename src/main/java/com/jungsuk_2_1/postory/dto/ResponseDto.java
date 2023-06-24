package com.jungsuk_2_1.postory.dto;

import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.List;

@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode(callSuper = false)
public class ResponseDto extends StatusResponseDto {
    private List<Object> data;
}
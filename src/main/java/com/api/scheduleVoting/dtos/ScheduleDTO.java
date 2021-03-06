package com.api.scheduleVoting.dtos;

import com.api.scheduleVoting.entity.ScheduleEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ScheduleDTO {

    private Integer id;

    @NotBlank(message = "Descrição deve ser preenchido")
    private String description;

    public static ScheduleEntity toEntity(ScheduleDTO dto) {
        return ScheduleEntity.builder()
                .id(dto.getId())
                .description(dto.getDescription())
                .build();
    }

    public static ScheduleDTO toDTO(ScheduleEntity contract) {
        return ScheduleDTO.builder()
                .id(contract.getId())
                .description(contract.getDescription())
                .build();
    }
}

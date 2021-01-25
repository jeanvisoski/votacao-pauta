package com.api.contractVoting.dtos;

import com.api.contractVoting.entity.ContractEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ContractDTO {

    private Integer id;

    @NotBlank(message = "Descrição deve ser preenchido")
    private String descricao;

    public static ContractEntity toEntity(ContractDTO dto) {
        return ContractEntity.builder()
                .id(dto.getId())
                .descricao(dto.getDescricao())
                .build();
    }

    public static ContractDTO toDTO(ContractEntity contract) {
        return ContractDTO.builder()
                .id(contract.getId())
                .descricao(contract.getDescricao())
                .build();
    }
}

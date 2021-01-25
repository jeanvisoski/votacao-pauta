package com.api.contractVoting.dtos;

import com.api.contractVoting.entity.AssociatedEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.br.CPF;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AssociatedDTO {

    private Integer id;

    @CPF(message = "Não é um CPF valido")
    @NotBlank(message = "CPF do associado deve ser preenchido")
    private String associatedCpf;

    @NotNull(message = "contractId deve ser preenchido")
    private Integer contractId;

    public static AssociatedEntity toEntity(AssociatedDTO dto) {
        return AssociatedEntity.builder()
                .id(dto.getId())
                .associatedCpf(dto.getAssociatedCpf())
                .contractId(dto.getContractId())
                .build();
    }
}

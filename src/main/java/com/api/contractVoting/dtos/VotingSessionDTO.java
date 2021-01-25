package com.api.contractVoting.dtos;

import com.api.contractVoting.entity.VotingSessionEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
public class VotingSessionDTO {

    private Integer id;

    private LocalDateTime dateTimeStart;

    private LocalDateTime dateTimeEnd;

    private Boolean active;

    public static VotingSessionEntity toEntity(VotingSessionDTO dto) {
        return VotingSessionEntity.builder()
                .id(dto.getId())
                .dateTimeStart(dto.getDateTimeStart())
                .dateTimeEnd(dto.getDateTimeEnd())
                .active(dto.getActive())
                .build();
    }

    public static VotingSessionDTO toDTO(VotingSessionEntity votingSession) {
        return VotingSessionDTO.builder()
                .id(votingSession.getId())
                .dateTimeStart(votingSession.getDateTimeStart())
                .dateTimeEnd(votingSession.getDateTimeEnd())
                .active(votingSession.getActive())
                .build();
    }
}
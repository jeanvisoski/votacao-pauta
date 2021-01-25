package com.api.contractVoting.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import java.time.LocalDateTime;
import java.util.Date;

@Data
@Builder
@Entity
@Table(name = "voting_session")
@AllArgsConstructor
@NoArgsConstructor
public class VotingSessionEntity {

    @Id
    @GeneratedValue(strategy= GenerationType.SEQUENCE, generator = "ID")
    @SequenceGenerator(name = "ID", sequenceName = "VOTING_SESSION_SEQ",allocationSize=1)
    @Column(name = "ID")
    private Integer id;

    @Column
    private LocalDateTime dateTimeStart;

    @Column
    private LocalDateTime dateTimeEnd;

    @Column
    private Boolean active;

}

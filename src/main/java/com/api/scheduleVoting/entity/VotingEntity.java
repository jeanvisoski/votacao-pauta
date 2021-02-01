package com.api.scheduleVoting.entity;

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

@Data
@Builder
@Entity
@Table(name = "voting")
@AllArgsConstructor
@NoArgsConstructor
public class VotingEntity {

    @Id
    @GeneratedValue(strategy= GenerationType.SEQUENCE, generator = "ID")
    @SequenceGenerator(name = "ID", sequenceName = "VOTING_SEQ",allocationSize=1)
    @Column(name = "ID")
    private Integer id;

    @Column
    private Boolean vote;

    @Column
    private Integer scheduleId;

    @Column
    private Integer votingSessionId;
}

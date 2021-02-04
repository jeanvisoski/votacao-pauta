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
import javax.persistence.Table;
import java.time.LocalDateTime;

@Data
@Builder
@Entity
@Table(name = "VOTING_SESSION")
@AllArgsConstructor
@NoArgsConstructor
public class VotingSessionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Integer id;

    @Column(name = "DATE_TIME_START")
    private LocalDateTime dateTimeStart;

    @Column(name = "DATE_TIME_END")
    private LocalDateTime dateTimeEnd;

    @Column(name = "ACTIVE")
    private Boolean active;

}

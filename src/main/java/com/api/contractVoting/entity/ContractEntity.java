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

@Data
@Builder
@Entity
@Table(name = "contract")
@AllArgsConstructor
@NoArgsConstructor
public class ContractEntity {

    @Id
    @GeneratedValue(strategy= GenerationType.SEQUENCE, generator = "ID")
    @SequenceGenerator(name = "ID", sequenceName = "CONTRACT_SEQ",allocationSize=1)
    @Column(name = "ID")
    private Integer id;

    @Column
    private String descricao;
}
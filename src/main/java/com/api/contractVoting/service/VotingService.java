package com.api.contractVoting.service;

import com.api.contractVoting.dtos.AssociatedDTO;
import com.api.contractVoting.dtos.ResultDTO;
import com.api.contractVoting.dtos.VoteDTO;
import com.api.contractVoting.dtos.VotingDTO;
import com.api.contractVoting.exception.NotFoundException;
import com.api.contractVoting.exception.SessionFindException;
import com.api.contractVoting.exception.InvalidVoteException;
import com.api.contractVoting.repository.VotingRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
public class VotingService {

    private final VotingRepository repository;
    private final ContractService contractService;
    private final VotingSessionService votingSessionService;
    private final AssociatedService associatedService;

    @Autowired
    public VotingService(VotingRepository repository, ContractService pautaService, VotingSessionService sessaoVotacaoService, AssociatedService associadoService) {
        this.repository = repository;
        this.contractService = pautaService;
        this.votingSessionService = sessaoVotacaoService;
        this.associatedService = associadoService;
    }

    @Transactional(readOnly = true)
    public boolean isValidVote(VoteDTO dto) {
        log.debug("Validando os dados para voto sessionId = {}, contractId = {}, associatedId = {}", dto.getVotingSessionId(), dto.getContractId(), dto.getAssociatedCpf());

        if (!contractService.isContractValid(dto.getContractId())) {
            log.error("Pauta nao localizada para votacao contractId {}", dto.getAssociatedCpf());
            throw new NotFoundException("Pauta não localizada id " + dto.getContractId());
        } else if (!votingSessionService.isValidVoteSession(dto.getVotingSessionId())) {
            log.error("Tentativa de voto para sessao encerrada votingSessionId {}", dto.getVotingSessionId());
            throw new SessionFindException("Sessão de votação já encerrada");
        } else if (!associatedService.isValidVotingMemberParticipation(dto.getAssociatedCpf(), dto.getContractId())) {
            log.error("Associado tentou votar mais de 1 vez associatedId {}", dto.getAssociatedCpf());
            throw new InvalidVoteException("Não é possível votar mais de 1 vez na mesma pauta");
        } else if (!associatedService.isAssociatedCanVote(dto.getAssociatedCpf())) {
            log.error("Associado nao esta habilitado para votar {}", dto.getAssociatedCpf());
            throw new InvalidVoteException("CPF não aceito para votação");
        }

        return Boolean.TRUE;
    }

    @Transactional
    public String vote(VoteDTO dto) {
        if (isValidVote(dto)) {
            log.debug("Dados validos para voto sessionId = {}, contractId = {}, associatedId = {}", dto.getVotingSessionId(), dto.getContractId(), dto.getAssociatedCpf());

            registerVote(VotingDTO.builder()
                    .contractId(dto.getContractId())
                    .votingSessionId(dto.getVotingSessionId())
                    .vote(dto.getVoting())
                    .build());

            registerAssociatedVoted(dto);

            return "Voto registrado com sucesso!";
        }
        return null;
    }

    @Transactional
    public void registerAssociatedVoted(VoteDTO dto) {
        associatedService.saveAssociated(AssociatedDTO.builder()
                .id(null)
                .associatedCpf(dto.getAssociatedCpf())
                .contractId(dto.getContractId())
                .build());
    }

    @Transactional
    public void registerVote(VotingDTO dto) {
        log.debug("Salvando o voto para contractId {}", dto.getContractId());
        repository.save(VotingDTO.toEntity(dto));
    }

    @Transactional(readOnly = true)
    public VotingDTO searchResultVoting(Integer contractId, Integer votingSessionId) {
        log.debug("Contabilizando os votos para contractId = {}, votingSessionId = {}", contractId, votingSessionId);

        return VotingDTO.builder()
                .contractId(contractId)
                .votingSessionId(votingSessionId)
                .quantityVoteYes(repository.countVotingByContractIdAndVotingSessionIdAndVote(contractId, votingSessionId, Boolean.TRUE))
                .quantityVoteNo(repository.countVotingByContractIdAndVotingSessionIdAndVote(contractId, votingSessionId, Boolean.FALSE))
                .build();
    }

    @Transactional(readOnly = true)
    public ResultDTO searchDataResultVoting(Integer contractId, Integer votingSessionId) {
        if (isValidDateExist(contractId, votingSessionId) && votingSessionService.isSessaoValidaForCount(votingSessionId)) {
            log.debug("Construindo o objeto de retorno do resultado para contractId = {}, votingSessionId = {}", contractId, votingSessionId);
            return new ResultDTO(contractService.searchContractById(contractId), searchResultVoting(contractId, votingSessionId));
        }
        throw new NotFoundException("Sessão de votação ainda está aberta, não é possível obter a contagem do resultado.");
    }

    @Transactional(readOnly = true)
    public boolean isValidDateExist(Integer contractId, Integer votingSessionId) {
        return votingSessionService.isSessionVoting(votingSessionId) && contractService.isContractValid(contractId);
    }
}
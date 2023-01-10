package com.example.letscompete.service;

import com.example.letscompete.dto.PlayerDTO;
import com.example.letscompete.dto.SponsorDTO;
import com.example.letscompete.exception.CannotDeleteEntityException;
import com.example.letscompete.model.Sponsor;
import com.example.letscompete.repository.SponsorRepository;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class SponsorService {

    private final SponsorRepository sponsorRepository;

    public SponsorService(SponsorRepository sponsorRepository) {
        this.sponsorRepository = sponsorRepository;
    }

    public List<SponsorDTO> getSponsorDTOs(){
        List<Sponsor> sponsorList = sponsorRepository.findAll();
        if(sponsorList.isEmpty())
            throw new EntityNotFoundException("There are no sponsors");
        return sponsorList.stream().map(SponsorDTO::new).collect(Collectors.toList());}

    public SponsorDTO getSponsorDTOById(int sponsorId){return new SponsorDTO(sponsorRepository.findById(sponsorId).orElseThrow(()->new EntityNotFoundException("Sponsor with id " + sponsorId + " not found")));}

    protected List<Sponsor> getSponsorsById(List<Integer> sponsorIds){return sponsorRepository.findAllById(sponsorIds);}

    public SponsorDTO add(Sponsor sponsor) {
        return new SponsorDTO(sponsorRepository.save(sponsor));
    }

    public SponsorDTO deleteSponsorWithId(int sponsorId){
        SponsorDTO sponsor = getSponsorDTOById(sponsorId);

        if(!sponsor.getTournamentNames().isEmpty())
            throw new CannotDeleteEntityException("Cannot delete sponsor with id " + sponsor.getSponsorId() + " because sponsor is assigned to one or more tournaments : " + sponsor.getTournamentNames());
        else{

            sponsorRepository.deleteById(sponsorId);
        }
        return sponsor;
    }


    public Sponsor getSponsorById(int sponsorId) { return sponsorRepository.findById(sponsorId).orElseThrow(()->new EntityNotFoundException("Sponsor with id " + sponsorId + " not found"));}

    public SponsorDTO update(Sponsor sponsor) { return new SponsorDTO(sponsorRepository.save(sponsor));}
}

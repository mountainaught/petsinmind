package com.petsinmind.controllers;

import com.petsinmind.JobOffer;
import com.petsinmind.Pet;
import com.petsinmind.Registry;
import com.petsinmind.users.PetOwner;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.*;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class JobOfferController {

    @PostMapping("/job-offer")
    public ResponseEntity<?> createJobOffer(@RequestBody Map<String, Object> body) {
        try {
            UUID petOwnerID = UUID.fromString((String) body.get("petownerID"));
            String location = (String) body.get("location");
            String type = (String) body.get("type");

            List<String> petIDs = (List<String>) body.get("petIDs");
            Calendar start = Calendar.getInstance();
            Calendar end = Calendar.getInstance();

            String startStr = (String) body.get("startDate");
            String endStr = (String) body.get("endDate");

            start.setTime(java.sql.Timestamp.valueOf(startStr.replace("T", " ") + ":00"));
            end.setTime(java.sql.Date.valueOf(endStr));


            JobOffer jobOffer = new JobOffer(
                new PetOwner(petOwnerID),
                new ArrayList<>(), // Skip fetching real pets
                location,
                start,
                end,
                new ArrayList<>(),
                new ArrayList<>(),
                type
            );
            jobOffer.setJobOfferID(UUID.randomUUID());
            Registry.getInstance().createJobOffer(jobOffer);

            return ResponseEntity.ok(Map.of("jobOfferID", jobOffer.getJobOfferID().toString()));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("❌ Error creating job offer: " + e.getMessage());
        }
    }
}


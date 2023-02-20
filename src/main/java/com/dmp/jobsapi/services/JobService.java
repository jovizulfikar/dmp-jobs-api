package com.dmp.jobsapi.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.dmp.jobsapi.models.Job;
import com.dmp.jobsapi.repositories.JobRepository;

@Service
public class JobService {

    @Autowired
    JobRepository jobRepository;

    public List<Job> getJobList() {
        List<Job> list = jobRepository.findAll();
        return list;
    }

    public Job getJobDetail(String id) {
        Job job = jobRepository.findById(id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "job not found."));

        return job;
    }
}

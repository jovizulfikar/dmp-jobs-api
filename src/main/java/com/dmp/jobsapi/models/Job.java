package com.dmp.jobsapi.models;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Job {
    private String id;
    private String type;
    private String url;
    private String createdAt;
    private String company;
    private String companyUrl;
    private String location;
    private String title;
    private String description;
    private String howToApply;
    private String companyLogo;
}

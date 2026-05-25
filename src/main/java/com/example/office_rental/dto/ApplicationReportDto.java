package com.example.office_rental.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ApplicationReportDto {

    private long totalApplications;

    private long pendingApplications;

    private long approvedApplications;

    private long rejectedApplications;

    private double approvalRate;

    private String mostActiveUser;

    private String mostRequestedOffice;

}
package com.resumeanalyzer.model;

import java.util.List;
import java.util.ArrayList;

public class Resume {
    private String candidateName;
    private String email;
    private String phone;
    private List<String> skills;
    private List<String> education;
    private List<String> experience;
    private int experienceYears;
    private String rawText;

    public Resume(String rawText) {
        this.rawText = rawText;
        this.skills = new ArrayList<>();
        this.education = new ArrayList<>();
        this.experience = new ArrayList<>();
        this.experienceYears = 0;
    }

    // Getters and Setters
    public String getCandidateName() { return candidateName; }
    public void setCandidateName(String candidateName) { this.candidateName = candidateName; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public List<String> getSkills() { return skills; }
    public void setSkills(List<String> skills) { this.skills = skills; }

    public List<String> getEducation() { return education; }
    public void setEducation(List<String> education) { this.education = education; }

    public List<String> getExperience() { return experience; }
    public void setExperience(List<String> experience) { this.experience = experience; }

    public int getExperienceYears() { return experienceYears; }
    public void setExperienceYears(int experienceYears) { this.experienceYears = experienceYears; }

    public String getRawText() { return rawText; }

    @Override
    public String toString() {
        return "Resume{" +
                "name='" + candidateName + '\'' +
                ", email='" + email + '\'' +
                ", skills=" + skills +
                ", experienceYears=" + experienceYears +
                '}';
    }
}

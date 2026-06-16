package com.resumeanalyzer.model;

import java.util.*;

public class JobProfile {
    private String roleName;
    private List<String> requiredSkills;
    private List<String> preferredSkills;
    private int minExperienceYears;

    public JobProfile(String roleName, List<String> requiredSkills,
                      List<String> preferredSkills, int minExperienceYears) {
        this.roleName = roleName;
        this.requiredSkills = requiredSkills;
        this.preferredSkills = preferredSkills;
        this.minExperienceYears = minExperienceYears;
    }

    public String getRoleName() { return roleName; }
    public List<String> getRequiredSkills() { return requiredSkills; }
    public List<String> getPreferredSkills() { return preferredSkills; }
    public int getMinExperienceYears() { return minExperienceYears; }

    // ── Built-in job profiles ──────────────────────────────────────────────
    public static Map<String, JobProfile> getDefaultProfiles() {
        Map<String, JobProfile> profiles = new LinkedHashMap<>();

        profiles.put("Java Developer", new JobProfile(
                "Java Developer",
                Arrays.asList("java", "spring", "hibernate", "sql", "git", "maven"),
                Arrays.asList("microservices", "docker", "kubernetes", "aws", "junit"),
                1
        ));

        profiles.put("Python Developer", new JobProfile(
                "Python Developer",
                Arrays.asList("python", "django", "flask", "sql", "git", "rest api"),
                Arrays.asList("machine learning", "pandas", "numpy", "docker", "aws"),
                1
        ));

        profiles.put("Data Scientist", new JobProfile(
                "Data Scientist",
                Arrays.asList("python", "machine learning", "statistics", "sql", "pandas", "numpy"),
                Arrays.asList("tensorflow", "pytorch", "spark", "tableau", "r"),
                2
        ));

        profiles.put("Frontend Developer", new JobProfile(
                "Frontend Developer",
                Arrays.asList("html", "css", "javascript", "react", "git"),
                Arrays.asList("typescript", "nodejs", "webpack", "figma", "jest"),
                1
        ));

        profiles.put("DevOps Engineer", new JobProfile(
                "DevOps Engineer",
                Arrays.asList("docker", "kubernetes", "jenkins", "linux", "git", "aws"),
                Arrays.asList("terraform", "ansible", "python", "ci/cd", "monitoring"),
                2
        ));

        return profiles;
    }
}

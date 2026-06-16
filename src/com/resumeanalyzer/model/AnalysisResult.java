package com.resumeanalyzer.model;

import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.LinkedHashMap;

public class AnalysisResult {
    private Resume resume;
    private String jobRole;
    private int matchScore;           // 0 - 100
    private String strengthLevel;     // Weak / Average / Strong / Excellent
    private List<String> matchedSkills;
    private List<String> missingSkills;
    private List<String> suggestions;
    private Map<String, Integer> sectionScores;  // section → score

    public AnalysisResult(Resume resume, String jobRole) {
        this.resume = resume;
        this.jobRole = jobRole;
        this.matchedSkills = new ArrayList<>();
        this.missingSkills = new ArrayList<>();
        this.suggestions = new ArrayList<>();
        this.sectionScores = new LinkedHashMap<>();
    }

    // Getters and Setters
    public Resume getResume() { return resume; }

    public String getJobRole() { return jobRole; }

    public int getMatchScore() { return matchScore; }
    public void setMatchScore(int matchScore) {
        this.matchScore = matchScore;
        this.strengthLevel = calculateStrengthLevel(matchScore);
    }

    public String getStrengthLevel() { return strengthLevel; }

    public List<String> getMatchedSkills() { return matchedSkills; }
    public void setMatchedSkills(List<String> matchedSkills) { this.matchedSkills = matchedSkills; }

    public List<String> getMissingSkills() { return missingSkills; }
    public void setMissingSkills(List<String> missingSkills) { this.missingSkills = missingSkills; }

    public List<String> getSuggestions() { return suggestions; }
    public void setSuggestions(List<String> suggestions) { this.suggestions = suggestions; }

    public Map<String, Integer> getSectionScores() { return sectionScores; }
    public void setSectionScores(Map<String, Integer> sectionScores) { this.sectionScores = sectionScores; }

    private String calculateStrengthLevel(int score) {
        if (score >= 80) return "Excellent";
        if (score >= 60) return "Strong";
        if (score >= 40) return "Average";
        return "Weak";
    }

    public void printReport() {
        System.out.println("\n╔══════════════════════════════════════════════════╗");
        System.out.println("║         SMART RESUME ANALYSIS REPORT            ║");
        System.out.println("╚══════════════════════════════════════════════════╝");
        System.out.println("  Candidate  : " + resume.getCandidateName());
        System.out.println("  Email      : " + resume.getEmail());
        System.out.println("  Job Role   : " + jobRole);
        System.out.println("  Experience : " + resume.getExperienceYears() + " year(s)");
        System.out.println("──────────────────────────────────────────────────");
        System.out.printf("  Match Score : %d%%  [%s]%n", matchScore, strengthLevel);
        System.out.println("──────────────────────────────────────────────────");

        System.out.println("  Section Scores:");
        sectionScores.forEach((section, score) ->
                System.out.printf("    %-20s : %d/100%n", section, score));

        System.out.println("──────────────────────────────────────────────────");
        System.out.println("  ✔ Matched Skills  : " + matchedSkills);
        System.out.println("  ✘ Missing Skills  : " + missingSkills);
        System.out.println("──────────────────────────────────────────────────");
        System.out.println("  💡 Suggestions:");
        suggestions.forEach(s -> System.out.println("    • " + s));
        System.out.println("══════════════════════════════════════════════════\n");
    }
}

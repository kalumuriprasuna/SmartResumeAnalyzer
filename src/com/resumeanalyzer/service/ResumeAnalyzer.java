package com.resumeanalyzer.service;

import com.resumeanalyzer.model.*;

import java.util.*;
import java.util.stream.Collectors;

public class ResumeAnalyzer {

    private static final int WEIGHT_SKILLS      = 50;
    private static final int WEIGHT_EXPERIENCE  = 25;
    private static final int WEIGHT_EDUCATION   = 15;
    private static final int WEIGHT_COMPLETENESS = 10;

    public AnalysisResult analyze(Resume resume, JobProfile jobProfile) {
        AnalysisResult result = new AnalysisResult(resume, jobProfile.getRoleName());

        // ── 1. Skill matching ────────────────────────────────────────────
        List<String> resumeSkills = resume.getSkills().stream()
                .map(String::toLowerCase).collect(Collectors.toList());

        List<String> matched = new ArrayList<>();
        List<String> missing = new ArrayList<>();

        for (String req : jobProfile.getRequiredSkills()) {
            if (resumeSkills.contains(req.toLowerCase())) {
                matched.add(req);
            } else {
                missing.add(req);
            }
        }
        // Bonus check on preferred skills
        for (String pref : jobProfile.getPreferredSkills()) {
            if (resumeSkills.contains(pref.toLowerCase()) && !matched.contains(pref)) {
                matched.add(pref + " ✨");
            }
        }

        result.setMatchedSkills(matched);
        result.setMissingSkills(missing);

        // ── 2. Section scores ────────────────────────────────────────────
        Map<String, Integer> sectionScores = new LinkedHashMap<>();

        int skillScore = calculateSkillScore(matched, missing, jobProfile);
        int expScore   = calculateExperienceScore(resume, jobProfile);
        int eduScore   = calculateEducationScore(resume);
        int complScore = calculateCompletenessScore(resume);

        sectionScores.put("Skills Match",    skillScore);
        sectionScores.put("Experience",      expScore);
        sectionScores.put("Education",       eduScore);
        sectionScores.put("Completeness",    complScore);

        result.setSectionScores(sectionScores);

        // ── 3. Weighted total ────────────────────────────────────────────
        int total = (skillScore      * WEIGHT_SKILLS      / 100)
                  + (expScore        * WEIGHT_EXPERIENCE  / 100)
                  + (eduScore        * WEIGHT_EDUCATION   / 100)
                  + (complScore      * WEIGHT_COMPLETENESS / 100);

        result.setMatchScore(Math.min(total, 100));

        // ── 4. Suggestions ───────────────────────────────────────────────
        result.setSuggestions(generateSuggestions(resume, jobProfile, missing, result.getMatchScore()));

        return result;
    }

    private int calculateSkillScore(List<String> matched, List<String> missing, JobProfile profile) {
        int total = profile.getRequiredSkills().size();
        if (total == 0) return 100;
        // Count only required (strip preferred ✨ markers)
        long reqMatched = matched.stream().filter(s -> !s.contains("✨")).count();
        return (int) ((reqMatched * 100) / total);
    }

    private int calculateExperienceScore(Resume resume, JobProfile profile) {
        int years = resume.getExperienceYears();
        int required = profile.getMinExperienceYears();
        if (required == 0) return 100;
        if (years == 0)    return 20;        // fresher penalty
        if (years >= required * 2) return 100;
        return Math.min(100, (years * 100) / (required * 2));
    }

    private int calculateEducationScore(Resume resume) {
        List<String> edu = resume.getEducation();
        if (edu.isEmpty()) return 30;
        // B.Tech / M.Tech / MCA = strong
        boolean hasDegree = edu.stream().anyMatch(e ->
                e.contains("BTECH") || e.contains("B.TECH") || e.contains("MTECH")
                || e.contains("MCA") || e.contains("BACHELOR") || e.contains("MASTER"));
        return hasDegree ? 100 : 60;
    }

    private int calculateCompletenessScore(Resume resume) {
        int score = 0;
        if (resume.getCandidateName() != null && !resume.getCandidateName().equals("Unknown")) score += 25;
        if (!resume.getEmail().equals("Not Found"))  score += 25;
        if (!resume.getPhone().equals("Not Found"))  score += 25;
        if (!resume.getSkills().isEmpty())           score += 25;
        return score;
    }

    private List<String> generateSuggestions(Resume resume, JobProfile profile,
                                              List<String> missing, int score) {
        List<String> suggestions = new ArrayList<>();

        if (!missing.isEmpty()) {
            suggestions.add("Add these missing skills to your resume: " + missing);
        }
        if (resume.getExperienceYears() < profile.getMinExperienceYears()) {
            suggestions.add("Build more hands-on experience; consider internships or open-source projects.");
        }
        if (resume.getEducation().isEmpty()) {
            suggestions.add("Include your educational qualifications clearly.");
        }
        if (resume.getPhone().equals("Not Found")) {
            suggestions.add("Add a phone number so recruiters can contact you easily.");
        }
        if (score < 50) {
            suggestions.add("Consider tailoring your resume specifically for the '" + profile.getRoleName() + "' role.");
        }
        if (score >= 80) {
            suggestions.add("Great match! Focus on quantifying your achievements (e.g., 'Reduced latency by 30%').");
        }
        if (suggestions.isEmpty()) {
            suggestions.add("Strong resume! Keep your skills section updated with latest technologies.");
        }
        return suggestions;
    }
}

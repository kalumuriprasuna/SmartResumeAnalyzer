package com.resumeanalyzer.service;

import com.resumeanalyzer.model.Resume;

import java.util.*;
import java.util.regex.*;

public class ResumeParser {

    // Known tech skills dictionary
    private static final List<String> KNOWN_SKILLS = Arrays.asList(
            "java", "python", "c++", "c#", "javascript", "typescript", "kotlin", "swift",
            "html", "css", "react", "angular", "vue", "nodejs", "spring", "spring boot",
            "hibernate", "django", "flask", "fastapi", "rest api", "graphql",
            "sql", "mysql", "postgresql", "mongodb", "redis", "elasticsearch",
            "docker", "kubernetes", "jenkins", "git", "github", "gitlab",
            "aws", "azure", "gcp", "terraform", "ansible", "linux",
            "machine learning", "deep learning", "tensorflow", "pytorch",
            "pandas", "numpy", "scikit-learn", "tableau", "power bi",
            "maven", "gradle", "junit", "selenium", "postman",
            "microservices", "ci/cd", "agile", "scrum", "jira", "figma",
            "spark", "hadoop", "kafka", "rabbitmq", "r", "statistics"
    );

    private static final List<String> EDUCATION_KEYWORDS = Arrays.asList(
            "b.tech", "b.e", "btech", "bachelor", "m.tech", "mtech", "master",
            "mca", "bca", "bsc", "msc", "phd", "diploma", "12th", "10th",
            "computer science", "information technology", "engineering"
    );

    public Resume parse(String rawText) {
        Resume resume = new Resume(rawText);
        String lowerText = rawText.toLowerCase();

        resume.setCandidateName(extractName(rawText));
        resume.setEmail(extractEmail(rawText));
        resume.setPhone(extractPhone(rawText));
        resume.setSkills(extractSkills(lowerText));
        resume.setEducation(extractEducation(lowerText));
        resume.setExperience(extractExperienceSections(rawText));
        resume.setExperienceYears(extractExperienceYears(lowerText));

        return resume;
    }

    private String extractName(String text) {
        // First non-empty line is usually the candidate name
        String[] lines = text.split("\n");
        for (String line : lines) {
            line = line.trim();
            if (!line.isEmpty() && line.length() < 50
                    && !line.contains("@") && !line.matches(".*\\d{5,}.*")) {
                return line;
            }
        }
        return "Unknown";
    }

    private String extractEmail(String text) {
        Pattern pattern = Pattern.compile("[a-zA-Z0-9._%+\\-]+@[a-zA-Z0-9.\\-]+\\.[a-zA-Z]{2,}");
        Matcher matcher = pattern.matcher(text);
        return matcher.find() ? matcher.group() : "Not Found";
    }

    private String extractPhone(String text) {
        Pattern pattern = Pattern.compile("(\\+91[\\s-]?)?[6-9]\\d{9}|\\d{3}[\\s-]\\d{3}[\\s-]\\d{4}");
        Matcher matcher = pattern.matcher(text);
        return matcher.find() ? matcher.group().trim() : "Not Found";
    }

    private List<String> extractSkills(String lowerText) {
        List<String> found = new ArrayList<>();
        for (String skill : KNOWN_SKILLS) {
            if (lowerText.contains(skill)) {
                found.add(skill);
            }
        }
        return found;
    }

    private List<String> extractEducation(String lowerText) {
        List<String> edu = new ArrayList<>();
        for (String keyword : EDUCATION_KEYWORDS) {
            if (lowerText.contains(keyword)) {
                edu.add(keyword.toUpperCase());
            }
        }
        return edu;
    }

    private List<String> extractExperienceSections(String text) {
        List<String> expList = new ArrayList<>();
        String[] lines = text.split("\n");
        boolean inExpSection = false;

        for (String line : lines) {
            String lower = line.toLowerCase().trim();
            if (lower.contains("experience") || lower.contains("work history")) {
                inExpSection = true;
                continue;
            }
            if (inExpSection && lower.isEmpty()) continue;
            if (inExpSection && (lower.contains("education") || lower.contains("skill")
                    || lower.contains("project") || lower.contains("certification"))) {
                inExpSection = false;
            }
            if (inExpSection && !line.trim().isEmpty()) {
                expList.add(line.trim());
            }
        }
        return expList;
    }

    private int extractExperienceYears(String lowerText) {
        // Match patterns like "3 years", "3+ years", "3 yrs"
        Pattern pattern = Pattern.compile("(\\d+)\\s*\\+?\\s*(year|yr)");
        Matcher matcher = pattern.matcher(lowerText);
        int max = 0;
        while (matcher.find()) {
            int val = Integer.parseInt(matcher.group(1));
            max = Math.max(max, val);
        }
        return max;
    }
}

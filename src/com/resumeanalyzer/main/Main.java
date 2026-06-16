package com.resumeanalyzer.main;

import com.resumeanalyzer.model.*;
import com.resumeanalyzer.service.*;
import com.resumeanalyzer.util.FileUtil;

import java.util.*;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ResumeParser parser = new ResumeParser();
        ResumeAnalyzer analyzer = new ResumeAnalyzer();
        Map<String, JobProfile> profiles = JobProfile.getDefaultProfiles();

        System.out.println("╔══════════════════════════════════════════════════╗");
        System.out.println("║        SMART RESUME ANALYZER  v1.0              ║");
        System.out.println("╚══════════════════════════════════════════════════╝");

        while (true) {
            System.out.println("\n  MAIN MENU");
            System.out.println("  1. Analyze resume from file");
            System.out.println("  2. Analyze sample resume (demo)");
            System.out.println("  3. View available job profiles");
            System.out.println("  4. Exit");
            System.out.print("\n  Enter choice: ");

            String choice = scanner.nextLine().trim();

            switch (choice) {
                case "1":
                    analyzeFromFile(scanner, parser, analyzer, profiles);
                    break;
                case "2":
                    runDemo(parser, analyzer, profiles);
                    break;
                case "3":
                    listProfiles(profiles);
                    break;
                case "4":
                    System.out.println("\n  Thank you for using Smart Resume Analyzer!");
                    return;
                default:
                    System.out.println("  Invalid choice. Please try again.");
            }
        }
    }

    private static void analyzeFromFile(Scanner scanner, ResumeParser parser,
                                        ResumeAnalyzer analyzer, Map<String, JobProfile> profiles) {
        System.out.print("\n  Enter path to resume (.txt file): ");
        String path = scanner.nextLine().trim();

        String resumeText;
        try {
            resumeText = FileUtil.readFile(path);
        } catch (Exception e) {
            System.out.println("  ✘ Error reading file: " + e.getMessage());
            return;
        }

        JobProfile profile = selectJobProfile(scanner, profiles);
        if (profile == null) return;

        Resume resume = parser.parse(resumeText);
        AnalysisResult result = analyzer.analyze(resume, profile);
        result.printReport();
    }

    private static void runDemo(ResumeParser parser, ResumeAnalyzer analyzer,
                                Map<String, JobProfile> profiles) {
        System.out.println("\n  Running demo with sample resume...\n");

        String sampleResume =
                "Arjun Sharma\n" +
                "arjun.sharma@email.com | +91 9876543210\n\n" +
                "EXPERIENCE\n" +
                "Software Engineer at TechCorp India - 2 years\n" +
                "Developed REST APIs using Spring Boot and Hibernate.\n" +
                "Worked with Docker and MySQL databases.\n\n" +
                "EDUCATION\n" +
                "B.Tech in Computer Science - NIT Raipur, 2022\n\n" +
                "SKILLS\n" +
                "Java, Spring Boot, Hibernate, MySQL, Git, Maven, Docker, REST API, Linux\n\n" +
                "PROJECTS\n" +
                "Online Library System - Java, Spring Boot, MySQL\n" +
                "Chat Application - Java Sockets, Multithreading";

        Resume resume = parser.parse(sampleResume);
        JobProfile profile = profiles.get("Java Developer");

        AnalysisResult result = analyzer.analyze(resume, profile);
        result.printReport();
    }

    private static JobProfile selectJobProfile(Scanner scanner, Map<String, JobProfile> profiles) {
        System.out.println("\n  Available Job Profiles:");
        List<String> keys = new ArrayList<>(profiles.keySet());
        for (int i = 0; i < keys.size(); i++) {
            System.out.println("  " + (i + 1) + ". " + keys.get(i));
        }
        System.out.print("\n  Select profile number: ");
        try {
            int idx = Integer.parseInt(scanner.nextLine().trim()) - 1;
            if (idx >= 0 && idx < keys.size()) {
                return profiles.get(keys.get(idx));
            }
        } catch (NumberFormatException ignored) {}
        System.out.println("  Invalid selection.");
        return null;
    }

    private static void listProfiles(Map<String, JobProfile> profiles) {
        System.out.println("\n  ── Available Job Profiles ──────────────────────");
        profiles.forEach((name, profile) -> {
            System.out.println("  Role     : " + name);
            System.out.println("  Required : " + profile.getRequiredSkills());
            System.out.println("  Min Exp  : " + profile.getMinExperienceYears() + " year(s)");
            System.out.println("  ────────────────────────────────────────────");
        });
    }
}

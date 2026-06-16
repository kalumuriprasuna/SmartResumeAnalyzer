# Smart Resume Analyzer 🧠

A Java console application that parses resumes and scores them against job profiles using keyword matching, experience analysis, and completeness checks.

## 📁 Project Structure

```
SmartResumeAnalyzer/
├── src/
│   └── com/resumeanalyzer/
│       ├── model/
│       │   ├── Resume.java          ← Data model for parsed resume
│       │   ├── AnalysisResult.java  ← Holds scores, matched/missing skills
│       │   └── JobProfile.java      ← Job roles with required skills
│       ├── service/
│       │   ├── ResumeParser.java    ← Extracts name, email, skills from text
│       │   └── ResumeAnalyzer.java  ← Scoring engine (skills, exp, education)
│       ├── util/
│       │   └── FileUtil.java        ← File read/write helper
│       └── main/
│           └── Main.java            ← CLI entry point
└── sample_resume.txt                ← Sample resume for testing
```

## ▶️ How to Run

### Compile
```bash
find src -name "*.java" > sources.txt
javac -d out @sources.txt
```

### Run
```bash
java -cp out com.resumeanalyzer.main.Main
```

## ✨ Features
- **Resume Parsing** — Extracts name, email, phone, skills, education, experience
- **Job Matching** — Matches 5 built-in job profiles (Java Dev, Python Dev, Data Scientist, Frontend, DevOps)
- **Scoring Engine** — Weighted scoring across Skills (50%), Experience (25%), Education (15%), Completeness (10%)
- **Smart Suggestions** — Personalized recommendations based on gaps
- **Demo Mode** — Run instantly without any file input

## 🔧 Key Concepts Used
- OOP (Encapsulation, Abstraction, Composition)
- Java Collections (List, Map, ArrayList, LinkedHashMap)
- Regex (Pattern, Matcher) for email, phone, year extraction
- Java Streams & Lambdas
- File I/O (java.nio.file)
- Weighted scoring algorithm

## 💡 Possible Extensions
- PDF resume parsing (Apache PDFBox)
- REST API with Spring Boot
- Web UI with React
- NLP-based skill extraction

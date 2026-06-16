package com.resumeanalyzer.gui;

import com.resumeanalyzer.model.*;
import com.resumeanalyzer.service.*;
import com.resumeanalyzer.util.FileUtil;

import javax.swing.*;
import java.awt.*;
import java.util.Map;

public class ResumeAnalyzerUI {

    JFrame frame;
    JTextField fileField;
    JComboBox<String> roleBox;
    JButton browseBtn;
    JButton analyzeBtn;
    JTextArea resultArea;
    JProgressBar progressBar;

    ResumeParser parser;
    ResumeAnalyzer analyzer;
    Map<String, JobProfile> profiles;

    public ResumeAnalyzerUI() {

        parser = new ResumeParser();
        analyzer = new ResumeAnalyzer();
        profiles = JobProfile.getDefaultProfiles();

        frame = new JFrame("Smart Resume Analyzer");

        frame.setSize(850,650);
        frame.setLayout(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JLabel title = new JLabel("SMART RESUME ANALYZER");

        title.setFont(new Font("Arial",Font.BOLD,28));

        title.setBounds(220,20,400,35);

        frame.add(title);

        JLabel resumeLabel = new JLabel("Resume");

        resumeLabel.setBounds(40,90,100,30);

        frame.add(resumeLabel);

        fileField = new JTextField();

        fileField.setBounds(120,90,500,30);

        frame.add(fileField);

        browseBtn = new JButton("Browse");

        browseBtn.setBounds(640,90,120,30);

        frame.add(browseBtn);

        JLabel roleLabel = new JLabel("Job Role");

        roleLabel.setBounds(40,140,100,30);

        frame.add(roleLabel);

        roleBox = new JComboBox<>();

        for(String s:profiles.keySet()){

            roleBox.addItem(s);

        }

        roleBox.setBounds(120,140,250,30);

        frame.add(roleBox);

        analyzeBtn = new JButton("Analyze Resume");

        analyzeBtn.setBounds(420,140,180,35);

        frame.add(analyzeBtn);

        progressBar = new JProgressBar();

        progressBar.setBounds(40,190,720,30);

        progressBar.setStringPainted(true);

        frame.add(progressBar);

        resultArea = new JTextArea();

        resultArea.setEditable(false);

        resultArea.setFont(new Font("Monospaced",Font.PLAIN,15));

        JScrollPane scroll = new JScrollPane(resultArea);

        scroll.setBounds(40,240,760,330);

        frame.add(scroll);

        browseBtn.addActionListener(e->{

            JFileChooser chooser=new JFileChooser();

            int option=chooser.showOpenDialog(frame);

            if(option==JFileChooser.APPROVE_OPTION){

                fileField.setText(

                        chooser.getSelectedFile().getAbsolutePath()

                );

            }

        });

        analyzeBtn.addActionListener(e->{

            try{

                String resumeText= FileUtil.readFile(fileField.getText());

                Resume resume=parser.parse(resumeText);

                JobProfile profile=

                        profiles.get(roleBox.getSelectedItem());

                AnalysisResult result=

                        analyzer.analyze(resume,profile);

                progressBar.setValue(result.getMatchScore());

                progressBar.setString(result.getMatchScore()+" %");

                StringBuilder sb=new StringBuilder();

                sb.append("========== SMART RESUME REPORT ==========\n\n");

                sb.append("Candidate : ")

                        .append(resume.getCandidateName())

                        .append("\n");

                sb.append("Email : ")

                        .append(resume.getEmail())

                        .append("\n");

                sb.append("Job Role : ")

                        .append(profile.getRoleName())

                        .append("\n");

                sb.append("Experience : ")

                        .append(resume.getExperienceYears())

                        .append(" Years\n\n");

                sb.append("Match Score : ")

                        .append(result.getMatchScore())

                        .append("%\n");

                sb.append("Strength : ")

                        .append(result.getStrengthLevel())

                        .append("\n\n");

                sb.append("SECTION SCORES\n\n");

                for(var entry:result.getSectionScores().entrySet()){

                    sb.append(entry.getKey())

                            .append(" : ")

                            .append(entry.getValue())

                            .append("/100\n");

                }

                sb.append("\nMatched Skills\n");

                for(String s:result.getMatchedSkills()){

                    sb.append("✔ ").append(s).append("\n");

                }

                sb.append("\nMissing Skills\n");

                for(String s:result.getMissingSkills()){

                    sb.append("✘ ").append(s).append("\n");

                }

                sb.append("\nSuggestions\n");

                for(String s:result.getSuggestions()){

                    sb.append("• ").append(s).append("\n");

                }

                resultArea.setText(sb.toString());

            }

            catch(Exception ex){

                JOptionPane.showMessageDialog(frame,

                        "Error : "+ex.getMessage());

            }

        });

        frame.setLocationRelativeTo(null);

        frame.setVisible(true);

    }

    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> new ResumeAnalyzerUI());

    }

}

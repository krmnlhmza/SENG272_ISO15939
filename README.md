
ISO 15939 Measurement Process Simulator
SENG272 - Software Project II - Final Project


STUDENT
-------
Name       : Muhammed Hamza Karamanlı
Student ID : 202328036
School     : Çankaya University


PROJECT OVERVIEW
----------------
A Java Swing desktop application that simulates the 5 core steps of the
ISO/IEC 15939 software measurement process:

  1. Profile  – enter user/session info
  2. Define   – pick quality type, mode, and scenario
  3. Plan     – view dimensions and metrics (read-only)
  4. Collect  – enter raw values, see auto-computed 1.0–5.0 scores
  5. Analyse  – weighted dimension averages + radar chart + gap analysis


REQUIREMENTS
------------
- Java SE 17 or higher (no external libraries used)
- Standard Java SE only (Swing, AWT, java.util.*)


HOW TO COMPILE
--------------
From inside the `src/` folder:

    javac *.java


HOW TO RUN
----------
From inside the `src/` folder, after compiling:

    java MeasurementSimulator


PROJECT STRUCTURE
-----------------
src/
├── MeasurementSimulator.java   # Main + wizard host (JFrame, navigation, step indicator)
├── ProfilePanel.java           # Step 1
├── DefinePanel.java            # Step 2
├── PlanPanel.java              # Step 3
├── CollectPanel.java           # Step 4
├── AnalysePanel.java           # Step 5
├── RadarChartPanel.java        # Bonus: radar chart drawn with Graphics2D
├── DataStore.java              # All hard-coded scenarios
├── SessionData.java            # Session DTO shared across panels
├── Scenario.java               # Model
├── Dimension.java              # Model
└── Metric.java                 # Model


DESIGN NOTES
------------
- MVC-ish separation: model classes (SessionData, DataStore, Scenario, Dimension,
  Metric) are completely decoupled from the Swing views (*Panel classes).
- Wizard navigation uses CardLayout; each step is its own JPanel.
- Score formulas (per assignment PDF, page 4):
    higher-is-better : score = 1 + (value − min) / (max − min) × 4
    lower-is-better  : score = 5 − (value − min) / (max − min) × 4
  Scores are clamped to [1.0, 5.0] and rounded to the nearest 0.5.
- Dimension score = Σ(metricScore × metricCoefficient) / Σ(metricCoefficient)
- Each scenario owns its own Dimension/Metric instances — no shared references
  between scenarios, so editing one does not affect another.


SCENARIOS PROVIDED
------------------
Education mode:
  - Scenario C - Team Alpha   (5 dimensions, 10 metrics)
  - Scenario D - Team Beta    (4 dimensions, 7 metrics)

Health mode:
  - Health Scenario 1 - Patient Portal       (3 dimensions, 6 metrics)
  - Health Scenario 2 - Hospital Info System (3 dimensions, 6 metrics)

Custom mode:
  - Reserved for the bonus "user-defined" path (no preset scenarios).


SCREENSHOT
----------
See `screenshot.png` in the repo root (or open the running app — the wizard
window is fully self-explanatory).


REPOSITORY
----------
https://github.com/krmnlhmza/SENG272_ISO15939

# ISO/IEC 15939 Software Measurement Process Simulator

[cite_start]This project is a desktop application developed in Java Swing that simulates the core stages of the **ISO/IEC 15939** international software measurement standard[cite: 5, 9]. [cite_start]It is designed with a clean **MVC (Model-View-Controller)** architecture to ensure a clear separation between data logic and the graphical user interface[cite: 21, 86].

##  Key Features

[cite_start]The application follows a 5-step wizard structure to guide users through the measurement process[cite: 11, 20]:

1.  [cite_start]**Profile:** Captures user and session metadata such as username, school, and session name[cite: 12, 23].
2.  [cite_start]**Define:** Allows selection of Quality Type (Product/Process), Mode (Health, Education, Custom), and specific measurement scenarios[cite: 12, 32].
3.  [cite_start]**Plan:** Displays pre-defined metrics, their coefficients, directions (Higher is Better/Lower is Better), and valid ranges in a read-only table[cite: 12, 47].
4.  [cite_start]**Collect:** Enables entry of raw data values and automatically calculates a score between 1.0 and 5.0 for each metric[cite: 12, 59].
5.  [cite_start]**Analyse:** Performs weighted average calculations for each dimension, generates a **Radar Chart** for visualization, and provides a **Gap Analysis** to identify areas needing improvement[cite: 12, 70, 76, 79].

## Technical Stack

* [cite_start]**Language:** Java SE 17 or higher[cite: 15].
* [cite_start]**GUI Framework:** Java Swing (using CardLayout, JTable, and Graphics2D)[cite: 16, 20, 78].
* [cite_start]**Architecture:** MVC Pattern with solid OOP principles (Encapsulation, Inheritance, Polymorphism)[cite: 18, 21, 87].
* [cite_start]**Data Handling:** Java Collections Framework (ArrayList, HashMap)[cite: 17].

## How to Build and Run

[cite_start]The project is designed to be fully portable and can be compiled and run from the command line without any external library dependencies or IDE requirements[cite: 96, 97, 98].

### Compilation
Navigate to the directory containing the source files and run:
```bash
javac *.java

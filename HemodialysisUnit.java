import java.io.IOException;
import java.util.*;
import java.util.concurrent.TimeUnit;

class Patient {
    int id;
    String name;
    int age;
    String birthday;
    String treatment;
    String gender;
    String physician;

    Patient(int id, String name, int age, String birthday, String treatment, String gender, String physician) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.birthday = birthday;
        this.treatment = treatment;
        this.gender = gender;
        this.physician = physician;
    }
}

class Doctor {
    String name;
    String specialty;
    String contactInfo;
    String workExperience;
    String availableHours;

    Doctor(String name, String specialty, String contactInfo, String workExperience, String availableHours) {
        this.name = name;
        this.specialty = specialty;
        this.contactInfo = contactInfo;
        this.workExperience = workExperience;
        this.availableHours = availableHours;
    }
}

class Nurse {
    String name;
    String department;
    String contactInfo;
    String workExperience;

    Nurse(String name, String department, String contactInfo, String workExperience) {
        this.name = name;
        this.department = department;
        this.contactInfo = contactInfo;
        this.workExperience = workExperience;
    }
}

public class HemodialysisUnit {
    private List<Patient> patients;
    private List<Doctor> doctors;
    private List<Nurse> nurses;
    private static int nextID = 1;

    public static void main(String[] args) {
        HemodialysisUnit system = new HemodialysisUnit();
        system.patients = new ArrayList<>();
        system.doctors = new ArrayList<>();
        system.nurses = new ArrayList<>();

        system.doctors.add(new Doctor("Dr. Lawrence Roaring", "Cardiologist", "123-456-789", "10 years of experience in cardiology", "Wed-Fri: 9 AM - 5 PM"));
        system.doctors.add(new Doctor("Dr. Joemle Agravante", "Nephrologist", "987-654-321", "5 years of experience in nephrology", "Thur-Fri: 8 AM - 4 PM"));
        system.doctors.add(new Doctor("Dr. John Lagriada", "Nephrologist", "321-456-123", "5 years of experience in nephrology", "Mon-Fri: 8 AM - 4 PM"));
        system.doctors.add(new Doctor("Dr. Julianne Alipio", "Cardiologist", "091-234-567-89", "5 years of experience in nephrology", "Mon-Fri: 8 AM - 4 PM"));
        system.nurses.add(new Nurse("Nurse Teemo", "Dialysis", "555-1111", "3 years in dialysis"));
        system.nurses.add(new Nurse("Nurse Samira", "Emergency", "555-2222", "4 years in emergency care"));

        int choice;
        do {
            system.clearScreen();
            system.displayBanner();

            system.typingAnimation("\t[1] View Patients/Remove Patients", 5);
            system.typingAnimation("\t[2] Add Patient", 5);
            system.typingAnimation("\t[3] View Doctors List", 5);
            system.typingAnimation("\t[4] View Nurses List", 5);
            system.typingAnimation("\t[5] Exit\n", 5);

            System.out.print("\tEnter your choice: ");

            choice = system.getValidatedChoice();

            switch (choice) {
                case 1:
                    system.removePatients(system.patients);
                    break;
                case 2:
                    system.addPatient();
                    break;
                case 3:
                    system.viewDoctorsList();
                    break;
                case 4:
                    system.viewNursesList();
                    break;
                case 5:
                    system.goodbyeAnimation();
                    break;
                default:
                    break;
            }

        } while (choice != 5);
    }

    private void displayBanner() {
        System.out.println(
                "  _   _ _____ __  __  ___  ____ ___    _    _  __   ______ ___ ____    _   _ _   _ ___ _____ \n" +
                " | | | | ____|  \\/  |/ _ \\|  _ \\_ _|  / \\  | | \\ \\ / / ___|_ _/ ___|  | | | | \\ | |_ _|_   _|\n" +
                " | |_| |  _| | |\\/| | | | | | | | |  / _ \\ | |  \\ V /\\___ \\| |\\___ \\  | | | |  \\| || |  | |  \n" +
                " |  _  | |___| |  | | |_| | |_| | | / ___ \\| |___| |  ___) | | ___) | | |_| | |\\  || |  | |  \n" +
                " |_| |_|_____|_|  |_|\\___/|____/___/_/   \\_\\_____|_| |____/___|____/   \\___/|_| \\_|___| |_|  \n"
        );
    }

    private void removePatients(List<Patient> patientList) {
        clearScreen();
        displayBanner();

        if (patientList.isEmpty()) {
            System.out.print("\tNo patients to display.\n");
System.out.print("\tReturning to the main menu: ");

            pause(1000);
            return;
        }

        while (true) {
            clearScreen();
            displayBanner();

            System.out.println("\n\tPATIENT LIST\n");
            System.out.printf("\t%-5s | %-20s | %-5s | %-15s | %-20s | %-10s | %-20s%n", "ID", "Name", "Age", "Birthday", "Treatment", "Gender", "Physician");
            System.out.println("\t-------------------------------------------------------------------------------------------------------------------------");

            for (Patient patient : patientList) {
                System.out.printf("\t%-5d | %-20s | %-5d | %-15s | %-20s | %-10s | %-20s%n", 
                    patient.id, 
                    patient.name, 
                    patient.age, 
                    patient.birthday, 
                    patient.treatment,
                    patient.gender,
                    patient.physician);
            }

            System.out.println();  
System.out.print("\tEnter the ID of the patient to remove (or press 'r' to return): ");  
Scanner scanner = new Scanner(System.in);
String input = scanner.nextLine();  

            if (input.equalsIgnoreCase("r")) {
                break;
            }

            if (!input.isEmpty()) {
                int patientID;
                try {
                    patientID = Integer.parseInt(input);
                } catch (NumberFormatException e) {
                    System.out.print("\tInvalid ID. Please enter a valid patient ID or 'r' to return: ");
                    continue;
                }

                boolean found = false;
                for (Iterator<Patient> iterator = patientList.iterator(); iterator.hasNext();) {
                    Patient patient = iterator.next();
                    if (patient.id == patientID) {
                        iterator.remove();
                        typingAnimation("\tPatient with ID " + patientID + " has been removed.", 5);
                        found = true;
                        break;
                    }
                }

                if (!found) {
                    typingAnimation("\tPatient with ID " + patientID + " not found.", 5);
                }
            }
        }
    }

    private void addPatient() {
    Scanner scanner = new Scanner(System.in);

    while (true) {
        clearScreen();
        displayBanner();
        System.out.println("\tAdd Patient");

        // Display default patients in a table
        System.out.println("\n\tDEFAULT PATIENTS LIST\n");
        System.out.printf("\t%-20s | %-5s | %-15s | %-20s | %-10s | %-20s%n", "Name", "Age", "Birthday", "Treatment", "Gender", "Physician");
        System.out.println("\t-----------------------------------------------------------------------------------------------------------");

        // Default Filipino names with some example data
        List<Patient> defaultPatients = Arrays.asList(
            new Patient(nextID++, "Juan Dela Cruz", 45, "1979-05-10", "Dialysis", "Male", "Dr. Lawrence Roaring"),
            new Patient(nextID++, "Maria Clara", 35, "1989-08-15", "Kidney Transplant", "Female", "Dr. Joemle Agravante"),
            new Patient(nextID++, "Josefa Santos", 50, "1974-02-21", "Dialysis", "Female", "Dr. John Lagriada"),
            new Patient(nextID++, "Andres Bonifacio", 40, "1984-03-22", "Kidney Transplant", "Male", "Dr. Julianne Alipio"),
            new Patient(nextID++, "Emilio Aguinaldo", 55, "1969-11-19", "Dialysis", "Male", "Dr. Lawrence Roaring")
        );

        for (Patient patient : defaultPatients) {
            System.out.printf("\t%-20s | %-5d | %-15s | %-20s | %-10s | %-20s%n",
                    patient.name, patient.age, patient.birthday, patient.treatment, patient.gender, patient.physician);
        }

        System.out.print("\n\tEnter the name of the patient to add (or 'e' to exit): ");
        String name = scanner.nextLine();
        if (name.equalsIgnoreCase("e")) {
            typingAnimation("\tExiting add patient form.", 5);
            return;
        }

        
        Patient selectedPatient = null;
        for (Patient patient : defaultPatients) {
            if (patient.name.equalsIgnoreCase(name)) {
                selectedPatient = patient;
                break;
            }
        }

        
        if (selectedPatient != null) {
            patients.add(selectedPatient);  
            typingAnimation("\tPatient added: " + selectedPatient.name, 5);
        } else {
            
            System.out.print("\tEnter patient age: ");
            int age = Integer.parseInt(scanner.nextLine());

            System.out.print("\tEnter patient birthday (YYYY-MM-DD): ");
            String birthday = scanner.nextLine();

            System.out.print("\tEnter treatment: ");
            String treatment = scanner.nextLine();

            System.out.print("\tEnter gender: ");
            String gender = scanner.nextLine();

            System.out.print("\tEnter physician's name: ");
            String physician = scanner.nextLine();

            Patient newPatient = new Patient(nextID++, name, age, birthday, treatment, gender, physician);
            patients.add(newPatient);
            typingAnimation("\tPatient added: " + newPatient.name, 5);
        }

        System.out.print("\tDo you want to add more patients? (y/n or 'e' to exit): ");
        char addMore = scanner.nextLine().charAt(0);
        if (addMore == 'n' || addMore == 'N' || addMore == 'e' || addMore == 'E') {
            typingAnimation("\tExiting add patient form.", 5);
            return;
        }
    }
}


    private void viewDoctorsList() {
        clearScreen();
        displayBanner();

        if (doctors.isEmpty()) {
            System.out.print("\tNo doctors to display.\n");
            System.out.print("\tReturning to the main menu: ");
            pause(1000);
            return;
        }

        while (true) {
            clearScreen();
            displayBanner();

            System.out.println("\tDEFAULT DOCTORS LIST\n");
            System.out.printf("\t%-20s | %-20s | %-20s | %-40s | %-20s%n", "Name", "Specialty", "Contact Info", "Work Experience", "Available Hours");
            System.out.println("\t-------------------------------------------------------------------------------------------------------------------------------------");

            for (Doctor doctor : doctors) {
                System.out.printf("\t%-20s | %-20s | %-20s | %-40s | %-20s%n", 
                    doctor.name, 
                    doctor.specialty, 
                    doctor.contactInfo, 
                    doctor.workExperience,
                    doctor.availableHours);
            }

            typingAnimation("\n\tOptions:\n", 5);
            typingAnimation("\t[1] Search Doctors by Name", 5);
            typingAnimation("\t[2] Sort by Specialty", 5);
            typingAnimation("\t[3] Add Doctor", 5);
            typingAnimation("\t[4] Remove Doctor", 5);
            typingAnimation("\t[5] Back to Menu\n", 5);

            System.out.print("\tEnter your choice: ");
            Scanner scanner = new Scanner(System.in);
            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    searchDoctorByName();
                    break;
                case 2:
                    sortDoctorsBySpecialty();
                    break;
                case 3:
                    addDoctor();
                    break;
                case 4:
                    removeDoctor();
                    break;
                case 5:
                    return;
                default:
                    typingAnimation("\tInvalid choice, please try again.", 5);
            }
        }
    }

    private void viewNursesList() {
        clearScreen();
        displayBanner();

        if (nurses.isEmpty()) {
            System.out.print("\tNo available nurse to display.\n");
System.out.print("\tReturning to the main menu: ");
            pause(1000);
            return;
        }

        while (true) {
            clearScreen();
            displayBanner();

            System.out.println("\tDEFAULT NURSES LIST\n");
            System.out.printf("\t%-20s | %-20s | %-20s | %-20s%n", "Name", "Department", "Contact Info", "Work Experience");
            System.out.println("\t-------------------------------------------------------------------------------------------------------");

            for (Nurse nurse : nurses) {
                System.out.printf("\t%-20s | %-20s | %-20s | %-20s%n", 
                    nurse.name, 
                    nurse.department, 
                    nurse.contactInfo,
                    nurse.workExperience);
            }

            typingAnimation("\n\tOptions:\n", 5);
            typingAnimation("\t[1] Search Nurses by Name", 5);
            typingAnimation("\t[2] Add Nurse", 5);
            typingAnimation("\t[3] Remove Nurse", 5);
            typingAnimation("\t[4] Back to Menu\n", 5);

            System.out.print("\tEnter your choice: ");
            Scanner scanner = new Scanner(System.in);
            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    searchNurseByName();
                    break;
                case 2:
                    addNurse();
                    break;
                case 3:
                    removeNurse();
                    break;
                case 4:
                    return;
                default:
                    typingAnimation("\tInvalid choice, please try again.", 5);
            }
        }
    }

    private void typingAnimation(String text, int delayInMillis) {
        for (char c : text.toCharArray()) {
            System.out.print(c);
            try {
                Thread.sleep(delayInMillis);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        System.out.println();
    }

    private void clearScreen() {
        try {
            if (System.getProperty("os.name").contains("Windows")) {
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            } else {
                System.out.print("\033[H\033[2J");
                System.out.flush();
            }
        } catch (IOException | InterruptedException ex) {
            ex.printStackTrace();
        }
    }

    private void pause(long milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    private int getValidatedChoice() {
        Scanner scanner = new Scanner(System.in);
        int choice = -1;
        while (choice < 1 || choice > 5) {
            try {
                choice = scanner.nextInt();
                if (choice < 1 || choice > 5) {
                    typingAnimation("\tInvalid input. Please enter a number between 1 and 5.", 5);
                }
            } catch (InputMismatchException e) {
                typingAnimation("\tInvalid input. Please enter a valid number.", 5);
                scanner.nextLine();  // Clear buffer
            }
        }
        return choice;
    }

    private void goodbyeAnimation() {
        typingAnimation("\n\tGoodbye! Thank you for using the Hemodialysis Unit system.", 5);
    }

private void searchDoctorByName() {
    
    System.out.print("\tEnter doctor's name to search: ");
    Scanner scanner = new Scanner(System.in);
    String name = scanner.nextLine();
    
    List<Doctor> result = new ArrayList<>();
    for (Doctor doctor : doctors) {
        if (doctor.name.toLowerCase().contains(name.toLowerCase())) {
            result.add(doctor);
        }
    }

    
    clearScreen();
    displayBanner();

    
    if (result.isEmpty()) {
        System.out.println("\tNo doctors found.");
    } else {
        
        System.out.println("\tDOCTOR FOUND:\n");
        System.out.printf("\t%-20s | %-20s | %-20s | %-40s | %-20s%n", "Name", "Specialty", "Contact Info", "Work Experience", "Available Hours");
        System.out.println("\t--------------------------------------------------------------------------------------------------------------------------------------");

        for (Doctor doctor : result) {
            System.out.printf("\t%-20s | %-20s | %-20s | %-40s | %-20s%n", 
                doctor.name, 
                doctor.specialty, 
                doctor.contactInfo, 
                doctor.workExperience, 
                doctor.availableHours);
        }
    }

    
    System.out.print("\n\tPress Enter to return to the previous menu: ");
    scanner.nextLine();  
}



    private void sortDoctorsBySpecialty() {
        doctors.sort(Comparator.comparing(d -> d.specialty));
        typingAnimation("\tDoctors have been sorted by specialty.", 10);
    }

    private void addDoctor() {
       System.out.print("\tEnter doctor's name: ");
Scanner scanner = new Scanner(System.in);
String name = scanner.nextLine();

System.out.print("\tEnter doctor's specialty: ");
String specialty = scanner.nextLine();

System.out.print("\tEnter doctor's contact info: ");
String contactInfo = scanner.nextLine();

System.out.print("\tEnter doctor's work experience: ");
String workExperience = scanner.nextLine();

System.out.print("\tEnter doctor's available hours: ");
String availableHours = scanner.nextLine();


        doctors.add(new Doctor(name, specialty, contactInfo, workExperience, availableHours));
        typingAnimation("\tDoctor has been added.", 5);
    }

    private void removeDoctor() {
        System.out.print("\tEnter the name of the doctor to remove: ");
        Scanner scanner = new Scanner(System.in);
        String name = scanner.nextLine();
        doctors.removeIf(d -> d.name.equalsIgnoreCase(name));
        typingAnimation("\tDoctor has been removed.", 5);
    }

    private void searchNurseByName() {
    // Prompt user for input
    System.out.print("\tEnter nurse's name to search: ");
    Scanner scanner = new Scanner(System.in);
    String name = scanner.nextLine();

    // Create a list to store the search results
    List<Nurse> result = new ArrayList<>();
    for (Nurse nurse : nurses) {
        if (nurse.name.toLowerCase().contains(name.toLowerCase())) {
            result.add(nurse);
        }
    }

    
    clearScreen();
    displayBanner();

    
    if (result.isEmpty()) {
        System.out.println("\tNo nurses found.");
    } else {
        // Display found nurses in a formatted table
        System.out.println("\tNURSE FOUND:\n");
        System.out.printf("\t%-20s | %-20s | %-15s | %-20s%n", "Name", "Department", "Contact Info", "Work Experience");
        System.out.println("\t---------------------------------------------------------------------------------------------------------");

        for (Nurse nurse : result) {
            System.out.printf("\t%-20s | %-20s | %-15s | %-20s%n", 
                nurse.name, 
                nurse.department, 
                nurse.contactInfo, 
                nurse.workExperience);
        }
    }

    
    System.out.print("\n\tPress Enter to return to the previous menu: ");
    scanner.nextLine();  
}


    private void addNurse() {
      System.out.print("\tEnter nurse's name: ");
Scanner scanner = new Scanner(System.in);
String name = scanner.nextLine();

System.out.print("\tEnter nurse's department: ");
String department = scanner.nextLine();

System.out.print("\tEnter nurse's contact info: ");
String contactInfo = scanner.nextLine();

System.out.print("\tEnter nurse's work experience: ");
String workExperience = scanner.nextLine();


        nurses.add(new Nurse(name, department, contactInfo, workExperience));
        typingAnimation("\tNurse has been added.", 5);
    }

    private void removeNurse() {
        System.out.print("\tEnter the name of the nurse to remove: ");
Scanner scanner = new Scanner(System.in);
String name = scanner.nextLine();

nurses.removeIf(n -> n.name.equalsIgnoreCase(name));

System.out.print("\tNurse has been removed.");

    }
}

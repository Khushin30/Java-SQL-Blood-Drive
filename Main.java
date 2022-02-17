// authors: Khushin Patel, Zaid Iqbal, Aleexis Sahagun

import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;
import java.util.*;
import java.lang.*;

public class Main {
    public static void main(String[] args) throws SQLException {
        String jbcURL = "jdbc:postgresql://localhost:5432/postgres";
        String username = "postgres";
        String password = "password";
        try {
            Connection connection = DriverManager.getConnection(jbcURL, username, password);
            System.out.println("Connected");
            connection.close();
        } catch (SQLException throwables) {
            System.out.println("Error in connecting to PostgreSQL");
            throwables.printStackTrace();
            return;
        }
        Connection connection = DriverManager.getConnection(jbcURL, username, password);
        Scanner ScanString = new Scanner(System.in);
        Scanner ScanInt = new Scanner(System.in);

        int user = SelectUser();
        mainMenu(user,connection);


    }

    //Asks user to enter their Role
    //Returns "Admin", "Doctor", or "Patient" String
    public static int SelectUser() {
        String input = "";
        while (true) {
            System.out.println("Please type in your role.\n'Admin'\n'Doctor'\n'Patient'");
            Scanner scan = new Scanner(System.in);
            input = scan.nextLine();
            if (!input.equals("Admin") && !input.equals("Doctor") && !input.equals("Patient")) {
                System.out.println("Role not found. Please try again");
            } else {
                break;
            }
        }
        if(input.equals("Admin"))
        {
            return 0;
        }
        else if(input.equals("Doctor"))
        {
            return 1;
        }
        else if(input.equals("Patient"))
        {
            return 2;
        }
        else
        {
            System.out.println("Unable to parse input");
            return -1;
        }
    }

    //Returns "Organ Donor List", "Blood Donor List", "Donor Match List", "Income Report", "Income Report", "Operations Report"
    public static void mainMenu(int user, Connection conn) throws SQLException {
        String input;
        String option;
        Scanner ScanString = new Scanner(System.in);
        Scanner ScanInt = new Scanner(System.in);

        // working with the admin allow access to all queries
        if (user == 0){
            // prompt the user to select the report type until they input a type that is supported
            // create a scanner object to ask for the report type
            // ask for which report they want
            //TODO update new options
            System.out.println("What category report would you like? Ex: 'Organ Donor List', 'Blood Donor List', 'donor match list', 'income report', 'operations report', 'add organ','create donor','delete donor','create doctor','delete doctor','create patient','delete patient','create hospital','delete hospital', 'exit'");
            input = ScanString.nextLine();
            switch (input.toLowerCase()) {
                case "organ donor list":
                    OrganDonorList(conn, ScanString);
                    askToExit(user,conn);
                case "blood donor list":
                    BloodDonorList(conn, ScanString);
                    askToExit(user,conn);
                case "donor match list":
                    DonorMatchList(conn);
                    askToExit(user,conn);
                case "income report":
                    incomeReport(conn);
                    askToExit(user,conn);
                case "operations report":
                    OperationsReport(conn);
                    askToExit(user,conn);
                case "add organ":
                    addOrgan(conn);
                    askToExit(user,conn);
                case "create donor":
                    CreateDonor(conn,ScanString,ScanInt);
                    askToExit(user,conn);
                case "delete donor":
                    DeleteDonor(conn, ScanInt);
                    askToExit(user,conn);
                case "create doctor":
                    CreateDoctor(conn, ScanString, ScanInt);
                    askToExit(user,conn);
                case "delete doctor":
                    DeleteDoctor(conn);
                    askToExit(user,conn);
                case "create patient":
                    createPatient(conn, ScanString, ScanInt);
                    askToExit(user,conn);
                case "delete patient":
                    DeletePatient(conn);
                    askToExit(user,conn);
                case "create hospital":
                    CreateHospital(conn);
                    askToExit(user,conn);
                case "delete hospital":
                    DeleteHospital(conn);
                    askToExit(user,conn);

            }
            // if the input isn't one of the queries we can work with try again
            System.out.println("That input is not supported please try again");
        }
        if (user ==1) {
            // prompt the user to select the report type until they input a type that is supported
            // create a scanner object to ask for the report type
            // ask for which report they want
            System.out.println("What category report would you like? Ex: Donor Info, patient Info, add organ, exit");
            input = ScanString.nextLine();
            switch (input.toLowerCase()) {
                case "donor info":
                    System.out.println("Do you want to get a specific donor? 'yes' or 'no' ");
                    option = ScanString.nextLine();
                    option = option.toLowerCase();
                    if (option.equals("yes")) {
                        getSpecificDonor(conn);
                    } else if (option.equals("no")) {
                        getDonors(conn);
                    } else {
                        System.out.println("Sorry option not supported, please try again ");
                    }
                    askToExit(user,conn);
                case "patient info":
                    System.out.println(" Do you want to get a specific patient? ");
                    option = ScanString.nextLine();
                    option = option.toLowerCase();
                    if (option.equals("yes")) {
                        getSpecificPatient(conn);
                    } else if (option.equals("no")) {
                        getPatients(conn);
                    } else {
                        System.out.println("Sorry option not supported, please try again ");
                    }
                    askToExit(user,conn);
                case "add organ":
                    addOrgan(conn);
                    askToExit(user,conn);
            }
            // if the input isn't one of the queries we can work with try again
            System.out.println("That input is not supported please try again");
        }
        if(user==2){
            // prompt the user to select the report type until they input a type that is supported
            // create a scanner object to ask for the report type
            // ask for which report they want
            System.out.println("What category report would you like? Ex: Donor Info, Patient Info, organ list, 'exit'");
            input = ScanString.nextLine();
            switch (input.toLowerCase()) {
                case "donor info":
                    getDonors(conn);
                    askToExit(user,conn);
                case "blood info":
                    PatientBloodDonorList(conn);
                    askToExit(user, conn);
                case  "organ list":
                    PatientOrganDonorList(conn);
                    askToExit(user, conn);
            }
            // if the input isn't one of the queries we can work with try again
            System.out.println("That input is not supported please try again");
        }
    }

    public static void testSQL (String url, String username, String password) throws SQLException {
        Connection connection = DriverManager.getConnection(url, username, password);

        String sql = "select *\nfrom doctor;";

        Statement statement = connection.createStatement();

        ResultSet rows = statement.executeQuery(sql);

        DBTablePrinter.printResultSet(rows);
    }

    public static void askToExit(int user, Connection conn){
        Scanner scanner = new Scanner(System.in);
        System.out.println("Would you like to continue");
        String input = scanner.next();
        if (input.toLowerCase().equals("yes")){
            try {
                mainMenu(user, conn);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }else {
            System.exit(0);
        }
    }
    public static void OrganDonorList (Connection conn, Scanner scanString) throws SQLException {
        System.out.println("Please enter the name of the organ or type 'all' to get all organs");
        String organ = scanString.nextLine();

        // grabs table of all organs if user types "all", otherwise it sends the table of organ requested
        if(organ.toLowerCase().equals("all"))
        {
            DBTablePrinter.printResultSet(conn.createStatement().executeQuery("select Organ.Name, Donor.Region, Doctor.name from Organ, Donor, Doctor where organ.donorID = donor.donorID and Organ.Name = doctor.specialization"));

        }
        else
        {
            DBTablePrinter.printResultSet(conn.createStatement().executeQuery("select Organ.Name, Donor.Region, Doctor.name from Organ, Donor, Doctor where organ.donorID = donor.donorID and Organ.Name = doctor.specialization and Organ.Name = '" + organ + "'"));
        }
    }

    public static void BloodDonorList(Connection conn, Scanner ScanString) throws SQLException
    {
        System.out.println("What Blood Type are you searching for (type 'all' for all blood type?");
        String input = ScanString.nextLine();

        if(input.toLowerCase().equals("all"))
        {
            DBTablePrinter.printResultSet(conn.createStatement().executeQuery("select Donor.Region, Blood.BloodType, Blood.Quantity as availability, Donor.Age as AgeGroup from Organ, Donor, Blood where Blood.Donorid = Donor.donorid group by Donor.Region, Blood.bloodtype, Blood.Quantity, Donor.Age"));

        }
        else
        {
            DBTablePrinter.printResultSet(conn.createStatement().executeQuery("select Donor.Region, Blood.BloodType, Blood.Quantity as availability, Donor.Age as AgeGroup from Organ, Donor, Blood where Blood.Donorid = Donor.donorid and blood.bloodtype = '"+input+"' group by Donor.Region, Blood.bloodtype, Blood.Quantity, Donor.Age\n"));
        }
    }

    public static void PatientBloodDonorList(Connection conn) throws SQLException
    {
        DBTablePrinter.printResultSet(conn.createStatement().executeQuery("select * from Blood"));
    }

    public static void OperationsReport (Connection conn) throws SQLException
    {
        DBTablePrinter.printResultSet(conn.createStatement().executeQuery("select Doctor.name, Hospital.region, count(*) as Count from Doctor, patient, hospital where hospital.name = patient.hospital and patient.doctor = doctor.workid group by doctor.name, hospital.region"));
    }

    public static void CreateDonor(Connection conn, Scanner ScanString, Scanner ScanInt) throws SQLException
    {
        String donate = "";
        while (true)
        {
            System.out.println("Is the Donor donating blood or an organ? Enter 'Blood' or 'Organ'");
            donate = ScanString.nextLine();
            if (!donate.equals("Blood") && !donate.equals("Organ"))
            {
                System.out.println("Unable to read input. Please enter 'Blood' or 'Organ'");
            }
            else
            {
                break;
            }
        }

        String BloodType = "";
        while(true)
        {
            System.out.println("What is the Blood Type of the Donor?\nPlease enter only 'A', 'B', 'O', 'AB'");
            BloodType = ScanString.nextLine();

            if(!BloodType.equals("A") && !BloodType.equals("B") && !BloodType.equals("O") && !BloodType.equals("AB"))
            {
                System.out.println("Unable to read input. Please enter only 'A', 'B', 'O', 'AB'");
            }
            else
            {
                break;
            }
        }


        System.out.println("What is the age of the Donor?");
        int Age = ScanInt.nextInt();

        System.out.println("What chronic diseases does the Donor have? Enter 'none' if there is none");
        String CD = ScanString.nextLine();

        System.out.println("What drugs has the Donor used? Enter 'none' if there is none");
        String DrugUse = ScanString.nextLine();

        System.out.println("Did the Donor ever recieve a Tattoo? Enter 'Y' for Yes or 'N' for No");
        String TattooInput = ScanString.nextLine();
        Date Tattoo = null;
        if (TattooInput.equals("Y"))
        {
            while (true)
            {
                System.out.println("Enter the Date the Donor received the Tattoo in the form of 'YYYY-MM-DD'. Example: 2002-01-01");
                String input = ScanString.nextLine();

                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);

                try
                {
                    Tattoo = formatter.parse(input);
                    break;
                }
                catch(ParseException e)
                {
                    System.out.println("Date was not entered in the correct format, please try again");
                }
            }
        }

        System.out.println("What Medication does the Donor take? Enter 'none' if there is none");
        String Meds = ScanString.nextLine();

        System.out.println("Has the Donor donated before? Enter 'Y' for Yes or 'N' for No");
        String lastDonorInput = ScanString.nextLine();
        Date LastDonation = null;
        if (lastDonorInput.equals("Y"))
        {
            while (true)
            {
                System.out.println("Enter the Date the Donor last donated in the form of 'YYYY-MM-DD'. Example: 2002-01-01");
                String input = ScanString.nextLine();

                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);

                try
                {
                    LastDonation = formatter.parse(input);
                    break;
                }
                catch(ParseException e)
                {
                    System.out.println("Date was not entered in the correct format, please try again");
                }
            }
        }

        System.out.println("What is the Donor's phone number?");
        String Phone = ScanString.nextLine();

        System.out.println("What Region is the Donor from");
        String Region = ScanString.nextLine();

        String OrganName = "";
        if(donate.equals("Organ"))
        {
            System.out.println("What organ is being donated?");
            OrganName = ScanString.nextLine();
        }

        ResultSet sqlLastID = conn.createStatement().executeQuery("select max(donorid) from Donor");
        sqlLastID.next();
        int LastDonorID = sqlLastID.getInt(1);

        PreparedStatement statement = conn.prepareStatement("insert into donor values (?,?,?,?,?,?,?,?,?,?,?,?,?)");

        statement.setInt(1,LastDonorID+1);

        if(donate.equals("Organ"))
        {
            statement.setInt(2,LastDonorID+1);
            statement.setNull(3, Types.NULL);
        }
        else if(donate.equals("Blood"))
        {
            statement.setNull(2,Types.NULL);
            statement.setInt(3, LastDonorID+1);
        }

        statement.setString(4,BloodType);
        statement.setInt(5,Age);

        if(CD.equals("none"))
        {
            statement.setNull(6, Types.NULL);
        }
        else
        {
            statement.setString(6,CD);
        }

        if(DrugUse.equals("none"))
        {
            statement.setNull(7, Types.NULL);
        }
        else
        {
            statement.setString(7,DrugUse);
        }

        if(Tattoo == null)
        {
            statement.setNull(8, Types.NULL);
        }
        else
        {
            statement.setDate(8,new java.sql.Date(Tattoo.getTime()));
        }

        if(Meds.equals("none"))
        {
            statement.setNull(9, Types.NULL);
        }
        else
        {
            statement.setString(9,Meds);
        }

        if(LastDonation == null)
        {
            statement.setNull(10, Types.NULL);
        }
        else
        {
            statement.setDate(10,new java.sql.Date(LastDonation.getTime()));
        }

        statement.setString(11, Phone);
        statement.setString(12, Region);

        if(OrganName.equals("none"))
        {
            statement.setNull(9, Types.NULL);
        }
        else
        {
            statement.setString(13,OrganName);
        }

        statement.executeUpdate();
        System.out.println("Donor added");
    }

    // Start of Khushin's work

    // Removes Donor based on DonorID
    public static void DeleteDonor(Connection conn, Scanner ScanInt) throws SQLException {
        System.out.println("What is the ID of the Donor you would like to remove?");
        int ID = ScanInt.nextInt();

        PreparedStatement statement = conn.prepareStatement("delete from donor where donorid = ?");
        statement.setInt(1,ID);
        statement.executeUpdate();
    }

    //    returns 0 if the patient was created successfully, else returns -1 if there was an error
    public static int createPatient(Connection connection, Scanner ScanString, Scanner ScanInt) throws SQLException {
        // ask the attributes the new patient will have
        System.out.println("Please enter the name of the patient");
        String pName = ScanString.nextLine();
        System.out.println("Please enter the blood type of the patient");
        String pBloodType = ScanString.nextLine();
        System.out.println("Please enter the age of the patient");
        int age = ScanInt.nextInt();
        System.out.println("Please enter the Doctor ID of the doctor treating the patient");
        int doctor = ScanInt.nextInt();
        System.out.println("Please enter the name of the hospital the patient is being treated at");
        String HospitalName = ScanString.nextLine();
        System.out.println("Please enter the phone number of the patient");
        int PhoneNumber = Integer.parseInt(ScanString.nextLine());
        System.out.println("Please enter the email of the patient");
        String email = ScanString.nextLine();
        System.out.println("Please enter the patient's address");
        String address = ScanString.nextLine();
        System.out.println("Please give the patient an ID");
        int patientID = ScanInt.nextInt();
//        try {
//            Statement statement = connection.createStatement();
//            // create an SQL statement that can create a new set of data when given the data values as before
//            String sql = "insert into patient " +
//                    "values (" + patientID + ",'" + pName + "','"+ pBloodType + "'," + age + "," + doctor + ",'"
//                    + HospitalName + "," + PhoneNumber + "," + email + "','" + address + "');";
//            // execute the SQL statement
//            int x = statement.executeUpdate(sql);
//            // if more than 1 lines were effected then it ran successfully
//            if (x > 0){
//                System.out.println("Insert successful, a new patient has been created");
//                return 0;
//            }
//
//        } catch (SQLException throwables) {
//            throwables.printStackTrace();
//        }
        PreparedStatement statement = connection.prepareStatement("insert into patient values (?,?,?,?,?,?,?,?,?)");

        statement.setInt(1,patientID);
        statement.setString(2,pName);
        statement.setString(3,pBloodType);
        statement.setInt(4,age);
        statement.setInt(5,doctor);
        statement.setString(6,HospitalName);
        statement.setInt(7,PhoneNumber);
        statement.setString(8,email);
        statement.setString(9,address);
        statement.executeUpdate();
        // if no lines were effected then it ran unsuccessfully
        System.out.println("end");
        return -1;
    }

    //    removes a patient when given the patient's ID
    public static void DeletePatient(Connection connection){
        Scanner scanner = new Scanner(System.in);
        // ask for the patient ID to delete
        System.out.println("Enter the PatientID for the paitent you want to delete");
        int ID = scanner.nextInt();
        try {
            // make a statement object
            Statement statement = connection.createStatement();
            // make the sql statement
            String sql = "delete from patient\n" +
                    "where patientid = " + ID + ";";
            // store the number of rows edited by the statement
            int x = statement.executeUpdate(sql);
            // if the number of rows edited was more than 0 then it worked
            if (x > 0){
                System.out.println("Delete successful, a patient has been removed");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void PatientOrganDonorList(Connection connection) throws SQLException {
        DBTablePrinter.printResultSet(connection.createStatement().executeQuery("select * from organ"));
//        Scanner scanner = new Scanner(System.in);
//        System.out.println("On what category would you like to search the organs on(enter the attribute you'd like to filter by or enter 'doctors' to see list of specialized doctors, or enter 'full table'");
//        String category = scanner.nextLine();
//        if (category.toLowerCase().equals("doctors")){
//            System.out.println("What organ would you like to find specialized doctors for");
//            String organRequested = scanner.nextLine();
//        }
    }

    // shows the total amount of money earned by the hospitals
    public static void incomeReport(Connection connection){
        // make the sql statement
        String sql = "select p.hospital, sum(doctor.fee) as income\n" +
                "from doctor join patient p on doctor.workid = p.doctor\n" +
                "group by p.hospital;";
        try {
            // make a statement object
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            DBTablePrinter.printResultSet(resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // finds a match for a patient or donor
    public static void DonorMatchList(Connection connection){
        // creating a scanner object
        Scanner scanner = new Scanner(System.in);
        // ask if they'd like results for a patient or donor
        System.out.println("Would you like to search by patient or donor");
        // read the input
        String searchBy = scanner.nextLine();
        //check what they entered
        if (searchBy.toLowerCase().equals("patient") ){
            // ask for and read the ID of the patient they'd like to search
            System.out.println("Please enter the patient's ID you'd like to find a match for");
            int ID = scanner.nextInt();
            // check if the patient has bloodtype of AB and is a universal receiver
            boolean done = ABMethod(connection, ID);
            // if AB returns true then its a special case and we show all the organs available ignoring the bloodtype and we're done
            if (done){
                return;
            }
            // otherwise we need to filter the organs found by the ones matching the bloodtype of patient
            // initialize the sql query
            String sql = "select distinct donor.donorid, patient.patientid, patient.name as patientName\n" +
                    "from donor, patient join doctor d on patient.doctor = d.workid\n" +
                    "where (donor.bloodtype = patient.bloodtype or donor.bloodtype = 'O') and d.specialization = donor.organname and patient.patientid = " + ID;
            try {
                Statement statement = connection.createStatement();
                // execute the sql query and store the result into resultSet
                ResultSet resultSet = statement.executeQuery(sql);
                // pretty print the resulting table
                DBTablePrinter.printResultSet(resultSet);

            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else if (searchBy.toLowerCase().equals("donor")){
            // ask for and read the ID of the patient they'd like to search
            System.out.println("Please enter the donor's ID you'd like to find a match for");
            int ID = scanner.nextInt();
            // initialize the sql query
            String sql = "select distinct donor.donorid, patient.patientid, patient.name\n" +
                    "from donor, patient join doctor d on patient.doctor = d.workid\n" +
                    "where (donor.bloodtype = patient.bloodtype or donor.bloodtype = 'O') and d.specialization = donor.organname and donor.donorid = " + ID;
            try {
                Statement statement = connection.createStatement();
                // execute the sql query and store the result into resultSet
                ResultSet resultSet = statement.executeQuery(sql);
                // pretty print the resulting table
                DBTablePrinter.printResultSet(resultSet);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }else {
            System.out.println("That input is not supported please try again");
            DonorMatchList(connection);
        }

    }

    // deals with the special case of patient having blood type AB
    public static boolean ABMethod(Connection connection, int ID){
        String sql = "select patient.bloodtype\n" +
                "from patient\n" +
                "where patient.patientid = " + ID;
        try {
            Statement statement = connection.createStatement();
            // execute the sql query and store the result into resultSet
            ResultSet resultSet = statement.executeQuery(sql);
            String BloodType = "";
            while (resultSet.next()){
                BloodType = resultSet.getString("bloodtype");
            }
            if (BloodType.toLowerCase().equals("ab")){
                sql = "select distinct donor.donorid, patient.patientid, patient.name\n" +
                        "from donor, patient join doctor d on patient.doctor = d.workid\n" +
                        "where d.specialization = donor.organname and patient.patientid = " + ID;
                resultSet = statement.executeQuery(sql);
                DBTablePrinter.printResultSet(resultSet);
                return true;
            }else {
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }

    }

    // end of Khushin's Work

    public static void getSpecificDonor(Connection conn) throws SQLException {
        Statement statement = conn.createStatement();
        Scanner scan = new Scanner(System.in);
        System.out.println(" Enter donor ID:");
        int donorid = Integer.parseInt(scan.nextLine());
        String sql = "SELECT Bloodtype, Age, Chronicdisease, Drugusage,Lasttattoodate, Medicationhistory, Lastdonation, Phonenumber, Region, Organname, OrganID, BloodID FROM donor WHERE DonorID = " + donorid;

        try {
            ResultSet r = statement.executeQuery(sql);
            DBTablePrinter.printResultSet(r);
        }
        catch(SQLException e){
            e.printStackTrace();
        }
    }

    public static void getDonors(Connection conn) throws SQLException {
        Statement statement = conn.createStatement();
        String sql = "SELECT * FROM donor";
        try {
            ResultSet r = statement.executeQuery(sql);
            DBTablePrinter.printResultSet(r);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static void addOrgan (Connection conn) throws SQLException {
        PreparedStatement statement = conn.prepareStatement("insert into organ values (?,?,?,?,?,?)");
        Scanner scan = new Scanner(System.in);
        ResultSet lastOrganID = conn.createStatement().executeQuery("select max(organID) from organ");
        lastOrganID.next();
        ResultSet lastDonorID = conn.createStatement().executeQuery("select max(donorid) from organ");
        lastDonorID.next();
        int organid = lastOrganID.getInt(1)+1;
        int donorid = lastDonorID.getInt(1)+1;
        System.out.println("Enter the organ name: ");
        String name = scan.nextLine();
        System.out.println("Enter the organ life: ");
        String life = scan.nextLine();
        Date AvailableDate = null;
        while (true)
        {
            System.out.println("Enter the organ available date (Format: YYYY-MM-DD Ex: 2016-04-05):");
            String input = scan.nextLine();

            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
            try
            {
                AvailableDate = formatter.parse(input);
                break;
            }
            catch(ParseException e)
            {
                System.out.println("Date was not entered in the correct format, please try again");
            }
        }
        Date ExpirationDate = null;
        while (true)
        {
            System.out.println(" Enter the organ expire date (Format: YYYY-MM-DD Ex: 2016-04-05):");
            String input = scan.nextLine();

            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);

            try
            {
                ExpirationDate = formatter.parse(input);
                break;
            }
            catch(ParseException e)
            {
                System.out.println("Date was not entered in the correct format, please try again");
            }
        }

        //Set statement values
        statement.setInt(1, organid);
        statement.setInt(2, donorid);
        statement.setString(3, name);
        statement.setInt(4, Integer.parseInt(life));
        statement.setDate(5, new java.sql.Date(AvailableDate.getTime()));
        statement.setDate(6, new java.sql.Date(ExpirationDate.getTime()));

        statement.executeUpdate();
        System.out.println("Organ added successfully!");
//        String sql = "INSERT INTO organ VALUES ("
//                + organid + ",'" + name + "','" + life + "',TO_DATE('"+AvailableDate+"','YYYY-MM-DD')',TO_DATE('"+ExpirationDate+"','YYYY-MM-DD'),'" + donorid + ")";
//        try {
//            statement.executeUpdate(sql);
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
    }
    public static void getSpecificPatient (Connection conn) throws SQLException {
        Statement statement = conn.createStatement();
        Scanner scan = new Scanner(System.in);
        System.out.println(" Enter patient ID:");
        int patientid = Integer.parseInt(scan.nextLine());
        String sql = "SELECT * FROM patient WHERE PatientID = " + patientid;
        ResultSet r = null;
        try {
            r = statement.executeQuery(sql);
            DBTablePrinter.printResultSet(r);
        }
        catch(SQLException e){
            e.printStackTrace();
        }
    }

    public static void getPatients (Connection conn) throws SQLException {
        Statement statement = conn.createStatement();
        String sql = "SELECT * FROM patient";
        try {
            ResultSet r = statement.executeQuery(sql);
            DBTablePrinter.printResultSet(r);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static void CreateDoctor (Connection conn, Scanner ScanString, Scanner ScanInt) throws SQLException {
        Statement statement = conn.createStatement();
        System.out.println("Enter the Work ID: ");
        int workID = ScanInt.nextInt();
        System.out.println("Enter the doctor's name: ");
        String name = ScanString.nextLine();
        System.out.println("Enter the doctors specialization: ");
        String sp = ScanString.nextLine();
        System.out.println("Enter the doctor's fee: ");
        int fee = ScanInt.nextInt();
        String sql = "INSERT INTO doctor (WorkID, Name, Specialization, Fee) VALUES ("
                + workID + ", '" + name + "', '" + sp + "'," + fee + ");";
        try {
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println("Doctor added successfully!");
    }
    public static void DeleteDoctor (Connection conn) throws SQLException {
        Statement statement = conn.createStatement();
        Scanner scan = new Scanner(System.in);
        System.out.println("Enter work ID of the doctor you want to delete: ");
        int workID = Integer.parseInt(scan.nextLine());
        String sql = " DELETE FROM doctor WHERE WorkID = '" + workID + "'";
        try {
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println("Doctor deleted ");
    }
    public static void CreateHospital (Connection conn) throws SQLException {
        Statement statement = conn.createStatement();
        Scanner scan = new Scanner(System.in);
        System.out.println("Enter the hospital name: ");
        String name = scan.nextLine();
        System.out.println("Enter the hospital's region: ");
        String region = scan.nextLine();
        System.out.println("Enter the hospital's cost: ");
        String cost = scan.nextLine();
        String sql = "INSERT INTO hospital (Name, Region, Cost) VALUES ('"
                + name + "', '" + region + "', '" + cost + "')";
        try {
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println("Hospital added successfully!");
    }
    public static void DeleteHospital (Connection conn) throws SQLException {
        Statement statement = conn.createStatement();
        Scanner scan = new Scanner(System.in);
        System.out.println("Enter name of the hospital you want to delete: ");
        String name = scan.nextLine();
        String sql = " DELETE FROM hospital WHERE Name = '" + name + "'";
        try {
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println("Hospital deleted ");

    }
}

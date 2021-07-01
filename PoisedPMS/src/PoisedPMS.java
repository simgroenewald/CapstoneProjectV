// Importing necessary modules
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Scanner;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.IOException;
import java.io.File;  

 
/**
 * Program for PoisedPMS to manage database
 * @author Simone Groenewald
 */
// Creates a class called Poised PMS
public class PoisedPMS {
    /**
     * The main method displays several menus to the user and allows for user input to select a method
     * The user can move backwards and forwards through the menus and certain methods will run based on the task selection.
     * Each menu runs in a loop until the menu is exited so that several tasks can be done
     * @param args Takes in string arguments
     */
	// Main Method
	public static void main(String[] args) {
		// While loop that will run until the program is exited
		while (true) {
		
		// Creates a scanner object that allows for user input
		Scanner input = new Scanner(System.in);
		
		// This allows the user to select which menu they would like to access
		System.out.print("Please enter the number of the menu you would like to access:\n"
				+ "1. Person\n"
				+ "2. Projects\n"
				+ "0. Exit\n");
		System.out.println("Please select a number from 0-2: ");
		// Saves the entered number as the menu
		String menu = input.nextLine();
		int menu_select = Integer.parseInt(menu);
		// Runs if statements based on what the user has selected in the main menu
		if (menu_select == 0) {
			System.out.print("Goodbye");
			break;
		}
		// Runs if statements based on what the user has selected in the main menu
		if (menu_select == 1) {
			Boolean person_view = true;
			while (person_view == true) {
				// This allows the user to select what they would like to do
				System.out.print("Please enter a number of the task you would like to do:\n"
						+ "1. Add Customer\n"
						+ "2. Add Contractor\n"
						+ "3. Add Architect\n"
						+ "4. Add Project Manager\n"
						+ "5. Allocate Project to Person\n"
						+ "0. Back to main menu.\n");
				System.out.println("Please select a number from 0-4: ");
				// Uses the entered number as the task
				String person_task = input.nextLine();
				int person_task_select = Integer.parseInt(person_task);
				if (person_task_select == 1) {
					add_person(0);
				}
				if (person_task_select == 2) {
					add_person(1);
				}
				if (person_task_select == 3) {
					add_person(2);
				}
				if (person_task_select == 4) {
					add_person(3);
				}
				if (person_task_select == 5) {
					assign_project();
				}
				if (person_task_select == 0) {
					person_view = false;
				}
			}
		}
		// Runs if statements based on what the user has selected in the main menu
		if (menu_select == 2) {
			Boolean project_view = true;
			while (project_view == true) {
				// This allows the user to select what they would like to do
				System.out.print("Please enter a number of the task you would like to do:\n"
						+ "1. Add Project\n"
						+ "2. Update Project Status\n"
						+ "3. Search Projects\n"
						+ "4. Finalize Project\n"
						+ "5. View Missed Deadline Project\n"
						+ "6. View Incomplete Project\n"
						+ "0. Back to Main Menu\n"
						);
				System.out.println("Please select a number from 0-4: ");
				// Uses the entered number as the task
				String project_task = input.nextLine();
				int project_task_select = Integer.parseInt(project_task);
				if (project_task_select == 1) {
					add_project();
				}
				if (project_task_select == 2) {
					update_project_status();
				}
				if (project_task_select == 3) {
					System.out.println("1. Search by ID\n2. Search by name");
					String project_search_str = input.nextLine();
					int project_search = Integer.parseInt(project_search_str);
					if (project_search == 1) {
						search_by_id();
					}
					if (project_search == 2) {
						search_by_name();
					}
				}
				if (project_task_select == 4) {
					finalise_project();
				}
				if (project_task_select == 5) {
					String missed_deadline = "SELECT * FROM projects "
							+ "INNER JOIN project_statuses ON projects.project_id = project_statuses.project_id "
							+ "WHERE completion_date > deadline_date;";
					String heading = "Missed Deadlines\n";
					list_projects(missed_deadline, heading);
				}
				if (project_task_select == 6) {
					String missed_deadline = "SELECT * FROM projects "
							+ "INNER JOIN project_statuses ON projects.project_id = project_statuses.project_id "
							+ "WHERE project_statuses.status <> 'Complete';";
					String heading = "Incomplete Projects\n";
					list_projects(missed_deadline, heading);
				}
				if (project_task_select == 0) {
					project_view = false;
				}
			}
			
		}
	}
}

	/**
	 * The add_person method can be accessed through user selection 1-4 in the people menu.
	 * It uses the type_selector parameter as an index value and returns the corresponding person type from the person type list
	 * The user is prompted for several field inputs and the objects is added to the person table in the database
	 * @param type_selector  Int that indicates the index of the person type in people_types
	 * @return Adds a new person of selected type to the people table in PoisedPMS database
	 */
	private static void add_person(int type_selector) {
		try (// Creates a connection and statement object
				Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/PoisedPMS","poisedpms","poisedpms");
				
				Statement stmt = conn.createStatement();
				
			){
			
			//List of people types
			String[] people_types = new String[] {"Customer", "Contractor", "Architect", "Project Manager"};
			
			// Variables that are needed
			String name = "";
			String surname = "";
			String email_address = "";
			String telephone_number = "";
			String address = "";
			String type = "";

			// Allows for user input
			Scanner input = new Scanner(System.in);
			
			// This allows the user to enter a first name
			System.out.print("First name: ");
			name = input.nextLine();
			
			// This allows the user to enter a last name
			System.out.print("Last name: ");
			surname = input.nextLine();
			
			// This allows the user to enter an email address
			System.out.print("Email: ");
			email_address = input.nextLine();
			
			// This allows the user to enter a contact number
			System.out.print("Contact number: ");
			telephone_number = input.nextLine();
			
			// This allows the user to enter a home address
			System.out.print("Home address: ");
			address = input.nextLine();
			
			// Select person type from list based on index number from parameter
			type = people_types[type_selector];
			
			// This adds all the values to a prepared statement
            PreparedStatement ps = conn.prepareStatement("INSERT INTO people (name, surname, email_address, telephone_number, address, type) VALUES(?,?,?,?,?,?)");
            ps.setString(1, name);
            ps.setString(2, surname);
            ps.setString(3, email_address);
            ps.setString(4, telephone_number);
            ps.setString(5, address);
            ps.setString(6, type);
            
            // Executes the statement
            ps.executeUpdate();

			// Issue a SELECT to check the that the correct values were added to the table
			String strSelect = String.format("SELECT * FROM people WHERE name = '%s'", name)  ;
			ResultSet rset = stmt . executeQuery(strSelect);
			
			// Move the cursor to the next row
			System.out.println("\nPerson Added to Database: ");
			while (rset.next()){
				
				System . out . println ( 
						"Person Sys ID: " + rset.getInt("person_id") + "\n"
						+ "Name: " + rset.getString("name") + " "
						+ rset.getString("surname") + "\n"
						+ "Email: " + rset.getString("email_address") + "\n"
						+ "Phone Number: " + rset.getString("telephone_number") + "\n"
						+ "Address: " + rset.getString("address") + "\n"
						+ "Description: " + rset.getString("type") + "\n");
			}
		/**
		 * @exception If the table entry cannot be created due to database constrains then the SQLException is thrown
		 */
		} catch ( SQLException ex ) {
			ex . printStackTrace ();
		}
	}
	/**
	 * The add_project method allows the user to add a new project to the projects table in PoisedPMS database
	 * A customer from existing people can be selected as the projects customer 
	 * A new project status is added to the project_statuses for the new project
	 * @return Adds a new project and project status to the projects and project_statuses tables in PoisedPMS database and assigns a project to a customer 
	 */
	private static void add_project() {

		try (// Creates a connection and statement object
				Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/PoisedPMS","poisedpms","poisedpms");
				
				Statement stmt = conn.createStatement();
				
			){
			
			//List of building types
			String[] building_types = new String[] {"House", "Apartment Block", "Commercial Property", "Industrial Property", "Agricultural Building"};
			
			// Variables that are needed
			int project_id = 0;
			String project_name = "";
			String building_type = "";
			String physical_address = "";
			int erf_num = 0;
			String cust_surname = "";
			Date deadline_date = null;
			int charged = 0;
			
			// Allows for user input
			Scanner input = new Scanner(System.in);
			
			//So first we need to select the correct customer
			String strSelect = "SELECT * FROM people WHERE type = 'Customer'";
			ResultSet rset = stmt . executeQuery ( strSelect );
			
			// Loops through available customers from the people table so that the user can select one
			System.out.println("\nSelect a customer for the project by typing the customers number.");
			// This creates a list of customer ids to check that the user only selects an available one
			ArrayList<Integer> cust_id_list = new ArrayList<Integer>(); 
			while (rset.next()) {
				System.out.println ( 
						"Customer Num: " + rset.getInt("person_id") + ", Fullname: " + rset.getString("name") + " " + rset.getString("surname"));
				cust_id_list.add(rset.getInt("person_id"));		
			}
			
			// This allows the user to select a customer
			Boolean select_cust = true;
			int customer_id = 0;
			while (select_cust == true) {
				System.out.println("Customer No: ");
				String customer_id_str = input.nextLine();
				try {
					customer_id = Integer.parseInt(customer_id_str);
					for (int i = 0; i < cust_id_list.size(); i ++) {
						if (customer_id == cust_id_list.get(i)) { 
							select_cust = false;
						}
					}
					if (select_cust == true) {
						System.out.println("Please only select an available customer id.");
					}
				}
				/**
				 * @exception Throws exception if the users input for customer id is not a number
				 */
				catch(Exception e) {
					  System.out.println("The customer number cannot contain letters or symbols. Please try again");
					}
				
			}
			
			// Prints out all available building types form the building type list
			System.out.print("Select a building type by typing the number: \n");
			for (int i=0; i < 5; i ++) {
				System.out.println((i + 1) + ": " + building_types[i]);
			}
			
			// This allows the user to select a building type
			Boolean select_building = true;
			int building_index = 0;
			while (select_building == true) {
				System.out.println("Building type no: ");
				String building_type_select_str = input.nextLine();
				try {
					building_index = Integer.parseInt(building_type_select_str) - 1;
					building_type = building_types[building_index];
					select_building = false;
				}
				/**
				 * @exception Throws exception if the users input for building type is not a number
				 */
				catch(Exception e) {
					  System.out.println("The building type number cannot contain letters or symbols. Please try again");
					}
			}
			
			// User gets to decide whether they want an automatically generated name.
			Boolean select_project_name = true;
			while (select_project_name == true) {
				System.out.println("Would you like to have the project automatically named (y or n)?");
				String customer_project_name_choice = input.nextLine();
				if (customer_project_name_choice.equals("y")){
					// Gets the customers surname for the project name
					String strSelectCustSurname = String.format("SELECT surname FROM people WHERE person_id = '%s'", customer_id);
					ResultSet cust_surname_rset = stmt . executeQuery ( strSelectCustSurname );
					while (cust_surname_rset.next()) {
						cust_surname = cust_surname_rset.getString("surname");
						project_name = building_type + " " + cust_surname;
						System.out.println("Project name: " + project_name);
						select_project_name = false;
					}
				}
				if (customer_project_name_choice.equals("n")) {
					System.out.println("Provide project name: ");
					project_name = input.nextLine();
					select_project_name = false;
				}
				if (!customer_project_name_choice.equals("y") && !customer_project_name_choice.equals("n")){
					System.out.println("You can only select either y or n. Please try again");
					select_project_name = true;
				}
			}
			
			// This allows the user to enter an erf number
			Boolean select_erf_num = true;
			while (select_erf_num == true) {
				System.out.print("ERF number: ");
				String erf_num_str = input.nextLine();
				try {
					erf_num = Integer.parseInt(erf_num_str);
					select_erf_num = false;
				}
				/**
				 * @exception Throws exception if the users input for erf number is not a number
				 */
				catch(Exception e) {
					  System.out.println("The erf number cannot contain letters or symbols. Please try again");
					}
			}
			
			// Allows the user to enter a physical address
			System.out.print("Physical Address: ");
			physical_address = input.nextLine();
			
			// Allows the user to enter a date
			Boolean select_deadline_date = true;
			while (select_deadline_date == true) {
				System.out.print("Deadline Date (YYYY-MM-DD): ");
				String deadline_date_str = input.nextLine();
				try {
					deadline_date= Date.valueOf(deadline_date_str);
					select_deadline_date = false;
				}
				/**
				 * @exception Throws exception if the users input for deadline date is not in the correct format
				 */
				catch(Exception e) {
					  System.out.println("The date must be in the format YYYY-MM-DD (eg. 2013-01-13). Please try again");
					}
			}
			
			// This allows the user to enter a charged amount 
			Boolean select_charged = true;
			while (select_charged == true) {
				System.out.print("Total Cost: R ");
				String charged_str = input.nextLine();
				try {
					charged = Integer.parseInt(charged_str);
					select_charged = false;
				}
				/**
				 * @exception Throws exception if the users input for the charged amount is not a number
				 */
				catch(Exception e) {
					  System.out.println("The charged amount cannot contain letters or symbols. Please try again");
					}
			}

			
			// This adds all the values to a prepared statement to update the projects table
            PreparedStatement ps = conn.prepareStatement("INSERT INTO projects (project_name , building_type, physical_address, erf_num) VALUES(?,?,?,?)");
            ps.setString(1, project_name);
            ps.setString(2, building_type);
            ps.setString(3, physical_address);
            ps.setInt(4, erf_num);
            
            // Executes the statement
            ps.executeUpdate();
            
            // This gets the created project's table id to use to update the people and project_statuses tables 
			String strSelectProject = String.format("SELECT * FROM projects WHERE project_name = '%s'", project_name);
			ResultSet project_rset = stmt.executeQuery(strSelectProject);
			while (project_rset.next()) {
				project_id = project_rset.getInt("project_id");
			}
			
			// This adds all the values to a prepared statement to update the people table
			PreparedStatement ps_customer = conn.prepareStatement(
					"UPDATE people SET projects = ? WHERE person_id = ?;");
			ps_customer.setInt(1, project_id);
			ps_customer.setInt(2, customer_id);
			ps_customer.executeUpdate();
            
			// This adds all the values to a prepared statement to update the project_statuses table
            PreparedStatement ps_status = conn.prepareStatement("INSERT INTO project_statuses (charged , deadline_date, project_id) VALUES(?,?,?)");
            ps_status.setInt(1, charged);
            ps_status.setDate(2, deadline_date);
            ps_status.setInt(3, project_id);
            ps_status.executeUpdate();

            // Once everything has been added to the tables the project that has been added gets printed out
			ResultSet project_added_rset = stmt.executeQuery(strSelectProject);
			while (project_added_rset.next()){
				System.out.println(
						"\nProjects Added: \n" +
						"Project Sys ID: " + project_added_rset.getInt("project_id") + "\n"
						+ "Project Name: " + project_added_rset.getString("project_name") + "\n"
						+ "Building Type: " + project_added_rset.getString("building_type") + "\n"
						+ "Physical Address: " + project_added_rset.getString("physical_address") + "\n"
						+ "Erf Number: " + project_added_rset.getInt("erf_num") + "\n");
			}
		/**
		 * @exception If any of the table entries cannot be created due to database constrains then the SQLException is thrown
		 */
		} catch ( SQLException ex ) {
			ex . printStackTrace ();
		}
	}
	/**
	 * The update project status method allows the user to update the projects status table
	 * The user can choose between updating the deadline date, the amount paid or the status
	 * @return Update values in the projects_statuses table in PoisedPMS database
	 */
	private static void update_project_status() {

		try (// Creates a connection and statement object
				Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/PoisedPMS","poisedpms","poisedpms");
				
				Statement stmt = conn.createStatement();
				
			){
			
			// Allows for user input
			Scanner input = new Scanner(System.in);
			
			
			System.out.println("Select a project ID to modify it.");
			
			// Prints out all project ids and names:
			String strSelectProject = String.format("SELECT * FROM projects INNER JOIN project_statuses ON "
					+ "projects.project_id = project_statuses.project_id;");
			ResultSet project_rset = stmt.executeQuery(strSelectProject);
			// This creates a list of project ids to check that the user only selects an available one
			ArrayList<Integer> project_id_list = new ArrayList<Integer>(); 
			while (project_rset.next()) {
				System.out.println("ID: " + project_rset.getInt("project_id") + ", Name:" + project_rset.getString("project_name"));
				project_id_list.add(project_rset.getInt("project_id"));
			}
			
			// Allows user to select a project to update 
			Boolean select_project = true;
			int project_id = 0;
			while (select_project == true) {
				System.out.println("Project ID: ");
				String project_id_str = input.nextLine();
				try {
					project_id = Integer.parseInt(project_id_str);
					for (int i = 0; i < project_id_list.size(); i ++) {
						if (project_id == project_id_list.get(i)) { 
							select_project = false;
						}
					}
					if (select_project == true) {
						System.out.println("Please only select an available project id.");
					}
				}
				/**
				 * @exception Throws exception if the users input for project id is not a number
				 */
				catch(Exception e) {
					  System.out.println("The project number cannot contain letters or symbols. Please try again");
					}
				
			}

			// The user can select either to change the due date, the status or the amount paid
			// The values are printed out so that the user can physically see the fields and decide
			System.out.println("Select 1, 2 or 3 depending on which field you want to update: ");
			
			ResultSet project_select_rset = stmt.executeQuery(strSelectProject);
			while (project_select_rset.next()) {
				if (project_select_rset.getInt("project_id") == project_id) {
					System.out.println(
					"ID: " + project_select_rset.getInt("project_id") + 
					"\nName: " + project_select_rset.getString("project_name") + 
					"\nCharged: R" + project_select_rset.getInt("charged") + 
					"\n1. Paid: R" + project_select_rset.getInt("paid") +  
					"\n2. Due on: " + project_select_rset.getDate("deadline_date") + 
					"\n3. Status: " + project_select_rset.getString("status"));
				}
			}
			
			//Here the user can select the field to update's number
			// This allows the user to enter a charged amount 
			Boolean select_update_field = true;
			int update_field = 0;
			while (select_update_field == true) {
				System.out.print("Field number ");
				String field_change_str = input.nextLine();
				try {
					update_field = Integer.parseInt(field_change_str);
					if (update_field == 1 || update_field == 2 || update_field == 3) {
						select_update_field = false;
					}
					if (update_field != 1 && update_field != 2 && update_field != 3) {
						System.out.println("You can only select 1, 2 or 3. Please try again");
					}
				}
				/**
				 * @exception Throws exception if the users input for selected field to update is not a number
				 */
				catch(Exception e) {
					  System.out.println("The selected field number cannot contain letters or symbols. Please try again");
					}
			}

			
			if (update_field == 1) {
				// This allows the user to enter update the amount paid 
				Boolean update_paid = true;
				int prev_paid = 0;
				int paid = 0;
				while (update_paid == true) {
					System.out.println("How much has been paid: R");
					String paid_amount_str = input.nextLine();
					try {
						paid = Integer.parseInt(paid_amount_str);
						update_paid = false;
					}
					/**
					 * @exception Throws exception if the users input for amount paid is not a number
					 */
					catch(Exception e) {
						  System.out.println("The paid amount cannot contain letters or symbols. Please try again");
						}
				}
				
				// Fetches any previous paid amount to add it to the new amount
				ResultSet project_paid_rset = stmt.executeQuery(strSelectProject);
				while (project_paid_rset.next()) {
					prev_paid = project_paid_rset.getInt("paid");
				}
				
				// Calculates the total
				int total_paid = prev_paid + paid;

				// Updates the field
				PreparedStatement ps_paid = conn.prepareStatement(
						"UPDATE project_statuses SET paid = ? WHERE project_id = ?;");
	            ps_paid.setInt(1, total_paid);
	            ps_paid.setInt(2, project_id);
	            ps_paid.executeUpdate();
	            System.out.println("\nUpdated Paid Amount to R" + total_paid + ".\n");
			}			
			
			if (update_field == 2) {
				
				// Allows the user to update the due date
				Boolean update_deadline_date = true;
				Date deadline_date = null;
				while (update_deadline_date == true) {
					System.out.print("Deadline Date (YYYY-MM-DD): ");
					String deadline_date_str = input.nextLine();
					try {
						deadline_date= Date.valueOf(deadline_date_str);
						update_deadline_date = false;
					}
					/**
					 * @exception Throws exception if the users input for deadline date is not in the correct format
					 */
					catch(Exception e) {
						  System.out.println("The date must be in the format YYYY-MM-DD (eg. 2013-01-13). Please try again");
						}
				}

				// Updates the value
				PreparedStatement ps_update_date = conn.prepareStatement(
						"UPDATE project_statuses SET completion_date = ? WHERE project_id = ?;");
				ps_update_date.setDate(1, deadline_date);
				ps_update_date.setInt(2, project_id);
				ps_update_date.executeUpdate();
				System.out.println("\nUpdated deadline date to " + deadline_date + ".\n");
			}
			
			if (update_field == 3) {
				System.out.print("In order to set the status to complete select finalize project from the project menu.\n"
						+ "Select status number from list of statuses: \n");
				String[] statuses = new String[] {"Initialised", "Paperwork Phase", "Foundations", "Primary Construction", "Finishes"};
				for (int i = 0; i < 5; i ++) {
					System.out.println((i + 1) + ". " + statuses[i]);
				}
				
				
				// Allows a user to select a new status
				Boolean update_status = true;
				int status_index = 0;
				while (update_status == true) {
					System.out.println("Status No: ");
					String status_index_str = input.nextLine();
					try {
						status_index = Integer.parseInt(status_index_str);
						for (int i = 0; i < 5; i ++) {
							if (status_index == i) { 
								update_status = false;
							}
						}
						if (update_status == true) {
							System.out.println("Please only select an available status numbers.");
						}
					}
					/**
					 * @exception Throws exception if the users input for status index is not a number
					 */
					catch(Exception e) {
						  System.out.println("The status number cannot contain letters or symbols. Please try again");
						}
					
				}

				PreparedStatement ps_status = conn.prepareStatement(
						"UPDATE project_statuses SET status = ? WHERE project_id = ?;");
	            ps_status.setString(1, statuses[status_index - 1]);
	            ps_status.setInt(2, project_id);
	            ps_status.executeUpdate();
	            System.out.println("\nThe status has been updated to " + statuses[status_index -1] + ".\n");
			}
		/**
		 * @exception If the table entry cannot be updated due to database constrains then the SQLException is thrown
		 */
		} catch ( SQLException ex ) {
			ex . printStackTrace ();
		}
	}
	/**
	 * Allows the user to search through the projects table for a table entry with the project id of the users choice
	 * All current projects are retrieved but only the searched one's information will be presented
	 * @return Prints out all relevant information regarding the searched project
	 */
	private static void search_by_id() {
		try (// Creates a connection and statement object
				Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/PoisedPMS","poisedpms","poisedpms");
				
				Statement stmt = conn.createStatement();
				
			){
			Scanner input = new Scanner(System.in);
		
			System.out.println("Project id: ");
			String project_id_str = input.nextLine();
		
			String strSelectProject = String.format("SELECT * FROM projects INNER JOIN project_statuses ON "
					+ "projects.project_id = project_statuses.project_id;");
			ResultSet project_rset = stmt.executeQuery(strSelectProject);
			
			fetch_all_project_info(stmt, project_id_str, project_rset);
		/**
		 * @exception If entered project id cannot be found in the projects table then an SQLException is thrown
		 */	
		} catch ( SQLException ex ) {
			ex . printStackTrace ();
		}
		
	}
	/**
	 * Allows the user to search through the projects table for a table entry with the project name of the users choice
	 * All current projects are retrieved but only the searched one's information will be presented
	 * @return Prints out all relevant information regarding the searched project
	 */
	private static void search_by_name() {
		try (// Creates a connection and statement object
				Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/PoisedPMS","poisedpms","poisedpms");
				
				Statement stmt = conn.createStatement();
				
			){
			Scanner input = new Scanner(System.in);
		
			System.out.println("Project Name: ");
			String project_name = input.nextLine();
		
			String strSelectProject = String.format("SELECT * FROM projects INNER JOIN project_statuses ON "
					+ "projects.project_id = project_statuses.project_id;");
			ResultSet project_rset = stmt.executeQuery(strSelectProject);
			
			fetch_all_project_info(stmt, project_name, project_rset);
		/**
		 * @exception If entered project name cannot be found in the projects table then an SQLException is thrown
		 */		
		} catch ( SQLException ex ) {
			ex . printStackTrace ();
		}
	}
	/**
	 * Loops through all projects and only displays information regarding the project that was searched
	 * The project identifier can be either a string of numerical characters or alphabetical characters
	 * If the string can be parsed to an integer then the method will print out information by comparing project ids
	 * If the string cannot be parsed to an integer then the method will print out information by comparing the project names
	 * The method also fetches all relevant people form the people table in the PoisedPMS database and print out the relevant information depending on person type 
	 * @return Prints out all relevant information
	 * @param stmt Statement that allows for queries to database
	 * @param project_identifier String input for method to do comparisons against
	 * @param project_rset ResultSet that contains all projects from projects table
	 * @throws SQLException If any values cannot be retrieved from the tables
	 */
	private static void fetch_all_project_info(Statement stmt, String project_identifier, ResultSet project_rset)
			throws SQLException {
		int get_project_id = 0;
		try {
			int project_id = Integer.parseInt(project_identifier);
			while (project_rset.next()) {
				if (project_rset.getInt("project_id") == project_id) {
					System.out.println(
							"ID: " + project_rset.getInt("project_id") + 
							"\nName: " + project_rset.getString("project_name") + 
							"\nBuilding Type: " + project_rset.getString("building_type") + 
							"\nPhysical Address: " + project_rset.getString("physical_address") + 
							"\nErf Number: " + project_rset.getInt("erf_num") + 
							"\nCharged: R" + project_rset.getInt("charged") + 
							"\nDue on: " + project_rset.getDate("deadline_date") + 
							"\nPaid: R" + project_rset.getInt("paid") +  
							"\nComplete On: " + project_rset.getDate("completion_date") +
							"\nStatus: " + project_rset.getString("status"));
					get_project_id = project_rset.getInt("project_id");
					
				}
			}
		}

		catch(Exception e) {
			  System.out.println("No ID detected. Searching by name");
			  while (project_rset.next()) {
					if (project_rset.getString("project_name").equals(project_identifier)) {
						System.out.println(
								"ID: " + project_rset.getInt("project_id") + 
								"\nName: " + project_rset.getString("project_name") + 
								"\nBuilding Type: " + project_rset.getString("building_type") + 
								"\nPhysical Address: " + project_rset.getString("physical_address") + 
								"\nErf Number: " + project_rset.getInt("erf_num") + 
								"\nCharged: R" + project_rset.getInt("charged") + 
								"\nDue on: " + project_rset.getDate("deadline_date") + 
								"\nPaid: R" + project_rset.getInt("paid") +  
								"\nComplete On: " + project_rset.getDate("completion_date") +
								"\nStatus: " + project_rset.getString("status"));
						get_project_id = project_rset.getInt("project_id");
						
					}
				}
			}
		String strSelectPeople = String.format("SELECT * FROM people WHERE projects = '%s';", get_project_id);
		ResultSet people_rset = stmt.executeQuery(strSelectPeople);

		while (people_rset.next()) {
			if (people_rset.getString("type").equals("Customer")) {
				System.out.println(
						"\nCustomer:" +
						"\nName: " + people_rset.getString("name") + " " + people_rset.getString("surname") + 
						"\nContact: " + people_rset.getString("email_address") + " " + people_rset.getString("telephone_number") + 
						"\nAddress: " + people_rset.getString("address"));
			}
			if (people_rset.getString("type").equals("Contractor")) {
				System.out.println(
						"\nContractor:" +
						"\nName: " + people_rset.getString("name") + " " + people_rset.getString("surname") + 
						"\nContact: " + people_rset.getString("email_address") + " " + people_rset.getString("telephone_number") + 
						"\nAddress: " + people_rset.getString("address"));
			}
			if (people_rset.getString("type").equals("Architect")) {
				System.out.println(
						"\nArchitect:" +
						"\nName: " + people_rset.getString("name") + " " + people_rset.getString("surname") + 
						"\nContact: " + people_rset.getString("email_address") + " " + people_rset.getString("telephone_number") + 
						"\nAddress: " + people_rset.getString("address"));
			}
			if (people_rset.getString("type").equals("Project Manager")) {
				System.out.println(
						"\nProject Manager:" +
						"\nName: " + people_rset.getString("name") + " " + people_rset.getString("surname") + 
						"\nContact: " + people_rset.getString("email_address") + " " + people_rset.getString("telephone_number") + 
						"\nAddress: " + people_rset.getString("address"));
			}
		}
	}
	/**
	 * The finalize project method allows the user to update the completion date, updates the projects status to complete and creates an invoice.
	 * @return Updated values in the project status table and an invoice if the full price has not been paid
	 */
	private static void finalise_project() {
		Date deadline_date = null;
		int paid = 0;
		int charged = 0;
		String project_name = "";
		String email = "";
		String number = "";
		String full_name = "";
		String address = "";
		Date completion_date = null;
		
		try (// Creates a connection and statement object
				Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/PoisedPMS","poisedpms","poisedpms");
				
				Statement stmt = conn.createStatement();
				
			){
			
			// Allows for user input
			Scanner input = new Scanner(System.in);
			
			System.out.println("Select a project ID to modify it.");
			
			// Prints out all project ids and names that are not complete:
			String strSelectProject = String.format("SELECT * FROM projects INNER JOIN project_statuses ON "
					+ "projects.project_id = project_statuses.project_id INNER JOIN people ON projects.project_id = people.projects "
					+ "WHERE project_statuses.status <>'Complete';");
			ResultSet project_rset = stmt.executeQuery(strSelectProject);
			// This creates a list of project ids to check that the user only selects an available one
			ArrayList<Integer> project_id_list = new ArrayList<Integer>(); 
			while (project_rset.next()) {
				System.out.println("ID: " + project_rset.getInt("project_id") + ", Name:" + project_rset.getString("project_name"));
				project_id_list.add(project_rset.getInt("project_id"));
			}
			
			// Allows user to select a project to finalize 
			Boolean select_project = true;
			int project_id = 0;
			while (select_project == true) {
				System.out.println("Project ID: ");
				String project_id_str = input.nextLine();
				try {
					project_id = Integer.parseInt(project_id_str);
					for (int i = 0; i < project_id_list.size(); i ++) {
						if (project_id == project_id_list.get(i)) { 
							select_project = false;
						}
					}
					if (select_project == true) {
						System.out.println("Please only select an available project id.");
					}
				}
				/**
				 * @exception Throws exception if the users input for project number is not a number
				 */
				catch(Exception e) {
					  System.out.println("The project number cannot contain letters or symbols. Please try again");
					}
			}
			
			// Gets values needed from the selected project
			ResultSet project_select_rset = stmt.executeQuery(strSelectProject);
			while (project_select_rset.next()) {
				if (project_select_rset.getInt("project_id") == project_id) {
					project_id = project_select_rset.getInt("project_id");
					project_name = project_select_rset.getString("project_name");
					deadline_date = project_select_rset.getDate("deadline_date");
					paid = project_select_rset.getInt("paid");
					charged = project_select_rset.getInt("charged");
				}
			}
			
			// Gets values needed from related customer
            // This gets the created project's table id to use to update the people and project_statuses tables 
			String strSelectCustomer = String.format("SELECT * FROM people WHERE projects = '%s'", project_id);
			ResultSet customer_rset = stmt.executeQuery(strSelectCustomer);
			while (customer_rset.next()) {
				full_name = customer_rset.getString("name") + " " + customer_rset.getString("surname");
				address = customer_rset.getString("address");
				email = customer_rset.getString("email_address");
				number = customer_rset.getString("telephone_number");
			}

			// This updates the completion date
			Boolean update_completion_date = true;
			while (update_completion_date == true) {
				System.out.print("Date Complete (YYYY-MM-DD): ");
				String completion_date_str = input.nextLine();
				try {
					completion_date = Date.valueOf(completion_date_str);
					update_completion_date = false;
				}
				/**
				 * @exception Throws exception if the users input for the completion date is in the wrong format
				 */
				catch(Exception e) {
					  System.out.println("The date must be in the format YYYY-MM-DD (eg. 2013-01-13). Please try again");
					}
			}

			// Updates the value
			PreparedStatement ps_finalise = conn.prepareStatement(
					"UPDATE project_statuses SET completion_date = ?, status = ? WHERE project_id = ?;");
			ps_finalise.setDate(1, completion_date);
			ps_finalise.setString(2, "Complete");
			ps_finalise.setInt(3, project_id);
			ps_finalise.executeUpdate();
			System.out.println("\nUpdated completion date to " + completion_date + ".\n");
		}	
		/**
		 * @exception If the project status table cannot be updated because of field constraints then an SQLException is thrown
		 */		
		catch ( SQLException ex ) {
			ex . printStackTrace ();
		}
		// Calculates the amount due and prints out an invoice
		int due = charged - paid;
		if (due > 0) {
			String content = "\t\t\t\tInvoice\n" + 
					"\nDate: " + completion_date +
					"\n\nName: " + full_name +
					"\nContact Details: " + email + "\n" + number + 
					"\nAddress: " + address + 
					"\n\nAmount due for " + project_name + 
					":\nTotal Cost:\t\t\t\t\t\t\tR " + charged + 
					"\nPaid:      \t\t\t\t\t\t\tR " + paid +
					"\n\n\nDue:       \t\t\t\t\t\t\tR " + due;
			CreateFile(project_name);
			WriteToFile(project_name, content);
		}
	}
	/**
	 * Writes given content to a specific file
	 * @return Content in given filename
	 * @param filename String of file that is to be written to
	 * @param insert String of the content that needs to be written to the file
	 */
	public static void WriteToFile (String filename, String insert) {
		    try {
		      FileWriter myWriter = new FileWriter(filename + ".txt");
		      myWriter.write(insert);
		      myWriter.close();
		      System.out.println("Successfully wrote to the file.");
		    } catch (IOException e) {
		      System.out.println("An error occurred.");
		      e.printStackTrace();
		    }
		  }
	/**
	 * 	Creates a file with a given filename in the current directory
	 * @return text file
	 * @param filename Name of the file in this case it is the project name
	 */
	public static void CreateFile (String filename) {
		    try {
		      File myObj = new File(filename + ".txt");
		      if (myObj.createNewFile()) {
		        System.out.println("File created: " + myObj.getName());
		      } else {
		        System.out.println("File already exists.");
		      }
		    } catch (IOException e) {
		      System.out.println("An error occurred.");
		      e.printStackTrace();
		    }
		  }
	
	/**
	 * Selects a list of projects based on the given SQL query and prints it to the user
	 * @return Printed list of projects
	 * @param select String SQL query
	 * @param list_heading String Heading for the list
	 */
	private static void list_projects(String select, String list_heading) {
		try (// Creates a connection and statement object
				Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/PoisedPMS","poisedpms","poisedpms");
				
				Statement stmt = conn.createStatement();
				
			){
		
			String strSelectProject = String.format(select);
			ResultSet project_rset = stmt.executeQuery(strSelectProject);
			
			System.out.println(list_heading);
		
			while (project_rset.next()) {
				System.out.println("Project ID: " + project_rset.getInt("project_id") + 
						"\nProject Name: " + project_rset.getString("project_name") + "\n");
			}
		/**
		 * @exception When something cannot be retrieved from the database then an SQLException is thrown	
		 */
		} catch ( SQLException ex ) {
			ex . printStackTrace ();
		}
	}
	/**
	 * The assign project method assigns a project to a specific type of person selected by the user.
	 * @return Updates the projects field in the people table in the PoisedPMS database
	 */
	private static void assign_project() {
		try (// Creates a connection and statement object
				Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/PoisedPMS","poisedpms","poisedpms");
				
				Statement stmt = conn.createStatement();
				
			){
			// Allows for user input
			Scanner input = new Scanner(System.in);
			
			//Print out all projects
		    System.out.println("Choose a project to assign to a person");
			String strSelectProject = String.format("SELECT * from projects;");
			ResultSet project_rset = stmt.executeQuery(strSelectProject);

			while (project_rset.next()) {
				System.out.println("Project ID: " + project_rset.getInt("project_id") + 
						"\nProject Name: " + project_rset.getString("project_name") + "\n");
			}
			
			// Runs until a project is chosen and the input can be parsed to an int
			Boolean project_chosen = true;
			int project_choice = 0;
			while (project_chosen == true) {
				System.out.println("Project No: ");
				String project_choice_str = input.nextLine();
				try {
					project_choice = Integer.parseInt(project_choice_str);
					project_chosen = false;
				}
				/**
				 * @exception Throws exception if the users input for project number is not an integer
				 */
				catch(Exception e) {
					  System.out.println("The project id cannot contain letters or symbols. Please try again");
					}
			}
			
			// Prints out all people types
			String[] people_types = new String[] {"Customer", "Contractor", "Architect", "Project Manager"};
			for (int i = 0; i < people_types.length; i ++) {
				System.out.println((i + 1) + ". " + people_types[i]);
			}
			
			// Allows user to select a person type and runs until the selected person type number can be parsed to an integer
			Boolean select_person_type = true;
			int person_type = 0;
			String person_type_str = "";
			while (select_person_type == true) {
				System.out.println("Select a number for the person type you wish to assign the project to:");
				person_type_str = input.nextLine();
				try {
					person_type = Integer.parseInt(person_type_str);
					for (int i = 1; i < people_types.length + 1; i ++) {
						if (person_type == i) { 
							person_type_str = people_types[i-1]; 
							select_person_type = false;
						}
					}
					// If user selected a number that was not displayed
					if (select_person_type == true) {
						System.out.println("Please only select an available person type number.");
					}
				}
				/**
				 * @exception Throws exception if the users input for project number is not a number
				 */
				catch(Exception e) {
					  System.out.println("The person type number cannot contain letters or symbols. Please try again");
					}
				}
				
				//Prints out all people of selected type
				String strSelectPeople = String.format("SELECT * FROM people WHERE type = '%s';", person_type_str);
				ResultSet people_rset = stmt.executeQuery(strSelectPeople);
				
				System.out.println(person_type_str + ":");
				
				while (people_rset.next()) {
					System.out.println("System ID: " + people_rset.getInt("person_id") + ", Name: " + people_rset.getString("name") + " " + people_rset.getString("surname"));
				}
				
				//Allows user to select a person to assign the project to
				Boolean person_chosen = true;
				int person_choice = 0;
				while (person_chosen == true) {
					System.out.println("Person No: ");
					String person_choice_str = input.nextLine();
					try {
						person_choice = Integer.parseInt(person_choice_str);
						person_chosen = false;
					}
					/**
					 * @exception Throws exception if the users input for person_number is not a number
					 */
					catch(Exception e) {
						  System.out.println("The person id cannot contain letters or symbols. Please try again");
						}
				}
				
				// Updates the table
				PreparedStatement ps_assign = conn.prepareStatement(
						"UPDATE people SET projects = ? WHERE person_id = ?;");
				ps_assign.setInt(1, project_choice);
				ps_assign.setInt(2, person_choice);
				ps_assign.executeUpdate();
				System.out.println("\nAssigned Project");

		/**
		 * @exception When something cannot be retrieved from the database then an SQLException is thrown	
		 */
		} catch ( SQLException ex ) {
			ex . printStackTrace ();
		}
	}
}
	


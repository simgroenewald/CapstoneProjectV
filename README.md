# CapstoneProjectV

Software Engineering Boot Camp - Data Science, Algorithms and Advanced Software Engineering - Task 8
Compulsory Task 1

	Follow these steps:

	Design and create a database called PoisePMS. Assume that each project can only be assigned to one 
	structural engineer. Each project will also only have one project manager, one architect and one customer.

	Submit the following:

		- Dependency diagrams for each table in the database.
		- An ERD that shows the relationships between the tables in your database.
		- Screenshots of your console that show how each table was created.
		- Add at least two rows of data to each table in the database. 
		- Submit screenshots of your console that show how data is added to the tables.

	Add at least two rows of data to each table in the database. 
	Submit screenshots of your console that show how data is added to the tables.
	
Compulsory Task 2

	Follow these steps:
	
	Copy and paste the code that you wrote for the last Capstone Project in the previous level of this Bootcamp 
	into the Dropbox folder for this Capstone Project.
	
	Modify your code so that it:
	
		- Reads and writes data about projects and people associated with projects from your database instead of 
		  text files. Your program should not use any text files.
		- Capture information about new projects and add these to the database.
		- Update information about existing projects.
		- Finalise existing projects. When a project is finalised the following should happen:
	
			* An invoice should be generated for the client. This invoice should contain the customer’s 
			  contact details and the total amount that the customer must still pay. This amount is calculated 
			  by subtracting the total amount paid to date from the total fee for the project. If the customer 
			  has already paid the full fee, an invoice should not be generated.
			* The project should be marked as “finalised” and the completion date should be added.
			
		- Finds all projects that still need to be completed from the database.
		- Finds all projects that are past the due date from the database.
		- Find and select a project by entering either the project number or project name.
		
	Besides meeting the above criteria, you should also do the following:
	
		- Include exception handling. Use try-catch blocks wherever appropriate.
		- Remove all errors from your code. Take extra care to detect and remove all logical and runtime errors.
		- Adequately refactor your code.
		- Document your code. Adhere to the style guide found here.
		- Use Javadoc to generate API documentation from documentation comment for your program. 
		- Follow the guidelines here to create a Readme file for this project.

package Address_Book.mainpackage;

import java.io.*;
import java.nio.file.DirectoryNotEmptyException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.*;

public class Change {
	public static void choose_field() throws FileNotFoundException, IOException {
		System.out.print("\033[H\033[2J");
		System.out.flush();

		Scanner input = new Scanner(System.in);
		int exit = 0;
		int answer;
		// we will loop until user wants to exit the application
		do { // according to user's input i go to the correct method
			System.out.println("Do you want to edit a contact based on the name or Phone number?");
			System.out.println("Give '1', '2' or '0' to return to main menu.");
			try {
				answer = input.nextInt();
			} catch (Exception e) {
				answer = 0;
			}
			if (answer == 1)
				name_search();
			else if (answer == 2)
				number_search();

		} while (answer != exit);
	}

	public static void name_search() throws IOException, FileNotFoundException {
		Scanner input = new Scanner(System.in);
		String name, surname;

		System.out.println("Give Name: ");
		name = input.nextLine();
		System.out.println("Give Surname: ");
		surname = input.nextLine();

		File file = new File(System.getProperty("user.dir") + "/Address_Book/mainpackage/contacts.txt");
		BufferedReader reader = new BufferedReader(new FileReader(file));
		String currentLine;
		boolean first = false;
		String[] fields = new String[0];
		List<String> lines = new ArrayList<String>();

		while ((currentLine = reader.readLine()) != null) {
			if (!first) {
				fields = currentLine.split(",");
				first = true;
			} else {
				// only if both of the user's inputs (name and surname) match a contact then
				// add this contact's info to an array
				String[] info = currentLine.split(",");

				if (info[0].equals(name) && info[1].equals(surname)) {
					System.out.println("----There is a contact for the information you gave----");
					for (int i = 0; i < fields.length; i++) {
						System.out.println(fields[i] + ": " + info[i]);
					}
					lines.add(currentLine);
				} else if (info[0].equals(name) && !info[1].equals(surname)) {
					System.out.println("----There is a contact for the Name you gave----");
					for (int i = 0; i < fields.length; i++) {
						System.out.println(fields[i] + ": " + info[i]);
					}
					System.out.println("----Name ans Surname must be valid----");
				} else if (!info[0].equals(name) && info[1].equals(surname)) {
					System.out.println("----There is a contact for the Surname you gave----");
					for (int i = 0; i < fields.length; i++) {
						System.out.println(fields[i] + ": " + info[i]);
					}
					System.out.println("----Name ans Surname must be valid----");
				}

			}
		}
		System.out.println("-------------------");
		reader.close();
		for (Object str : lines) {
			// for every contatc that i found that is a match
			info_check(str.toString(), fields);
		}
	}

	public static boolean isValidMobileNo(String str) {
		// (0/91): number starts with (0/91)
		// [7-9]: starting of the number may contain a digit between 0 to 9
		// [0-9]: then contains digits 0 to 9
		Pattern ptrn = Pattern.compile("(0/91)?[7-9][0-9]{9}");
		// the matcher() method creates a matcher that will match the given input
		// against this pattern
		Matcher match = ptrn.matcher(str);
		// returns a boolean value
		return (match.find() && match.group().equals(str));
	}

	public static void number_search() throws IOException, FileNotFoundException {
		Scanner input = new Scanner(System.in);
		int f1 = -1;
		int f2 = -1;
		String mobilePhone = "";
		boolean valid;
		List<String> lines = new ArrayList<String>();
		System.out.println("Give Phone number: ");
		do {
			mobilePhone = input.nextLine();
			valid = isValidMobileNo(mobilePhone);

		} while (valid == false);

		File file = new File(System.getProperty("user.dir") + "/Address_Book/mainpackage/contacts.txt");
		BufferedReader reader = new BufferedReader(new FileReader(file));
		String currentLine;
		boolean first = false;
		String[] fields = new String[0];

		if (mobilePhone.isEmpty()) {
			System.out.println("-------------------");
			System.out.println("You gave wrong information.");
		} else {
			while ((currentLine = reader.readLine()) != null) {
				if (!first) {
					fields = currentLine.split(",");
					first = true;
				} else {
					String[] info = currentLine.split(",");
					if (info[2].equals(mobilePhone)) {
						System.out.println("----There is a contact for the information you gave----");
						for (int i = 0; i < fields.length; i++) {
							System.out.println(fields[i] + ": " + info[i]);
						}
						lines.add(currentLine);
					}
				}
			}
		}

		System.out.println("-------------------");
		reader.close();
		for (Object str : lines) {
			info_check(str.toString(), fields);
		}
	}

	public static void info_check(String line, String[] fields) throws IOException, FileNotFoundException {
		Scanner input = new Scanner(System.in);
		String currentLine;
		String name = "";
		String surname = "";
		String mobilePhone;
		String emailId = "";
		String street = "";
		String town = "";
		int streetNo = -1;
		int zipCode = -1;
		String str = "";
		boolean duplicate, valid;
		System.out.println("----Edit Information----");
		String[] info = line.split(",");
		System.out.println("Change the information " + fields[0] + ": " + info[0] + ", to:");
		name = input.nextLine();
		System.out.println("Change the information " + fields[1] + ": " + info[1] + ", to:");
		surname = input.nextLine();
		do {
			duplicate = false;
			valid = true;
			System.out.println("Change the information " + fields[2] + ": " + info[2] + ", to:");
			mobilePhone = input.nextLine();
			valid = isValidMobileNo(mobilePhone);

		} while (duplicate == true || valid == false);

		do {
			duplicate = false;
			System.out.println("Change the information " + fields[3] + ": " + info[3] + ", to:");
			emailId = input.nextLine();
		} while (duplicate == true);
		System.out.println("Change the information " + fields[4] + ": " + info[4] + ", to:");
		street = input.nextLine();
		System.out.println("Change the information " + fields[5] + ": " + info[5] + ", to:");
		try {
			streetNo = Integer.parseInt(input.nextLine());
		} catch (NumberFormatException e) {
		}
		System.out.println("Change the information " + fields[6] + ": " + info[6] + ", to:");
		town = input.nextLine();
		System.out.println("Change the information " + fields[7] + ": " + info[7] + ", to:");
		try {
			zipCode = Integer.parseInt(input.nextLine());
		} catch (NumberFormatException e) {
		}
		str = name + "," + surname + "," + mobilePhone + "," + emailId + "," + street + ","
				+ String.valueOf(streetNo) + "," + town + "," + String.valueOf(zipCode);
		if (name == "" || surname == "" || emailId == "" || street == "" || town == "" || mobilePhone.isEmpty()
				|| streetNo == -1 || zipCode == -1) {
			// if any of the variables is not valid or has a value assigned to it
			System.out.println("You gave wrong information, information change wasn't successful.");
		} else { // else i call this method and pass the string of the new info i built
			contact_change(line, str);
		}
	}

	public static void contact_change(String line, String str) throws IOException, FileNotFoundException {
		File file = new File(System.getProperty("user.dir") + "/Address_Book/mainpackage/contacts.txt");
		BufferedReader reader = new BufferedReader(new FileReader(file));
		String currentLine;
		boolean first = false;
		String[] fields = new String[0];
		String[] info1 = new String[0];
		String[] info2 = new String[0];
		info1 = str.split(",");
		File file2 = new File(System.getProperty("user.dir") + "/Address_Book/mainpackage/contactstemp.txt");
		BufferedWriter writer = new BufferedWriter(new FileWriter(file2));
		while ((currentLine = reader.readLine()) != null) {
			if (!first) {
				fields = currentLine.split(",");
				writer.write(currentLine + "\n");
				first = true;
			} else if (currentLine.equals(line)) {// if the current line in the reader is the one we want to change
				writer.write(str + "\n"); // i write the new info instead of the old
			} else { // for the rest of the lines nothing changes
				info2 = currentLine.split(",");
				writer.write(currentLine + "\n");
			}
		}
		reader.close();
		writer.close();

		file.delete();	// we delete the original file
		file2.renameTo(file);	// we rename the temporary file to the original file's name
	}

}

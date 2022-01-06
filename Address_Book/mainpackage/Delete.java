package Address_Book.mainpackage;

import java.io.*;
import java.nio.file.DirectoryNotEmptyException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.*;

public class Delete {
	public static void choose_field() throws FileNotFoundException, IOException {
		System.out.print("\033[H\033[2J");
		System.out.flush();
		
		Scanner input = new Scanner(System.in);
		int exit = 0;
		int answer;
		// we will loop until user wants to exit the application
		do {// according to user's input i go to the correct method
			System.out.println("Do you want to delete a contact based on the name or the phone number?");
			System.out.println("Give '1', '2' or '0' to go back to main menu.");
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
			} else {// only if both of the user's inputs (name and surname) match a contact then i
					// add this contact's info to an array
				String[] info = currentLine.split(",");
				if (info[0].equals(name) && info[1].equals(surname)) {
					System.out.println("----There is a contact for the information you gave----");
					for (int i = 0; i < fields.length; i++) {
						System.out.println(fields[i] + ": " + info[i]);
					}
					// contact_change(currentLine);
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
					System.out.println("----Name and Surname must be valid----");
				}

			}
		}
		System.out.println("-------------------");
		reader.close();
		for (Object str : lines) {// for every contatc that i found that is a match
			contact_delete(str.toString());
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
		// if (f1 == -1 && f2 == -1)
		if (mobilePhone.isEmpty())
		 {
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
						contact_delete(currentLine);
					}
				}
			}
		}

		System.out.println("-------------------");
		reader.close();
	}

	public static void contact_delete(String line) throws IOException, FileNotFoundException {
		File file1 = new File(System.getProperty("user.dir") + "/Address_Book/mainpackage/contacts.txt");
		BufferedReader reader = new BufferedReader(new FileReader(file1));
		String currentLine;
		boolean first = false;
		String[] fields = new String[0];

		File file2 = new File(System.getProperty("user.dir") + "/Address_Book/contactstemp.txt");

		BufferedWriter writer = new BufferedWriter(new FileWriter(file2));
		while ((currentLine = reader.readLine()) != null) {
			if (!first) {
				fields = currentLine.split(",");
				writer.write(currentLine + "\n");
				first = true;
			} else if (!currentLine.equals(line)) {// if the current line in the reader is not the one we want to
													// delete we write it to the temp file
				writer.write(currentLine + "\n");
			}
		}
		reader.close();
		writer.close();
		file1.delete();// we delete the original file
		file2.renameTo(file1);// we rename the temporary file to the original file's name
		System.out.println("Information was valid, deletion completed successfully");
	}

}

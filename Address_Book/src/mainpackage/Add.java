package mainpackage;

import java.util.*;
import java.util.Arrays;
import java.io.*;
import java.net.*;
import java.nio.charset.Charset;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.Scanner;
import java.util.regex.*;

public class Add extends Main {
	public static void add_contact() throws IOException, FileNotFoundException {
		System.out.print("\033[H\033[2J");
		System.out.flush();

		File file = new File(System.getProperty("user.dir") + "/Address_Book/src/contacts.txt");// we get the file
		BufferedReader reader = new BufferedReader(new FileReader(file)); // we get reader for the file
		OutputStreamWriter outputStreamWriter = new OutputStreamWriter(
				new FileOutputStream(System.getProperty("user.dir") + "/Address_Book/src/contacts.txt", true), "UTF-8");
		BufferedWriter writer = new BufferedWriter(outputStreamWriter);
		// this is a way to get a writer for th specific file

		Scanner input = new Scanner(System.in);
		boolean duplicate, valid;
		String currentLine;

		String name = ""; // I initialize the variablesto avoid errors
		String surname = "";
		String emailID = "";
		String street = "";
		String town = "";
		String string;
		String mobilePhoneString = "";

		int f3 = -1;
		int mobilePhone = -1;
		int streetNo = -1;
		int zipCode = -1;

		System.out.println("Give Name: ");
		name = input.nextLine();

		System.out.println("Give Surname: ");
		surname = input.nextLine();

		do {
			duplicate = false;
			valid = true;
			System.out.println("Give Mobile phone: ");
			// try {
			// 	mobilePhone = Integer.parseInt(input.nextLine());
			// } catch (NumberFormatException e) {
			// 	System.out.println("Mobile phone must be number.");
			// 	valid = false;
			// }
			mobilePhoneString = input.nextLine();
			valid = isValidMobileNo(mobilePhoneString);

			while ((currentLine = reader.readLine()) != null) {// check for duplicate
				String[] words = currentLine.split(",");
				if (words[3].equals(String.valueOf(mobilePhone))) {
					duplicate = true;
					System.out.println("Mobile Phone must be unique among the contacts.");
				}
			}
			reader = new BufferedReader(new FileReader(file));
		} while (duplicate == true || valid == false);

		do {
			duplicate = false;
			System.out.println("Give E-mail: ");
			emailID = input.nextLine();
			while ((currentLine = reader.readLine()) != null) {// check for duplicate
				String[] words = currentLine.split(",");
				if (words[4].equals(emailID)) {
					duplicate = true;
					System.out.println("E-mail must be unique among the contacts.");
				}
			}
			reader = new BufferedReader(new FileReader(file));
		} while (duplicate == true);

		System.out.println("Give Street: ");
		street = input.nextLine();

		System.out.println("Give street number: ");
		do {
			valid = true;
			try {
				streetNo = Integer.parseInt(input.nextLine());
			} catch (NumberFormatException e) {
				// e.printStackTrace();
				System.out.println("Street number must be a number.");
				valid = false;
			}
		} while (valid == false);

		System.out.println("Give town: ");
		town = input.nextLine();

		System.out.println("Give Zip code: ");
		do {
			valid = true;
			try {
				zipCode = Integer.parseInt(input.nextLine());
			} catch (NumberFormatException e) {
				System.out.println("Zip code must be a number.");
				valid = false;
			}
		} while (valid == false);

		if (name == "" || surname == "" || emailID == "" || street == "" || town == "" || mobilePhoneString == ""
				|| streetNo == -1 || zipCode == -1) {
			System.out.println("You gave false inputs, adding new contact wasn't successful: ");
		} else {
			// if everything is correct i build a string
			string = name + "," + surname + "," + mobilePhoneString + "," + emailID
					+ "," + street + ","
					+ String.valueOf(streetNo) + "," + town + "," + String.valueOf(zipCode);
			PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(file, true)));
			// with these code I add a line at the bottom of the file

			out.println(string);
			out.close();
		}

		writer.close();
		reader.close();
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

}

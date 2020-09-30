package example;

import java.util.Scanner;

public class Pricing {
	static double p;
	String flatrate;
	public static double Rfr, Rpp, Gfr, Gpp;
	// public static int markup=0,BasePrice=0,RegularpriceMarkup;
	public static double finalprice = 0, markup = 0, BasePrice = 0, RegularpriceMarkup, Grouprice = 0;

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int count = 0;
		System.out.println("Enter Your quantity");
		Scanner sc = new Scanner(System.in);
		int quntatity = sc.nextInt();
		System.out.println("Enter the price type(flatrate or perpiece)");
		Scanner sc1 = new Scanner(System.in);
		String pricetype = sc1.nextLine();
		System.out.println("Enter price");
double price = sc.nextDouble();

		if (pricetype.equalsIgnoreCase("flatrate")) {
			p = price;
			Rfr = p;
		} else {
			p = price * quntatity;
			Rpp = p;
		}

		System.out.println("do you want to enter Group pricing(Y/N)");
		String g = sc.next();
		if (g.equalsIgnoreCase("Y")) {

			System.out.println("Enter the price type(flatrate or perpiece)");
			Scanner sc2 = new Scanner(System.in);
			String Grouppricetype = sc2.nextLine();
			System.out.println("Enter Group price");
			int Groupprice = sc.nextInt();

			if (Grouppricetype.equalsIgnoreCase("flatrate")) {
				Gfr = Groupprice;
			} else {
				Gpp = Groupprice * quntatity;
			}
			if (Gfr <= Rfr || Gpp <= Rpp) {
				if (Grouppricetype.equalsIgnoreCase("flatrate")) {
					p = Groupprice;

				} else {
					p = Groupprice * quntatity;

				}
			}
		}
		System.out.println("do you want to enter Markupprice(Y/N)");
		String m = sc1.nextLine();
		if (m.equalsIgnoreCase("Y")) {
			System.out.println("Enter markup percent");
			Scanner sc3 = new Scanner(System.in);
			markup = sc3.nextInt();
			finalprice = p / 100;
			RegularpriceMarkup = finalprice * markup;
			p = p + RegularpriceMarkup;
		} else {
		}

		System.out.println("do you want to enter BasePrice(Y/N)");
		String b = sc.next();
		if (b.equalsIgnoreCase("Y")) {

			System.out.println("Enter Baseprice");
			BasePrice = sc.nextInt();

			// int RegularpriceMarkup=finalprice*markup;
		} else {
		}

		if (b.equalsIgnoreCase("Y")) {
			if (BasePrice >= p) {
				System.out.println("Final price is:" + BasePrice);
			} else {
				System.out.println("Final price is:" + p);
			}
		} else {
			System.out.println("Final price is:" + p);
		}

	}

}
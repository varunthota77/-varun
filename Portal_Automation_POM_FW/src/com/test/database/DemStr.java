package com.test.database;

import com.test.hooks.Hooks;

public class DemStr {

	public static void main(String[] args) {
		
		String claimNumber=  "Claim 28552";
		System.out.println("The Claim Number is: " + claimNumber) ;
		
		String[] convertedClaimNumber = claimNumber.split(" ");	
		String Claim_Number = convertedClaimNumber[1];
		System.out.println("The Converted Claim Numberr is: " + Claim_Number) ;
	}
	
}

package com.icss;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

public class FindBestOperator {
	private float bestUnitPrice = -1;
	private String bestOperator = null;
	private Map<String, Trie> opWisePrefixes = null;
	
	public float getBestUnitPrice() {
		return bestUnitPrice;
	}

	public void setBestUnitPrice(float bestUnitPrice) {
		this.bestUnitPrice = bestUnitPrice;
	}

	public String getBestOperator() {
		return bestOperator;
	}

	public void setBestOperator(String bestOperator) {
		this.bestOperator = bestOperator;
	}

	public void findBestOperator(Map<String, Map<String, String>> operatorWisePriceMap, String phoneNum) {
		try {
			Map<String, String> opWiseBestMatchMap = new HashMap<String, String>();
			Set<String> keySet = operatorWisePriceMap.keySet();
			
			// to find the unit price for the given phone number based on best
			// prefix match(i.e whichever prefix is longest) for each operator
			// and build a map
			for (String key : keySet) {
				Map<String, String> operatorTarrifMap = operatorWisePriceMap.get(key);
				boolean matchFound = false;
				String matchedPrefix = null;
				//using Trie data structure to search the best matched prefix
				Trie trie = opWisePrefixes.get(key);
				matchedPrefix = trie.getMatchingPrefix(phoneNum);
				if(matchedPrefix != null && !"".equals(matchedPrefix)){
					matchFound = true;
				}
				if (matchFound) {
					opWiseBestMatchMap.put(key, operatorTarrifMap.get(matchedPrefix));
				}
			} // end of for-each loop to go around set of all operator

			if (opWiseBestMatchMap.isEmpty()) {
				System.out.println("No Operator found");
			} else {
				Set<String> operatorKeySet = opWiseBestMatchMap.keySet();
				float bestUnitPrice = -1;
				String bestOperator = null;
				//compare the unit price of all operators and find the cheapest
				for (String operatorKey : operatorKeySet) {
					float unitPrice = Float.parseFloat(opWiseBestMatchMap.get(operatorKey));
					if (bestUnitPrice == -1 && bestOperator == null) {
						bestUnitPrice = unitPrice;
						bestOperator = operatorKey;
					} else if (unitPrice < bestUnitPrice) {
						bestUnitPrice = unitPrice;
						bestOperator = operatorKey;
					}
				} // end of loop to find cheapest operator

				this.setBestOperator(bestOperator);
				this.setBestUnitPrice(bestUnitPrice);
				System.out.println("The best operator is " + bestOperator + " with lowest unit price of "
						+ bestUnitPrice + " /min");

			}

		} catch (Exception ex) {
			ex.printStackTrace();
		}

	}

	public Map<String, Map<String, String>> getOperatorWisePrice() {
		String strInput = null;
		//HashMap contains prefix as key and unit price as value
		Map<String, String> tarrifMap = null;
		// HashMap to maintain all operators and its call rate based on
		// prefix(here key is operator name and value is again a map contains
		// prefix as key and unit price as value)
		Map<String, Map<String,String>> allDetailsMap = new HashMap<String, Map<String,String>>();
		opWisePrefixes = new HashMap<String, Trie>();
		Scanner sc = null;
		int count = 1;
		try {
			//to get the input from user(i.e console)
			sc = new Scanner(System.in);
			// sc.useDelimiter("\t");
			while (true) {
				String operator = null;
				//condition to check for first iteration and to skip asking for confirmation to enter details
				if (count == 1) {
					System.out.println("Enter The Operator Name");
					//to read the operator name entered by user
					operator = sc.next();
				}
				//in second iteration onwards we ask the user to enter details for another operator
				if (count != 1) {
					System.out.println("Do you want to continue entering the details of another operator, yes/no?");
					String newOption = sc.next();
					//to exit from the loop if user enters no
					if ("no".equalsIgnoreCase(newOption)) {
						break;
					} else {
						System.out.println("Enter The Operator Name");
						operator = sc.next();
					}
				}
				System.out.println("Enter the comma seperated prefix and tarrif details of Operator " + operator);
				System.out.println("Type exit once finished entering the list of price details");
				tarrifMap = new HashMap<String, String>();
				Trie trie = new Trie();
				//infinite loop to allow the user to enter any number of key value pairs (i.e prefix and unit price)
				while (true) {
					String tarrifArr[] = null;
					String prefix = null;
					String pricePerMin = null;
					strInput = sc.next();
					//to exit when user enter exit after entering all prefixes and unit prices
					if ("Exit".equalsIgnoreCase(strInput)) {
						break;
					}
					//to validate the user input
					if ("".equals(strInput) || strInput.indexOf(",") != -1) {

						tarrifArr = strInput.split(",");
						prefix = tarrifArr[0];
						pricePerMin = tarrifArr[1];

						if (prefix == null || "".equals(prefix.trim()) || tarrifArr.length != 2) {
							System.out.println("Please enter valid input");
							continue;
						}

						if (pricePerMin == null || "".equals(pricePerMin.trim())) {
							System.out.println("Please enter valid input");
							continue;
						}

						tarrifMap.put(prefix, pricePerMin);
						trie.insert(prefix);

					} else {
						System.out.println("Please enter valid input");
					}

				}
				allDetailsMap.put(operator, tarrifMap);
				opWisePrefixes.put(operator, trie);
		
				count++;
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}finally{
			
		}
		return allDetailsMap;
	}
	
	/**
	 * This is a simple method written to test with small data
	 */
	public Map<String, Map<String, String>> getopWisePrice() {
		Map<String, String> tarrifMap = null;
		Map<String, Map<String,String>> allDetailsMap = new HashMap<String, Map<String,String>>();
		tarrifMap = new HashMap<String,String>();
		tarrifMap.put("1","0.9");
		tarrifMap.put("268","5.1");
		tarrifMap.put("268","5.1");
		tarrifMap.put("46","0.17");
		tarrifMap.put("4620","0.0");
		tarrifMap.put("468","0.15");
		tarrifMap.put("4631","0.15");
		tarrifMap.put("4673","0.9");
		tarrifMap.put("46732","1.1");
		allDetailsMap.put("airtel", tarrifMap);
		
		tarrifMap = new HashMap<String,String>();
		tarrifMap.put("1","0.92");
		tarrifMap.put("44","0.5");
		tarrifMap.put("46","0.2");
		tarrifMap.put("467","1.0");
		tarrifMap.put("48","1.2");
		
		allDetailsMap.put("lyca", tarrifMap);
		
		tarrifMap = new HashMap<String,String>();
		tarrifMap.put("468","0.15");
		tarrifMap.put("4631","0.15");
		tarrifMap.put("4673","0.9");
		tarrifMap.put("46732","1.1");
		allDetailsMap.put("Tele2", tarrifMap);
		
		return allDetailsMap;
	}
	
	public static void main(String[] args) {
		try{
			FindBestOperator fBestOperator = new FindBestOperator();
			Map<String,Map<String,String>> allDetailsMap = fBestOperator.getOperatorWisePrice();
			//Map<String,Map<String,String>> allDetailsMap = fBestOperator.getopWisePrice();
			
			fBestOperator.findBestOperator(allDetailsMap, "4473212345");
			
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
	
}

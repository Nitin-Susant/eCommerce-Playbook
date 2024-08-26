package com.ebook.dsa;

import java.io.File;
import java.util.Arrays;

import jakarta.persistence.criteria.CriteriaBuilder.In;

public class Problema {

	public static void main(String[] args) {
//		 contain();
		 
		 File of = new File("H:\\DSA Cources\\[Educative.io] Ace the"
		 		+ " Java Coding Interview\\3 DSA\\Ace the Java Coding "
		 		+ "Interview\\2. Data Structures\\10. Graphs");
		 
		 
		 
		
		  
		 
		 String[] list = of.list();
		 for (String string : list) {
			 if (string.contains(".txt")) {
				continue;
			}
			
			String[] split = string.split(". ");
			System.out.println(string);
			System.out.println(Arrays.toString(split));
			Integer num =  Integer.parseInt(split[0]);
			String newname ="";
			
			if (num<10) {
				newname+="A0";
			}
			else if(num>=10 && num<100) {
				newname+="A";
			}
			else if(num>=100 && num<=199) {
				newname+="B";
			}
			else if(num>=200 && num<=299) {
				newname+="C";
			}
			
			 for (int i = 0; i < split.length; i++) {
				 newname+=split[i]+" ";
			}
			
			 
			 System.out.println(newname);
			 File file = new File(of,string);
			 File file1 = new File(of,newname);
 			file.renameTo(file1);
			
			
			
		}
		 
//		 System.out.println(of.getParent());
//		 
//		 String nf = "mana.ods";
//		 
//		 File ofi = new File(of.getParent(),nf);
//		 boolean renameTo = of.renameTo(ofi);
//		 
//		 System.out.println(renameTo);
	}

	private static void contain() {
		String s = "apple";
		 System.out.println(s.contains("ae"));
	}

}

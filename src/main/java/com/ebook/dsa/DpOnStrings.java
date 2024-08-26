package com.ebook.dsa;

import java.util.Arrays;
import java.util.PriorityQueue;

 


public class DpOnStrings {

	static class pair{
		int v1,v2;

		public pair(int v1, int v2) {
			super();
			this.v1 = v1;
			this.v2 = v2;
		}

		@Override
		public String toString() {
			return "pair [v1=" + v1 + ", v2=" + v2 + "]";
		}
		
		
	}
	
	
	public static int lcs(String s1,String s2,int i,int j) {
		
		if (i==s1.length() || j == s2.length()) {
			return 0;
		}
		
		if(s1.charAt(i)==s2.charAt(j)) {
			return 1 + lcs(s1, s2, i+1, j+1);
		}
		
		int st1 = lcs(s1, s2, i, j+1);
		int st2 = lcs(s1, s2, i+1, j);
		return Math.max(st1, st2);
		
	}
	
	
	public static int mjtor(int arr[],int ind,int st[]) {
		
		if (ind>=arr.length) {
			return Integer.MIN_VALUE;
		}
		if (ind==arr.length-1) {
			return 0;
		}
		if (st[ind]!= -1) {
			return st[ind];
		}
		
		int min = Integer.MIN_VALUE;
		for(int i=1;i<=arr[ind];i++) {
			min = Math.max(min, mjtor(arr, ind+i,st));
		}
		 if (min!=  Integer.MIN_VALUE){
			 return st[ind] = min+1;
		 }
		 return  min;
	 
			
	 
		
	}
	
	public static int subset(int arr[],int sum,int i,int dp[][]) {
		
		if(i>=arr.length) {
			if (sum==0) {
				return 1;
			}
			else {
				return 0;
			}
		}
		if (sum<0) {
			return 0;
		}
		if (dp[i][sum] !=-1 ) {
			return dp[i][sum];
		}
		
		int inclde = subset(arr, sum-arr[i], i+1,dp);
		int exclde = subset(arr, sum, i+1,dp);
		
		return dp[i][sum] = inclde + exclde;
	}
	
	public static int editdistance(String s1,String s2,int i,int j) {
		
		if (i==0) {
			return j;
		}
		if (j==0) {
			return i;
		}
		
		
		if(s1.charAt(i-1)==s2.charAt(j-1)) {
			return editdistance(s1, s2, i-1, j-1);
		}
		
		int replace = editdistance(s1, s2, i-1, j-1);
		int insert = editdistance(s1, s2, i-1, j);
		int delete = editdistance(s1, s2, i, j-1);
		
		return Math.min(replace, Math.min(insert, delete)) +1;
		
	}
	
	
	public static void subsethelp() {
		int [] arr = {6,1,2,3,5,1};
		int n = 11;
		int [][]dp = new int[arr.length+1][n+1];
		for (int[] is : dp) {
			Arrays.fill(is, -1);
		}
		
		int subset = subset1(arr, 0, n,dp);
		for (int[] is : dp) {
			System.out.println(Arrays.toString(is));
		}
		System.out.println(subset);
		
	}
	public static int subset1(int arr[],int i,int sum,int dp[][]) {
		
		if (sum==0) {
			return 1;
		}
		if (i >=arr.length || sum<0 ) {
			return 0;
		}
		
		if (dp[i][sum] != -1) {
			return dp[i][sum];
		}
		
		int take = subset1(arr, i+1, sum-arr[i],dp);
		
		int skip = subset1(arr, i+1, sum,dp);
		
		return dp[i][sum] =  take | skip;
	}
	
	public static void main(String[] args) {
		
		subsethelp();
//		System.out.println(lcs("anash", "sashu", 0, 0));
//		int arr [] = {3,4,2,1,2};
//		int st[] = new int[arr.length];
//		Arrays.fill(st, -1);
//		
//		System.out.println(mjtor(arr,0,st));
//		System.out.println(Arrays.toString(st));
		
//		int arr [] = {3,4,2,1};
//		int sum = 3;
//		int dp[][] = new int[arr.length+1][sum+1];
//		for (int[] is : dp) {
//			Arrays.fill(is,-1);
//		}
//		for (int[] is : dp) {
//			System.out.println(Arrays.toString(is));
//		}
//	
// 		System.out.println(subset(arr, sum, 0,dp));
//		
//		for (int[] is : dp) {
//			System.out.println(Arrays.toString(is));
//		}
		
		
//		String s1 = "caet";
//		String s2 = "cut";
//		System.out.println(editdistance(s1, s2, s1.length(), s2.length()));
		
		
		
//	 
//		PriorityQueue<pair> pq =new PriorityQueue<>((a,b)->{
//			return Integer.compare(a.v1, b.v1);
//		});
//		pair p1 = new pair(1, 3);
//		pair p2 = new pair(2, 3);
//		pq.add(p1);
//		pq.add(p2);
//		pq.add( new pair(4, 3));
//		pq.add( new pair(6, 3));
//		pq.add( new pair(0, 3));
//		pq.add( new pair(9, 3));
//		
//		 while (!pq.isEmpty()) {
//			System.out.println(pq.poll());
//		}
//		

	}

}

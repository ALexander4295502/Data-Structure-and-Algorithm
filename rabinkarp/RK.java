package rabinkarp;

import java.util.LinkedList;
import java.util.Queue;

public class RK {
	
	//
	// Be sure to look at the write up for this assignment
	//  so that you get full credit by satisfying all
	//  of its requirements
	//
	private long patHash; //pattern hashvalue
	private int patm;  //pattern length  
	private int loc; //pattern location
	private char[] patarray; // save the m chars in window
	/**
	 * Rabin-Karp string matching for a window of the specified size
	 * @param m size of the window
	 */
	public RK(int m) {
		patm = m;
		loc = 0;
		patHash = 0;
		patarray = new char[m];
		System.out.println("I am here!!!!!!!!!");
	}
	

	/**
	 * Compute the rolling hash for the previous m-1 characters with d appended.
	 * @param d the next character in the target string
	 * @return
	 */
	
	public void arrayadd(char x){
		int i=loc%(patm);
		patarray[i]=x;
	}
	
	public char arraypoll(){
		int i=loc%(patm);
		return patarray[i];
	}
	
	public static long mod(long a, long b){
		return (a % b+ b) % b;
    }
	
	
	//Important:  Math.pow, being in double arithmetic, doesn't obey the truncation rules used by ints
	static long mpow(long x, int k) {
	    long result = 1;
	    for(; k > 0; k >>= 1) {
	        if(k % 2 == 1) {
	            result = mod(result * x ,511);
	        }
	        x = mod(x * x , 511);
	    }
	    return result;
	}
	
	
	public int nextCh(char d) {
		int vald = 0; //c is the character about to leave that window
		int valc = 0; //d is the character next to be incorporated into the window
		char out;
		if(loc <= (patm-1)){
			arrayadd(d);
			vald = d;
			valc = 0;
		}else{
			out = arraypoll();
			valc = out;
			arrayadd(d);
			vald = d;
		}
		patHash = mod(patHash*31-mpow(31,patm)*valc+vald,511);
		System.out.println("patHash is "+patHash);
		loc++;
		return (int)patHash;
	}

}

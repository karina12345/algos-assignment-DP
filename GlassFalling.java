/**
 * Glass Falling
 * Author: Karina Ibragimova
 */
public class GlassFalling {

  // Do not change the parameters!
  public int glassFallingRecur(int floors, int sheets) {
          // base cases
          if(floors == 0) return 0;
	  if(floors == 1) return 1;
	  if(sheets == 1) return floors; 
 
          // go through every floor 
	        int minimum = floors;
	        for(int trials = 1; trials <= floors; trials++) {
	        	                 // max of glass breaking vs not breaking
	         int outcome = 1 + Math.max(glassFallingRecur(trials-1, sheets-1),
	        		               glassFallingRecur(floors-trials, sheets));
	        
	         minimum = Math.min(outcome, minimum);
	       }
	       return minimum; // minimum trials
  }

 
  // Pick whatever parameters you want to, just make sure to return an int.
  public int glassFallingMemoized(int floors, int sheets) {
    int[][] table = new int[sheets+1][floors+1];
		// store results in a look up table
		
		for (int sheet = 0; sheet <= sheets; sheet++) {
			for (int floor = 0; floor <= floors; floor++){
				table[sheet][floor] = Integer.MAX_VALUE;	
			}
		}
		return gfMemo(floors, sheets, table);  
  }
  	// helper method
	public int gfMemo(int floors, int sheets, int trials[][]) {
		// base cases
		if(floors == 0) return 0;
		if(floors == 1) return 1;
		if(sheets == 1) return floors;
		 
		 int minimum = floors; 
		 
		 for(int i, j, k = 1; k < floors; k++) {
			 i = trials[sheets-1][k-1]; // glass breaks
			 j = trials[sheets][floors-k]; // doesn't break
			 if(i == Integer.MAX_VALUE) {
				i = gfMemo(k-1, sheets-1, trials);
			 }
			 if(j == Integer.MAX_VALUE) {  
				j = gfMemo(floors-k, sheets, trials);
			 }
			 minimum = Math.min(minimum, Math.max(i, j));	 
	          }
			 
		  if(floors < trials[sheets][floors]) {
	             trials[sheets][floors] = minimum + 1;
		  } 
		  return minimum + 1; // number of trials
          }

  // Do not change the parameters!
  public int glassFallingBottomUp(int floors, int sheets) {
    int[][] trials = new int[floors+1][sheets+1];
    // base cases
    for (int sheet = 0; sheet <= sheets; sheet++) {
      trials[0][sheet] = 0; // no floors = no trials
    }
    for (int sheet = 0; sheet <= sheets; sheet++) {
      trials[1][sheet] = 1; // 1 floor = 1 trial
    }
    for (int floor = 0; floor <= floors; floor++) {
      trials[floors][0] = 0; // no sheets = no trials
    }
    for (int floor = 0; floor <= floors; floor++) {
      trials[floor][1] = floor; 
    }
     for (int sheet = 2; sheet <= sheets; sheet++) {
	     
	      for (int floor = 2; floor <= floors; floor++) {
		      
                  trials[floor][sheet] = floors;
                   // x = trials
	        for (int x = 1; x <= floor; x++) {
                              // max of glass breaking vs not breaking
	          int outcome = 1 + Math.max(trials[x-1][sheet-1], 
	        		                  trials[floor-x][sheet]); 
	          trials[floor][sheet] = Math.min(outcome, trials[floor][sheet]);
	        }
	     }
      }
      return trials[floors][sheets]; // minimum number of trials
  }


  public static void main(String args[]){
      GlassFalling gf = new GlassFalling();

      // Do not touch the below lines of code, and make sure
      // in your final turned-in copy, these are the only things printed
      int minTrials1Recur = gf.glassFallingRecur(27, 2);
      int minTrials1Bottom = gf.glassFallingBottomUp(27, 2);
      int minTrials2Memo = gf.glassFallingMemoized(100, 3);
      int minTrials2Bottom = gf.glassFallingBottomUp(100, 3);
      System.out.println(minTrials1Recur + " " + minTrials1Bottom);
      System.out.println(minTrials2Memo + " " + minTrials2Bottom);
  }
}

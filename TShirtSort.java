import java.util.Scanner;

public class TShirtSort{
	public static void main(String[] args){

		//Variable declaration
		Scanner in = new Scanner(System.in);
		int n = 0;
		n = in.nextInt();
		in.nextLine();
		TShirt closet[] = new TShirt[n];
		for (int i=0; i<n; i++){
			closet[i] = new TShirt(in.nextLine(), in.nextLine());
		}

		//TShirt sort
		//String alphabetical order can checked with StringA.compareTo(StringB).
		mergeSort(closet, n);
		for(int i=0; i<n; i++){
			System.out.println(closet[i].getTeamName());
		}
	}

	public static void mergeSort(TShirt[] a, int n) {
		//Modified to work with TShirt objects
    	if (n < 2) {
    	    return;
    	}
    	int mid = n / 2;
    	TShirt[] l = new TShirt[mid];
    	TShirt[] r = new TShirt[n - mid];
 	
 	   	for (int i = 0; i < mid; i++) {
 	   	    l[i] = a[i];
 	   	}
 	   	for (int i = mid; i < n; i++) {
 	   	    r[i - mid] = a[i];
 	   	}
 	   	mergeSort(l, mid);
 	   	mergeSort(r, n - mid);
 	
 	   	merge(a, l, r, mid, n - mid);
	}

	public static void merge(TShirt[] a, TShirt[] l, TShirt[] r, int left, int right) {
		int i = 0, j = 0, k = 0;
		while (i < left && j < right) {
  			if(l[i].getTeamNationality().compareTo(r[j].getTeamNationality()) < 0){
  	        	a[k++] = l[i++];
  			}
  			else if(l[i].getTeamNationality().compareTo(r[j].getTeamNationality()) == 0){
  				if(l[i].getTeamName().compareTo(r[j].getTeamName()) < 0){
  	        		a[k++] = l[i++];
  				}
  				else{
  					a[k++] = r[j++];
  				}
  			}
  	      	else {
  	        	a[k++] = r[j++];
  	      	}
  	  	}
  	  	while (i < left) {
  	      	a[k++] = l[i++];
  	  	}
  	  	while (j < right) {
  	      	a[k++] = r[j++];
  	  	}
	}

}

class TShirt{
	private String teamName;
	private String teamNationality;

	public TShirt(String teamName, String teamNationality){
		this.teamName = teamName;
		this.teamNationality = teamNationality;
	}

	public String getTeamName(){
		return teamName;
	}

	public void setTeamName(String teamName){
		this.teamName = teamName;
	}

	public String getTeamNationality(){
		return teamNationality;
	}

	public void setTeamNationality(String teamNationality){
		this.teamNationality = teamNationality;
	}
}
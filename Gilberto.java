import java.util.*;

public class Gilberto{
	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		int n = 0; //number of sites at risk
		n = in.nextInt();
		in.nextLine();
		String[] input = new String[n];
		Vector<Point> floodedAreas= new Vector<Point>();
		//All inputs have the form: "(#,#) (#,#)"
		for(int i=0; i<n; i++){
			input[i] = in.nextLine();
			//Create two points from the string given
			floodedAreas.add(new Point(Integer.valueOf(input[i].substring(1,2)), Integer.valueOf(input[i].substring(3,4))));
			floodedAreas.add(new Point(Integer.valueOf(input[i].substring(7,8)), Integer.valueOf(input[i].substring(9,10))));
		}
		for(int i=0; i<n*2; i=i+2){
			floodedAreas.add(new Point(floodedAreas.get(i).x, floodedAreas.get(i+1).y));
			floodedAreas.add(new Point(floodedAreas.get(i+1).x, floodedAreas.get(i).y));
		}
		//Create point space from floodedAreas
		Point pointSpace[] = new Point[4*n];
		for(int i=0; i<pointSpace.length; i++){
			pointSpace[i] = floodedAreas.get(i); 
		}
		convexHull(pointSpace, pointSpace.length);
	}

	public static void pointSort(Vector<Point> pointVector){
		for(int i=0; i<pointVector.size(); i++){
			for(int j=i+1; j<pointVector.size(); j++){
				if(pointVector.get(i).x > pointVector.get(j).x){
					Point temporal = pointVector.get(i);
					pointVector.setElementAt(pointVector.get(j), i);
					pointVector.setElementAt(temporal, j);
				}
				else if(pointVector.get(i).x == pointVector.get(j).x){
					if(pointVector.get(i).y > pointVector.get(j).y){
						Point temporal = pointVector.get(i);
						pointVector.setElementAt(pointVector.get(j), i);
						pointVector.setElementAt(temporal, j);
					}
				}
			}
		}
	}

	public static void pointSortY(Vector<Point> pointVector){
		for(int i=0; i<pointVector.size(); i++){
			for(int j=i+1; j<pointVector.size(); j++){
				if(pointVector.get(i).y > pointVector.get(j).y){
					Point temporal = pointVector.get(i);
					pointVector.setElementAt(pointVector.get(j), i);
					pointVector.setElementAt(temporal, j);
				}
				else if(pointVector.get(i).y == pointVector.get(j).y){
					if(pointVector.get(i).x > pointVector.get(j).x){
						Point temporal = pointVector.get(i);
						pointVector.setElementAt(pointVector.get(j), i);
						pointVector.setElementAt(temporal, j);
					}
				}
			}
		}
	}

	public static void eliminateCollinear(Vector<Point> pointVector){
		int minX = pointVector.get(0).x;
		int maxX = pointVector.get(pointVector.size()-1).x;
		int mapXSize = maxX - minX + 1;
		int[] hashMapX = new int[mapXSize];
		for(int i=0; i<hashMapX.length; i++){
			try{
				hashMapX[pointVector.get(i).x - minX] ++;
			}
			catch(Exception e){}
		}

		int minY = pointVector.get(0).y;
		int maxY = pointVector.get(0).y;
		for(int i=0; i<pointVector.size(); i++){
			if(pointVector.get(i).y < minY){
				minY = pointVector.get(i).y;
			}
			if(pointVector.get(i).y > maxY){
				maxY = pointVector.get(i).y;
			}
		}

		int mapYSize = maxY - minY + 1;
		//System.out.println("Value of mapySize is: " + mapYSize);
		int[] hashMapY = new int[mapYSize];
		/*for(int i=0; i<hashMapY.length; i++){
			try{
				hashMapY[pointVector.get(i).y - minY] ++;
			}
			catch(Exception e){}
		}*/

		for(int z=0; z<pointVector.size(); z++){
			int temporalY = pointVector.get(z).y;
			hashMapY[temporalY-minY]++;
		}

		//For x, check for 3 or more
		for(int i=0; i<hashMapX.length; i++){
			if(hashMapX[i] > 2){
				int eliminateQueue = hashMapX[i] - 2; //Number of points to eliminate from Vector
				//Eliminate every single point in pointVector whose y is not minimum or maximum and whose x is equal to hashMapX[i] according position
				for(int j=0; j<pointVector.size(); j++){
					if(pointVector.get(j).x == i + minX){
						for(int k=1; k<eliminateQueue + 1; k++){
							pointVector.setElementAt(new Point(-1,-1),j+k);
							eliminateQueue = 0;
						}
					}
				}
			}
		}
		pointSortY(pointVector);
		//For y, check for 3 or more
		for(int i=0; i<hashMapY.length; i++){
			if(hashMapY[i] > 2){
				int eliminateQueue = hashMapY[i] - 2; //Number of points to eliminate from Vector
				//Eliminate every single point in pointVector whose x is not minimum or maximum and whose y is equal to hashMapY[i] according position
				for(int j=0; j<pointVector.size(); j++){
					if(pointVector.get(j).y == i + minY){
						for(int k=1; k<eliminateQueue + 1; k++){
							pointVector.setElementAt(new Point(-1,-1),j+k);
							eliminateQueue = 0;
						}
					}
				}
			}
		}
		pointSort(pointVector);

	}

	public static int orientation(Point p, Point q, Point r) 
    { 
        int val = (q.y - p.y) * (r.x - q.x) - 
                  (q.x - p.x) * (r.y - q.y); 
       
        if (val == 0) return 0;
        return (val > 0)? 1: 2;
    } 
      
    public static void convexHull(Point points[], int n) 
    { 
        if (n < 3) return; 
       
        Vector<Point> hull = new Vector<Point>(); 
       
        int l = 0; 
        for (int i = 1; i < n; i++) 
            if (points[i].x < points[l].x) 
                l = i; 
       
        int p = l, q; 
        do
        { 
            hull.add(points[p]); 
            q = (p + 1) % n; 
              
            for (int i = 0; i < n; i++) 
            { 
            	if (orientation(points[p], points[i], points[q]) == 2) 
                	q = i; 
            } 
            p = q; 
       
        } while (p != l);

        pointSort(hull);
        eliminateCollinear(hull);
       
        for (int i=0; i<hull.size(); i++) {
        	if(hull.get(i).x != -1){
        		System.out.println("(" + hull.get(i).x + ", " + hull.get(i).y + ")");
        	}
        }
    }


}

class Point 
{ 
    int x;
    int y;

    public Point(int x, int y){ 
        this.x=x; 
        this.y=y; 
    }
} 
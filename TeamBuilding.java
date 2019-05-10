import java.util.*;

public class TeamBuilding{
	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		int n = 0;
		n = in.nextInt();
		int[][] collabMatrix = new int[n][n];
		boolean[] assignedToTeam = new boolean[n];
		for (int i=0; i<n; i++){
			for(int j=0; j<n; j++){
				collabMatrix[i][j] = in.nextInt();
			}
			assignedToTeam[i] = false;
		}

		Vector<Team> teamList = new Vector<Team>();
		for(int k=0; k<n; k++){
			//Create base teams
			teamList.add(new Team());
		}
		//Cada Vector es una lista de integers que es el identificador de los weyes, se encuentra en un vector que contiene a estos vectores que son los teams.
		for(int i=0; i<n; i++){
			for(int j=0; j<n; j++){
				if(collabMatrix[i][j] == 0){
					//TRABAJANDO EN TEAM NÚMERO i (digamos UNO)
					//Agregar al team a las personas "i" y "j"
					addToTeam(i, i+1, teamList, collabMatrix, assignedToTeam);
					addToTeam(i, j+1, teamList, collabMatrix, assignedToTeam);
				}
			}
		}

		//Order the workers ascendingly
		for(int i=0; i<n; i++){
			for(int j=0; j<teamList.get(i).getTeamMembers().size(); j++){
				for(int k=j+1; k<teamList.get(i).getTeamMembers().size(); k++){
					if(teamList.get(i).getTeamMembers().get(j) > teamList.get(i).getTeamMembers().get(k)){
						int temp = teamList.get(i).getTeamMembers().get(j);
						teamList.get(i).getTeamMembers().setElementAt(teamList.get(i).getTeamMembers().get(k), j);
						teamList.get(i).getTeamMembers().setElementAt(temp, k);
					}
				}
			}
		}


		boolean teamExists = false;
		for(int i=0; i<n; i++){
			//Checar si el team está vacío
			if(teamList.get(i).getTeamMembers().size() != 0){
				teamExists = true;
				for(int j=0; j<teamList.get(i).getTeamMembers().size(); j++){
					System.out.print(teamList.get(i).getTeamMembers().get(j));
					if(j != teamList.get(i).getTeamMembers().size() - 1){
						System.out.print(" ");
					}
				}
				System.out.println("");
			}
		}
		if(!teamExists){
			System.out.println(-1);
		}

	}

	public static void addToTeam(int teamNumber, int a, Vector<Team> teamList, int[][] collabMatrix, boolean[] assignedToTeam){
		//Cuando se intente agregar un wey a la matriz, necesitas checar que ese susodicho no ha trabajado con NADIE del equipo.

		//Agregar sujeto al team 1. Antes de agregar a alguien a un team, se recorre el team, y para cada persona se comprueba que no haya trabajado con el que se va a agregar. En caso
		//de pasar la prueba, se agrega el sujeto al team 1. En caso contrario, se vuelve a llamar addToTeam pero se pasa un teamNumber + 1.

		int control = 0;

		for(int i=0; i<teamList.get(teamNumber).getTeamMembers().size(); i++){
			int teamMember = teamList.get(teamNumber).getTeamMembers().get(i);
			if(collabMatrix[teamMember - 1][a-1] == 1 && (teamMember-1) != (a-1)){
				//Conflict: Members have worked together previously
				control = 1;
			}
		}
		if(control == 0 && !assignedToTeam[a-1]){
			teamList.get(teamNumber).addMember(a);
			assignedToTeam[a-1] = true;
		}
		else if(assignedToTeam[a-1]){
			return;
		}
		else{
			addToTeam(teamNumber+1, a, teamList, collabMatrix, assignedToTeam);
		}
	}

}

class Team{

	Vector<Integer> teamMembers = new Vector<>();

	public void addMember(int memberID){
		teamMembers.add(memberID);
	}

	public Vector<Integer> getTeamMembers(){
		return teamMembers;
	}

}
import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;

public class projet_demineur {

	public static int entierAleatoire(int a, int b){
		return ThreadLocalRandom.current().nextInt(a, b + 1);
	}

	static int[][] T; 				
	static int[][] Tadj;

	public static void init(int h, int l, int n) {
		T=new int[h][l];
		Tadj=new int[h][l];
		for(int i=0; i<n; i++) {
			int e1=entierAleatoire(0,h-1);
			int e2=entierAleatoire(0,l-1);
			while(Tadj[e1][e2]==-1)
				e1=entierAleatoire(0,h-1);
				e2=entierAleatoire(0,l-1);
			Tadj[e1][e2]=-1;
		}
	}
	
	public static boolean caseCorrecte(int i, int j) {
		return  i>=0 && j>=0 && i<Tadj.length && j<Tadj[i].length;
	}

	public static void calculerAdjacent() {
		for(int i=0; i<T.length; i++) 
			for(int j=0;j<T[i].length;j++)  			//on parcours toutes les cases du tableau
				if(Tadj[i][j]==-1) 						//� la premi�re mine
					for(int c1=-1; c1<=1; c1++) 		
						for(int c2=-1; c2<=1; c2++) 	//on parcours toutes les cases adjacentes (lourd)
							if (caseCorrecte(i+c1, j+c2) && Tadj[i+c1][j+c2]!=-1)  //cf first comment /*
									Tadj[i+c1][j+c2]+=1;	
								//on augmente de 1 la case adjacente si ce n'est pas une mine (conserve -1)	
	}
	
	/* 
	 * FirstComment
	 * PB : si une mine se trouve sur les bords du tableaux les coordonn�es des cases adjacentes
	 * |->on utilise donc la fonction caseCorrecte
	 * Je pr�f�re imbriquer deux if plut�t que de les fusionner (faute a l'erreur si la case n'existe pas)
	 * 		|->putting the fonction caseCorrecte first with && we skiping the error on Tadj[i+c1][j+c2]
	 * 		if the case doesn't exist
	 */

	public static void afficherGrille(boolean affMines) {
		System.out.print(" ");
		if(T.length>=10)
			System.out.print(" ");
		if(T.length>=100)
			System.out.print(" ");
			
		for(int c=0; c<T[0].length; c++) { //On a retirer la premiere forloop (rep lines) car l'alphabet est a T[0]
			System.out.print("|");
			int col=c+65;				//Les 26premieres lettres (en majuscules), lorsque c=0, col=65 (=='A')
			if(col>90) 					//== c>25, (� c=26 la condition==true), lorsque c=25, col=90 (=='Z')
				col+=6;					//on saute les valeurs ASCII 91 � 96 (inclus), pour arriver aux minuscules
			char b=(char)(col);
			System.out.print(b);		//52col au max!
		}
		System.out.print("|");
		System.out.println();
		
		for(int i=0; i<T.length; i++) {
			for(int j=0; j<T[i].length; j++) {
					
				if(j==0) {
					if(T.length>=10) 
						if(i<10)
							System.out.print("0");
						if(T.length>=100)
							if(i<100)
								System.out.print("0");
					System.out.print(i);
				}
				System.out.print("|");
				if(T[i][j]==0 && (!affMines||Tadj[i][j]!=-1)) //(!affMines||Tadj[i][j]!=-1) pour �viter (" " + "!")
					System.out.print(" ");
				else if(T[i][j]==2)
					System.out.print("X");
				else if(T[i][j]==1)
					System.out.print(Tadj[i][j]);
				if(affMines && Tadj[i][j]==-1)
					System.out.print("!");
				
				
			
			}
			System.out.print("|");
		System.out.println();
		}
	}


	public static boolean caseAdjacenteZero(int i, int j) {
		for(int c1=-1; c1<=1; c1++) {
			for(int c2=-1; c2<=1; c2++) {
				if(caseCorrecte(i+c1, j+c2) && T[i+c1][j+c2]==1 && Tadj[i+c1][j+c2]==0) // && (c1 == 0 ^ c2 == 0)
					return true;	//On veut arreter le programme si AU MOINS une case adjacente est 0
			}
		}
		return false;				//Aucune des cases adjacentes ne correspondent on renvoit false
	}
	
	/*  
	 * return Tadj[i+c1][j+c2]==0;
	 *  Ne fonctionne pas car il stop la fonction � la premiere coordon�ees
	 */

	public static void revelation(int i, int j) { 
		T[i][j]=1;
		if(Tadj[i][j]==0) {
			boolean rev=true;
			while(rev){							//si while ou doWhile ou forloop dans le code -> voir 'BUG' 
				System.out.println("while");
				rev=false;									//reset du compteur
				for(int c1=0; c1<T.length; c1++) {			//Parcours 1fois toute la grille
					for(int c2=0; c2<T[c1].length; c2++) {
						if(caseAdjacenteZero(c1, c2)) {		//rajout a cette fonction (..&& T[i+c1][j+c2]==1..)
							System.out.println("if "+c1+" "+c2);
							T[c1][c2]=1;
							//rev=true;					//si au moins une case a ete revelee la boucle continue
						}
						
					}	
				}
				
				
			}
		}
	}
		
	/*
	 * repete en infinite loop == La boucle va tout le temps v�rifier les cases revelees sans autres conditions
	while
	if15 0 -> Voisin du 0 revelee
	if16 0
	if16 1
	if17 0
	if17 1
	if17 2
	if18 0
	if18 1
	 *
	 */
	
	/*
	 * La fonction caseAdjacenteZero(i,j) peut �tre remplac� par comparaison de Tadj[i][j] ?
	 * 	-pas cool:
	 * 		if(Tadj[c1][c2]!=-1)
	 * 			T[c1][c2]=1;
	 * why: Il peut y avoir que des cases adjacentes � 1 ou 2mines
	 */
	
	/*
	 * 2eme id�e:
	 *  if(Tadj[c1][c2]==0) //Pour bypass la double boucle caseAdjacenteZero
	 *  	T[c1][c2]=1;	//EDIT: On travaillera sur cette id�e � la fonction revelation2
	 * 
	 */


	public static void revelation2(int i, int j) {
		T[i][j] = 1;
		if (Tadj[i][j] != 0) return;
		for (int c1 = i-1; c1 <= i + 1; c1++) {
			for (int c2 = j-1; c2 <= j + 1; c2++) {
				if (caseCorrecte(c1, c2) && c1 != i && c2 != j) revelation2(c1, c2);
			}
		}
	}
	
	/*
	 * Fonction r�cursive: BUG : java.lang.StackOverflowError (need 5 more To RAM)
	 * -not so good optimized ?
	 * -infinite loop ?
	 * autre facon d'ecrire la fonction recursive
	 T[i][j]=1;
		if(Tadj[i][j]==0) {
			for(int c1=-1; c1<=1; c1++) {			
				for(int c2=-1; c2<=1; c2++) {
					System.out.println("i+c1: "+i+c1);
					System.out.println("j+c2: "+j+c2);
					if(caseCorrecte(i+c1, j+c2) && c1!=0 && c2!=0 && Tadj[i+c1][j+c2]==0 ) {
						revelation2(i+c1, j+c2);
						//T[i+c1][j+c2]=1; EDIT: dej� fait en debut de fonction
					}
				}	
			}
		}
	 */
	
	/*
	 * 1ere id�e : while(caseAdjacenteZero(i,j)) 
	 *	|-> T[i+c1][j+c2]=1;	
	 * 2eme id�e : petite fonction r�cursive (comme la f� factorielle)
	 */

	public static void actionDrapeau(int i, int j) {
		if(T[i][j]!=1) {	 //le code s'applique lorsque la case concern�e est non r�v�l�e 
			if(T[i][j]==2) 	 //Si la case contient d�j� l'information drapeau (==2)
				T[i][j]=0;		//Alors 'enlever' le drapeau en restaurant l'�tat non r�v�l�e (==0) � la case
			else			 //Si la case est non r�v�l�e et non flag (SINON)
				T[i][j]=2;		//Alors 'poser' le flag (==update la valeur de la case � =2;)
		}
	}
	
	/*
	 * Le tout premier if est pour eviter de poser des drapeau � des endroits inutiles
	 * (case d�j� r�v�l�e == on sait que ce n'est pas une bombe)
		c'est une s�curit� contre l'utilisateur
		si la premiere condition n'est pas satisfaite tant pis,
		l'utilisateur ne connait pas les r�gle ou a fait un missclick/writing
		le sinon � cette condition serait un rappel des regles
        		/italic ->Vous ne pouvez pas flag une case d�j� r�v�l�e
     * (La fonction verificationFormat y remedie) 
	 */
	
	/*
	 * D�tails:
	 * if(T[i][j]!=1) {
 		deuxieme condition
    		modification de T (enlever le drapeau)==(retour � l'�tat r�v�l�)
  		Troisieme condition (sinon)
    		modition de T (pose du drapeau)
	   }
	 *
	 *T[coordonn�e premiere][coordonn�e seconde]=chiffre correspondant au bon �tat;
	 */
	
	/*
	 * J'ai aid� yacine chougui -MA4-8 sur cette question 3.b]
	 * Les commentaires proviennent de ma discussion avec lui pour l'aider �
	 * comprendre certain concept utilis� dans ce programme.
	 * Par 2h de discussion et travail, il a comprit le principe et a r�dig� un code presque similaire au mien.
	 * Il travaille bien!
	 */
	
	public static boolean revelerCase(int i, int j) {
		if(Tadj[i][j]==-1)	//Pour permettre au fin de code de la fonct. On ne met pas �a :	return Tadj[i][j]==-1;
			return false;
		revelation(i,j); //revelation2(i,j);
		return true;
	}


	public static boolean aGagne() {
		for(int c1=0; c1<T.length; c1++) {
			for(int c2=0; c2<T[c1].length; c2++) {
				if(Tadj[c1][c2]!=-1 && T[c1][c2]==0)
					return false;
			}
		}
		return true;
	} 
	
	/*
	 * 1ereId�e:
	 * 
	 * On parcour tout le tableau et on v�rifie si tout est r�v�l�
	 * ->en checkant si il y a une case non mine qui n'est pas r�v�l�e
	 * 	->au lieu de contenir une information supp dans une variable en plus
	 * 
	 * Nul besoin de check si une mine est r�v�l�e en plus
	 * 	->== Loser == on a une focntion pour �a
	 * 	->et aucunes situation peut arriver comme
	 * 		->conditions pour trigger aGagne et affMines (dans la fonction jeu)
	 */


	public static boolean verifierFormat(String input) {
		char[] c= new char[input.length()];
		if(input.length()<=2)							 //Fix du BUG: User put less than 3char-> Bypass for loop
			return false;
		for(int i=0; i<input.length(); ++i)				 //On initialise la cha�ne de charact�re
			c[i]=input.charAt(i);
		if(c[0]=='d' || c[0]=='r') {
			if(Character.isLetter(c[input.length()-1])) {

				for(int i=1; i<input.length()-1; ++i) { //On commence notre loop � indice 1 -> n-1 char
					if(!Character.isDigit(c[i]))
						return false;		//Un chiffre (et valable) suffit pour etre accepter par la verif 
				}
				return true;
			}
		}
		//else de d�co {
		return false; 					//si la coordon�e et format ne son pas valide
	}
	
	/*
	 * Pour le character.isLetter(condition):
	 *condition:
	 *c[input.length()-1] on pref � c[3] pour premettre d'avoir +que99lignes (==nombres � 3 chiffres allowed)
	 */


	public static int[] conversionCoordonnees(String input) {
		int[] cord=new int[3];							//Cr�ation d'un tableau vide {0,0,0}
		char[] c= new char[input.length()];
		for(int i=0; i<input.length(); ++i)				//On initialise la cha�ne de charact�re
			c[i]=input.charAt(i);
		
		if(c.length==3)									//Premier �l�ment (l'indice de la ligne)
			cord[0]=c[1]-48;							//Patch-Bug and fix on comment down below
		else {
			int i=1;									//On calcule le num de la ligne		
			while(Character.isDigit(c[2+i]))			//en comptant le nb de chiffres avant la virgule
				++i;
			for(int j=0; j<=i; ++j)			
				cord[0]+=Math.pow(10, i-j)*(c[1+j]-48);	//Multipliant chaque chiffres par la bonne puissance de 10
		}
										
		
		if(c[(input.length()-1)]>='A'&&c[(input.length()-1)]<='Z')
			cord[1]=c[(input.length()-1)]-65;			//Deuxieme element - colonne (check si majuscule/minuscule)
		else											//La lettre est convertie en integer
			cord[1]=c[(input.length()-1)]-71; 			//-97+26 pour atteindre le bon indice de T et Tadj
						
		if(c[0]=='d')									//Troisi�me �l�ment (==case marqu�e ou r�v�l�e)
			cord[2]=0;
		else
			cord[2]=1;
		return cord;
	}
	
	/*
	 * Pour le Premier �l�ment de cord[] :
	 * -Je pensais cr�er une loop: while(Character.isDigit(C[i])) ++i
	 * -int sommeLigne = Multiplier par i*c[1], puis i-1*c[1+1], etc i fois;
	 * if(c[1]==0)
			cord[0]=c[2];
	   else...
	 * ce if est cancel si l'utilisateur met un 0 avant le vrai num�ro de ligne
	 * comme 002 ou 016 etc
	 */
	
	/*
	 * Premier code du Premier element:
	 * if(c[1]==0)										//Premier �l�ment (l'indice de la ligne)
			cord[0]=c[2];
	   else {											
			int i=1;									//On calcule le num de la ligne	
			while(Character.isDigit(c[2+i]))			//en comptant le nb de chiffres avant la virgule
				++i;
			for(int j=0; j<=i; ++j)						//Multipliant chaque chiffres par la bonne puissance de 10
				cord[0]+=Math.pow(10, i-j)*c[1+j];
	   }
	 */
	
	/*
	 * List BUG (du Premier Element-CodePremier):
	 * -c[2+i] out of bounds si c.length=3 (un seul chiffre au lieu de deux min -> 05 marche comme 5)
	 *   ->FIX : contourner par un if recuperant directement c[1] si c.length==3
	 * -if(c[1]==0)	BUG lorsque User put 0 before the real line number
	 * -donne pas le bon chiffre
	 *   ->FIX du CHAPEAU POINTU: 10^(i-j) -> Math.pow(X,puissance);
	 *   ->char '9' != 9 :p
	 * 	 ->FIX: char '9'-48 = 9 (CODE ASCII DigitChar!=int)
	 */
	
	/*
	 * Code test (pour determiner ou �taient les pb):
	 	int[] cord={0, 0, 0};
		char[] c= {'r','1','9','8','5','a'};
		//{'r','5','a'};
		//{'r','0','5','a'};
		//{'r','0','9','6','5','a'};
		if(c.length==3)
			cord[0]=c[1]-48;
		
		else {
		
			int i=1;									
			while(Character.isDigit(c[2+i]))	{
				++i;
				System.out.println("i: "+i);
			}
			for(int j=0; j<=i; ++j)		{				
				System.out.println("i: "+i);
				System.out.println("j: "+j);
				cord[0]+=Math.pow(10, i-j)*(c[1+j]-48);
				System.out.println("10^(i-j): "+Math.pow(10, i-j));
				System.out.printf("c[1+j]: %c",c[1+j]);
				System.out.println();
				System.out.println("cord[0]: "+cord[0]);
			}
		}
		System.out.println("cord[0]: "+cord[0]); 
	 */
	

	public static void jeu() {
		boolean ask=true;					//Demande Standard
		boolean missWriting=false;			//Ajoute a la demande une ligne d'aide
		boolean affmines=false;
		Scanner sc = new Scanner(System.in);
		
		afficherGrille(affmines);
		System.out.println();
		
		while(!aGagne() && affmines!=true) {//On rajoute || affmines!=true pour stopper whileloop lorsque User lost
		int[] cord=new int[3];				//Avoid error after while
		
		
		
			while(ask){						//Repete la demande jusqu'a ce que le joueur ait saisit qqch de bon
				System.out.println("Tip your move: ");
				if(missWriting==true)		//Au Deuxieme tour du whileloop
					System.out.println("type 'exemple' to get the commands: ");
				String input = sc.nextLine();
			
				if(verifierFormat(input)) {
					cord=conversionCoordonnees(input);
					if(caseCorrecte(cord[0], cord[1]))	//pour eviter les conditions a ralonge dans la f� verifierFormat
						ask=false;
					else								//pour la phrase d'aide
						missWriting=true;
				}
				else
					missWriting=true;
				if(input=="exemple") {		//Effectif tout le temps
					System.out.println("r05C: r�v�le la case ligne 05, colonne C");
					System.out.println("d10A: marque ou retire un drapeau pour la case ligne 10, colonne A");
				}
			}
		
			if(cord[2]==0)
				actionDrapeau(cord[0], cord[1]);
			else {
				if(Tadj[cord[0]][cord[1]]==-1) {
					System.out.println("Perdu!");
					affmines=true;
				}
				else {
					revelerCase(cord[0], cord[1]);
				}
					
			}
			
			afficherGrille(affmines);	//La boucle while n'affichera pas la grille finale, on l'affiche ici
			System.out.println();		//-> on affiche la grille avant le while (pour le premier move)
										//-> on affiche en fin de while loop (affichage de la grille postmove)
			ask=true;					//Evite l'infinite loop bypassant ask user
		}
		if(aGagne())
			System.out.println("GAGNE ! Bien jou� !");
		sc.close();
	}


	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		int h=0, l=0, n=0;									//Trigger le while
		while(l>=53 || l<=0 || h<=0 || n<=0 || n>=h*l) {	//ligne==hauteur(h) - colonnes==longeur(l) eu quiproquo
			System.out.println("Le nombre de lignes (conditions: 0<nb entier)");
			h = sc.nextInt();
			System.out.println("Le nombre de colonne (conditions: 0<nb entier<=52)");
			l = sc.nextInt();
			System.out.println("Le nombre de mines � placer (conditions: 0<nb entier mines<lignes*colonnes )");
			n = sc.nextInt();
		}			
		init(h, l, n);			//on initialise les deux tableaux (info et status)
		calculerAdjacent();		//on rempli les info dans Tadj
		
		//Affiche les coordonn�es de tous les z�ro: FIX en faisant en sorte que la premiere coordonnée revelee par
		//l'user soit un 0
//		for(int i=0; i<T.length; i++) {
//			for(int j=0; j<T[i].length; j++) {
//				if(Tadj[i][j]==0)
//					System.out.println(i+" "+j);
//			}
//		}
		
		jeu();					//on lance le jeu (loop while qui fait l'action User tant, loop while qui ask User
		sc.close();
	}


	public static void aide() {
		
	}


	public static void intelligenceArtificielle() {
		
	}


	public static void statistiquesVictoiresIA() {
		
	}

}

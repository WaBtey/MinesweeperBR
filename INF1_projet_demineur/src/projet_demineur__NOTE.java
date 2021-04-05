// Ceci est un squelette à REMPLIR pour le projet INF1 sur le jeu de démineur
//
// - N'oubliez pas de renseigner vos deux noms
// Prénom Nom Groupe : Florian EPAIN, MA4
// Prénom Nom Groupe : BEN AYAD Salma, TD3
//
// - Pour chaque question, le squelette donne le nom de la fonction à écrire mais *pas* la signature
//   il faut remplir les types d'entrées et de sorties (indiqués par ?) et remplir l'intérieur du code de chaque fonction.
//
// - L'unique fichier de code que vous soumettrez sera ce fichier Java, donc n'hésitez pas à le commenter abondamment.
//   inutile d'exporter votre projet comme archive Zip et de rendre ce zip.
//   Optionnel : vous pouvez aussi joindre un document PDF donnant des explications supplémentaires (si vous utilisez OpenOffice/LibreOffice/Word, exportez le document en PDF), avec éventuellement des captures d'écran montrant des étapes affichées dans la console
//
// - Regardez en ligne sur le Moodle pour le reste des consignes, et dans le fichier PDF du sujet du projet
//   https://foad.univ-rennes1.fr/mod/assign/view.php?id=534254
//
// - A rendre avant le vendredi 04 décembre, maximum 23h59.
//
// - ATTENTION Le projet est assez long, ne commencez pas au dernier moment !
//
// - Enfin, n'hésitez pas à contacter l'équipe pédagogique, en posant une question sur le forum du Moodle, si quelque chose n'est pas clair.
//

// Pour utiliser des scanners pour lire des entrées depuis le clavier
// utilisés en questions 4.d] pour la fonction jeu()
import java.util.Scanner;

// Pour la fonction entierAleatoire(a, b) que l'on vous donne ci-dessous
import java.util.concurrent.ThreadLocalRandom;

// L'unique classe de votre projet
public class projet_demineur__NOTE {

	// Donné, utile pour la question 1.b]
	public static int entierAleatoire(int a, int b){
		// Renvoie un entier aléatoire uniforme entre a (inclus) et b (inclus).
		return ThreadLocalRandom.current().nextInt(a, b + 1);
	}


	//
	// Exercice 1 : Initialisation des tableaux
	//

	// Question 1.a] déclarez les variables globales T et Tadj ici
	static int[][] T; 				//Voici les variables globales
	static int[][] Tadj;

	// Question 1.b] Fonction init
	public static void init(int h, int l, int n) {
		T=new int[h][l];
		Tadj=new int[h][l];
		for(int i=0; i<n; i++) {
			int e1=entierAleatoire(0,h-1);
			int e2=entierAleatoire(0,l-1);
			while(Tadj[e1][e2]==-1) {
				e1=entierAleatoire(0,h-1);
				e2=entierAleatoire(0,l-1);
			}
			Tadj[e1][e2]=-1;
		}
	}

	/*
	 * Premier choix de code :
	 * for(int i=0; i<n; i++) {
			Tadj[entierAleatoire(0,h-1)][entierAleatoire(0,l-1)]=-1;
	   }
	 *
	 *Le probleme c'est que les coordonn�es al�atoires 'choisies' peuvent etre plusieurs fois les m�mes
	 *Il y aura peut etre qu'une seule bombe avec une probabilit� tr�s faible
	 *Mais r�el!!
	 *
	 */

	// Question 1.c] Fonction caseCorrecte
	public static boolean caseCorrecte(int i, int j) {
		//System.out.println("i: "+i); Deggogage
		//System.out.println("j: "+j);

		return  i>=0 && j>=0 && i<Tadj.length && j<Tadj[i].length;
	}

	/*
	 * -J'aurais voulu comparer i et j avec les int h et l, mais ça ne correspond pas à la question
	 * 		2parameter en plus (int h, int l) code :
	 * return i<=h && j<=l;
	 * 		-alternative:
	 * return T[i][j]!=undefined && Tadj[i][j]!=undefined;
	 * 		-Finalement, j'ai retrouv� la m�moire sur le .length (god blessed)
	 */

	// Question 1.d] Fonction calculerAdjacent
	public static void calculerAdjacent() {
		for(int i=0; i<T.length; i++) {
			for(int j=0;j<T[i].length;j++) { 			//on parcours toutes les cases du tableau
				if(Tadj[i][j]==-1) {					//� la premi�re mine
					for(int c1=-1; c1<=1; c1++) {
						for(int c2=-1; c2<=1; c2++) {		//on parcours toutes les cases adjacentes (lourd)
							if (caseCorrecte(i+c1, j+c2) && Tadj[i+c1][j+c2]!=-1)  //cf first comment /*
									Tadj[i+c1][j+c2]+=1;
								//on augmente de 1 la case adjacente si ce n'est pas une mine (conserve -1)
						}
					}
				}
			}
		}
	}

	/*
	 * FirstComment
	 * PB : si une mine se trouve sur les bords du tableaux les coordonn�es des cases adjacentes
	 * |->on utilise donc la focntion caseCorrecte
	 * Je pr�f�re imbriquer deux if plut�t que de les fusionner (faute a l'erreur si la case n'existe pas)
	 * 		|->putting the fonction caseCorrecte first with && we skiping the error on Tadj[i+c1][j+c2]
	 * 		if the case doesn't exist
	 */

	/*
	 * C'est Ugly mais moins que �a :
	    Tadj[i-1][j-1]+=1;
		Tadj[i-1][j]+=1;
		Tadj[i-1][j+1]+=1;
		Tadj[i][j-1]+=1;
		Tadj[i][j]+=1; ->
		Tadj[i][j+1]+=1;
		Tadj[i+1][j-1]+=1;
		Tadj[i+1][j]+=1;
		Tadj[i+1][j+1]+=1;
	 *	Pose Probleme car même les cases minées (==-1) vont subir le +1
	 */

	//
	// Exercice 2 : Affichage de la grille
	//

	// Question 2.a]
	public static void afficherGrille(boolean affMines) {
		System.out.print("  ");

		for(int c=0; c<T[0].length; c++) { //On a retirer la premiere forloop (rep lines) car l'alphabet est a T[0]
			System.out.print("|");
			int col=c+65;				//Les 26premieres lettres (en majuscules), lorsque c=0, col=65 (=='A')
			if(col>90) 					//== c>25, (� c=26 la condition==true), lorsque c=25, col=90 (=='Z')
				col+=6;					//on saute les valeurs ASCII 91 � 96 (inclus), pour arriver aux minuscules
			char b=(char)(col);
			System.out.print(b);		//52col au max!
		}
		// Note : okay mais compliqué...!
		System.out.print("|");
		System.out.println();

		for(int i=0; i<T.length; i++) {
			for(int j=0; j<T[i].length; j++) {

				if(j==0) {
					if(i<10)
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
				// Note : un peu dense, préférer if () avec une espace



			}
			System.out.print("|");
		System.out.println();
		}
		//Note : La jonction entre les lignes 99 et 100 est d�geu (laiss� pour compte atm)
	}


	//
	// Exercice 3 : Révéler une case
	//

	// Question 3.a]
	public static boolean caseAdjacenteZero(int i, int j) {
		for(int c1=-1; c1<=1; c1++) {
			for(int c2=-1; c2<=1; c2++) {
				if(caseCorrecte(i+c1, j+c2) && T[i+c1][j+c2]==1 && Tadj[i+c1][j+c2]==0) // && (c1 == 0 ^ c2 == 0)
					return true;	//On veut arreter le programme si AU MOINS une case adjacente est 0
			}
		}
		// Note : okay
		return false;				//Aucune des cases adjacentes ne correspondent on renvoit false
	}

	/*
	 * return Tadj[i+c1][j+c2]==0;
	 *  Ne focntionne pas car il stop la fonction � la premiere coordon�ees
	 */

	// Question 3.b]
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
	 * repete en infinite loop : == La boucle va tout le temps v�rifier les cases revelees sans autres conditions
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
	 *  	T[c1][c2]=1;	//EDIT: On travaillera sur cette id�e � la question 3.c]
	 *
	 */


	// Question 3.c] Optionnel
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

	// Question 3.d]
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
     * (La fonction verificationFormat y remidie)
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

	// Question 3.e]
	public static boolean revelerCase(int i, int j) {
		if(Tadj[i][j]==-1)	//Pour permettre au fin de code de la fonct. On ne met pas �a :	return Tadj[i][j]==-1;
			return false;
		revelation2(i,j); //revelation2(i,j);
		return true;
	}


	//
	// Exercice 4 : Boucle de jeu
	//

	// Question 4.a]
	public static boolean aGagne() {
		for(int c1=0; c1<T.length; c1++) {
			for(int c2=0; c2<T[c1].length; c2++) {
				// Note : pas clair de savoir pourquoi ça suffirait...
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

	// Question 4.b]
	public static boolean verifierFormat(String input) {
		char[] c= new char[input.length()];
		if(input.length()<=2)							 //Fix du BUG: User put less than 3char-> Bypass for loop
			return false;
		for(int i=0; i<input.length(); ++i)				 //On initialise la cha�ne de charact�re
			c[i]=input.charAt(i);
			// Note : bonne idée
		if(c[0]=='d' || c[0]=='r') {
			if(Character.isLetter(c[input.length()-1])) {

				for(int i=1; i<input.length()-1; ++i) { //On commence notre loop � indice 1 -> n-1 char
					if(!Character.isDigit(c[i]))
						return false;		//Un chiffre (et valable) suffit pour etre accepter par la verif
				}
				return true;
			}
		}
		// Note : un peu compliqué
		//else de d�co {
		return false; 					//si la coordon�e et format ne son pas valide
	}

	/*
	 * Pour le character.isLetter(condition):
	 *condition:
	 *c[input.length()-1] on pref � c[3] pour premettre d'avoir +que99lignes (==nombres � 3 chiffres allowed)
	 */


	// Question 4.c]
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
				// Note : vraiment compliqué ! le sujet demandait d'utiliser Integer.parseInt()...
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

	// Question 4.d]
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

	// Question 4.e]
	// Votre *unique* méthode main
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		int h=0, l=0, n=0;									//Trigger le while
		// Note : très bien d'avoir vérifié ça
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

		//Affiche les coordonn�es de tous les z�ro:
		// Note : ?? pourquoi, cela n'était pas demandé !
		for(int i=0; i<T.length; i++) {
			for(int j=0; j<T[i].length; j++) {
				if(Tadj[i][j]==0)
					System.out.println(i+" "+j);
			}
		}

		jeu();					//on lance le jeu (loop while qui fait l'action User tant, loop while qui ask User
		sc.close();
	}

	/*
	 * List Bug Fixed (main) (PAS LA PEINE DE REGARDER):
	 *
	 * 1-condition du While quiproquo (fonction main) :
	 *  ->FIXED : interchanger h et l dans la condition l!=nb de lignes et h!=nb de colonnes
	 *
	 * 2-Grille d�geu (fonction afficheGrille)
	 *  ->FIXED : just un "| " au d�but pas "| |"
	 * 3-Grille d�geu 2: la premiere ligne (Alphabet) est r�p�t�e
	 *  ->FIXED : retirer une forloop en trop et changer T[i].length en T[0].length
	 * 4-Note : La jonction entre les lignes 99 et 100 est d�geu (laiss� pour compte atm)
	 *
	 * 5-INFINITE LOOP : Affiche la grille en loop sans ask user (user's first move : d05H, r05H)
	 *  ->FIXED : ask=true; � la fin de la 1ere WhileLoop de la fonction Jeu
	 *
	 * 6-lorsque User a perdu, la grille n'affiche pas les mines:
	 * 	->FIXED mais pose pb en graphisme
	 *
	 * 7-l'action r (de r�v�ler une case) ne focntionne pas:
	 *  ->fonction revelerCase ne change pas l'etat de la case en question
	 *  ->revelerCase appelle revelation qui change l'etat de la case en r�v�l�e
	 *  -> FIXED : change if(Tadj[i][j]!=-1) to if(Tadj[i][j]==-1) (revelerCase function)
	 *
	 * 8-Lorsque un 0 est r�v�l�e revelation ne fait pas son job
	 * 	->FIXED : erreur du return de caseCorrecte (==mauvaise organisation des && dans la condition)
	 *
	 *
	 * 9-out of bounds lors d'un user's ask (fonction caseCorrecte)
i: -1 //des system.out.println(i) et (j) avant le return de la fonction caseCorrecte
j: -1
Exception in thread "main" java.lang.ArrayIndexOutOfBoundsException: Index -1 out of bounds for length 5
	at projet_demineur.caseCorrecte(projet_demineur.java:80)
	at projet_demineur.caseAdjacenteZero(projet_demineur.java:191)
	at projet_demineur.revelation(projet_demineur.java:212)
	at projet_demineur.revelerCase(projet_demineur.java:285)
	at projet_demineur.jeu(projet_demineur.java:504)
	at projet_demineur.main(projet_demineur.java:532)
	 *  ->(faux) FIX1 : unmerge on condition caseAdjacenteZero (caseCorrecte && coordonn�e de la case � tester)
	 *  |-> en deux if imbriqu�
	 * -n'importe quel coordonn�e donn�e par User se retrouve i=-1 et j=-1 � caseCorrect
	 *  |-> implique un pb dans le return de caseCorrecte
	 * 	->FIX2 : arranger l'ordre des condition && dans le return de caseCorrecte (FIX1 ne fix rien)
	 *
	 * 10- 1bombes set up : (fonction revelation)
	      |A|B|C|D|E|
		00|0|0|0|1| |
		01|0|0|0|1| | -> Manque la revelation d'un z�ro
		02|0|0|0|0|0| 	|-> FIXED : <= pour les conditions des 2forloop (fonction caseAdjacenteZero)
		03|0|0|0|0|0|
		04|0|0|0|0|0|
	 *
	 *  11- M�me set up (fonction calculerAdjacent)
	 *  r1E
			Perdu!
  		  |A|B|C|D|E|
		00|0|0|0|1| |
		01|0|0|0|1|!|
		02|0|0|0|0|0| 11prime-> Mauvais compte caseAdjacent
		03|0|0|0|0|0| 		|-> FIXED : <= pour les conditions des 2forloop
		04|0|0|0|0|0| 11second-> Le jeu continue � la mort ou � la win
							 |-> FIXED : && au lieu de || pour la condition du 1er whileLoop (fonction Jeu)
	 *
	 	  |A|B|C|D|E|F|G|H|I|J|
		00| | |0|0|0|0|0|0|0|0|
		01| !| !|0|0|0|0|1|1|0|0|11third/6-> deux instructions (afficheGrille car if puis if)==(" "+"!")
		02|0|0|0|0|0|1|2| !|0|0|		 |-> FIXED : if(T[i][j]==0 && (!affMines||Tadj[i][j]!=-1)) en condition
		03|0|0|0|0|0|1| !|0|0|0| 11prime-> les cases en dessous des bombes ne re�oivent pas la modif de calculerAdjacent
		04|0|0|0|0|0|0|0|0|0|0|		   |-> FIXED
		05|0|0|0|0|0|0|1|1|0|0|
		06|0|0|0|0|0|0|1| !|0|0|
		07|0|0|0|0|0|0|0|0|0|0|
		08|0|0|0|0|0|0|0|0|0|0|
		09|0|0|0|0|0|0|0|0|0|0|
	 *
	 * 12-Affiche un z�ro sur la grille postmove (� la case [0][0]) (lors de la infinite loop fixed)
	 *    -> Smell like FIXED
	 *
	 * 13-PLANTAGE : une fois sur deux, alt�rn�e propre wtf, plante (avec n'importe quel h, l et n)

Exception in thread "main" java.lang.ArrayIndexOutOfBoundsException: Index -1 out of bounds for length 20 (le 20=h)
	at projet_demineur.caseCorrecte(projet_demineur.java:78)
	at projet_demineur.calculerAdjacent(projet_demineur.java:97)
	at projet_demineur.main(projet_demineur.java:495)

	 *  -> calculerAdjacent
	 *  	Imbriquer deux if plut�t que de les fusionner (faute a l'erreur si la case n'existe pas)
	 * 		|->putting the fonction caseCorrecte first with && we skiping the error on Tadj[i+c1][j+c2]
	 * 		if the case doesn't exist
	 *  -> caseCorrecte
	 *  	T[i] ?
	 *  |-> les deux pr�c�dente hypoth�ses ont �t� arrag�es pour d'autres bug.
	 *  -> le Bug ne semble plus d'actualit�
	 *
	 *
	 * 14-Tomb� dans la hutte de madame Irma - trop de revelation (fonction revelation1)
	 *   -> la fonction revelation revele TOUS les z�ro
	 *   -> FIXED : rajout a caseAdjacenteZero cette condition (..&& T[i+c1][j+c2]==1..)

	 (h=50,l=52,n=500)
	 Tip your move:
	 r00A
  |A|B|C|D|E|F|G|H|I|J|K|L|M|N|O|P|Q|R|S|T|U|V|W|X|Y|Z|a|b|c|d|e|f|g|h|i|j|k|l|m|n|o|p|q|r|s|t|u|v|w|x|y|z|
00|0|0|1| |1|0|0|0|0|0|0|0|0|0|0|0|1| |1|0|0|0|1| | | | | | | | | | |1|0|1| | | | | | | | | | | | | | | | |
01|0|0|1| |1|1|1|1|0|0|1|2|2|2|1|1|2| |2|0|0|0|1| |2|1|2| | | | | | |2|0|1|1|1|1|3| | | | | | | | | | |2|1|
02|0|0|1| | | | |1|0|0|2| | | | | | | |2|0|0|1|2| |1|0|1|2| | | | | |1|1|1|1|0|0|2| | | | |2|2|1|2|2| |1|0|
03|0|0|1| | | | |3|2|2|3| | | | | | |2|1|1|1|2| | |2|1|0|1| | | | | | | | |1|0|0|1|1| | | |1|0|0|0|1| |1|0|
04|0|0|1|1|3| | | | | | | | | | | | |1|0|1| | | | | |1|1|2| | | | | | | |1|1|0|0|0|1| |2|1|1|0|0|0|2| |3|1|
05|0|0|0|0|1| | | | | | | | | |2|1|1|1|1|2| | | | | | | | | | | | | | | |1|0|1|1|1|2| |2|0|1|1|1|0|1| | | |
06|0|0|0|1|2| | | | | | | | | |1|0|1| | | | | | | | | | | | | | |3|2|3| |2|1|1| | | | |2|0|2| |3|1|2| | | |
07|0|1|2|3| | | | | | | | | |1|1|1|2| | | | | |2|1|2| | | | | | |2|0|1|2| |1|1|1|2| |2|1|1|4| | | | | | | |
08|1|2| | | | | | | | | | | |1|0|1| | | | | | |1|0|1| | | | | | |1|0|0|1| |1|0|0|1| |1|0|1| | | | | | | | |
09| | | | | | | | | | | | | |1|0|2| | | | |2|1|2|1|2| | | | | | |1|0|1|1| |1|1|0|1| |1|0|1|2|4| | | | |1|1|
10|1|1|1|1|2|2|1|2| | | | | |2|1|2| | | | |1|0|1| | | | |3|3|3| |1|0|1| | | |2|1|1| |1|1|0|0|2| | | | |1|0|
11|0|0|0|0|0|0|0|1| | | | | | | | | |3|2|3|3|2|2| | |1|1|1|0|1|1|2|1|2| | | | | | | | |2|0|0|1|1|1| | |3|1|
12|0|0|0|0|0|0|1|2| | | | | | | | | |1|0|1| | | | | |1|0|0|0|0|0|1| | | | | | | | | | |2|1|1|1|0|1| | | | |
13|0|1|1|1|0|0|2| | | | | |1|1|1| | |1|1|2| | | | | |1|0|0|1|1|1|1|1|2| | | | | | | | | | | |2|1|1| | |3|1|
14|0|2| |2|1|1|3| | | | | |1|0|1| | | | | | | | |1|1|1|0|0|1| |1|0|0|1|3| | | | | |3|1|1| | | | | | | |1|0|
15|2|4| | | | | | | | | | |2|1|2| | |2|1|1|1|1| |1|0|0|1|2|3| |1|0|0|0|1| | | | | |1|0|1| |2|1|1|1| | |1|0|
16| | | |2|1|1|1|1| | | | | | | | | |2|0|0|0|1| |2|1|1|1| | | |1|0|0|0|1| | | | | |2|1|1|1|1|0|0|1|1|2|1|1|
17| | | |2|0|0|0|1| | | | | | | | | |1|0|0|0|1|1|2| | | | | | |2|1|1|1|2| | | | | | |1|0|0|0|1|1|1|0|1| | |
18| | | |3|1|1|1|1| | | |2|1|2| | | |2|1|0|0|0|0|1| | | | | | | | | | | | | | | | | |2|1|2|1|2| |2|1|2| | |
19| | | | | | | | | | | |1|0|2| | | | |3|2|2|2|3|2| | | | | | | | | | | | | | | | | | | | | | | | | | | | |
20| | | | | | | | | | | |1|0|2| | | | | | | | | | | | | | | | | | | | | |2|1|1|1| | | | | | | |2|1|3| |3|1|
21| | | | | | | | | | | |2|1|2| | | |2|2|2|2| | | | | | | | | | |2|1|1|1|1|0|0|1| | | | | | | |2|0|3| |3|0|
22| | | | | |2|1|1|3| | | | | | | | |2|0|0|1| | | | | | | | | | |1|0|0|1|1|2|2|2| | | | |3|2|1|2|1|3| |3|1|
23| | | | | |1|0|0|1|1|2| | | | | | |1|0|1|2| | | | | |2|2|2| | |1|1|1|3| | | | | | | | |1|0|0|1| | | | | |
24|1|2| | | |1|0|0|0|0|1| | | | | | |2|1|2| | | | | | |1|0|1| | | | | | | | | | | | | | |3|2|1|1|1|1|1|2| |
25|0|2| | | |2|1|0|0|0|1| | | |2|1|1|1| |2|1|2|2| | | |2|1|2| | | | | | | | | | | | | | | | |1|0|0|0|0|1|1|
26|0|2| | | | |1|0|1|1|2| | |2|1|0|0|1|1|1|0|0|1|1|3| | | | | | | | | | | | | | | | | | | | |3|1|0|0|0|0|0|
27|1|3| | | | |2|1|2| | |2|1|1|0|1|1|1|0|0|1|1|1|0|2| | | | | | | | | | | | | | | | | | | | | |1|0|1|1|1|0|
28| | | | | | | | | | | |1|0|0|0|1| |1|1|1|2| |2|1|2|1|2| | | | | | | | |2|1|1|1|1| | |1|1|2|2|1|0|1| |1|0|
29| | | | | | | | | |3|1|2|1|1|0|1| | | | | | | | |1|0|1|2|2|2|1| | |2|1|1|0|0|0|1| | |1|0|0|0|0|0|1|1|1|0|
30|1|1|2|1| | | | | |2|0|1| |1|1|1| | | | | | | | |3|1|0|0|0|0|1| |2|1|0|1|1|1|0|1| |2|1|0|0|1|2|2|1|0|0|0|
31|0|0|0|1| |2|1|2| |3|1|2|1|1|1| | | | | | | | | | |2|0|0|0|1|2| |1|0|0|2| |4|3|3| |1|0|0|1|2| | |2|1|1|0|
32|0|0|0|1| |1|0|1| | | |1|0|0|1|1| | | | | | | | | |2|0|0|0|1| |2|1|1|1|4| | | | | |1|2|1|2| |3|2|2| |3|2|
33|0|0|0|2| |2|0|1| | | |2|1|1|0|1| | | | | | | | | |2|1|1|0|1|1|1|0|1| | | | | | | | | | | | |1|0|1|2| | |
34|1|1|1|2| |1|0|1| | | | | |2|1|2| | | | | | | | | | | |1|0|0|0|0|1|2| | | | | |3|1|1|2| | | |1|0|0|2| | |
35| | | | | |2|0|2| | | | | | | | | | | | | | |3|1|1|1|1|1|0|0|0|0|2| | | | | | |2|0|0|1| | | |1|0|1|3| | |
36|1|1|2| | |1|0|3| | |2|1|1| | | | | | | |1|1|1|0|0|0|1|1|1|0|0|0|2| | | | | | |2|1|0|1| | | |1|0|1| | | |
37|0|0|1|2|2|1|0|2| | |2|0|1| | | | | | | |1|0|0|0|0|1|3| |3|1|1|0|1|1| | | | | | |1|0|1| | | |1|1|1|2|2|1|
38|0|0|0|0|1|2|2|2| | |2|2|4| | | | | | | |1|0|0|0|0|1| | | | |1|0|0|1| | | | | |2|1|0|1| | | | | | |1|0|0|
39|1|1|1|1|2| | |2|1|1|2| | | | | | | | |3|1|0|0|0|1|2| | | |1|1|0|0|1| | | |3|1|1|0|1|2| | | | | | |2|2|2|
40| | | | | | | |2|0|0|1|2|4| | | | | | |2|0|1|1|1|1| | | | |1|0|0|0|2| | | |1|0|1|1|2| | | | | |2|1|2| | |
41|1|1|2| | | | |2|1|0|0|0|2| | | | | |3|1|0|1| |1|1|1|2| | |1|0|1|1|2| | | |1|0|1| | | | | | | |2|0|1| | |
42|0|0|1| | | | | |1|0|0|0|1| |2|1|1| |1|0|0|1|1|1|0|0|1|3| |2|0|1| | | | | |1|1|2| | | | | | | |1|0|1| | |
43|1|1|2| | | | | |2|2|2|1|1|1|1|0|1|1|2|1|1|0|1|1|1|0|0|2| |2|0|1|1|1|1| | | | | | | | | | | | |2|1|2| | |
44| | | | | | | | | | | |1|0|1|1|1|0|1| | |1|0|1| |1|1|1|3| |2|0|0|0|0|1|1| | | | |2|2|2|3| | | | | | | | |
45|1|1| | | | | | | | | |2|0|2| |2|0|1| | |1|0|2| | | | |2|1|2|1|1|0|0|0|1| | | | |1|0|0|1| | | | | | | | |
46|0|1| | | | | | | | | |1|0|2| |2|0|1| | |1|1|2| | | | |2|0|1| |1|1|1|1|1| | | | |2|0|1|2|2|1|1|2| | | | |
47|0|2| | | | | | |2|2|3|2|1|2| |3|1|1| | | | | | | | | |1|1|2|2|1|1| |1|1|1|1|1| |1|0|1| |1|0|0|1|3| | | |
48|0|3| | | | | | |2|0|1| | | | | | | | | | | | | |1|1|1|1|1| |1|0|1|1|1|0|0|0|2| |2|1|2| |2|1|0|0|3| | | |
49|0|2| | | | | | |2|0|1| | | | | | | | | | | | | |1|0|0|0|1| |1|0|0|0|0|0|0|0|1| | | | | | |1|0|0|2| | | |
	 *
	 *
	 */

	/*
	 * List Bug :
	 *
	 * 04- Note : La jonction entre les lignes 99 et 100 est d�geu (laiss� pour compte atm)
	 * 	 -> A FIX : rajouter condition si h<=x chiffres rajouter 1, 2, .. zero au num de ligne
	 *
	 * 15-Revelation bug:
	 *   -> cf 15prime
	 * 15prime-Lorsque je rentre la coordonn�e d'une mine les commandes ne fonctionnent plus (enter, nothing, enter,..)
	 		  -> Si while ou doWhile ou forloop dans le code (pour reit�rer le parcours de toute la grille si au moins
	 		  	 une case a �t� r�v�l�e)
	 Tip your move:
	 r15H
	 35
  |A|B|C|D|E|F|G|H|I|J|K|L|M|N|O|P|Q|R|S|T|
00| | | | | | | | | | | | | | | | | | | | |
01| | | | | | | | | | | | | | | | | | | | |
02| | | | | | | | | | | | | | | | | | | | |
03| | | | | | | | | | | | | | | | | | | | |
04| | | | | | | | | | | | | | | | | | | | |
05| | | | | | | | | | | | | | | | | | | | |
06|1|1| | | | | | | | | | | | | | | | | | |
07|0|1| | | | | | | | | | | | | | | | | | |
08|1|2| | | | | | | | | | | | | | | | | | |
09| | | | | | | | | | | | | | | | | | | | |
10| | | | | | | | | | | | | | | | | | | | |
11| | | | | | | | | | | | | | | | | | | | |
12| | | | | | | | | | | | | | | | | | | | |
13| | | | | | | | | | | | | | | | | | | | |
14| | | | | | |0|1|2| | | | | | | | | | | |
15| | | | | |0|0|0|0|0|0|1| | | | | | | | | -> Manque des r�v�lations de voisins de 0
16| | | | |2|1|1|1|0|0|1|2| | | | | | | | |	   qui ne sont pas des 0 et pas user's ask
17| | | | | | | |2|0|0|1| | | | | | | | | |	   qui sont les 'voisins du dessus' des 0 sous revelation
18| | | | | | | |2|0|1|2| | | | | | | | | |	   |-> pb solved by adding a while loop to reiterate the whole parcours
19| | | | | | | |1|0|1| | | | | | | | | | |	   |-> cause infinite loop unsolved
	 *
	 *pour :
12| | | | | |2|1|2| | | | | | | | | | | | |
13| | | | | |1|0|1| | | | | | | | | | | | | -> ce 0 a fait r�v�ler ses voisins
14| | | | |2|1|0|1|2|2|1|1|1| | | | | | | |
15| | | | |1|0|0|0|0|0|0|1| | | | | | | | | -> ces 0 non
16| | | | |2|1|1|1|0|0|1|2| | | | | | | | |
17| | | | | | | |2|0|0|1| | | | | | | | | |
18| | | | | | | |2|0|1|2| | | | | | | | | |
19| | | | | | | |1|0|1| | | | | | | | | | |
	 */

	//
	// Exercice 5 bonus challenge : Pour aller plus loin
	//

	//BONUS : enregistre les input machine (ce que fait l''IA') sur un txt

	/*
	 * Diff�rence en joueur machine et IA:
	 * -> faut jouer au d�mineur bcp (j'ai avec Maeto j'ai les meilleures moves � faire selon certaines situations)
	 * -> une ia jouerait et apprendrait les meilleures moves
	 * -> on veut diriger la machine pour lui donner les meilleurs moves � faire en code
	 */


	// Question 5.a] Optionnel
	public static void aide() {

	}

	// Question 5.b] Optionnel
	public static void intelligenceArtificielle() {

	}

	// Question 5.c] Optionnel
	public static void statistiquesVictoiresIA() {

	}

}

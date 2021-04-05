// Ceci est un squelette √† REMPLIR pour le projet INF1 sur le jeu de d√©mineur
//
// - N'oubliez pas de renseigner vos deux noms
// Pr√©nom Nom Groupe : Florian EPAIN MA4
// Pr√©nom Nom Groupe : -1Salma 
//
// - Pour chaque question, le squelette donne le nom de la fonction √† √©crire mais *pas* la signature
//   il faut remplir les types d'entr√©es et de sorties (indiqu√©s par ?) et remplir l'int√©rieur du code de chaque fonction.
//
// - L'unique fichier de code que vous soumettrez sera ce fichier Java, donc n'h√©sitez pas √† le commenter abondamment.
//   inutile d'exporter votre projet comme archive Zip et de rendre ce zip.
//   Optionnel : vous pouvez aussi joindre un document PDF donnant des explications suppl√©mentaires (si vous utilisez OpenOffice/LibreOffice/Word, exportez le document en PDF), avec √©ventuellement des captures d'√©cran montrant des √©tapes affich√©es dans la console
//
// - Regardez en ligne sur le Moodle pour le reste des consignes, et dans le fichier PDF du sujet du projet
//   https://foad.univ-rennes1.fr/mod/assign/view.php?id=534254
//
// - A rendre avant le vendredi 04 d√©cembre, maximum 23h59.
//
// - ATTENTION Le projet est assez long, ne commencez pas au dernier moment !
//
// - Enfin, n'h√©sitez pas √† contacter l'√©quipe p√©dagogique, en posant une question sur le forum du Moodle, si quelque chose n'est pas clair.
//

// Pour utiliser des scanners pour lire des entr√©es depuis le clavier
// utilis√©s en questions 4.d] pour la fonction jeu()
import java.util.Scanner;

// Pour la fonction entierAleatoire(a, b) que l'on vous donne ci-dessous
import java.util.concurrent.ThreadLocalRandom;

// L'unique classe de votre projet
public class patchTest {

	static int[][] T;
	static int[][] Tadj;
	
	public static int entierAleatoire(int a, int b){
		// Renvoie un entier al√©atoire uniforme entre a (inclus) et b (inclus).
		return ThreadLocalRandom.current().nextInt(a, b + 1);
	}
	
	public static void init() {
		int h=20, l=20, n=10;
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
	
	public static void testConversionCoordonnee() {
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
	}
	
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
	
	public static void testAfficherGrille(boolean affMines) {
		System.out.print("  ");
			
			for(int c=0; c<T[0].length; c++) { //On a retirer la premiere forloop (rep lines) car l'alphabet est a T[0]
				System.out.print("|");
				int col=c+65;		//Les 26premieres lettres (en majuscules), lorsque c=0, col=65 (=='A')
				if(col>90) {		//== c>25, (‡ c=26 la condition==true), lorsque c=25, col=90 (=='Z')
					col+=6;			//on saute les valeurs ASCII 91 ‡ 96 (inclus), pour les arriver aux minuscules
				}
				char b=(char)(col);
				System.out.print(b);	//52col au max!
			}
			System.out.println();
		
		for(int i=0; i<T.length; i++) {
			for(int j=0; j<T[i].length; j++) {
					
				if(j==0) {
					if(i<10)
						System.out.print("0");
					System.out.print(i);
				}
				System.out.print("|");
				if(T[i][j]==0)
					System.out.print(" ");
				else if(T[i][j]==2)
					System.out.print("X");
				else if(T[i][j]==1)
					System.out.print(Tadj[i][j]);
				else if(affMines && Tadj[i][j]==-1)
					System.out.print("!");
				
				//System.out.print("|");
			
			}
		System.out.println();
		}

	}
	
	
	/*
	 * List Bug Fixed (main):
	 * 
	 * 1-condition du While quiproquo (fonction main) :
	 *  ->FIXED : interchanger h et l dans la condition l!=nb de lignes et h!=nb de colonnes
	 * 
	 * 2-Grille dÈgeu (fonction afficheGrille)
	 *  ->FIXED : just un "| " au dÈbut pas "| |"
	 * 3-Grille dÈgeu 2: la premiere ligne (Alphabet) est rÈpÈtÈe
	 *  ->FIXED : retirer une forloop en trop et changer T[i].length en T[0].length
	 * 4-Note : La jonction entre les lignes 99 et 100 est dÈgeu (laissÈ pour compte atm)
	 * 
	 * 5-INFINITE LOOP : Affiche la grille en loop sans ask user (user's first move : d05H, r05H)
	 *  ->FIXED : ask=true; ‡ la fin de la 1ere WhileLoop de la fonction Jeu
	 * 
	 * 6-lorsque User a perdu, la grille n'affiche pas les mines:
	 * 	->FIXED mais pose pb en graphisme
	 * 
	 * 7-l'action r (de rÈvÈler une case) ne focntionne pas:
	 *  ->fonction revelerCase ne change pas l'etat de la case en question
	 *  ->revelerCase appelle revelation qui change l'etat de la case en rÈvÈlÈe
	 *  -> FIXED : change if(Tadj[i][j]!=-1) to if(Tadj[i][j]==-1) (revelerCase function)
	 *  
	 * 8-Lorsque un 0 est rÈvÈlÈe revelation ne fait pas son job
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
	 *  ->(faux) FIX1 : unmerge on condition caseAdjacenteZero (caseCorrecte && coordonnÈe de la case ‡ tester)
	 *  |-> en deux if imbriquÈ
	 * -n'importe quel coordonnÈe donnÈe par User se retrouve i=-1 et j=-1 ‡ caseCorrect 
	 *  |-> implique un pb dans le return de caseCorrecte
	 * 	->FIX2 : arranger l'ordre des condition && dans le return de caseCorrecte (FIX1 ne fix rien)
	 * 
	 * 10- 1bombes set up : (fonction revelation)
	      |A|B|C|D|E|
		00|0|0|0|1| |
		01|0|0|0|1| | -> Manque la revelation d'un zÈro
		02|0|0|0|0|0| 	|-> FIXED : <= pour les conditions des 2forloop (fonction caseAdjacenteZero)
		03|0|0|0|0|0|
		04|0|0|0|0|0|
	 *  
	 *  11- MÍme set up (fonction calculerAdjacent)
	 *  r1E
			Perdu!
  		  |A|B|C|D|E|
		00|0|0|0|1| |
		01|0|0|0|1|!|
		02|0|0|0|0|0| 11prime-> Mauvais compte caseAdjacent
		03|0|0|0|0|0| 		|-> FIXED : <= pour les conditions des 2forloop
		04|0|0|0|0|0| 11second-> Le jeu continue ‡ la mort ou ‡ la win
							 |-> FIXED : && au lieu de || pour la condition du 1er whileLoop (fonction Jeu)
	 *  
	 	  |A|B|C|D|E|F|G|H|I|J|
		00| | |0|0|0|0|0|0|0|0|
		01| !| !|0|0|0|0|1|1|0|0|11third/6-> deux instructions (afficheGrille car if puis if)==(" "+"!")
		02|0|0|0|0|0|1|2| !|0|0|		 |-> FIXED : if(T[i][j]==0 && (!affMines||Tadj[i][j]!=-1)) en condition
		03|0|0|0|0|0|1| !|0|0|0| 11prime-> les cases en dessous des bombes ne reÁoivent pas la modif de calculerAdjacent
		04|0|0|0|0|0|0|0|0|0|0|		   |-> FIXED
		05|0|0|0|0|0|0|1|1|0|0|
		06|0|0|0|0|0|0|1| !|0|0|
		07|0|0|0|0|0|0|0|0|0|0|
		08|0|0|0|0|0|0|0|0|0|0|
		09|0|0|0|0|0|0|0|0|0|0|
	 *  
	 * 12-Affiche un zÈro sur la grille postmove (‡ la case [0][0]) (lors de la infinite loop fixed)
	 *    -> Smell like FIXED
	 *  
	 * 13-PLANTAGE : une fois sur deux, altÈrnÈe propre wtf, plante (avec n'importe quel h, l et n)

Exception in thread "main" java.lang.ArrayIndexOutOfBoundsException: Index -1 out of bounds for length 20 (le 20=h)
	at projet_demineur.caseCorrecte(projet_demineur.java:78)
	at projet_demineur.calculerAdjacent(projet_demineur.java:97)
	at projet_demineur.main(projet_demineur.java:495)
	
	 *  -> calculerAdjacent
	 *  	Imbriquer deux if plutÙt que de les fusionner (faute a l'erreur si la case n'existe pas)
	 * 		|->putting the fonction caseCorrecte first with && we skiping the error on Tadj[i+c1][j+c2]
	 * 		if the case doesn't exist
	 *  -> caseCorrecte
	 *  	T[i] ?
	 *  |-> les deux prÈcÈdente hypothËses ont ÈtÈ arragÈes pour d'autres bug.
	 *  -> le Bug ne semble plus d'actualitÈ
	 *
	 * 
	 * 14-TombÈ dans la hutte de madame Irma - trop de revelation (fonction revelation1)
	 *   -> la fonction revelation revele TOUS les zÈro
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
	 * 04- Note : La jonction entre les lignes 99 et 100 est dÈgeu (laissÈ pour compte atm)
	 * 	 -> A FIX : rajouter condition si h<=x chiffres rajouter 1, 2, .. zero au num de ligne
	 * 
	 * 15-Revelation bug:
	 *   -> 
	 * 15prime-Lorsque je rentre la coordonnÈe d'une mine les commandes ne fonctionnent plus (enter, nothing, enter,..)
	 		  -> Si while ou doWhile ou forloop dans le code (pour reitÈrer le parcours de toute la grille si au moins
	 		  	 une case a ÈtÈ rÈvÈlÈe)
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
15| | | | | |0|0|0|0|0|0|1| | | | | | | | | -> Manque des rÈvÈlations de voisins de 0 
16| | | | |2|1|1|1|0|0|1|2| | | | | | | | |	   qui ne sont pas des 0 et pas user's ask
17| | | | | | | |2|0|0|1| | | | | | | | | |
18| | | | | | | |2|0|1|2| | | | | | | | | |
19| | | | | | | |1|0|1| | | | | | | | | | |
	 *
	 *pour :
12| | | | | |2|1|2| | | | | | | | | | | | |
13| | | | | |1|0|1| | | | | | | | | | | | | -> ce 0 a fait rÈvÈler ses voisins
14| | | | |2|1|0|1|2|2|1|1|1| | | | | | | |
15| | | | |1|0|0|0|0|0|0|1| | | | | | | | | -> ces 0 non
16| | | | |2|1|1|1|0|0|1|2| | | | | | | | |
17| | | | | | | |2|0|0|1| | | | | | | | | |
18| | | | | | | |2|0|1|2| | | | | | | | | |
19| | | | | | | |1|0|1| | | | | | | | | | |
	 */
	
}

package servlet;

import java.io.IOException;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import BD.BDUser;
import api.Users;

/**
 * Servlet implementation class GetProfil
 */
public class GetProfil extends HttpServlet {
	private static final long serialVersionUID = 1L;
	public static final String CHAMP_LOGIN = "login";
	public static final String CHAMP_NOM = "nom";
	public static final String CHAMP_PRENOM = "prenom";
	public static final String CHAMP_USER_PROFIL = "user_profil";
	public static final String ATT_RESULTAT = "resultat";
	public static final String FWD_PROFIL = "/profil.jsp";
	public static final String FWD_ACCUEIL = "/index.jsp";

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public GetProfil() {
		super();
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String resultat="";
		String liste_amis="";
		HashMap<String, String> infos= new HashMap<String, String>();

		/* Récupération des champs du formulaire. */
		String login = request.getParameter( CHAMP_LOGIN );		// ce champs est composé du prenom et nom de l'utilsateur
		String[] patronyme = login.split(" ");

		try {
			infos = BDUser.getInfoUser(patronyme[1], patronyme[0]);
		} catch (Exception e) {
			resultat= e.getMessage();
		}

		/* Stockage du résultat et des messages d'erreur dans l'objet request */
		request.setAttribute( ATT_RESULTAT, resultat );
		request.setAttribute( CHAMP_LOGIN, infos.get("login"));
		request.setAttribute(CHAMP_NOM, infos.get("nom"));
		request.setAttribute(CHAMP_PRENOM, infos.get("prenom"));
		request.setAttribute(CHAMP_USER_PROFIL, login);

		/*
		 <table name="zone_recherche" id="zone_recherche">
					<tr>
						<td><font color="white">Nom : ${nom}</font></td>
					</tr>
					<tr>
						<td><font color="white">Prénom : ${prenom}</font></td>
					</tr>
					<tr>
						<td><font color="white">Login : ${login}</font></td>
					</tr>
				</table>
		 */
		if(resultat.length()==0){		//	enregistrement effectuE sans erreurs
			System.out.println("GetProfil.doPost : chargement du profil de "+login);
			
			/* Transmission de la paire d'objets request/response à notre JSP */
			this.getServletContext().getRequestDispatcher( FWD_PROFIL ).forward( request, response );
		}else{
			System.out.println("GetProfil.doPost : "+resultat);

			/* Transmission de la paire d'objets request/response à notre JSP */
			this.getServletContext().getRequestDispatcher( FWD_ACCUEIL ).forward( request, response );
		}

	}

}

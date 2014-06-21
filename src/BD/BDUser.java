package BD;

import java.net.UnknownHostException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.Mongo;
import com.mongodb.MongoException;

import exceptions.EndOfSessionException;
import exceptions.KeyUndefinedException;

public class BDUser {

	// renvoie vrai si l'utilisateur existe, faux sinon 
	public static boolean userExists(String login){
		DB db=null;

		try {
			db = DBStatic.getMongoDb();

			DBCollection collection = db.getCollection("users");	// selection de la collection voulue
			BasicDBObject searchQuery = new BasicDBObject("login", login);	// creation du champs recherchE
			DBCursor cursor = collection.find(searchQuery);		// recherche du champs au sein de la base

			if (cursor.count() != 0){
				db.getMongo().close();
				return true;
			}

		} catch (UnknownHostException e) {
			db.getMongo().close();
			e.printStackTrace();
		}
		finally{	// fermeture de la connection
			try{
				if (db!=null){
					db.getMongo().close();
				}
			}catch (Exception e){
				e.printStackTrace();
			}
		}

		return false;
	}

	//insert un utilisateur à la BD
	public static void insertUser(String prenom,String nom,String login,String password) throws Exception{
		DB db=null;

		try {
			db = DBStatic.getMongoDb();

			DBCollection collection = db.getCollection("users");	// selection de la collection voulue
			BasicDBObject user = new BasicDBObject("nom", nom).append("prenom", prenom).append("login", login).append("password", password);	// creation de l'objet A insErer
			collection.insert(user);	// insertion du nouvel utilisateur

			db.getMongo().close();	// fermeture de la connection

		} catch (UnknownHostException e) {
			throw new Exception("Probleme serveur. Veuillez refaire votre demande");
		}
		finally{	// fermeture de la connection
			try{
				if (db!=null){
					db.getMongo().close();
				}
			}catch (Exception e){
				throw new Exception("Probleme serveur. Veuillez refaire votre demande");
			}
		}
	}

	// renvoie toutes les informations relatives a un utilisateur donnE
	public static HashMap<String, String> getInfoUser(String login) throws Exception{
		DB db=null;
		HashMap<String, String> map= new HashMap<String, String>();

		try {
			db = DBStatic.getMongoDb();

			DBCollection collection = db.getCollection("users");	// selection de la collection voulue
			BasicDBObject searchQuery = new BasicDBObject("login", login);	// creation du champs recherchE
			DBCursor cursor = collection.find(searchQuery);		// recherche du champs au sein de la base

			if (cursor.count() == 0){
				db.getMongo().close();
				throw new Exception("Probleme serveur. Veuillez refaire votre demande");
			}

			// transformation des donnEes en map
			List<DBObject> tab = cursor.toArray();	
			DBObject dbObject = tab.get(0);
			Set<String> keys = dbObject.keySet();
			for (String key : keys) {
				map.put(key, dbObject.get(key).toString());
			}

		} catch (UnknownHostException e) {
			db.getMongo().close();
			throw new Exception("Probleme serveur. Veuillez refaire votre demande");
		}
		finally{	// fermeture de la connection
			try{
				if (db!=null){
					db.getMongo().close();
				}
			}catch (Exception e){
				throw new Exception("Probleme serveur. Veuillez refaire votre demande");
			}
		}

		return map;
	}

	//renvoie vrai si le login correspond au password, faux sinon 
	public static boolean verifLoginPassword(String login,String password){
		DB db=null;
		boolean result = false;

		try {
			db = DBStatic.getMongoDb();

			DBCollection collection = db.getCollection("users");	// selection de la collection voulue
			BasicDBObject searchQuery = new BasicDBObject("login", login).append("password", password);	// creation du champs recherchE
			DBCursor cursor = collection.find(searchQuery);		// recherche du champs au sein de la base

			if (cursor.count() != 0){
				result= true;
			}

		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		finally{	// fermeture de la connection
			try{
				if (db!=null){
					db.getMongo().close();
				}
			}catch (Exception e){
				e.printStackTrace();
			}
		}

		System.out.println(result?"BDUser.verifLoginPassword : User found ":"BDUser.verifLoginPassword : User not found ");

		return result;
	}

	//ajout d'un ami A l'aide de son id 
	public static void addFriend(String key, String id_friend) {
		//		Connection c=null;
		//		Statement st=null;
		//		try {
		//			int id_user=getUserByKey(key); //soit on fait une exception au lieu de renvoyer -1 dans la fonction, soit on crée une exception dans cette fct comme suit
		//			if (sessionPerimee(key)||(id_user==-1)){//on crée une erreur pour sortir direct et fermer la connection avec finally
		//				throw new EndOfSessionException("Session terminée"); 
		//			}
		//			Class.forName("com.mysql.jdbc.driver");
		//			c = DBStatic.getMysqlConnection();
		//			st = c.createStatement();
		//			st.executeUpdate("INSERT into Friends values("+id_user+","+id_friend+")");
		//		} catch (ClassNotFoundException e) {
		//			e.printStackTrace();
		//		} catch (SQLException e) {
		//			e.printStackTrace();
		//		} catch (KeyUndefinedException e) {
		//			e.printStackTrace();
		//		} catch (EndOfSessionException e) {
		//			e.printStackTrace();
		//		}catch (Exception e){
		//			e.printStackTrace();
		//		}
		//		finally{
		//			try {
		//				st.close();
		//				c.close();
		//			} catch (SQLException e) {
		//				e.printStackTrace();
		//			}
		//		}
	}

	//renvoie vrai si la session est périmée, sinon faux
	private static boolean sessionPerimee(String key) {
		//		Connection c=null;
		//		Statement st=null;
		//		try {
		//			Class.forName("com.mysql.jdbc.driver");
		//			c = DBStatic.getMysqlConnection();
		//			st = c.createStatement();
		//			int id_user = getUserByKey(key);
		//			ResultSet r = st.executeQuery("SELECT * FROM Sessions WHERE id_user_key="+id_user+" AND peremption<NOW()");
		//			if (!r.next()){
		//				throw new EndOfSessionException("Session valide introuvable pour cet utilisateur");
		//			}
		//			else{
		//				st.close();
		//				c.close();
		//				return true;
		//			}
		//		} catch (ClassNotFoundException e) {
		//			e.printStackTrace();
		//		} catch (SQLException e) {
		//			e.printStackTrace();
		//		} catch (KeyUndefinedException e) {
		//			e.printStackTrace();
		//		} catch (EndOfSessionException e) {
		//			e.printStackTrace();
		//		}catch (Exception e){
		//			e.printStackTrace();
		//		}
		//		finally{
		//			try {
		//				st.close();
		//				c.close();
		//			} catch (SQLException e) {
		//				e.printStackTrace();
		//			}
		//		}
		return false;
	}

	//renvoie une exception si elle ne trouve pas la clé, sinon renvoie l'id associé à la clé
	private static int getUserByKey(String key) throws KeyUndefinedException {
		//		Connection c=null;
		//		Statement st=null;
		//		try {
		//			Class.forName("com.mysql.jdbc.driver");
		//			c = DBStatic.getMysqlConnection();
		//			st = c.createStatement();
		//			ResultSet r = st.executeQuery("SELECT * FROM Users WHERE key="+key);
		//			if (!r.next()){
		//				st.close();
		//				c.close();
		//				throw new KeyUndefinedException("Cles introuvable");
		//			}
		//			else{
		//				st.close();
		//				c.close();
		//				return new Integer(r.getString("id")).intValue();
		//			}
		//		} catch (ClassNotFoundException e) {
		//			e.printStackTrace();
		//		} catch (SQLException e) {
		//			e.printStackTrace();
		//		}catch (Exception e){
		//			e.printStackTrace();
		//		}
		//		finally{
		//			try {
		//				st.close();
		//				c.close();
		//			} catch (SQLException e) {
		//				e.printStackTrace();
		//			}
		//		}
		return -1;
	}

	public static void removeFriend(String key, String id_friend) {
		//		Connection c=null;
		//		Statement st=null;
		//		try {
		//			int id_user=getUserByKey(key);
		//			if (sessionPerimee(key)||(id_user==-1)){//on crée une erreur pour sortir directement et fermer la connection avec finally
		//				throw new EndOfSessionException("Session terminee"); 
		//			}
		//			Class.forName("com.mysql.jdbc.driver");
		//			c = DBStatic.getMysqlConnection();
		//			st = c.createStatement();
		//			st.executeUpdate("DELETE FROM Friends WHERE id_from="+id_user+" AND id_to="+id_friend);
		//		} catch (ClassNotFoundException e) {
		//			e.printStackTrace();
		//		} catch (SQLException e) {
		//			e.printStackTrace();
		//		} catch (KeyUndefinedException e) {
		//			e.printStackTrace();
		//		} catch (EndOfSessionException e) {
		//			e.printStackTrace();
		//		}catch (Exception e){
		//			e.printStackTrace();
		//		}
		//		finally{
		//			try {
		//				st.close();
		//				c.close();
		//			} catch (SQLException e) {
		//				e.printStackTrace();
		//			}
		//		}
	}

	//ajout d'un commentaire à la base NOSQL
	public static void addcomment(String key, String text) {
		try {
			int id=getUserByKey(key); 
			if (sessionPerimee(key)||(id==-1)){//on crée une erreur pour sortir directement
				throw new EndOfSessionException("Session terminee");
			}
			Mongo m = new Mongo("132.227.201.134",27017);
			DB db = m.getDB("li328");
			DBCollection coll = db.getCollection("binome10");
			BasicDBObject com = new BasicDBObject();
			com.put("auteur", id);
			com.put("texte", text);
			GregorianCalendar cal = new GregorianCalendar();
			Date d = cal.getTime();
			com.put("date", d);
			coll.insert(com);
		}catch (EndOfSessionException e){
			e.printStackTrace();
		}catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (MongoException e) {
			e.printStackTrace();
		}catch (Exception e){
			e.printStackTrace();
		}
	}
	/*
	//recherche de commentaires par mots clés !
	public static String search(String key, String query, String friends) {//friends = 1 si cochée, sinon 0
		String result="";
		int id_user;
		int ami = new Integer(friends).intValue();

		try {
			Mongo mongo = new Mongo("132.227.201.134",27017);
			DB db = mongo.getDB("li328");
			DBCollection collection = db.getCollection("binome10");
			BasicDBObject doc;

			if ((key=="")&&(query=="")&&(friends=="")){		//si search sans paramétre on affiche les commentaires de moins d'une heure
				GregorianCalendar calendar = new java.util.GregorianCalendar();
				calendar.add(Calendar.HOUR,-1);
				java.util.Date date = calendar.getTime();
				doc = new BasicDBObject();
				doc.put("date", new BasicDBObject("$gt",date));
				DBCursor curseur =collection.find(doc);
				while (curseur.hasNext())
					result+=curseur.next();
			}

			id_user = getUserByKey(key);
			if (sessionPerimee(key)||(id_user==-1)){		//on crée une erreur pour sortir directement
				throw new EndOfSessionException("Session terminée");
			}

			doc = new BasicDBObject();
			doc.put("auteur", id_user);		//tous les commentaires de id

			if((query=="")||(query==null)){		// recherche sans mots clés

				if (ami==0){			//recherche sans tri par ami et sans mots clés 
					DBCursor curseur = collection.find(doc);
					while (curseur.hasNext())
						result+=curseur.next();

				}else{				//recherche par tri d'amis et sans mots clés

					//connection à la base SQL et récupération de la liste des amis de l'utilisateur
					Class.forName("com.mysql.jdbc.driver");
					Connection connexion = DBStatic.getMysqlConnection();
					Statement statement = connexion.createStatement();	
					ResultSet listAmi = statement.executeQuery("SELECT * FROM Friends WHERE id_user_key="+id_user);
					if (!listAmi.next()){
						throw new EndOfSessionException("Liste d'amis introuvable");
					}
					else{
						statement.close();
						connexion.close();
					}	//liste d'amis obtenue 

					//recherche des comentaires avec la liste d'amis 
					doc = new BasicDBObject();
					ArrayList<Integer> list = new ArrayList<Integer>();		
					while (listAmi.next())
						list.add(new Integer(listAmi.toString()).intValue());
					doc.put("auteur", new BasicDBObject("$in",list));	//trie les commentaires par amis 

					DBCursor curseur = collection.find(doc);
					while (curseur.hasNext())
						result+=curseur.next();
				}


			}else{		// recherche avec mots clés


			}
		} catch (KeyUndefinedException e) {
			e.printStackTrace();
		} catch (EndOfSessionException e) {
			e.printStackTrace();
		} catch (Exception e){
			e.printStackTrace();
		}		//FAIRE UN FINALLY QUI RENVOIT UN CHAMP D'ERREUR <==============================

		return result;		// la BD NOSQL retourne le commentaire au type JSON et pas STRING comment faire alors ?
	}
	 */}

<html>

<head>
<link rel="stylesheet" title="kit" media="screen" type="text/css"
	href="style/index.css" />
<%
	//allow access only if session exists
	String user = null;
	String user_profil = null;
	if (session.getAttribute("user") == null) {
		response.sendRedirect("connexion.jsp");
	} else {
		user = (String) session.getAttribute("user"); // user <=> prenom nom
		user_profil = (String) request.getAttribute("user_profil");
	}
	String userName = null;
	String sessionID = null;
	Cookie[] cookies = request.getCookies();
	if (cookies != null) {
		for (Cookie cookie : cookies) {
			if (cookie.getName().equals("user"))
				userName = cookie.getValue();
			if (cookie.getName().equals("JSESSIONID"))
				sessionID = cookie.getValue();
		}
	} else {
		sessionID = session.getId();
	}
%>

<title><%=user_profil%></title>

<link rel="stylesheet" title="kit" media="screen" type="text/css"
	href="style/index.css" />
<style>
body {
	background: url(images/bg.png);
	margin: 0 auto;
}
</style>
<meta name="robots" content="noindex,follow" />
</head>

<body>

	<div id="header">

		<div id="logo">
			<a href="index.jsp"><img src="images/logo.png" alt="Logo" /></a>
		</div>
		<!-- zone de recherche avec les choix de recherche sur les diffÃ©rents sites !! -->
		<div id="recherche"></div>

		<%
			if (user == null) {
		%>
		<div id="liens">
			<span> <a href='connexion.jsp'>Connexion</a> / <a
				href='enregistrement.jsp'> Enregistrement </a>
			</span>
		</div>
		<%
			} else {
		%>
		<div id="liens">
			<span>
				<form action="<%=response.encodeURL("GetProfil")%>" method="post">
					<input type="hidden" name="login" value="<%=user%>" /> <input
						type="submit" value="<%=user%>">
				</form>
				<form action="<%=response.encodeURL("Logout")%>" method="post">
					<input type="submit" value="Logout">
				</form>
			</span>
		</div>
		<%
			}
		%>
	</div>

	<div id="page">
		<div id="stat">
			<span> <I> Informations personnelles : </I>
				<p></p>
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
			</span>

			<!-- listes d'amis -->
			<span> <I> Liste d'amis : </I>
				<p></p> 
				${liste_amis}
			</span>
		</div>

		<div id="content">derniers commentaires postés</div>
	</div>

</body>
</html>
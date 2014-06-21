
<html>

<head>

<title>Page de connexion</title>

<link rel="stylesheet" href="style/connexion.css" media="screen">
<style>
body {
	background: url(images/bg.png) center;
	margin: 0 auto;
	width: 960px;
	padding-top: 50px
}

.footer {
	margin-top: 50px;
	text-align: center;
	color: #666;
	font: bold 14px Arial
}

.footer a {
	color: #999;
	text-decoration: none
}

.login-form {
	margin: 50px auto;
}
</style>
<meta name="robots" content="noindex,follow" />
<script type="text/javascript" src="script/connexion.js"></script>
<script type="text/javascript" src="script/jquery.js"></script>

</head>

<body>

	<div class="login-form">

		<h1>Ouvrir une session</h1>

		<form method="post" action="ConnectUser">
			<!-- onSubmit="javascript:connecte(this)"> -->
			<!-- "javascript:(function(){return;})()" -->
			<div id="formulaire" name="formulaire">
			<div  id="erreur" color="green">${resultat}</div>
				<input type="text" name="username" placeholder="Username"/> 
				<input type="password" name="password" placeholder="Password" /> 
				<input type="submit" value="Connexion" onClick="javascript:if(connecte(document.formulaire)){document.form.submit();}"/> 
				<input type="button" value="Annuler" onClick="window.location.href='index.jsp'" />

			</div>
		</form>
	</div>
</body>
</html>
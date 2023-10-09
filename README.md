API de Gestion de Produits avec Spring Boot

Aperçu :

Il s'agit d'une API REST pour la gestion des informations de produits, développée en utilisant Java et Spring Boot.
Elle permet aux utilisateurs d'effectuer diverses opérations liées à la gestion des produits,
notamment la création de nouveaux produits, la récupération des détails des produits,
la mise à jour des informations sur les produits et la suppression des produits.

Fonctionnalités : 

Créer un Produit : Créer un nouveau produit avec des détails spécifiques (on suppose que le nom d'un produit est unique dans la BDD,
et que le code d'un produit est une chaine de caractère unique dans la BDD composer de 9 caractères).
Obtenir un Produit : Récupérer les informations sur un produit en fonction de son ID.
Mettre à Jour un Produit : Modifier les détails d'un produit (on suppose que le nom d'un produit est unique dans la BDD,
et que le code d'un produit est une chaine de caractère unique dans la BDD composer de 9 caractères).
Supprimer un Produit : Supprimer un produit du système.
Lister les Produits : Afficher une liste de tous les produits.

Authentification :

Pour récupérer un accessToken, vous devez fournir les paramètres suivants dans le DTO JwtTokenRequestDto :
    grantType = "password" pour s'authentifier ou "refreshToken" pour récupérer un nouveau accessToken en fournissant un RefreshToken valid dans le champs refreshToken.
    username = "user" ou "admin"
    password = "1234"
    withRefreshToken = true pour obtenir un refreshToken.
    refreshToken : Si vous fournissez un refreshToken, vous pouvez obtenir un nouveau accessToken.

Authentification et Gestion des Rôles

Lorsque vous lancez l'application pour la première fois, deux rôles sont créés : "ADMIN" et "USER".
Deux utilisateurs sont également créés pour tester l'application :
    Un utilisateur avec le rôle USER, identifiant "user" et mot de passe "1234".
    Un administrateur avec le rôle ADMIN, identifiant "admin" et mot de passe "1234".
Le rôle USER permet uniquement de consulter la liste de tous les produits et les détails d'un produit.
Le rôle ADMIN permet d'effectuer toutes les opérations précédentes, y compris la création d'un produit, la mise à jour d'un produit et la suppression d'un produit.

Technologies Utilisées : 

Java 17 : Langage de programmation orienté objet.
Spring Boot : Framework Java pour créer des applications web.
Spring Data JPA : Facilite l'accès aux bases de données relationnelles.
Hibernate : Framework de persistance pour Java.
MySQL : Système de gestion de base de données relationnelle.

Prérequis :

Java JDK installé sur votre machine.
Maven installé pour la gestion des dépendances.
XAMPP installé sur votre machine pour la gestion de la base de données.

Installation :

Clonez ce dépôt: https://github.com/ybouzourine/GestionDesProduits.git
Importez le projet dans votre IDE Java.

Pour tester et utiliser l'API REST :

Lancez le projet.
Accédez à l'interface Swagger en utilisant le lien suivant : http://localhost:8080/swagger-ui/index.html.
Vous y trouverez la documentation Swagger pour explorer et tester les fonctionnalités de l'API.

Gestion des Erreurs :

En cas d'erreur lors de l'appel à l'API, des messages d'erreur appropriés seront renvoyés avec les codes d'état HTTP correspondants.
Veuillez vous référer à la documentation des endpoints pour obtenir des détails sur les erreurs possibles.


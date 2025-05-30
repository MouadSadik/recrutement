# Projet Agence de Recrutement

## Description

Ce projet est une application desktop Java Swing pour une agence de recrutement.  
Elle permet aux entreprises de publier des offres, consulter et gérer les candidatures, et aux candidats de postuler et suivre leurs démarches.  

---

## Fonctionnalités principales

- **Pour les entreprises :**  
  - Publication, modification et suppression des offres d’emploi  
  - Consultation et filtrage des candidatures reçues  
  - Recrutement et suivi des candidats  
  - Visualisation de l’historique des recrutements  
  - Gestion du profil entreprise

- **Pour les candidats :**  
  - Inscription et gestion de profil  
  - Consultation des offres d’emploi disponibles  
  - Postulation en ligne avec CV et lettre de motivation  
  - Suivi du statut des candidatures  
  - Notifications des nouvelles offres correspondant au profil

---

## Capture d’écran

![Interface principale](./imgs/image.png)


---

## Technologies utilisées

- **Langage :** Java 17  
- **Interface graphique :** Java Swing  
- **Base de données :** PostgreSql
- **Connexion DB :** JDBC avec DAO Pattern  
- **Outils de gestion DB :** Supabase 
- **Gestion de versions :** Git  

---


## 🛢 Base de Données

- Tables principales : `client`, `entreprise`, `demandeur`, `journal`, `categorie`, `edition`, `abonnement`, `offre_emploi`, `postulation`, `recrutement`
- Clés étrangères et héritages respectés selon le diagramme UML fourni.

---

## 📦 Installation et Exécution

1. Cloner le projet :
   ```bash
         git clone https://github.com/MouadSadik/recrutement.git

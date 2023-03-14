<h2 align="center">Takenoko</h2>
<h3 align="center">PS5 - Projet de développement S5</h3>

- **L'équipe ayant réalisé ce projet est composé de :**

  - Arthur Rodriguez

  - Loic Pantano

  - Loris DRID

  - Guillaume Arrigoni


## Sommaire
1. [État du projet](#1-état-du-projet)
2. [Installation](#2-installation)
3. [Comment exécuter le programme](#3-comment-exécuter-le-programme)
4. [Comment exécuter les tests](#4-comment-exécuter-les-tests)
5. [Description et règle du jeu](#5-description-et-règle-du-jeu)
6. [Organisation du travail](#6-organisation-du-travail)

## 1. État du projet
Projet terminé, toutes les slices ont étaient développées. Le code lui reste tout de même perfectible. 

### 2. Installation
1. Clone the repo
   `git clone https://github.com/pns-si3-projects/projet2-ps5-22-23-takenoko-2023-t`
2. Build the project
   `mvn clean install`
3. Run the project
   `mvn exec:java`

## 3. Comment exécuter le programme
Pour exécuter le programme, il faut run la classe **[src/main/java/fr/cotedazur/univ/polytech/startingpoint/takenoko/Main.java](https://github.com/guillaumeArrigoni/School_Project_Takenoko/blob/master/src/main/java/fr/cotedazur/univ/polytech/startingpoint/takenoko/Main.java).**

<img src="https://github.com/guillaumeArrigoni/School_Project_Takenoko/blob/master/doc/Image/run_main.png" alt="exécuter le programme" width="350"/>

## 4. Comment exécuter les tests
Pour exécuter les programmes, il faut run les classes de test situé dans **[src/test/java/fr/cotedazur/univ/polytech/startingpoint/takenoko/*](https://github.com/guillaumeArrigoni/School_Project_Takenoko/tree/master/src/test/java/fr/cotedazur/univ/polytech/startingpoint/takenoko)**

<img src="https://github.com/guillaumeArrigoni/School_Project_Takenoko/blob/master/doc/Image/test.png" alt="exécuter les tests" width="350"/>

### 5. Description du projet :
Ce projet à pour but de simuler des partie du jeu Takenoko avec plusieurs bots.
Plusieurs paramètre peuvent être défini lors de l'exécution du programme : 
- --demo : permettant d'afficher toutes les actions des bots ainsi que la description du plateau.
- --2Thousand : permettant de simuler 2000 parties afin d'obtenir des statistiques sur le niveau de chaque bot
- --csv : permettant d'obtenir un récapitulatif des différentes parties qui ont été joué depuis l'implémentation du paramètre afin d'obtenir des statistiques encore plus précises

##### Description et règles du jeu :
Le but du jeu est de marquer le plus de point. 
Il se déroule en 2 étapes :
- Lancer du dès pour déterminer la météo
- Réalisation entre 2 et 3 actions selon la météo.

Au niveau des actions possibles, on a :
- Bouger le Panda
- Bouger le Jardinier
- Piocher et poser des tuiles terrain
- Prendre un jeton irrigation
- Prendre un contrat

Les contrats permettent de marquer les points.
Le joueur peut finir un contrat n'importe quand pendant son tour.
Le jeu s'arrète lorsque un des joueurs atteint :
- 9 contrats à 2 joueurs
- 7 contrats à 3 joueurs
- 5 contrats à 4 joueurs

Ce joueur gagne un bonus de 2 points.
Les joueurs finissent le tour puis comple leurs points. 

Pour plus de précision, une description détaillée du jeu peut être trouvé [ici](https://www.matagot.com/IMG/pdf/takenokoregle-2.pdf).

### 6. Organisation du travail :
Se référer à la page suivante : [RAPPORT-P.md](https://github.com/guillaumeArrigoni/School_Project_Takenoko/blob/master/doc/RAPPORT-P.md)

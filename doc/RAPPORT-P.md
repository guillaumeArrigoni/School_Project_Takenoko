## Point d'avancement :
### Fonctionnalités : 
- Un plateau (Board) pouvant contenir des tuiles (HexagoneBox) et des irrigations placés entre deux tuiles (Crest)
- Des tuiles pouvant contenir des bambous et un jeton spécial
- Pousse de bambou (en accord avec le jeton de la tuile)
- Le panda peut manger des bambous (en accord avec le jeton de la tuile et si des bambous existent sur la tuile)
- Le déplacement du jardinier
- Le déplacement du panda
- Prendre une irrigation
- Poser une irrigation
- Piocher un objectif
- Vérification des objectifs
- Lancer le dé et action correspondante à la face
- L'objectif bonus de 2 points au premier à finir le nombre d'objectifs fixé
- Une vérification :
	Lors du placement des tuiles (doit être placé à coté de deux tuiles)
	Lors du placement des irrigations (doit être relié à une autre irrigation ou au lac central)
	Lors du placement des jetons spéciaux (pas de bambou sur la tuile)
	Lors de la pousse de bambou (hauteur maximale de 4, la tuile doit être irriguée)
	Lors du retrait de bambou, c'est à dire lorsque le panda mange un bambou (la hauteur ne peut pas être négative, la tuile ne doit pas contenir de jeton "protection")

### Les logs : 
Toutes les classes relative au log sont accessibles dans le package "logger" à la racine du package "takenoko".
Ce package contient 6 classes : 
  - LoggerMain : classe principale, permettant la création du logger, de définir à l'aide de setter ses attributs et de créer un log.
  - FormatterForLog : permet de modifier l'affichage des logs pour les rendre plus clair. Cette classe override la méthode "format", utilisée dans la ConsoleHandler des logs afin de définir la couleur des logs, et les informations renvoyées.
  - Des classes qui étendent LoggerMain : 
    - LogInfoDemo : permet d'afficher les logs lors d'une partie (tour, actions ou objectifs réalisés, nombre de points, gagnant...)
    - LogInfoStats : permet d'afficher les logs après plusieurs simulations, ou les résultats des statistiques csv.
    - LoggerSevere : permet d'afficher toutes les Exception "thrown" mais "catch" afin d'éviter l'interruption de la partie. Ce sont toues les exceptions relatifs à des problème mineur étant donné qu'ils peuvent être corriger en cours de fonctionnement.
    - LoggerError : permet d'afficher toutes les Exceptions "majeures", ne pouvant pas, ou n'ayant pas été "catch".
Les différents logger peuvent tous être paramétrés pour s'afficher ou non à l'aide du paramètre "IsOn" déclaré à la création du log.
Ainsi, par exemple, la simulation "--thousand" affichera uniquement le logger LogInfoStats.

### Les statistiques en CSV :
La simulation "--csv" permet de lancer 100 parties et d'enregistrer les résultats dans un fichier csv. Ce fichier (stats.csv) contient le pourcentage de victoire de chaque bot, son score moyen ainsi que le nombre de parties qu'il a joué.
Tout cela est fait à l'aide de la classe CSVHandler et l'on procède ainsi :
  - On récupère le chemin du fichier stats.csv qu'importe le système d'exploitation (ou on le crée s'il n'existe pas)
  - Si le fichier est vide (ou vient d'être créé), on lance les 100 parties et on écrit les statistiques dans le fichier
  - Sinon, on lit le contenu du fichier, on en crée un autre temporaire (temp_stats.csv), dedans on écrit le résultat du calcul des nouvelles statistiques de chaque bot en prenant en comtpe le nombre de parties jouées, on supprime ensuite "stats.csv" et on renomme "temps_stats.csv" en "stats.csv".

### Bot spécifique :
Le bot spécifique demandé est capable de :
  - Récupérer un maximum de bambous, même s'il n'a pas de cartes de la couleur correspondante
  - Essayer d'avoir toujours 5 cartes objectifs en sa possession
  - Récupérer un maximum d'irrigation
  - Prendre un jeton spécial "Irrigation" dès qu'il le peut (donc notamment avec la face du dé "?")
  - Corréler plusieurs objectifs du même type afin de les réaliser d'une manière plus efficace (sauf en ce qui concerne les objectifs de type "Terrain", la méthode est créée, mais un bug relatif à l'irrigation n'a pas été résolu)
  - Surveiller les mouvements de ses adversaires afin d'essayer de saboter leurs réalisation des objectifs :
    - Il estime quel type d'objectif l'adversaire avec le plus de point est entrain de réaliser.
    - Il récupère la dernière action que cet adversaire à vraisemblablement pu effectuer pour tenter de réaliser cet objectif et il estime quelle action il doit effectuer pour saboter le plan de l'adversaire :
      - Un objectif de type "Terrain" : 
          Le dernier placement de tuile réalisé par cet adversaire
          Placer autour de cette tuile, une tuile d'une autre couleur
      - Un objectif de type "Jardinier" : 
          Le dernier déplacement du jardinier
          Déplacer le panda sur cette tuile ou sur une tuile autour de cette dernière de la même couleur
      - Un objectif de type "Panda" : 
          Le dernier déplacement du panda
          Déplacer le panda sur n'importe quel tuile de la même couleur

### Analyse
En ce qui concerne l'analyse, sur plus d'une centaine de réalisations avec les bots suivant participant à la partie :
  Deux bots Random
  Un bot de type DFS
  Le bot en question (BotRuleBased)
On constate environ une cinquantaine de pourcentage de victoire pour le bot DFS, contre environ une petite quarantaine pour le bot en question.
Le meilleur bot semble donc être le bot DFS.
Ce résultat peut s'expliquer par plusieurs raisons :
  - Tout d'abord le bot DFS à une profondeur de 1, il cherche donc uniquement à résoudre un objectif se tour ci, le sabotage du bot en question est donc inutile.
  - Le BotRuleBased n'est pas complétement aboutit, il manque la corrélation entre les objectifs de type "Terrain".
  - Les parties se déroulant à 4 bots, il peut être probable qu'un bot Random prenne l'avantage en début de partie, passant donc dans la priorité pour le sabotage.
  - Cela à deux effets, tout d'abord le bot random ne peut pas être saboté étant donné qu'il n'a aucune stratégie, et le bot DFS est finalement avantagé par rapport au BotRuleBased car ce dernier perd des tours à effectuer des sabotages inutiles
On peut cependant noter que même lors de la réalisation de parties avec le BotRuleBased contre le bot DFS, ce dernier l'emporte en moyenne plus souvent, appuyant nos premiers arguments.

## Architecture et Qualité
### Choix d'architecture
#### Bots
Les bots sont composés à la base d'une classe abstraite Bot qui sers de base pour tout les autres les différents bots disponibles sont:
- Le botRandom qui fait des coups aléatoirement
- Le botRuleBased qui fait des coups basé sur un ensemble de règles
- Le botDFS qui était supposé être un botMCTS mais qui a été modifié pour mieux répondre aux contraites du takenoko et qui pour chaque coup vas simuler en se servant du BotSimulator l'ensemble des posibilités et prendre la meilleure.

#### Objectives
Le objectifs étaient sensé à la base être des enums par souci de praticité, cependant ils étaient trop complexes pour des enums, ils ont donc été changés pour des classes basées l'héritage.


### Localisation des informations sur le fonctionnement des différentes classes
Les information sur le fonctionnement de chaque classe peuvent être trouvées dans la javadoc du projet.

### Diagnostique de la base du code par sonarlint
#### Dette Technique
##### Les principales classes avec une grandes dette technique:

| Classe | Durée de la dette |
|--------|-------------------| 
| ElementOfTheBoard | 4h27 |
| Main | 2h44 |
| Board/CrestGestionnary | 2h10 |
| BotRuleBase/GestionObjectives | 1h50 |

##### Les principales classes avec une faible dette technique:

| Classe | Durée de la dette |
|--------|-------------------| 
| GenerateAWayToIrrigateTheBox | 1h15 |
| LogInfoDemo | 48min|
| Crest | 37min |
| HexagoneBoxPlaced | 33min |

##### Les Classes sont dette technique:
1. AdjacenteException
2. HexagoneBoxSimulation
3. Node
4. BotRandom
5. HexagoneBox
6. BotDFS
7. Bot
8. BotSimulator
9. GameState
10. PatterParcelle
11. PatternPanda

#### Coverage
##### Les principales classes sans coverage dans les tests:
1. GetAllBoxFillingThePatternEntered
2. ChoixApprocheBot
3. GenerateOptimizePathForSeveralBoxWithSimulation
4. CrestNotRegistered
5. Log

##### Les principales classes avec un coverage moyen (<80%):

| Classe | % de coverage |
|--------|---------------|
| BotRuleBased | 38% |
| Node | 57% |
| LogInfoDemo | 70% |
| ElementOfTheBoard | 76% |

##### Les principales classes avec un coverage quasiment complet: 
| Classe | % de coverage |
|--------|---------------|
| BotRandom | 81% |
| HexagoneBoxPlaced | 87% |
| GestionObjective | 89% |
| Crest/Bot | 91% |
| GenerateOptimizePathForSeveralBox | 91% |
| Board/CrestGestionnary | 92% |
| RetrieveBoxIdWithParameters | 95% |

##### Les pricipales classes avec un coverage complet:

1. BotSimulator
2. BoardSimulation
3. Objective
4. Permutation

### Points Forts/Faibles
#### Points Forts

Nous pouvons donc voir que les classes possédant la plus faible dette technique ainsi que le coverage le plus élevé et donc théoriquement les classes les classes les plus dignes de confiance sont les Elements de base du jeu comme HexagoneBox/HexagoneBoxPlaced/Crest ainsi la plupart des bot comme BotRandom/BotSimulator/BotDFS.

#### Points Faibles

Au contraire les classes représentant les points faibles seraient GestionObjectives/BotRuleBased/ElementOfTheBoard qui de part leur taille amènent beaucoup de dette technique ainsi que beaucoup de petites classes comme choixApprocheBot ou Log qui n'ont pas pu être testées dus à un manque de temps. 

## Processus
### Répartition du travail
Qui est responsable de quoi / qui a fait quoi ?
- Loris :
    - La classe MétéoDice
    - La classe Objective et ses classes "enfants" 
    - La classe Pattern et ses classes "enfants"
    - La classe TypeObjective
    - La classe GestionObjectives
    - Les tests de ces classes
- Guillaume :
    - La classe Board
    - La classe HexagoneBox
    - La classe HexagoneBoxPlaced
    - La classe Crest
    - La classe CrestGestionnary
    - La classe ElementOfTheBoard
    - La classe StackOfBox
    - La classe RetrieveBoxIdWIthParameters
    - La classe GetAllBoxFillingThePatternEntered
    - La classe Combination
    - La classe Permutation
    - La classe GenerateAWayToIrrigateTheBox
    - La classe GenerateOptimizePathForSeveralBox
    - La classe GenerateOptimizePathForSeveralBoxWithSimulation
    - Quelques méthodes de la classes BotRuleBased et GestionObjectifs
    - La classe TakenokoException et ses classes "enfants"
    - Les classes Simulations des précédentes si besoin (Board, HexagoneBoxPlaced,StackOfBox...)
    - Les tests de ces classes
- Arthur :
    - La classe Main
    - La classe Game
    - La classe Log
    - La classe BotRuleBased
    - Les tests de ces classes
- Loïc :
   - La classe Bot
   - La classe BotDFS
   - La classe BotRandom
   - La classe BotSimulator
   - La classe ActionLog
   - La classe ActionLogIrrigation
   - La classe GameState
   - La classe Node
   - Les tests de ces classes

### Le process de l'équipe :
#### Milestones : 
   - 11 milestones ont été utilisé dont 2 pour la dernière semaine.
   - Chaque milestone couvre de nouvelles fonctionnalités sur un maximum de règles et éléments du jeu
   - Chaque milestone dont toutes les issues sont fermées est également fermé
#### Issues : 
   - Chaque issue est toujours rattachée à un milestone
   - Création d'une nouvelle issue pour chaque nouvelle fonctionnalité (ou même pour chacune des "sous" fonctionnalités quand elle est trop importante)
   - Chaque issue finie est fermée
#### Label :
   - Création de plusieurs labels personnalisés afin de mieux distinguer les issues entre elles (merging, refactoring, test...)
#### Branche : 
   - 13 branches en tout
   - Création de plusieurs branches afin de distinguer l'avancement de chaque fonctionnalité et de travailler sans problèmes. 
     Ainsi dans le cas du travail sur plusieurs fonctionnalités à la fois, il y a une possibilité de "checkout" la branche correspondant à la fonctionnalité voulue et travailler dessus, 
     tout en mettant en ligne et en permettant à d'autres membres d'également travailler sur cette fonctionnalité si besoin
   - Création de branches pour de nouvelles fonctionnalités qui ont, par la suite été abandonnées pour une autre implémentation (branche sql par exemple)
   - Création de branches pour des types de travaux récurrents, par exemple la branche refactoring, implementTest...

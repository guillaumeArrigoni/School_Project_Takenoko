1 - Point d'avancement :
Fonctionnalités : 
- Un plateau (Board) pouvant contenir des tuiles (HexagoneBox) et des irrigations placés entr deux tuiles (Crest)
- Des tuiles pouvant contenir des bambou et un jeton spécial
- Pousse de bambou (en accord avec le jeton de la tuile)
- Le panda peut manger des bambous (en accord avec le jeton de la tuile et si des bambous existe sur la tuile)
- Le déplacement du jardinier
- Le déplacement du panda
- Prendre une irrigation
- Poser une irrigation
- Piocher un objectif
- Vérification des objectifs
- Lancer le dé et action correspondante à la face
- L'objectif bonus de 2 points au premier à finir le nombre d'objectif fixé
- Une vérification :
	Lors du placement des tuiles (doit être placé à coté de deux truiles)
	Lors du placement des irrigations (doit être relié à une autre irrigation ou au lac central)
	Lors du placements des jetons spéciaux (pas de bambou sur la tuile)
	Lors de la pousse de bambou (hauteur maximal de 4, la tuile doit être irrigué)
	Lors du retrait de bambou, c'est à dire lorsque le panda mange un bambou (la heuteur ne peut pas être négative, la tuile ne doit pas contenir de jeton "protection")

Les logs : 
Toutes les classes relative au log sont accessible dans le package "logger" à la racine du package "takenoko".
Ce package contient 6 classes : 
  - LoggerMain : classe principale, permettant la création du logger, de définir à l'aide de setter ses attribus et de créer un log.
  - FormatterForLog : permettant de modifier l'affichage des logs pour les rendre plus clair. Cette classe override la méthode "format", utiliser dans la ConsoleHandler des logs afin de définir la couleur des logs, et les informations renvoyées.
  - Des classes extandant LoggerMain : 
    - LogInfoDemo : permettant d'afficher les logs lors d'une partie (tour, actions ou objectifs réalisé, nombre de point, gagnant...)
    - LogInfoStats : permettant d'afficher les logs après plusieurs simulations, ou les résultats des statistiques csv.
    - LoggerSevere : permettant d'afficher toutes les Exception "thrown" mais "catch" afin d'éviter l'interruption de la partie. Ce sont toues les exceptions relatifs à des problème mineur étant donné qu'ils peuvent être corriger en cours de fonctionnement.
    - LoggerError : permettant d'afficher toutes les Exceptions "majeurs", ne pouvant pas, ou n'ayant pas été "catch".
Les différents logger peuvent tous être paramétrés pour s'afficher ou non à l'aide du paramètre "IsOn" déclaré à la création du log.
Ainsi, par exemple, la simulation "--thousand" affichera uniquement le logger LogInfoStats.

Les statistiques en CSV :

Bot spécifique et analyse :
Le bot spécifique demandé est capable de :
  - Récupérer un maximum de bambous, même s'ol n'a pas de cartes de la couleur correspondante
  - Essayer d'avoir toujours 5 cartes objectifs en sa possession
  - Récupérer un maximum d'irrigation
  - Prendre un jeton spécial "Irrigation" dès qu'il le peut (donc notamment avec la face du dé "?")
  - Corréler plusieurs objectifs du même type afin de les réaliser d'une manière plus efficace (sauf en ce qui concerne les objectifs de type "Terrain", la méthode est créer, mais un bug relatif à l'irrigation n'a pas été résolu)
  - Surveiller les mouvement de ses adversaires afin d'essayer de saboter leurs réalisation des objectifs :
    - Il estime quel type d'objectif l'adversaire avec le plus de point est entrain de réaliser.
    - Il récupère la dernière action que cet adversaire à vraisemblablement pu effectuer pour tenter de réaliser cet objectif et il estime quel action il doit effectuer pour saboter le plan de l'adversaire :
      - Un objectif de type "Terrain" : 
          Le dernier placement de tuile réaliser par cette Adversaire
          Placer autour de cette tuile, un tuile d'une autre couleur
      - Un objectif de type "Jardinier" : 
          Le dernier déplacement du jardinier
          Déplacer le panda sur cette tuile ou sur une tuile autour de cette dernière de la même couleur
      - Un objectif de type "Panda" : 
          Le dernier déplacement du panda
          Déplacer le panda sur n'importe quel tuile de la même couleur

En ce qui concerne l'analyse, sur plus d'une centaine de réalisation avec les bots suivant participant à la partie :
  Deux bots Random
  Un bot de type DFS
  Le bot en question (BotRuleBased)
On constate environ une cinquantaine de pourcentage de victoire pour le bot DFS, contre environ une petite quarentaine pour le bot en question.
Le meilleur bot semble donc être le bot DFS.
Ce résultat peut s'expliquer par plusieurs raison :
  - Tout d'abord le bot DFS à une profondeur de 1, il cherche donc uniquement à résoudre un objectif se tour ci, le sabotage du bot en question est donc inutile.
  - Le BotRuleBased n'est pas complétement aboutit, il manque la corrélation entre les objectifs de type "Terrain".
  - Les parties se déroulant à 4 bots, il peut être probable qu'un bot Random prenne l'avantage en début de partie, passant donc dans la priorité pour le sabotage.
    - Cela à deux effets, tout d'abord le bot random ne peut pas être saboter étant donné qu'il n'a aucune stratégie, et le bot DFS est finalement avantagé par rapport au BotRuleBased car ce dernier perd des tour à effectuer des sabotages inutiles
On peut cependant noté que même lors de la réalisation de partie avec le BotRuleBased contre le bot DFS, ce dernier l'emporte en moyenne plus souvent, appuyant nos premiers arguements.
  
  
  3 - Processus



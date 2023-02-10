### DIAGNOSTIQUE DE LA BASE DU CODE PAR SONARQUBE ###
#### DETTE TECHNIQUE
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

#### COVERAGE
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








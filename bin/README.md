# M2_Agents_assistants_intelligents

# Objectifs
stratégie de l’agent + interface graphique pour chercher une œuvre ou remplir le profil de préférences.

Claire :
Intégration webservice.

Louis : 
DTO / DAO / Entity + revoir si on peut utiliser le projet de Blanchard

Anne-Laure:
Behaviours

* Recherche par titre
* Recherche par critères
* J'envoie une notif pour voir quelle oeuvre, la noter etc...

**Internaute**

* Conception de l’interface graphique permettant d’interagir avec les distributeurs.
* Un profil avec des préférences pour chaque type de media, un temps de visionnage moyen et un budget mensuel.
* Maximiser sa satisfaction en téléchargant/streamant des contenus qui lui plaisent.
* Interaction avec les distributeurs pour obtenir ces contenus et avec le système de recommandation pour obtenir les évaluations de ces contenus, des distributeurs et des producteurs. 
* Il peut également noter lui-même ces contenus, des distributeurs et des producteurs.

**TODO**

* Création de la base du projet

* Récupération de la base d'Antoine

* Créer l'agent avec ses préférences

* Création des échanges (avec Mock)
	* Agent e-reputation
	* Agents distributeurs

* Choix des stratégies achats/choix initiaux

* Calcul de la satisfaction globale

* Calcul satisfaction par raooirt à un distributeur


## Trello :

[https://trello.com/b/bsh2egHk/projet-ia](https://trello.com/b/bsh2egHk/projet-ia)

## Projet Agents et Assistants Intelligents : 

L’objectif du projet est de concevoir le système de vente en ligne d’un revendeur multimédia.  
Ce projet met en œuvre la plateforme Jade distribué au travers d’un mini réseau de 4 machines.

La plate-forme se compose de quatre types d’agents représentant respectivement :
 * les agents,
 * les internautes,
 * les agents producteurs,
 * les agents distributeurs (les services de VOD, de streaming, de cloud ou de téléchargement),
 * l’agent e-réputation (service de notation de producteurs, distributeurs et clients).

### Distributeurs

Le distributeur propose un catalogue d’œuvres assez large incluant des films, des séries, des jeux vidéo, et des CD musicaux.  
Il propose ses services en téléchargement (achats ponctuels) et/ou en streaming/cloud sous forme d’abonnements avec période d’engagement (plus avantageux pour une consommation régulière mais plus d’accès possible aux produits en cas de désabonnement).  
Certaines œuvres peuvent être accessibles uniquement aux internautes abonnés.  
Chaque distributeur a le droit d’utiliser quand bon lui semble jusqu’à 20 jours de soldes flottantes par an sur les ventes en téléchargement à condition de prévenir l’agent e-réputation au moins 2 semaines avant.  
Les ventes à perte ne sont pas autorisées.

Il faut que l’agent distributeur soit capable de répondre à une requête de l’agent internaute.

Pour cela, deux scénarios possibles :
 1. L’internaute cherche une œuvre précise en téléchargement à partir du nom.  
 Le distributeur vérifie s’il a cette œuvre.  
 Si oui, il lui annonce le prix du produit en fonction de sa stratégie qu’il est le seul à connaître (récence de l’œuvre, niveau de désirabilité, coût de revient, etc.). L’agent distributeur cherche à faire un maximum de profits (en vendant beaucoup ou en augmentant ses marges).  
 Sinon il lui indique qu’il n’a pas cette œuvre dans son catalogue mais peut proposer des ressources similaires via un système de recommandation.
2. L’internaute abonné cherche un produit à partir de certains critères (ex. : films avec Chris Evans, ou musique de rock alternatif des années 2010, etc.).  
Le distributeur cherche toutes les œuvres répondant à ces critères (avec les informations correspondantes) et l’utilisateur choisit celles qu’il souhaite consommer (potentiellement en séquence dans le cas de séries ou de musiques).  
Le distributeur cherche à maximiser la satisfaction des internautes au travers des recommandations faites via ce moteur de recherche pour les inciter à rester abonnés.  
Le moteur de recherche est également accessible publiquement pour capter de nouveaux abonnés.

### Producteurs

Les agents producteurs doivent quant à eux essayer de négocier les prix auxquels l’agent distributeur est prêt à acheter leurs œuvres.  
Chaque œuvre a une date de sortie.  
Le catalogue peut donc évoluer au cours du temps.  
Les producteurs ont des catalogues d’œuvres disjoints.
Les agents distributeurs, en revanche, peuvent avoir des œuvres en commun dans leurs catalogues respectifs ou avoir négocié des exclusivités avec les producteurs à un tarif plus élevé.  
Evidemment, les distributeurs sont en compétition, tout comme les producteurs.  
Les producteurs ne connaissent pas la stratégie de leurs concurrents et doivent définir la stratégie la plus efficace possible pour maximiser leurs gains (délai de paiement autorisé, prix variable en fonction du nombre d’œuvres négociées conjointement ou en fonction de l’exclusivité, priorité donnée à certaines œuvres au détriment d’autres, ...).  
Chaque œuvre a un coût de production pouvant varier en fonction des artistes recrutés (les acteurs, actrices, réalisateurs, réalisatrices, développeurs ou développeuses de jeu connus sont chers mais font augmenter le niveau de désirabilité de l’œuvre). Un producteur, tout comme l’agent distributeur, a une
trésorerie initiale limitée (la même pour les concurrents).  
Les agents distributeurs ont également un espace de stockage (et donc un catalogue) limité.

### Internaute & e-réputation

L’agent internaute cherche à passer de bonnes soirées en consultant/achetant des œuvres maximisant sa satisfaction chez un ou plusieurs distributeurs (à noter que vous pourrez lancer plusieurs instances de cet agent pour simuler plusieurs internautes).  
Chaque agent a donc des préférences qui lui sont propres (à définir dans un profil via l’interface graphique) et un temps de consommation moyen par mois.  
Il a également un budget maximum alloué pour ses loisirs multimédias.

L’agent internaute peut, à tout moment et en particulier à l’issue d’une consultation ou d’un achat, émettre un avis à propos d’une œuvre, d’un artiste, d’un
distributeur ou d’un producteur auprès d’un agent « e-réputation ».  
Les notes obtenues peuvent influer sur les décisions des autres agents (désirabilité d’une œuvre, coût de production pour les futures œuvres en fonction de la popularité des artistes, incitation à s’abonner chez un distributeur...).  
L’agent e-réputation pourra communiquer des métriques telles que le niveau de désirabilité au cours du temps de chaque œuvre. Un critère d’obsolescence sera également mis en place (le niveau de désirabilité d’une œuvre diminuant avec le temps de façon indépendante de la popularité des artistes, notamment si cette œuvre est de moins en moins consultée).

### Communication & groupe transverse

Pour les échanges entre agents, vous pourrez :

 * soit concevoir une ontologie commune aux différents groupes (avec Protégé par exemple),
 * soit définir une syntaxe sous forme de messages texte/JSON/XML ou d’objets Java.  

La définition de cette ontologie ou de ces formats d’échanges de messages (en détaillant les différents critères de recherche applicables pour chaque œuvre) sera à la charge d’un groupe transverse composé de coordinateurs.  
Vous devrez désigner un coordinateur dans chaque groupe projet.

Pour faciliter les échanges entre agents (définition des entrées/sorties, des formats de message, du catalogue des œuvres, des coûts de fabrication, des schémas de BdD).

### Objetcifs

 * 1 groupe e-réputation : développement de l’agent « e-réputation » (chargé de collecter les avis sur les distributeurs, producteurs, artistes et sur les œuvres) + calcul des niveaux de désirabilité des œuvres, de popularité des artistes + système de recommandation d’œuvres ou d’artistes (uniquement basé sur la popularité) tous distributeurs confondus.
 * 1 groupe Client : stratégie de l’agent + interface graphique pour chercher une œuvre ou remplir le profil de préférences.
 * 2 groupes Producteur : stratégie de l’agent + gestion du catalogue des œuvres produites au cours du temps + système de recommandation pour mettre en avant les œuvres pertinentes en fonction de la stratégie de vente.
 * 2 groupes Distributeur : stratégie de l’agent + système de recommandation pour mettre en avant les œuvres pertinentes en fonction des préférences connues des internautes.
 * 1 groupe transverse : 1 membre de chaque groupe pour coordonner le projet (définition des formats de messages, du catalogue produits, ...).
 
Chaque groupe doit être en mesure d’évaluer après simulation la pertinence des stratégies mises en œuvre au cours du temps, ainsi que d’évaluer les performances de son système de recommandation.

![](http://alexandra.korovina.free.fr/Pictures/bts/Creations/Bt_Home.png)
# "Phenix 

Le projet est une réponse au [Challenge Phenix](https://github.com/Carrefour-Group/phenix-challenge) 

Il  génère les fichiers suivants pour une date donnée. 
1. top_100_ventes_<ID_MAGASIN>_YYYYMMDD.data
3. top_100_ca_<ID_MAGASIN>_YYYYMMDD.data
7. top_100_ca_<ID_MAGASIN>_YYYYMMDD-J7.data *(créé à partir des fichiers n°3)*

## *Configuration*
 Java 8; Scala 2.12.3

## *Utilisation*
1. télécharger fichier **_phenix-0.1.0-SNAPSHOT.zip_**
2. copier sur le serveur
3. deziper:
	> unzip phenix-0.1.0-SNAPSHOT.zip
4. lancer:
	> ./phenix-0.1.0-SNAPSHOT/bin/phenix
5. renseigner la date en format demandé (sinon - le traitement se fera pour la date courante)
6. renseigner le chemin vers le dossier de data (sinon - le dossier par défaut - c'est le dossier en cour)

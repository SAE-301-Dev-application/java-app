<div align="center">

# SAÉ 301 - Développement d'une application | QUIZ

</div>

Le but de ce projet est de permettre à des utilisateurs, principalement en informatique, de tester leurs connaissances sur des quiz. 
Cette application de quiz est réalisée en Java avec une interface graphique JavaFX et une mise en page en CSS.

## Installation

`git clone SAE-301-Dev-application/java-app.git`

Puis copiez le fichier `java-app\WorkspaceEclipse\javaApplication\src\info2\sae301\quiz\sauvegardes\Exemple_sauvegardeDonneesQuiz.ser` et renommez le en `sauvegardeDonneesQuiz.ser` pour avoir les questions par défaut du jeu présentes dans le même dossier au format CSV.

## Versions

- [Eclipse JEE : Septembre 2023 R](https://ftp.fau.de/eclipse/technology/epp/downloads/release/2023-09/R/eclipse-jee-2023-09-R-win32-x86_64.zip)
- [JDK : 21](https://download.oracle.com/java/21/latest/jdk-21_windows-x64_bin.zip)
- JUnit : 5
- [JavaFX (SDK) : 21](https://download2.gluonhq.com/openjfx/21/openjfx-21_windows-x64_bin-sdk.zip)
- [E(fx)clipse : 3.9.0](https://download.eclipse.org/efxclipse/updates-released/3.9.0/site/)
- [SceneBuilder : 21.0.1](https://gluonhq.com/products/scene-builder/thanks/?dl=https://download2.gluonhq.com/scenebuilder/21.0.0/install/win/SceneBuilder-21.0.0.msi)
- GanttProject : 3.2

Run configuration > Java application > Quiz :
--module-path "\path\to\JavaFX21\lib" --add-modules javafx.controls,javafx.fxml

## Features

- Paramétrer une partie (nombre questions, catégories, difficulté)
- Jouer à un quiz avec une réponse juste et jusqu'à 4 réponses fausses
- Voir le feedback du quiz
- Voir les statistiques des réponses
- Créer / Modifier / Supprimer des questions et catégories
- Obtenir de l'aide sur n'importe quelle fenêtre
- Importer des données depuis un fichier CSV
- Importer des données depuis un ordinateur distant
- Exporter ses données vers un autre ordinateur
- Choisir les questions et catégories à importer et exporter
- Avoir une expérience personnalisée (pseudo)
- Sauvegarder (en sérialisant) localement ses données

## Contributeurs (Nom Prénom / E-Mail / Page GitHub)
- FABRE Florian / florian.fabre@iut-rodez.fr / [Florian](https://github.com/Odonata971)
- FAUGIÈRES Loïc / loic.faugieres@iut-rodez.fr / [Loïc](https://github.com/xGk93)
- GUIL Jonathan / jonathan.guil@iut-rodez.fr / [Jonathan](https://github.com/belicfr)
- GUIRAUD Simon / simon.guiraud@iut-rodez.fr / [Simon](https://github.com/SyberSim)
- LACAM Samuel / samuel.lacam@iut-rodez.fr / [Samuel](https://github.com/SamuelLacam)

## Répartition des rôles selon les itérations SCRUM : 

 ___________________________________________________________________________________________
| Sprint    | Florian       | Loïc          | Jonathan      | Simon         | Samuel        |
|-----------|---------------|---------------|---------------|---------------|---------------|
| Sprint 0  | Product Owner | Développeur   | Développeur   | SCRUM master  | Développeur   |
| Sprint 1  | Développeur   | Développeur   | Product Owner | Développeur   | SCRUM Master  |
| Sprint 2  | Développeur   | SCRUM master  | Développeur   | Product Owner | Développeur   |
| Sprint 3  | SCRUM Master  | Développeur   | Développeur   | Développeur   | Product Owner |
| Sprint 4  | Développeur   | Product Owner | SCRUM Master  | Développeur   | Développeur   |

## Liens

- Github :
  - Repository : https://github.com/SAE-301-Dev-application/java-app
  - Board agile : https://github.com/orgs/SAE-301-Dev-application/projects/3
- Google Drive : https://drive.google.com/drive/u/1/folders/0AAgUBn2XFZXuUk9PVA
- Serveur Discord de discussions : https://discord.gg/jhcd7aprgZ

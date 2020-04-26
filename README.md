# Projet apprentissage par renforcement

Code réalisé par Jérémy KEROUANTON et Nicolas LE NEZET.


## Résumé

Projet basé sur un pion dans un labyrinthe qui doit trouver une récompense représentée par une case jaune.

L'interface permet de modifier les paramètres epsilon (taux d'exploration), lambda (remise) et alpha (taux d'apprentissage). Ces valeurs sont dans un premier temps à régler à la main par l'utilisateur. 

## Epsilon automatique

On veut dans un premier temps faire en sorte que le taux d'exploration du pion soit réglé automatiquement. Pour cela on fait en sorte de calculer la moyenne de réussite du pion, c'est à dire avec quel pourcentage de chances il trouve la récompense en moins de 1000 pas. 

Si cette moyenne est basse, ça veut dire que le pion ne trouve pas la récompense, on doit donc monter epsilon pour qu'il explore davantage le labyrinthe et trouve la récompense. Si la moyenne est élevée, le pion trouve la récompense souvent. Il n'a donc pas besoin d'explorer énormément, on baisse donc epsilon.

## Perception non-markovienne

Jusque là, la perception de notre pion était limitée a sa position propre, une perception markovienne. Dans la réalité, un agent aura une perception basée sur ce qu'il récupère avec des capteurs qui ne sont pas forcément à sa position propre. On ajoute donc des capteurs, qu'on choisit de lier à la position de l'agent, mais qui sont décalés d'une case selon X en plus et en moins. Comme il y a deux perception, l'apprentissage est supposé être un peu plus rapide.

## Notes

La branche master correspond à la version fonctionnelle du projet, malgré de menus bugs que nous ne comprenons pas, tel que l'agent qui ne fait que des mouvements droite-gauche à répétition.
La branche develop correspond à une potentielle amélioration qui devrait permettre d'avoir un nombre de capteurs n tant que c'est un multiple de 4. Cette dernière n'est pas fonctionnelle, elle se lance mais ne fonctionne pas et nous n'avons pas trouvé le moyen de la faire tomber en marche.

## Fonctionnement

Sous Linux, lancer le compiler avec : 
```
./compiler.sh
```
Attention, il y a peut-être besoin de le rendre exécutable avec : 
```
chmod +x compiler.sh
```

Sous Windows, passez à Linux et suivez les étapes ci-dessus.

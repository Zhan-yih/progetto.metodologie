# Breakout

Breakout è un gioco d'azione in tempo reale scritto in Java con interfaccia grafica JavaFX. Il giocatore controlla un soldato che si infiltra in un complesso nemico, attraversa cinque stanze, sconfigge i nemici e porta a termine la missione finale: liberare gli ostaggi rinchiusi nella prigione.

## Descrizione
Il complesso è formato da cinque stanze, da attraversare in ordine: ingresso, armeria, infermeria, magazzino e prigione. In ogni stanza il soldato può raccogliere armi e consumabili, e finché c'è un nemico la porta verso la stanza successiva resta bloccata. Nella prigione ti aspetta il boss, e dopo averlo sconfitto devi liberare tutti e tre gli ostaggi per vincere. Se i punti vita del soldato arrivano a zero, la partita è persa.

## Come eseguire il progetto
### Prerequisiti
* Java 25
* Gradle 9.1.0

### Istruzioni

git clone https://github.com/Zhan-yih/progetto.metodologie.git

cd progetto.metodologie


### Build del progetto:

./gradlew build

### Esecuzione:
./gradlew run

## Funzionalità principali
### Soldato
* Punti vita con barra sopra il personaggio
* Movimento libero nella stanza e mira con il mouse

### Combattimento
* Sparo in tempo reale con proiettili, precisione e munizioni limitate per ogni arma
* Nemici che sparano a loro volta e proiettili schivabili
* Boss nella stanza finale, armato di fucile da cecchino

### Inventario
* Armi da equipaggiare e cambiare durante lo scontro
* Consumabili: kit medico, granata flas e granata fumogena

### Esplorazione
* Cinque stanze collegate da porte, che si aprono solo quando la stanza è libera
* Raccolta degli oggetti e liberazione degli ostaggi avvicinandosi, con animazioni

### Salvataggio
* Salvataggio e caricamento della partita su file XML

## Comandi
* WASD o frecce per muoversi
* Mouse oppure SPAZIO per sparare verso il mouse
* E per raccogliere un oggetto o liberare un ostaggio
* 1, 2, 3 per equipaggiare l'arma corrispodente nell'inventario
* 4, 5, 6 per usare il primo, il secondo o il terzo consumabile
* Pulsanti Salva e Carica nel pannello a destra


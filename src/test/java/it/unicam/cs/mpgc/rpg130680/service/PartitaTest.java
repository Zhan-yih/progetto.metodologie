package it.unicam.cs.mpgc.rpg130680.service;

import it.unicam.cs.mpgc.rpg130680.model.entity.Nemico;
import it.unicam.cs.mpgc.rpg130680.model.entity.Ostaggio;
import it.unicam.cs.mpgc.rpg130680.model.entity.Soldato;
import it.unicam.cs.mpgc.rpg130680.model.item.Arma;
import it.unicam.cs.mpgc.rpg130680.model.item.Consumabile;
import it.unicam.cs.mpgc.rpg130680.model.item.GranataFlash;
import it.unicam.cs.mpgc.rpg130680.model.item.KitMedico;
import it.unicam.cs.mpgc.rpg130680.model.map.Mappa;
import it.unicam.cs.mpgc.rpg130680.model.map.Stanza;
import org.junit.jupiter.api.Test;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static it.unicam.cs.mpgc.rpg130680.service.StatoPartita.*;
import static org.junit.jupiter.api.Assertions.*;

public class PartitaTest {

    private Partita creaPartitaArmata(Nemico nemico) {
        Soldato soldato = new Soldato("io");
        Arma pistola = new Arma("Pistola", 15, 75, 12);
        soldato.raccogliArma(pistola);
        soldato.equipaggiaArma(pistola);
        Stanza stanza = new Stanza("ingresso", "Una porta blindata");
        if (nemico != null) {
            stanza.inserisciNemico(nemico);
        }
        return new Partita(soldato, new Mappa(List.of(stanza)), new Combattimento(() -> 0));
    }

    private void svuotaCaricatore(Partita partita) {
        Arma arma = partita.getSoldato().getArmaEquipaggiata().orElseThrow();
        while (arma.haMunizioni()) {
            arma.consumaColpo();
        }
    }

    @Test
    void partitaNuovaInCorso(){
        Soldato soldato= new Soldato("io");
        Stanza stanza= new Stanza("ingresso", "Una porta blindata");
        Mappa mappa= new Mappa(List.of( stanza));
        Combattimento combattimento= new Combattimento(()->0);
        Partita partita= new Partita(soldato, mappa, combattimento);
        assertEquals(IN_CORSO, partita.getStato());
    }

    @Test
    void soldatoMuoreSconfitta(){
        Soldato soldato= new Soldato("io");
        Stanza stanza= new Stanza("ingresso", "Una porta blindata");
        Mappa mappa= new Mappa(List.of( stanza));
        Combattimento combattimento= new Combattimento(()->0);
        Partita partita= new Partita(soldato, mappa, combattimento);
        soldato.subisciDanno(100);
        assertEquals(SCONFITTA, partita.getStato());
    }

    @Test
    void eccezioneParametriNulli(){
        assertThrows(IllegalArgumentException.class, ()-> new Partita(null, null, null));
    }

    @Test
    void soldatoNulloEccezione(){
        Stanza stanza= new Stanza("ingresso", "Una porta blindata");
        Mappa mappa= new Mappa(List.of( stanza));
        Combattimento combattimento= new Combattimento(()->0);
        assertThrows(IllegalArgumentException.class, ()-> new Partita(null, mappa, combattimento));
    }

    @Test
    void mappaNulloEccezione(){
        Soldato soldato= new Soldato("io");
        Combattimento combattimento= new Combattimento(()->0);
        assertThrows(IllegalArgumentException.class, ()-> new Partita(soldato, null, combattimento));
    }

    @Test
    void combattimentoNulloEccezione(){
        Soldato soldato= new Soldato("io");
        Stanza stanza= new Stanza("ingresso", "Una porta blindata");
        Mappa mappa= new Mappa(List.of( stanza));
        assertThrows(IllegalArgumentException.class, ()-> new Partita(soldato, mappa, null));
    }

    @Test
    void partitaNuovaStanzaCorrenteVuoto(){
        Soldato soldato= new Soldato("io");
        Stanza stanza= new Stanza("ingresso", "Una porta blindata");
        Mappa mappa= new Mappa(List.of( stanza));
        Combattimento combattimento= new Combattimento(()->0);
        Partita partita= new Partita(soldato, mappa, combattimento);
        assertTrue(partita.getStanzaCorrente().isEmpty());
        partita.entraInStanza(0);
        assertEquals(Optional.of(stanza), partita.getStanzaCorrente());
    }

    @Test
    void eccezioneIndiciNonValido(){
        Soldato soldato= new Soldato("io");
        Stanza stanza= new Stanza("ingresso", "Una porta blindata");
        Mappa mappa= new Mappa(List.of( stanza));
        Combattimento combattimento= new Combattimento(()->0);
        Partita partita= new Partita(soldato, mappa, combattimento);
        assertThrows(IllegalArgumentException.class, ()-> partita.entraInStanza(-1));
        assertThrows(IllegalArgumentException.class, ()-> partita.entraInStanza(1));
    }

    @Test
    void eccezionePartitaFinita(){
        Soldato soldato= new Soldato("io");
        Stanza stanza= new Stanza("ingresso", "Una porta blindata");
        Mappa mappa= new Mappa(List.of( stanza));
        Combattimento combattimento= new Combattimento(()->0);
        Partita partita= new Partita(soldato, mappa, combattimento);
        soldato.subisciDanno(100);
        assertThrows(IllegalStateException.class, ()-> partita.entraInStanza(0));
    }

    @Test
    void nemicoNonSconfittoRestaNellaStanza(){
        Soldato soldato= new Soldato("io");
        Arma arma= new Arma("Pistola",15,75,12);
        soldato.raccogliArma(arma);
        soldato.equipaggiaArma(arma);
        Stanza stanza= new Stanza("ingresso", "Una porta blindata");
        stanza.inserisciNemico(new Nemico("avversario",100,new Arma("Fucile",10,60,15)));
        Mappa mappa= new Mappa(List.of( stanza));
        Combattimento combattimento= new Combattimento(()->0);
        Partita partita= new Partita(soldato, mappa, combattimento);
        partita.entraInStanza(0);
        EsitoTurno esito= partita.combatti();
        assertFalse(esito.nemicoSconfitto());
        assertTrue(stanza.getNemico().isPresent());
    }

    @Test
    void nemicoSconfitto(){
        Soldato soldato= new Soldato("io");
        Arma arma= new Arma("Pistola",15,75,12);
        soldato.raccogliArma(arma);
        soldato.equipaggiaArma(arma);
        Stanza stanza= new Stanza("ingresso", "Una porta blindata");
        stanza.inserisciNemico(new Nemico("avversario",10,new Arma("Fucile",10,60,15)));
        Mappa mappa= new Mappa(List.of( stanza));
        Combattimento combattimento= new Combattimento(()->0);
        Partita partita= new Partita(soldato, mappa, combattimento);
        partita.entraInStanza(0);
        EsitoTurno esito= partita.combatti();
        assertTrue(esito.nemicoSconfitto());
        assertTrue(stanza.getNemico().isEmpty());
    }

    @Test
    void eccezioneNonEntratoInStanza(){
        Soldato soldato= new Soldato("io");
        Arma arma= new Arma("Pistola",15,75,12);
        soldato.raccogliArma(arma);
        soldato.equipaggiaArma(arma);
        Stanza stanza= new Stanza("ingresso", "Una porta blindata");
        stanza.inserisciNemico(new Nemico("avversario",100,new Arma("Fucile",10,60,15)));
        Mappa mappa= new Mappa(List.of( stanza));
        Combattimento combattimento= new Combattimento(()->0);
        Partita partita= new Partita(soldato, mappa, combattimento);
        assertThrows(IllegalStateException.class, ()-> partita.combatti());
    }

    @Test
    void eccezioneSenzaNemicoInStanza(){
        Soldato soldato= new Soldato("io");
        Arma arma= new Arma("Pistola",15,75,12);
        soldato.raccogliArma(arma);
        soldato.equipaggiaArma(arma);
        Stanza stanza= new Stanza("ingresso", "Una porta blindata");
        Mappa mappa= new Mappa(List.of( stanza));
        Combattimento combattimento= new Combattimento(()->0);
        Partita partita= new Partita(soldato, mappa, combattimento);
        partita.entraInStanza(0);
        assertThrows(IllegalStateException.class, ()-> partita.combatti());
    }

    @Test
    void completaStanzaRaccogliOggettiELasciaStanza(){
        Soldato soldato= new Soldato("io");
        Arma arma= new Arma("Pistola",15,75,12);
        Consumabile kit= new KitMedico(30);
        Stanza stanza= new Stanza("ingresso", "Una porta blindata");
        stanza.aggiungiArma(arma);
        stanza.aggiungiConsumabile(kit);
        stanza.aggiungiOstaggio(new Ostaggio("prova"));
        Mappa mappa= new Mappa(List.of( stanza));
        Combattimento combattimento= new Combattimento(()->0);
        Partita partita= new Partita(soldato, mappa, combattimento);
        partita.entraInStanza(0);
        partita.completaStanza();
        assertTrue(soldato.getArmi().contains(arma));
        assertTrue(soldato.getConsumabili().contains(kit));
        assertTrue(partita.getStanzaCorrente().isEmpty());
    }

    @Test
    void completaStanzaOstaggioLiberato(){
        Soldato soldato= new Soldato("io");
        Ostaggio ostaggio= new Ostaggio("prova");
        Stanza stanza= new Stanza("ingresso", "Una porta blindata");
        stanza.aggiungiOstaggio(ostaggio);
        Mappa mappa= new Mappa(List.of( stanza));
        Combattimento combattimento= new Combattimento(()->0);
        Partita partita= new Partita(soldato, mappa, combattimento);
        partita.entraInStanza(0);
        partita.completaStanza();
        assertTrue(ostaggio.isLiberato());
    }

    @Test
    void eccezioneNemicoPresenteCompletaStanza(){
        Soldato soldato= new Soldato("io");
        Nemico nemico= new Nemico("avversario",100,new Arma("Fucile",10,60,15));
        Stanza stanza= new Stanza("ingresso", "Una porta blindata");
        stanza.inserisciNemico(nemico);
        Mappa mappa= new Mappa(List.of( stanza));
        Combattimento combattimento= new Combattimento(()->0);
        Partita partita= new Partita(soldato, mappa, combattimento);
        partita.entraInStanza(0);
        assertThrows(IllegalStateException.class, ()-> partita.completaStanza());
    }

    @Test
    void stanzaCompletaVittoria(){
        Soldato soldato= new Soldato("io");
        Stanza stanza= new Stanza("ingresso", "Una porta blindata");
        Mappa mappa= new Mappa(List.of( stanza));
        Combattimento combattimento= new Combattimento(()->0);
        Partita partita= new Partita(soldato, mappa, combattimento);
        partita.entraInStanza(0);
        partita.completaStanza();
        assertEquals(VITTORIA, partita.getStato());
    }

    @Test
    void eccezioneStanzaGiaEsplorata(){
        Soldato soldato= new Soldato("io");
        Stanza stanza1= new Stanza("ingresso", "Una porta blindata");
        Stanza stanza2= new Stanza("sala", "Una porta blindata");
        Mappa mappa= new Mappa(List.of( stanza1, stanza2));
        Combattimento combattimento= new Combattimento(()->0);
        Partita partita= new Partita(soldato, mappa, combattimento);
        partita.entraInStanza(0);
        partita.completaStanza();
        assertThrows(IllegalArgumentException.class, ()-> partita.entraInStanza(0));
    }

    @Test
    void kitMedicoSenzaIlNemiciCuraSoldato(){
        Soldato soldato= new Soldato("io");
        Consumabile kit= new KitMedico(30);
        soldato.subisciDanno(50);
        soldato.raccogliConsumabile(kit);
        Stanza stanza= new Stanza("ingresso", "Una porta blindata");
        Mappa mappa= new Mappa(List.of( stanza));
        Combattimento combattimento= new Combattimento(()->0);
        Partita partita= new Partita(soldato, mappa, combattimento);
        partita.entraInStanza(0);
        partita.usaConsumabile(kit);
        assertEquals(80,soldato.getPuntiVita());
        assertFalse(soldato.getConsumabili().contains(kit));
    }

    @Test
    void senzaNemicoGranataFlashScarta(){
        Soldato soldato= new Soldato("io");
        Consumabile granata= new GranataFlash();
        soldato.raccogliConsumabile(granata);
        Stanza stanza= new Stanza("ingresso", "Una porta blindata");
        Mappa mappa= new Mappa(List.of( stanza));
        Combattimento combattimento= new Combattimento(()->0);
        Partita partita= new Partita(soldato, mappa, combattimento);
        partita.entraInStanza(0);
        partita.usaConsumabile(granata);
        assertFalse(soldato.getConsumabili().contains(granata));
    }

    @Test
    void flashConNemico(){
        Soldato soldato= new Soldato("io");
        Consumabile granata= new GranataFlash();
        soldato.raccogliConsumabile(granata);
        Nemico nemico= new Nemico("avversario",100,new Arma("Fucile",10,60,15));
        Stanza stanza= new Stanza("ingresso", "Una porta blindata");
        stanza.inserisciNemico(nemico);
        Mappa mappa= new Mappa(List.of( stanza));
        Combattimento combattimento= new Combattimento(()->0);
        Partita partita= new Partita(soldato, mappa, combattimento);
        partita.entraInStanza(0);
        partita.usaConsumabile(granata);
        assertTrue(nemico.isStordito());
        assertFalse(soldato.getConsumabili().contains(granata));
    }

    @Test
    void senzaStanzaCorrenteEccezione(){
        Soldato soldato= new Soldato("io");
        Stanza stanza= new Stanza("ingresso", "Una porta blindata");
        Mappa mappa= new Mappa(List.of( stanza));
        Combattimento combattimento= new Combattimento(()->0);
        Partita partita= new Partita(soldato, mappa, combattimento);
        assertThrows(IllegalStateException.class, ()-> partita.usaConsumabile(new KitMedico(30)));
    }

    @Test
    void eccezioneConsumabileNullo(){
        Soldato soldato= new Soldato("io");
        Stanza stanza= new Stanza("ingresso", "Una porta blindata");
        Mappa mappa= new Mappa(List.of( stanza));
        Combattimento combattimento= new Combattimento(()->0);
        Partita partita= new Partita(soldato, mappa, combattimento);
        partita.entraInStanza(0);
        assertThrows(IllegalArgumentException.class, ()-> partita.usaConsumabile(null));
    }

    private Mappa creaMappa() {
        return new Mappa(List.of(
                new Stanza("Ingresso", "Una sala vuota"),
                new Stanza("Magazzino", "Casse impilate")));
    }

    @Test
    void costruttoreRipristinaStanzeEsplorateEStanzaCorrente() {
        Partita partita = new Partita(new Soldato("Rambo"), creaMappa(),
                new Combattimento(() -> 0), Set.of(0), 1);

        assertEquals(Set.of(0), partita.getStanzeEsplorate());
        assertEquals(1, partita.getIndiceStanzaCorrente());
    }

    @Test
    void costruttoreAccettaNessunaStanzaCorrente() {
        Partita partita = new Partita(new Soldato("Rambo"), creaMappa(),
                new Combattimento(() -> 0), Set.of(0), -1);

        assertEquals(-1, partita.getIndiceStanzaCorrente());
    }

    @Test
    void costruttoreRifiutaStanzaEsplorataNonValida() {
        assertThrows(IllegalArgumentException.class, () ->
                new Partita(new Soldato("Rambo"), creaMappa(),
                        new Combattimento(() -> 0), Set.of(99), -1));
    }

    @Test
    void costruttoreRifiutaStanzaCorrenteNonValida() {
        assertThrows(IllegalArgumentException.class, () ->
                new Partita(new Soldato("Rambo"), creaMappa(),
                        new Combattimento(() -> 0), Set.of(), 99));
    }

    @Test
    void costruttoreRifiutaStanzaCorrenteGiaEsplorata() {
        assertThrows(IllegalArgumentException.class, () ->
                new Partita(new Soldato("Rambo"), creaMappa(),
                        new Combattimento(() -> 0), Set.of(0), 0));
    }

    @Test
    void sparaInfliggeDannoENemicoResta() {
        Partita partita = creaPartitaArmata(new Nemico("avversario", 100, new Arma("Fucile", 10, 60, 15)));
        partita.entraInStanza(0);
        assertEquals(15, partita.spara());
        assertTrue(partita.getStanzaCorrente().orElseThrow().getNemico().isPresent());
    }

    @Test
    void sparaUccideEPoiRimuoveIlNemico() {
        Partita partita = creaPartitaArmata(new Nemico("avversario", 10, new Arma("Fucile", 10, 60, 15)));
        partita.entraInStanza(0);
        partita.spara();
        assertTrue(partita.getStanzaCorrente().orElseThrow().getNemico().isEmpty());
    }

    @Test
    void sparaSenzaStanzaCorrenteLanciaEccezione() {
        Partita partita = creaPartitaArmata(new Nemico("avversario", 100, new Arma("Fucile", 10, 60, 15)));
        assertThrows(IllegalStateException.class, () -> partita.spara());
    }

    @Test
    void sparaSenzaNemicoLanciaEccezione() {
        Partita partita = creaPartitaArmata(null);
        partita.entraInStanza(0);
        assertThrows(IllegalStateException.class, () -> partita.spara());
    }

    @Test
    void turnoNemicoColpisceIlSoldato() {
        Partita partita = creaPartitaArmata(new Nemico("avversario", 100, new Arma("Fucile", 10, 60, 15)));
        partita.entraInStanza(0);
        assertEquals(10, partita.turnoNemico());
        assertEquals(90, partita.getSoldato().getPuntiVita());
    }

    @Test
    void turnoNemicoAPartitaFinitaLanciaEccezione() {
        Partita partita = creaPartitaArmata(new Nemico("avversario", 100, new Arma("Fucile", 10, 60, 15)));
        partita.entraInStanza(0);
        partita.getSoldato().subisciDanno(100);
        assertThrows(IllegalStateException.class, () -> partita.turnoNemico());
    }

    @Test
    void sparaAlVuotoConsumaUnaMunizione() {
        Partita partita = creaPartitaArmata(null);
        partita.entraInStanza(0);
        partita.sparaAlVuoto();
        assertEquals(11, partita.getSoldato().getArmaEquipaggiata().orElseThrow().getMunizioni());
    }

    @Test
    void sparaAlVuotoSenzaArmaLanciaEccezione() {
        Partita partita = new Partita(new Soldato("io"),
                new Mappa(List.of(new Stanza("ingresso", "Una porta blindata"))),
                new Combattimento(() -> 0));
        assertThrows(IllegalStateException.class, () -> partita.sparaAlVuoto());
    }

    @Test
    void sparaAlVuotoAPartitaFinitaLanciaEccezione() {
        Partita partita = creaPartitaArmata(null);
        partita.getSoldato().subisciDanno(100);
        assertThrows(IllegalStateException.class, () -> partita.sparaAlVuoto());
    }

    @Test
    void sparaAlVuotoSenzaMunizioniLanciaEccezione() {
        Partita partita = creaPartitaArmata(null);
        partita.entraInStanza(0);
        svuotaCaricatore(partita);
        assertThrows(IllegalStateException.class, () -> partita.sparaAlVuoto());
    }

    @Test
    void sparaSenzaMunizioniLanciaEccezione() {
        Partita partita = creaPartitaArmata(new Nemico("avversario", 100, new Arma("Fucile", 10, 60, 15)));
        partita.entraInStanza(0);
        svuotaCaricatore(partita);
        assertThrows(IllegalStateException.class, () -> partita.spara());
    }

    private Partita creaPartitaConDueStanze(Stanza prima) {
        return new Partita(new Soldato("io"),
                new Mappa(List.of(prima, new Stanza("armeria", "Armi"))),
                new Combattimento(() -> 0));
    }

    @Test
    void raccogliArmaLaSpostaDallaStanzaAlSoldato() {
        Stanza stanza = new Stanza("ingresso", "Una porta blindata");
        Arma arma = new Arma("Pistola", 15, 75, 12);
        stanza.aggiungiArma(arma);
        Partita partita = creaPartitaConDueStanze(stanza);
        partita.entraInStanza(0);
        partita.raccogliArma(arma);
        assertTrue(partita.getSoldato().getArmi().contains(arma));
        assertFalse(stanza.getArmi().contains(arma));
    }

    @Test
    void raccogliArmaNonPresenteLanciaEccezione() {
        Partita partita = creaPartitaConDueStanze(new Stanza("ingresso", "Una porta blindata"));
        partita.entraInStanza(0);
        assertThrows(IllegalArgumentException.class,
                () -> partita.raccogliArma(new Arma("Pistola", 15, 75, 12)));
    }

    @Test
    void raccogliConsumabileLoSpostaDallaStanzaAlSoldato() {
        Stanza stanza = new Stanza("ingresso", "Una porta blindata");
        Consumabile kit = new KitMedico(30);
        stanza.aggiungiConsumabile(kit);
        Partita partita = creaPartitaConDueStanze(stanza);
        partita.entraInStanza(0);
        partita.raccogliConsumabile(kit);
        assertTrue(partita.getSoldato().getConsumabili().contains(kit));
        assertFalse(stanza.getConsumabili().contains(kit));
    }

    @Test
    void liberaOstaggioLoLibera() {
        Stanza stanza = new Stanza("ingresso", "Una porta blindata");
        Ostaggio ostaggio = new Ostaggio("prova");
        stanza.aggiungiOstaggio(ostaggio);
        Partita partita = creaPartitaConDueStanze(stanza);
        partita.entraInStanza(0);
        partita.liberaOstaggio(ostaggio);
        assertTrue(ostaggio.isLiberato());
    }

    @Test
    void liberaOstaggioConNemicoPresenteLanciaEccezione() {
        Stanza stanza = new Stanza("ingresso", "Una porta blindata");
        Ostaggio ostaggio = new Ostaggio("prova");
        stanza.aggiungiOstaggio(ostaggio);
        stanza.inserisciNemico(new Nemico("avversario", 100, new Arma("Fucile", 10, 60, 15)));
        Partita partita = creaPartitaConDueStanze(stanza);
        partita.entraInStanza(0);
        assertThrows(IllegalStateException.class, () -> partita.liberaOstaggio(ostaggio));
    }

    @Test
    void attraversaPortaPassaAllaStanzaSuccessiva() {
        Partita partita = creaPartitaConDueStanze(new Stanza("ingresso", "Una porta blindata"));
        partita.entraInStanza(0);
        partita.attraversaPorta();
        assertEquals(1, partita.getIndiceStanzaCorrente());
        assertEquals(Set.of(0), partita.getStanzeEsplorate());
    }

    @Test
    void attraversaPortaConNemicoLanciaEccezione() {
        Stanza stanza = new Stanza("ingresso", "Una porta blindata");
        stanza.inserisciNemico(new Nemico("avversario", 100, new Arma("Fucile", 10, 60, 15)));
        Partita partita = creaPartitaConDueStanze(stanza);
        partita.entraInStanza(0);
        assertThrows(IllegalStateException.class, () -> partita.attraversaPorta());
    }

    @Test
    void attraversaPortaConOstaggioDaLiberareLanciaEccezione() {
        Stanza stanza = new Stanza("ingresso", "Una porta blindata");
        stanza.aggiungiOstaggio(new Ostaggio("prova"));
        Partita partita = creaPartitaConDueStanze(stanza);
        partita.entraInStanza(0);
        assertThrows(IllegalStateException.class, () -> partita.attraversaPorta());
    }

    @Test
    void attraversaPortaDellUltimaStanzaPortaAllaVittoria() {
        Partita partita = new Partita(new Soldato("io"),
                new Mappa(List.of(new Stanza("ingresso", "Una porta blindata"))),
                new Combattimento(() -> 0));
        partita.entraInStanza(0);
        partita.attraversaPorta();
        assertEquals(VITTORIA, partita.getStato());
    }
}


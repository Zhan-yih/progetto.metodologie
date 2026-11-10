package it.unicam.cs.mpgc.rpg130680.persistence;

import it.unicam.cs.mpgc.rpg130680.model.entity.Soldato;
import it.unicam.cs.mpgc.rpg130680.model.item.Arma;
import it.unicam.cs.mpgc.rpg130680.model.item.Consumabile;
import it.unicam.cs.mpgc.rpg130680.model.item.KitMedico;
import it.unicam.cs.mpgc.rpg130680.service.Combattimento;
import it.unicam.cs.mpgc.rpg130680.service.Partita;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.nio.file.Path;

public class XmlSalvataggioRepository implements SalvataggioRepository {
    private final Path file;

    public XmlSalvataggioRepository(Path file) {
        if(file == null){
            throw new IllegalArgumentException("file nullo");
        }
        this.file = file;
    }

    @Override
    public void salva(Partita partita){
        if(partita == null){
            throw new IllegalArgumentException("partita nulla");
        }
        try {
            Document doc = DocumentBuilderFactory.newInstance()
                    .newDocumentBuilder().newDocument();
            doc.appendChild(creaPartita(doc, partita));

            Transformer transformer = TransformerFactory.newInstance().newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.transform(new DOMSource(doc), new StreamResult(file.toFile()));
        } catch (ParserConfigurationException | TransformerException e) {
            throw new SalvataggioException("Errore nel salvataggio", e);
        }
    }

    @Override
    public Partita carica(Combattimento combattimento){
        throw new UnsupportedOperationException("da implementare");
    }

    private Element creaPartita(Document doc, Partita partita){
        Element radice=doc.createElement("partita");
        radice.setAttribute("stanzaCorrente", String.valueOf(partita.getIndiceStanzaCorrente()));
        radice.appendChild(creaSoldato(doc, partita.getSoldato()));
        return radice;
    }

    private Element creaArma(Document doc, Arma arma) {
        Element e = doc.createElement("arma");
        e.setAttribute("nome", arma.getNome());
        e.setAttribute("danno", String.valueOf(arma.getDanno()));
        e.setAttribute("precisione", String.valueOf(arma.getPrecisione()));
        e.setAttribute("munizioni", String.valueOf(arma.getMunizioni()));
        return e;
    }

    private Element creaSoldato(Document doc, Soldato soldato) {
        Element e = doc.createElement("soldato");
        e.setAttribute("nome", soldato.getNome());
        e.setAttribute("punti", String.valueOf(soldato.getPuntiVita()));

        int indiceArma = soldato.getArmaEquipaggiata()
                .map(a -> soldato.getArmi().indexOf(a))
                .orElse(-1);
        e.setAttribute("armaEquipaggiata", String.valueOf(indiceArma));

        Element armi = doc.createElement("armi");
        soldato.getArmi().forEach(a -> armi.appendChild(creaArma(doc, a)));
        e.appendChild(armi);

        Element consumabili = doc.createElement("consumabili");
        soldato.getConsumabili().forEach(c -> consumabili.appendChild(creaConsumabile(doc, c)));
        e.appendChild(consumabili);

        return e;
    }

    private Element creaConsumabile(Document doc, Consumabile c) {
        Element e = doc.createElement("consumabile");
        e.setAttribute("tipo", ConvertitoreConsumabile.tipoDi(c));
        if (c instanceof KitMedico kit) {
            e.setAttribute("quantita", String.valueOf(kit.getQuantita()));
        }
        return e;
    }

}

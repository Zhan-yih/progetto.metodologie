package it.unicam.cs.mpgc.rpg130680.persistence;

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
        return radice;
    }
}

package it.unicam.cs.mpgc.rpg130680.persistence;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import javax.xml.xpath.*;
import java.util.ArrayList;
import java.util.List;

class LettoreXml {
    private final XPath xpath = XPathFactory.newInstance().newXPath();

    String testo(Node contesto, String espressione) {
        try {
            return xpath.evaluate(espressione, contesto);
        } catch (XPathExpressionException e) {
            throw new SalvataggioException("Espressione XPath non valida: " + espressione, e);
        }
    }

    int intero(Node contesto, String espressione) {
        String valore = testo(contesto, espressione);
        try {
            return Integer.parseInt(valore);
        } catch (NumberFormatException e) {
            throw new SalvataggioException("Valore non numerico per " + espressione + ": " + valore, e);
        }
    }

    boolean booleano(Node contesto, String espressione) {
        return Boolean.parseBoolean(testo(contesto, espressione));
    }

    List<Element> elementi(Node contesto, String espressione) {
        try {
            NodeList nodi = (NodeList) xpath.evaluate(espressione, contesto, XPathConstants.NODESET);
            List<Element> risultato = new ArrayList<>();
            for (int i = 0; i < nodi.getLength(); i++) {
                risultato.add((Element) nodi.item(i));
            }
            return risultato;
        } catch (XPathExpressionException e) {
            throw new SalvataggioException("Espressione XPath non valida: " + espressione, e);
        }
    }
}


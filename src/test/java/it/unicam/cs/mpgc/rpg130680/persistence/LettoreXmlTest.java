package it.unicam.cs.mpgc.rpg130680.persistence;

import org.junit.jupiter.api.Test;
import org.w3c.dom.Element;
import org.xml.sax.InputSource;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.StringReader;
import static org.junit.jupiter.api.Assertions.*;

public class LettoreXmlTest {

    private Element radice(String xml) throws Exception {
        return DocumentBuilderFactory.newInstance().newDocumentBuilder()
                .parse(new InputSource(new StringReader(xml))).getDocumentElement();
    }

    @Test
    void testoLeggeUnAttributo() throws Exception {
        Element r = radice("<a nome=\"x\"/>");
        assertEquals("x", new LettoreXml().testo(r, "@nome"));
    }

    @Test
    void interoLeggeUnNumero() throws Exception {
        Element r = radice("<a n=\"5\"/>");
        assertEquals(5, new LettoreXml().intero(r, "@n"));
    }

    @Test
    void interoNonNumericoLanciaEccezione() throws Exception {
        Element r = radice("<a n=\"abc\"/>");
        assertThrows(SalvataggioException.class, () -> new LettoreXml().intero(r, "@n"));
    }

    @Test
    void booleanoLeggeTrueEFalse() throws Exception {
        Element r = radice("<a v=\"true\" f=\"false\"/>");
        LettoreXml lettore = new LettoreXml();
        assertTrue(lettore.booleano(r, "@v"));
        assertFalse(lettore.booleano(r, "@f"));
    }

    @Test
    void elementiRestituisceLaListaGiusta() throws Exception {
        Element r = radice("<a><b/><b/></a>");
        assertEquals(2, new LettoreXml().elementi(r, "b").size());
    }
}


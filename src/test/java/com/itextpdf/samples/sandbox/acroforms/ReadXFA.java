package com.itextpdf.samples.sandbox.acroforms;

import com.itextpdf.core.pdf.PdfDocument;
import com.itextpdf.core.pdf.PdfReader;
import com.itextpdf.core.pdf.PdfWriter;
import com.itextpdf.core.testutils.annotations.type.SampleTest;
import com.itextpdf.forms.PdfAcroForm;
import com.itextpdf.forms.xfa.XfaForm;
import com.itextpdf.samples.GenericTest;
import org.junit.experimental.categories.Category;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.FileOutputStream;

@Category(SampleTest.class)
public class ReadXFA extends GenericTest {

    public static final String SRC = "./src/test/resources/sandbox/acroforms/xfa_form_poland.pdf";
    public static final String DEST = "./target/test/resources/sandbox/acroforms/xfa_form_poland.xml";

    public static void main(String[] args) throws Exception {
        File file = new File(DEST);
        file.getParentFile().mkdirs();
        new ReadXFA().manipulatePdf(DEST);
    }

    @Override
    protected void manipulatePdf(String dest) throws Exception {
        compareXml = true;
        PdfDocument pdfDocument = new PdfDocument(new PdfReader(SRC));
        PdfAcroForm form = PdfAcroForm.getAcroForm(pdfDocument, true);
        XfaForm xfa = form.getXfaForm();
        Node node = xfa.getDatasetsNode();
        NodeList list = node.getChildNodes();
        for (int i = 0; i < list.getLength(); i++) {
            if("data".equals(list.item(i).getLocalName())) {
                node = list.item(i);
                break;
            }
        }
        list = node.getChildNodes();
        for (int i = 0; i < list.getLength(); i++) {
            if("movies".equals(list.item(i).getLocalName())) {
                node = list.item(i);
                break;
            }
        }
        Transformer tf = TransformerFactory.newInstance().newTransformer();
        tf.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
        tf.setOutputProperty(OutputKeys.INDENT, "yes");
        FileOutputStream os = new FileOutputStream(DEST);
        tf.transform(new DOMSource(node), new StreamResult(os));
        pdfDocument.close();
    }

}
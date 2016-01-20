package com.itextpdf.samples.book.part2.chapter07;

import com.itextpdf.basics.io.ByteArrayOutputStream;
import com.itextpdf.core.pdf.PdfDocument;
import com.itextpdf.core.pdf.PdfReader;
import com.itextpdf.core.pdf.PdfWriter;
import com.itextpdf.core.pdf.action.PdfAction;
import com.itextpdf.core.testutils.annotations.type.SampleTest;
import com.itextpdf.model.Document;
import com.itextpdf.model.element.Link;
import com.itextpdf.model.element.Paragraph;
import com.itextpdf.samples.GenericTest;
import com.lowagie.database.DatabaseConnection;
import com.lowagie.database.HsqldbConnection;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.junit.experimental.categories.Category;

@Category(SampleTest.class)
public class Listing_07_09_FindDirectors extends GenericTest {
    public static final String DEST
            = "./target/test/resources/book/part2/chapter07/Listing_07_09_FindDirectors.pdf";
    public static final String RESOURCE
            = "./src/test/resources/book/part2/chapter07/find_director.js";

    public static final String NESTED_TABLES = "./src/test/resources/book/part1/chapter04/cmp_Listing_04_17_NestedTables.pdf";

    protected String[] arguments;

    public static void main(String args[]) throws IOException, SQLException {
        new Listing_07_07_AddVersionChecker().manipulatePdf(DEST);
    }

    protected static String readFileToString(String path) throws IOException {
        File file = new File(path);
        byte[] jsBytes = new byte[(int) file.length()];
        FileInputStream f = new FileInputStream(file);
        f.read(jsBytes);
        return new String(jsBytes);
    }

    public void manipulatePdf(String dest) throws IOException, SQLException {
        // new Listing_04_17_NestedTables().main(arguments);
        manipulatePdf2(DEST);
    }

    public void manipulatePdf2(String dest) throws IOException, SQLException {
        // Create a database connection and statement
        DatabaseConnection connection = new HsqldbConnection("filmfestival");
        Statement stm = connection.createStatement();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PdfDocument pdfDoc = new PdfDocument(new PdfWriter(baos));
        Document doc = new Document(pdfDoc);
        ResultSet rs = stm.executeQuery(
                "SELECT name, given_name FROM film_director ORDER BY name, given_name");
        while (rs.next()) {
            doc.add(createDirectorParagraph(pdfDoc, rs));
        }

        // Close the database connection and statement
        stm.close();
        connection.close();
        doc.close();

        // Create pdfDocuments
        PdfDocument[] pdfDocumentses = {
                new PdfDocument(new PdfReader(new ByteArrayInputStream(baos.toByteArray()))),
                new PdfDocument(new PdfReader(NESTED_TABLES))};

        pdfDoc = new PdfDocument(new PdfWriter(DEST));
        pdfDoc.getCatalog()
                .setOpenAction(PdfAction.createJavaScript(pdfDoc, readFileToString(RESOURCE)));
        int n;
        for (int i = 0; i < pdfDocumentses.length; i++) {
            n = pdfDocumentses[i].getNumberOfPages();
            pdfDocumentses[i].copyPages(1, n, pdfDoc);
//            for (int j = 1; j <= n; j++) {
//                PdfFormXObject page = pdfDocumentses[i].getPage(j).copyAsFormXObject(pdfDoc);
//                new PdfCanvas(pdfDoc.addNewPage()).addXObject(page, 0, 0);
//            }
        }

        pdfDoc.close();
        for (int i = 0; i < pdfDocumentses.length; i++) {
            pdfDocumentses[i].close();
        }
    }

    /**
     * Creates a Phrase with the name and given name of a director
     * using different fonts.
     *
     * @param rs the ResultSet containing director records.
     */
    public Paragraph createDirectorParagraph(PdfDocument pdfDoc, ResultSet rs) throws SQLException {
        String strName = rs.getString("name");
        PdfAction action = PdfAction.createJavaScript(pdfDoc, String.format("findDirector('%s');", strName));
        StringBuffer buffer = new StringBuffer();
        buffer.append(strName);
        buffer.append(", ");
        buffer.append(rs.getString("given_name"));
        Link name = new Link(buffer.toString(), action);
        return new Paragraph(name);
    }
}
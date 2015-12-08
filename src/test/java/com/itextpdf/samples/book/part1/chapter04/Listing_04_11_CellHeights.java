package com.itextpdf.samples.book.part1.chapter04;

import com.itextpdf.basics.geom.PageSize;
import com.itextpdf.core.pdf.PdfDocument;
import com.itextpdf.core.pdf.PdfWriter;
import com.itextpdf.core.testutils.annotations.type.SampleTest;
import com.itextpdf.model.Document;
import com.itextpdf.model.element.Cell;
import com.itextpdf.model.element.Paragraph;
import com.itextpdf.model.element.Table;
import com.itextpdf.samples.GenericTest;

import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.SQLException;

import org.junit.Ignore;
import org.junit.experimental.categories.Category;

@Ignore
@Category(SampleTest.class)
public class Listing_04_11_CellHeights extends GenericTest {
    static public final String DEST = "./target/test/resources/book/part1/chapter04/Listing_04_11_CellHeights.pdf";

    public static void main(String args[]) throws IOException, SQLException {
        new Listing_04_11_CellHeights().manipulatePdf(DEST);
    }

    public void manipulatePdf(String dest) throws IOException, SQLException {
        FileOutputStream fos = new FileOutputStream(dest);
        PdfWriter writer = new PdfWriter(fos);
        PdfDocument pdfDoc = new PdfDocument(writer);
        Document doc = new Document(pdfDoc, new PageSize(PageSize.A5).rotate());

        Table table = new Table(2);
        // a long phrase
        Paragraph p = new Paragraph(
                "Dr. iText or: How I Learned to Stop Worrying and Love PDF.");
        Cell cell = new Cell().add(p);
        // the prhase is wrapped
        table.addCell(new Cell().add(new Paragraph("wrap")));
        // TODO No setNoWrap(boolean)
        // cell.setNoWrap(false);
        table.addCell(cell);
        // the phrase isn't wrapped
        table.addCell(new Cell().add(new Paragraph("no wrap")));
        // cell.setNoWrap(true);
        table.addCell(cell);
        // a long phrase with newlines
        p = new Paragraph(
                "Dr. iText or:\nHow I Learned to Stop Worrying\nand Love PDF.");
        cell = new Cell().add(p);
        // the phrase fits the fixed height
        table.addCell(new Cell().add(new Paragraph("fixed height (more than sufficient)")));
        // TODO No setFixedHeight
        // cell.setHeight(72f);
        table.addCell(cell);
        // the phrase doesn't fit the fixed height
        table.addCell(new Cell().add(new Paragraph("fixed height (not sufficient)")));
        cell.setHeight(36f);
        table.addCell(cell);
        // The minimum height is exceeded
        table.addCell(new Cell().add(new Paragraph("minimum height")));
        cell = new Cell().add(new Paragraph("Dr. iText"));
        // TODO No setMinimumHeight(float)
        cell.setHeight(36f);
        table.addCell(cell);
        // The last row is extended
        // TODO No setExtendedLastRow
        // table.setExtendLastRow(true);
        table.addCell(new Cell().add(new Paragraph("extend last row")));
        table.addCell(cell);
        doc.add(table);
        doc.close();
    }
}

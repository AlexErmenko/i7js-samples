package com.itextpdf.samples.book.part4.chapter13;

import com.itextpdf.core.pdf.PdfDictionary;
import com.itextpdf.core.pdf.PdfDocument;
import com.itextpdf.core.pdf.PdfName;
import com.itextpdf.core.pdf.PdfNumber;
import com.itextpdf.core.pdf.PdfReader;
import com.itextpdf.core.pdf.PdfWriter;
import com.itextpdf.core.testutils.annotations.type.SampleTest;
import com.itextpdf.core.xmp.XMPException;
import com.itextpdf.samples.GenericTest;

import java.io.IOException;
import java.sql.SQLException;

import org.junit.experimental.categories.Category;

@Category(SampleTest.class)
public class Listing_13_09_RotatePages extends GenericTest {
    public static final String DEST
            = "./target/test/resources/book/part4/chapter13/Listing_13_09_RotatePages.pdf";
    public static final String MOVIE_TEMPLATES
            = "./src/test/resources/book/part1/chapter03/cmp_Listing_03_29_MovieTemplates.pdf";

    public static void main(String args[]) throws IOException, SQLException, XMPException {
        new Listing_13_09_RotatePages().manipulatePdf(DEST);
    }

    @Override
    protected void manipulatePdf(String dest) throws IOException, SQLException, XMPException {
        changePdf(MOVIE_TEMPLATES, dest);
    }

    public void changePdf(String src, String dest) throws IOException {
        PdfDocument pdfDoc = new PdfDocument(new PdfReader(src), new PdfWriter(dest));
        int n = pdfDoc.getNumberOfPages();
        PdfDictionary pageDict;
        int rot;
        for (int i = 1; i <= n; i++) {
            rot = pdfDoc.getPage(i).getRotation();
            pageDict = pdfDoc.getPage(i).getPdfObject();
            pageDict.put(PdfName.Rotate, new PdfNumber(rot + 90));
        }
        pdfDoc.close();
    }
}

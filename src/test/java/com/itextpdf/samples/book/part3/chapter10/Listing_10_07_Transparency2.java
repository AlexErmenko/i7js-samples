package com.itextpdf.samples.book.part3.chapter10;

import com.itextpdf.basics.geom.Rectangle;
import com.itextpdf.canvas.PdfCanvas;
import com.itextpdf.core.color.Color;
import com.itextpdf.core.color.DeviceCmyk;
import com.itextpdf.core.pdf.PdfDocument;
import com.itextpdf.core.pdf.PdfWriter;
import com.itextpdf.core.pdf.colorspace.PdfDeviceCs;
import com.itextpdf.core.pdf.colorspace.PdfPattern;
import com.itextpdf.core.pdf.colorspace.PdfShading;
import com.itextpdf.core.pdf.extgstate.PdfExtGState;
import com.itextpdf.core.pdf.xobject.PdfFormXObject;
import com.itextpdf.core.testutils.annotations.type.SampleTest;
import com.itextpdf.samples.GenericTest;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.junit.Ignore;
import org.junit.experimental.categories.Category;

@Ignore
@Category(SampleTest.class)
public class Listing_10_07_Transparency2 extends GenericTest {
    public static final String DEST = "./target/test/resources/book/part3/chapter10/Listing_10_07_Transparency2.pdf";

    public static void main(String args[]) throws IOException {
        new Listing_10_07_Transparency2().manipulatePdf(DEST);
    }

    public void manipulatePdf(String dest) throws FileNotFoundException {
        try {
            PdfDocument pdfDoc = new PdfDocument(new PdfWriter(DEST));
            PdfCanvas canvas = new PdfCanvas(pdfDoc.addNewPage());
            float gap = (pdfDoc.getDefaultPageSize().getWidth() - 400) / 3;

            pictureBackdrop(gap, 500, canvas, pdfDoc);
            pictureBackdrop(200 + 2 * gap, 500, canvas, pdfDoc);
            pictureBackdrop(gap, 500 - 200 - gap, canvas, pdfDoc);
            pictureBackdrop(200 + 2 * gap, 500 - 200 - gap, canvas, pdfDoc);
            PdfFormXObject tp;
            PdfCanvas xObjectCanvas;
            // TODO No PdfTransparencyGroup
            // PdfTransparencyGroup group;

            tp = new PdfFormXObject(new Rectangle(200, 200));
            xObjectCanvas = new PdfCanvas(tp, pdfDoc);
            pictureCircles(0, 0, xObjectCanvas);
            // group = new PdfTransparencyGroup();
            // group.setIsolated(true);
            // group.setKnockout(true);
            // tp.setGroup(group);
            canvas.addXObject(tp, gap, 500);

            tp = new PdfFormXObject(new Rectangle(200, 200));
            xObjectCanvas = new PdfCanvas(tp, pdfDoc);
            pictureCircles(0, 0, xObjectCanvas);
            // group = new PdfTransparencyGroup();
            // group.setIsolated(true);
            // group.setKnockout(false);
            // tp.setGroup(group);
            canvas.addXObject(tp, 200 + 2 * gap, 500);

            tp = new PdfFormXObject(new Rectangle(200, 200));
            xObjectCanvas = new PdfCanvas(tp, pdfDoc);
            pictureCircles(0, 0, xObjectCanvas);
            // group = new PdfTransparencyGroup();
            // group.setIsolated(false);
            // group.setKnockout(true);
            // tp.setGroup(group);
            canvas.addXObject(tp, gap, 500 - 200 - gap);

            tp = new PdfFormXObject(new Rectangle(200, 200));
            xObjectCanvas = new PdfCanvas(tp, pdfDoc);
            pictureCircles(0, 0, xObjectCanvas);
            // group = new PdfTransparencyGroup();
            // group.setIsolated(false);
            // group.setKnockout(false);
            // tp.setGroup(group);
            canvas.addXObject(tp, 200 + 2 * gap, 500 - 200 - gap);

            pdfDoc.close();
        } catch (IOException ioe) {
            System.err.println(ioe.getMessage());
        }
    }

    /**
     * Prints a square and fills half of it with a gray rectangle.
     *
     * @param x
     * @param y
     * @param canvas
     * @throws Exception
     */
    public static void pictureBackdrop(float x, float y, PdfCanvas canvas,
                                       PdfDocument pdfDoc) {
        PdfShading.Axial axial = new PdfShading.Axial(new PdfDeviceCs.Rgb(), x, y, Color.YELLOW.getColorValue(),
                x + 200, y, Color.RED.getColorValue(), pdfDoc);
        PdfPattern.Shading axialPattern = new PdfPattern.Shading(axial);
        canvas.setFillColorShading(axialPattern);
        canvas.setStrokeColor(Color.BLACK);
        canvas.setLineWidth(2);
        canvas.rectangle(x, y, 200, 200);
        canvas.fillStroke();
    }

    /**
     * Prints 3 circles in different colors that intersect with eachother.
     *
     * @param x
     * @param y
     * @param canvas
     * @throws Exception
     */
    public static void pictureCircles(float x, float y, PdfCanvas canvas) {
        PdfExtGState gs = new PdfExtGState();
        gs.setBlendMode(PdfExtGState.BM_MULTIPLY);
        gs.setFillOpacity(1f);
        canvas.setExtGState(gs);
        canvas.setFillColor(new DeviceCmyk(0f, 0f, 0f, 0.15f));
        canvas.circle(x + 75, y + 75, 70);
        canvas.fill();
        canvas.circle(x + 75, y + 125, 70);
        canvas.fill();
        canvas.circle(x + 125, y + 75, 70);
        canvas.fill();
        canvas.circle(x + 125, y + 125, 70);
        canvas.fill();
    }
}
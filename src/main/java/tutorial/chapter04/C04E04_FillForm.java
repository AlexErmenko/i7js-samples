package tutorial.chapter04;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfReader;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.forms.PdfAcroForm;
import com.itextpdf.forms.fields.PdfFormField;

import java.io.*;
import java.util.Map;

/**
 * Simple filling out form example.
 */
public class C04E04_FillForm {

    public static final String SRC = "src/main/resources/pdf/job_application.pdf";
    public static final String DEST = "results/chapter04/fill_form.pdf";

    public static void main(String args[]) throws IOException {
        File file = new File(DEST);
        file.getParentFile().mkdirs();
        new C04E04_FillForm().manipulatePdf(DEST);
    }

    public void manipulatePdf(String dest) throws IOException {

        //Initialize PDF reader
        PdfReader reader = new PdfReader(SRC);

        //Initialize PDF writer
        PdfWriter writer = new PdfWriter(dest);

        //Initialize PDF document
        PdfDocument pdfDoc = new PdfDocument(reader, writer);

        PdfAcroForm form = PdfAcroForm.getAcroForm(pdfDoc, true);
        Map<String, PdfFormField> fields = form.getFormFields();
        fields.get("name").setValue("James Bond");
        fields.get("language").setValue("English");
        fields.get("experience1").setValue("Off");
        fields.get("experience2").setValue("Yes");
        fields.get("experience3").setValue("Yes");
        fields.get("shift").setValue("Any");
        fields.get("info").setValue("I was 38 years old when I became a 007 agent.");

        pdfDoc.close();

    }
}
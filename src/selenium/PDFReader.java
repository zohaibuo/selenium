package selenium;

import java.io.File;
import java.io.IOException;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.encryption.InvalidPasswordException;
import org.apache.pdfbox.text.PDFTextStripper;

public class PDFReader {
	

	static PDDocument pd;
	static PDFTextStripper pdf;

	public static void main(String[] args) throws InvalidPasswordException, IOException {
		// TODO Auto-generated method stub
		
		pd = PDDocument.load(new File(System.getProperty("user.dir")+"\\R357874230.pdf"));
		System.out.println("Number of Pages in PDF Document : "+pd.getNumberOfPages());
		
		// Read PDF Content
		pdf = new PDFTextStripper();
		/*System.out.println("PDF Content : ");
		System.out.println();
		System.out.println(pdf.getText(pd));*/
		
		if(pdf.getText(pd).contains("663252180013109850")) {
			System.out.println("Status -- Passed | Expected text is found in the PDF document");
		}else {
			System.out.println("Status -- Failed | Expected text is not found in the PDF document");
		}
		
	}

}

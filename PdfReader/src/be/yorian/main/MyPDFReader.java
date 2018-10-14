package be.yorian.main;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Array;
import java.text.ParseException;
import java.util.ArrayList;

import org.apache.pdfbox.cos.COSDocument;
import org.apache.pdfbox.io.RandomAccessFile;
import org.apache.pdfbox.pdfparser.PDFParser;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

import be.yorian.entities.Transactie;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;


public class MyPDFReader {

	private PDFParser parser;
	private PDFTextStripper pdfStripper;
	private PDDocument pdDoc;
	private COSDocument cosDoc;
	private String text;
	private ObservableList<Transactie> transactions = FXCollections.observableArrayList();
	private String lines[];
	private String jaartal;

	// Constructor
	public MyPDFReader() {
	}

	
	// Zet een pdf-file om naar text
	public String zetOmNaarText(File file) throws IOException {
		this.pdfStripper = null;
		this.pdDoc = null;
		this.cosDoc = null;

		parser = new PDFParser(new RandomAccessFile(file, "r"));

		parser.parse();
		cosDoc = parser.getDocument();
		pdfStripper = new PDFTextStripper();
		try {
			pdDoc = new PDDocument(cosDoc);
			pdDoc.getNumberOfPages();
			pdfStripper.setStartPage(1);
			pdfStripper.setEndPage(pdDoc.getNumberOfPages());

			text = pdfStripper.getText(pdDoc);
		}
		finally {
			pdDoc.close();
		}
		System.out.println(text);
		return text;
		
	}

	
	
	public ObservableList<Transactie> filterTransacties() throws ParseException{
		int counter = 0;
		this.lines = text.split("[\\r\\n]+");

		for (String line : this.lines) {
			
			String words[] = line.split("\\s+");
			if (words.length >= 2) {
				
				// check jaartal
				if (words[0].equals("Premium")) {
					this.jaartal = words[4] ;
				}
				// check transactie nummer
				if (words[0].equals("") && words[1].matches("[0-9]{4}") && !words[2].matches("[0-9]{4}")) {
					 
					// zoek teken + of -
					int lengte = words.length;
					if(words[lengte -1].equals("+") || words[lengte -1].equals("-")){
						mapShortTransactie(words);
					} else {
						mapLongTransactie(counter);
					}
				}
			}
			counter++;
		}
		
		return transactions;
	}
	
	private void mapLongTransactie(int positieEersteLijn) throws ParseException {
		
		Transactie tx = null;
		int positieLaatsteLijn = positieEersteLijn;
		// behandel 1e lijn
		String line = this.lines[positieEersteLijn];
		String eersteLijn[] = line.split("\\s+");
		
		tx = new Transactie(eersteLijn[1]);
		
		
		// zoek laatste lijn
		String laatsteLijn[] = eersteLijn;
		
		while(!heeftTeken(laatsteLijn)){
			positieLaatsteLijn++;
			line = this.lines[positieLaatsteLijn];
			laatsteLijn = line.split("\\s+");
			if(!heeftTeken(laatsteLijn)) {
				eersteLijn = concatenate(eersteLijn,laatsteLijn);
			}
		}
		int lengte = laatsteLijn.length;
		if(laatsteLijn[lengte-1].equals("-")){		
			tx.setTeken(laatsteLijn[lengte-1]);
			tx.setBedrag(laatsteLijn[lengte -2]);
			tx.setDatum(laatsteLijn[lengte -3] + "-" + jaartal);
			System.out.println("negatieve transx: " + tx.getTxnummer());
			this.transactions.add(tx);
		} else{
			tx.setTeken("+");
			tx.setPositiefBedrag(laatsteLijn[lengte-1]);
			tx.setDatum(laatsteLijn[lengte -2] + "-" + jaartal);
			System.out.println("positieve transx: " + tx.getTxnummer());
			this.transactions.add(tx);
		}
		// omschrijving
		tx.setOmschrijving(bepaalLangeOmschrijving(eersteLijn.length, eersteLijn));
	}

	private void mapShortTransactie(String[] words) throws ParseException {
		
		int lengte = words.length;
		
		Transactie tx = new Transactie(words[1]);
		tx.setOmschrijving(bepaalKorteOmschrijving(lengte, words));
		tx.setTeken(words[lengte -1]);
		tx.setBedrag(words[lengte -2]);
		tx.setDatum(words[lengte -3] + "-" + jaartal);
		
		this.transactions.add(tx);
		
	}

	
	private String bepaalKorteOmschrijving(int lengte, String[] words) {
		String omschrijving = "";
		for(int i = 2; i < lengte-3 ;i++) {
			omschrijving = omschrijving + words[i] + " ";
		}
		System.out.println(omschrijving);
		return omschrijving;
	}
	
	private String bepaalLangeOmschrijving(int lengte, String[] words) {
		String omschrijving = "";
		for(int i = 2; i < lengte ;i++) {
			omschrijving = omschrijving + words[i] + " ";
		}
		System.out.println(omschrijving);
		return omschrijving;
	}


	public <T> T[] concatenate(T[] a, T[] b) {
	    int aLen = a.length;
	    int bLen = b.length;

	    @SuppressWarnings("unchecked")
	    T[] c = (T[]) Array.newInstance(a.getClass().getComponentType(), aLen + bLen);
	    System.arraycopy(a, 0, c, 0, aLen);
	    System.arraycopy(b, 0, c, aLen, bLen);

	    return c;
	}
	private boolean heeftTeken(String words[]){
		
		boolean heeftTeken = false;
		int lengte = words.length;
		
		if(words[lengte -1].contains("+") || words[lengte -1].equals("-")) {
			heeftTeken = true;
		}
		
		return heeftTeken;
	}

}

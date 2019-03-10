package be.yorian.entities;

public enum Categorie {
		
	ALLERLEI("allerlei"),
	ACV("acv"),
	AUTO("auto"),
	BANCCONTACT("banccontact"),
	BANK("bank"),
	BELASTINGEN("belastingen"),
	DIEREN("dieren"),
	ELECTRO("electro"),
	ENERGIE("energie"),
	GEZONDHEID("gezondheid"),
	GSM("gsm"),
	KINDERGELD("kindergeld"),
	KLEDING("kleding"),
	KLUSSEN("klussen"),
	KREDIET("krediet"),
	LENING("lening"),
	LIDGELD("lidgeld"),
	LOON("loon"),
	NIEUWBOUW("nieuwbouw"),
	ONBEPAALD("..."),
	OPNAMESPAARGELD("opnamespaargeld"),
	PENSIOENSPAREN("pensioensparen"),
	SCHOOL("school"),
	SPAREN("sparen"),
	TANKEN("tanken"),
	TELENET("Telenet"),
	TUIN("tuin"),
	UITSTAPPEN("uitstappen"),
	VERZEKERING("verzekering"),
	VOEDING("voeding"),
	ZIEKENFONDS("ziekenfonds");
	
	

	private String categorie;
	
	
	private Categorie(String categorie) {
		this.categorie = categorie;
	}

	public String getCategorie() {
		return categorie;
	}
	
	
}

1. STRUKTURA KLASA I ODNOSI

* 'Person' interface definise "ugovor"/ponasanje za sve objekte koji predstavljaju osobu
- Ovaj interfejs sadrzi dvije metode: 'getIdentity()' i 'getTitle()'

* 'Engineer' apstraktna klasa koja implementira 'Person' interfejs i ona sluzi kao roditelj 
za sve tipove inzenjera i sadrzi zajednicke podatke: Name, LastName, internalTitle, experience, expertises.
- Ova klasa sadrzi init blok za validaciju podataka (provjeravanje da li je lista za ekspertizu prazna i da li su godine iskustva vece od nula)
- U ovoj klasi je definisan i metod 'specificData' koji je implementiran u child klasama 

* 'SoftwareEngineer' i 'ElectricalEngineer' su izvedene klase koje nasljedjuju 'Engineer' klasu.
- One preuzimaju sve zajednicke podatke i ponasanja od 'Engineer' klase.
- Dodaju svoje specificne podatke: 'NumberOfProjects' (za software inzenjera) i 'NumberOfCertificates' (za elektro inzenjera).
- Implementiraju apstraktni metod 'specificData()' kako bi vracale specificne podatke.

-------------------------------------------------------------------------------------------

2. FUNKCIONALNE OPERACIJE

* fold() funkcija je koristena za rucno grupisanje. Listu inzenjera je svela u jednu mapu (Map<String, MutableList<Engineer>>). Pocela je sa praznom mapom (akumulatorom) i u svakoj iteraciji je dodavala inzenjera u odgovarajucu listu unutar mape koju je na kraju i vratila.

* filterIsInstance<T>() je koristena za razdvajanje inzenjera iz liste 'Engineers', a u toj listi su izmijesani softverski i elektro inzenjeri.

* reduce() je koristena na specificnim listama `softwareEng` i `electricarEng` da bi se lista smotala u jedan element (najiskusnijeg inzenjera). Radi tako što poredi elemente medjusobno dok ne ostane samo jedan element.

* groupingBy() je koristena za pripremu za agregaciju. Kreira plan grupisanja bez kreiranja. U ovom slučaju, grupisala je po imenu klase `it.javaClass.simpleName`.

* aggregate() je pozvana nakon groupingBy() i izvršila je "mini-fold" operaciju unutar svake grupe. Korištena je za sabiranje specifičnih podataka (projekata ili certifikata) kako bi se dobila ukupna statistika po tipu inženjera.

-------------------------------------------------------------------------------------------

3. POREDJENJE FOLD, REDUCE I AGGREGATE

* fold() mora imati eksplicitnu pocetnu vrijednost. Radi na cijeloj listi i radi na praznim listama, tj. vraca pocetnu vrijednost. Moze biti bilo koji tip, nevezano za tip elemenata u listi.

* reduce() nema pocetnu vrijednost. Koristi prvi element liste kao pocetnu vrijednost za akumulator. Radi na cijeloj listi. Baca iznimke ako je lista prazna. Mora biti isti tip kao i elementi u listi.

* aggregate() Nema eksplicitnu pocetnu vrijednost. Akumulator je null pri prvom ulasku u svaku grupu. Radi samo nakon 'groupingBy()'. Izvrsava se na svakoj grupi posebno. Moze biti bilo koji tip, nevezano za tip elemenata u listi.

-------------------------------------------------------------------------------------------

4. NACIN POKRETANJA PROGRAMA

* STANDARDAN RUN PROGRAMA (SHIFT+F10)
-------------------------------------------------------------------------------------------

5. NAPOMENA O KORISTENJU AI ALATA

* AI alat je koristen za detaljno objasnjenje funkcionalnih operacija fold, reduce i aggregate na generisanim primjerima, objasnjavanjem liniju po liniju koda. 
- Takodje sam koristio AI alat za sintaksne greske i otklanjanje declaration clasha, jer je bio problem u tome sto sam imao Property 'var Title: String' (sa velikim 'T') i metodu 'override fun getTitle()' koju je interfejs trazio, sto su prakticno bile dvije metode generisane sa istim imenom, jedna koja je automatski generisana iz propertyja Title (public String getTitle())i jedna koju je napisao sam interfejs (public String getTitle()).
package it.polito.tdp.bar.model;

public class Tavolo implements Comparable<Tavolo>{
	Integer capienza;
	Integer numero_tavoli;
	Integer disponibili;
	public Tavolo(Integer capienza, Integer numero_tavoli) {
		super();
		this.capienza = capienza;
		this.numero_tavoli = numero_tavoli;
		this.disponibili=numero_tavoli;
	}
	
	public void siedi() {
		
			disponibili--;
		
	}
	public void alza() {
		disponibili++;
	}

	public Integer getCapienza() {
		return capienza;
	}
	public boolean libero() {
		if(disponibili>0) return true;
		return false;
	}
	@Override
	public int compareTo(Tavolo o) {
		
		return this.capienza-o.getCapienza();
	}
	
}

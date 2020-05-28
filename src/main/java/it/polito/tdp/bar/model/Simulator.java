package it.polito.tdp.bar.model;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

import it.polito.tdp.bar.model.Event.EventType;

public class Simulator {
// Parametri di simulazione
	private final int NumEventi=2000;
	private final int MinNumPersone=1;
	private final int MaxNumPersone=10;
	private final Duration MinIntervallo= Duration.ofMinutes(1);
	private final Duration MaxIntervallo= Duration.ofMinutes(10);
	private final Duration MinConsumazione= Duration.ofMinutes(60);
	private final Duration MaxConsumazione= Duration.ofMinutes(120);
	
// Output da calcolare
	private int numero_totale_clienti;
	private int numero_clienti_soddisfatti;
	private int numero_clienti_insoddisfatti;
// Stato del sistema
	private PriorityQueue<Event> clienti;
	private PriorityQueue<Tavolo> tavoli;
	
	
	

// Inizializzazione
	public void init() {
		this.clienti=new PriorityQueue<>();
		this.tavoli=new PriorityQueue<>();
		
		tavoli.add(new Tavolo(10,2));
		tavoli.add(new Tavolo(8,4));
		tavoli.add(new Tavolo(6,4));
		tavoli.add(new Tavolo(4,5));
		
		this.numero_totale_clienti=0;
		this.numero_clienti_soddisfatti=0;
		this.numero_clienti_insoddisfatti=0;
		LocalDateTime orologio= LocalDateTime.of(2020, 01, 01, 07, 00);
		// Creazione della coda
		for(int i=0;i<this.NumEventi;i++) {
			Duration NextEvent=  Duration.ofMinutes(this.RandomTempi(MinIntervallo, MaxIntervallo));
			Duration consumazione= Duration.ofMinutes(this.RandomTempi(MinConsumazione, MaxConsumazione));
			Integer persone= (int) (Math.random()*(MaxNumPersone-MinNumPersone+1)+MinNumPersone);
			Double tolleranza = Math.random()*(0.90000000001-0.0)+0.0;
			System.out.println(tolleranza);
			Event e = new Event(EventType.ARRIVO_GRUPPO_CLIENTI,orologio,persone,consumazione,tolleranza);
			orologio=orologio.plus(NextEvent);
			clienti.add(e);
			
			}
		// esecuzione del ciclo di simulazione
				while(!this.clienti.isEmpty()) {
				Event e = this.clienti.poll();
				processEvent(e);
				//System.out.println(e.getTime());
		
												}
				System.out.println("numero_totale_clienti "+numero_totale_clienti);
				System.out.println("numero_clienti_soddisfatti "+numero_clienti_soddisfatti);
				System.out.println("numero_clienti_insoddisfatti "+numero_clienti_insoddisfatti);
				
				}
	
	
	
		
private void processEvent(Event e) {
	switch(e.getType()) {
	
	//Clienti che entrano
	case ARRIVO_GRUPPO_CLIENTI:
		numero_totale_clienti=numero_totale_clienti+e.getNum_persone();
		
		Tavolo scelto=null;
		
		for(Tavolo t: tavoli) {
			if((t.getCapienza()>e.getNum_persone())&&(t.libero())) {
				scelto=t;
				break;
			}

		}
		if(scelto==null||scelto.getCapienza()>2*e.getNum_persone()) {
			//if(e.getTolleranza()>0.9) numero_clienti_soddisfatti=numero_clienti_soddisfatti+e.getNum_persone();
			//else numero_clienti_insoddisfatti=numero_clienti_insoddisfatti+e.getNum_persone();
			int soddisfatti= (int)(e.getNum_persone()*e.getTolleranza());
			numero_clienti_soddisfatti=numero_clienti_soddisfatti+soddisfatti;
			numero_clienti_insoddisfatti=numero_clienti_insoddisfatti+(e.getNum_persone()-soddisfatti);
			
			break;
		}
		scelto.siedi();
		e.setTavolo(scelto);
		numero_clienti_soddisfatti=numero_clienti_soddisfatti+e.getNum_persone();
		clienti.add(new Event(EventType.PARTENZA_GRUPPO_CLIENTI,e.getTime().plus(e.getDurata()),scelto));
		break;
	
	//Clienti che escono
	case PARTENZA_GRUPPO_CLIENTI:
		e.getTavolo().alza();
		e.setTavolo(null);
		
		break;
	}
	
}




// Esecuzione
	
//Estrazione temporale 
	private long RandomTempi(Duration Min,Duration Max) {
		double random,result;
		random=Math.random();
		result=random*(Max.toMinutes()-Min.toMinutes()+1)+Min.toMinutes();
		
		
		return (long) result;
		
		
		
	}
}

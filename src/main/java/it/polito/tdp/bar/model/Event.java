package it.polito.tdp.bar.model;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class Event implements Comparable<Event>{
	// Eventi
	public enum EventType {
		ARRIVO_GRUPPO_CLIENTI,
		PARTENZA_GRUPPO_CLIENTI,	
	}
	// Variabili
	private EventType type ;
	private LocalDateTime time ;
	private Integer num_persone;
	private Duration durata ;
	private double tolleranza;
	private Tavolo tavolo;
	public Event(EventType type, LocalDateTime time, Integer num_persone, Duration durata, double tolleranza) {
		super();
		this.type = type;
		this.time = time;
		this.num_persone = num_persone;
		this.durata = durata;
		this.tolleranza = tolleranza;
	}
	public Event(EventType type, LocalDateTime time, Tavolo tavolo) {
		super();
		this.type = type;
		this.time = time;
		this.tavolo= tavolo;
		
		
	}
	public EventType getType() {
		return type;
	}
	public LocalDateTime getTime() {
		return time;
	}
	public Integer getNum_persone() {
		return num_persone;
	}
	public Duration getDurata() {
		return durata;
	}
	public double getTolleranza() {
		return tolleranza;
	}
	
	
	
	
	public Tavolo getTavolo() {
		return tavolo;
	}
	public void setTavolo(Tavolo tavolo) {
		this.tavolo = tavolo;
	}
	
	@Override
	public int compareTo(Event o) {
		
		return this.getTime().compareTo(o.getTime());
	}
	
}

package fr.mrcraftcod.simulator.simulation;

import fr.mrcraftcod.simulator.Environment;
import fr.mrcraftcod.simulator.metrics.MetricEventDispatcher;
import fr.mrcraftcod.simulator.simulation.events.StartEvent;
import fr.mrcraftcod.simulator.utils.UnreadableQueue;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.LongProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleLongProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.Objects;
import java.util.PriorityQueue;
import java.util.Queue;

/**
 * Simulator.
 * <p>
 * Created by Thomas Couchoud (MrCraftCod - zerderr@gmail.com) on 2018-11-07.
 *
 * @author Thomas Couchoud
 */
public class Simulator implements Runnable{
	private static final Logger LOGGER = LoggerFactory.getLogger(Simulator.class);
	private final PriorityQueue<SimulationEvent> events = new PriorityQueue<>(){
		private static final long serialVersionUID = 861836721153587412L;
		
		@Override
		public boolean add(final SimulationEvent simulationEvent){
			if(simulationEvent.getTime() < currentTime.get())
				return false;
			return super.add(simulationEvent);
		}
		
		@Override
		public boolean offer(final SimulationEvent simulationEvent){
			if(simulationEvent.getTime() < currentTime.get())
				return false;
			return super.offer(simulationEvent);
		}
	};
	private final UnreadableQueue<SimulationEvent> unreadableQueue = new UnreadableQueue<>(events);
	private final Environment environment;
	private final DoubleProperty currentTime = new SimpleDoubleProperty(0);
	private final LongProperty delay = new SimpleLongProperty(0);
	private final MetricEventDispatcher metricEventDispatcher;
	private boolean running;
	private boolean stop;
	
	/**
	 * Constructor.
	 *
	 * @param environment The environment.
	 */
	public Simulator(final Environment environment){
		this.environment = environment;
		this.metricEventDispatcher = new MetricEventDispatcher(environment);
		this.running = true;
		this.stop = false;
		currentTime.set(0);
		events.clear();
	}
	
	/**
	 * Remove all the events of a class from the queue.
	 *
	 * @param eventClass The class of the events to remove.
	 */
	public void removeAllEventsOfClass(final Class<? extends SimulationEvent> eventClass){
		events.removeIf(elem -> Objects.equals(elem.getClass(), eventClass));
	}
	
	/**
	 * Stop the simulation by clearing the queue.
	 */
	public void stop(){
		LOGGER.info("Stopping, clearing queue");
		this.getEvents().clear();
		this.stop = true;
		setRunning(true);
		this.getMetricEventDispatcher().close();
	}
	
	/**
	 * Get the queue of events.
	 *
	 * @return The events.
	 */
	private Queue<SimulationEvent> getEvents(){
		return events;
	}
	
	/**
	 * Set the running status of the simulation.
	 *
	 * @param status True to set running, false to pause.
	 */
	public void setRunning(final boolean status){
		this.running = status;
	}
	
	/**
	 * Get the event dispatcher of this simulation.
	 *
	 * @return The event dispatcher.
	 */
	public MetricEventDispatcher getMetricEventDispatcher(){
		return this.metricEventDispatcher;
	}
	
	/**
	 * Get the delay property of the simulation.
	 *
	 * @return The delay of the simulation.
	 */
	public LongProperty delayProperty(){
		return delay;
	}
	
	@Override
	public void run(){
		LOGGER.info("Starting simulator");
		events.add(new StartEvent(0));
		SimulationEvent event;
		while(!stop && (event = getEvents().poll()) != null){
			while(!running){
				try{
					Thread.sleep(1000);
				}
				catch(final InterruptedException ignored){
				}
			}
			LOGGER.debug("Executing event {} at time {}", event.getClass().getSimpleName(), event.getTime());
			currentTime.set(event.getTime());
			try{
				event.accept(this.getEnvironment());
			}
			catch(final Exception e){
				LOGGER.error("Error in event {}", event, e);
			}
			getMetricEventDispatcher().fire();
			if(delay.get() > 0){
				try{
					Thread.sleep(delay.get());
				}
				catch(final InterruptedException ignored){
				}
			}
		}
		LOGGER.info("Simulation ended");
	}
	
	/**
	 * Get the environment.
	 *
	 * @return The environment.
	 */
	private Environment getEnvironment(){
		return this.environment;
	}
	
	/**
	 * Get the current time of the simulation.
	 *
	 * @return The current time.
	 */
	public double getCurrentTime(){
		return currentTimeProperty().get();
	}
	
	/**
	 * Get the current time of the simulation property.
	 *
	 * @return The current time property.
	 */
	public DoubleProperty currentTimeProperty(){
		return currentTime;
	}
	
	/**
	 * Get a queue of events to add new ones.
	 *
	 * @return An unreadable queue of the events.
	 */
	public UnreadableQueue<SimulationEvent> getUnreadableQueue(){
		return unreadableQueue;
	}
}

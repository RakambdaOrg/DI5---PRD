package fr.mrcraftcod.simulator.rault.sensors;

import fr.mrcraftcod.simulator.Environment;
import fr.mrcraftcod.simulator.sensors.Sensor;
import fr.mrcraftcod.simulator.utils.Identifiable;
import org.jetbrains.annotations.NotNull;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.Objects;

/**
 * Class representing a sensor.
 * If a custom sensor needs to be done, extend this class and change its behaviour.
 * Don't forget to override {@link #fillFromJson(Environment, JSONObject)} to get custom fields and call the super method.
 * <p>
 * Created by Thomas Couchoud (MrCraftCod - zerderr@gmail.com) on 2018-18-04.
 *
 * @author Thomas Couchoud
 */
@SuppressWarnings("WeakerAccess")
public class LrLcSensor extends Sensor{
	private final static Logger LOGGER = LoggerFactory.getLogger(LrLcSensor.class);
	private boolean plannedForCharging;
	private double lc;
	private double lr;
	
	/**
	 * Constructor used by the JSON filler.
	 *
	 * @param environment The environment the sensor is in.
	 */
	public LrLcSensor(final Environment environment){
		this(environment, 0, 0);
	}
	
	/**
	 * Constructor.
	 *
	 * @param environment The environment.
	 * @param lc          The Lc value.
	 * @param lr          The Lr value.
	 */
	private LrLcSensor(final Environment environment, @SuppressWarnings("SameParameterValue") final double lc, @SuppressWarnings("SameParameterValue") final double lr){
		super(environment);
		this.lc = lc;
		this.lr = lr;
		this.plannedForCharging = false;
		this.addSensorListener(new LrLcSensorListener(this));
		LOGGER.debug("New sensor created: {}", getUniqueIdentifier());
	}
	
	@Override
	public LrLcSensor fillFromJson(@NotNull final Environment environment, @NotNull final JSONObject json){
		super.fillFromJson(environment, json);
		setLr(json.getDouble("lr"));
		setLc(json.getDouble("lc"));
		return this;
	}
	
	@Override
	public boolean haveSameValues(final Identifiable identifiable){
		super.haveSameValues(identifiable);
		final var sensor = (LrLcSensor) identifiable;
		return Objects.equals(getLc(), sensor.getLc()) && Objects.equals(getLr(), sensor.getLr());
	}
	
	/**
	 * Get the Lc value.
	 *
	 * @return The Lc value.
	 */
	public double getLc(){
		return this.lc;
	}
	
	/**
	 * Set Lc.
	 *
	 * @param lc The Lc value.
	 */
	private void setLc(final double lc){
		if(lc < 0){
			throw new IllegalArgumentException("Lc must be positive or null");
		}
		this.lc = lc;
	}
	
	/**
	 * Get the Lr value.
	 *
	 * @return The Lr value.
	 */
	public double getLr(){
		return this.lr;
	}
	
	/**
	 * Set Lr.
	 *
	 * @param lr The Lr value.
	 */
	private void setLr(final double lr){
		if(lr < 0){
			throw new IllegalArgumentException("Lr must be positive or null");
		}
		this.lr = lr;
	}
	
	/**
	 * Tell if the sensor have been planner into a tour.
	 *
	 * @return True if planned, false otherwise.
	 */
	public boolean isPlannedForCharging(){
		return plannedForCharging;
	}
	
	/**
	 * Set the status of the charging planification.
	 *
	 * @param plannedForCharging True if planned for charging, false otherwise.
	 */
	public void setPlannedForCharging(final boolean plannedForCharging){
		this.plannedForCharging = plannedForCharging;
	}
}

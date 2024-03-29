package fr.mrcraftcod.simulator.capacity;

import fr.mrcraftcod.simulator.Environment;
import fr.mrcraftcod.simulator.utils.JSONParsable;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.jetbrains.annotations.NotNull;
import java.util.Objects;

/**
 * Represents a capacity.
 * <p>
 * Created by Thomas Couchoud (MrCraftCod - zerderr@gmail.com) on 2019-01-17.
 *
 * @author Thomas Couchoud
 * @since 2019-01-17
 */
@SuppressWarnings("WeakerAccess")
public abstract class AbstractCapacity implements JSONParsable<AbstractCapacity>{
	private double capacity;
	
	/**
	 * Constructor.
	 *
	 * @param environment The environment the capacity is in.
	 */
	public AbstractCapacity(@SuppressWarnings("unused") @NotNull final Environment environment){
		this(0);
	}
	
	/**
	 * Constructor.
	 *
	 * @param capacity The capacity.
	 */
	public AbstractCapacity(final double capacity){this.capacity = capacity;}
	
	@Override
	public int hashCode(){
		return Objects.hash(capacity);
	}
	
	@Override
	public boolean equals(final Object o){
		if(this == o){
			return true;
		}
		if(o instanceof AbstractCapacity){
			final var capacity = (AbstractCapacity) o;
			return capacity.capacity == this.capacity;
		}
		return false;
	}
	
	@Override
	public String toString(){
		return new ToStringBuilder(this).append("capacity", capacity).toString();
	}
	
	/**
	 * Get the capacity.
	 *
	 * @return The capacity.
	 */
	public double getCapacity(){
		return capacity;
	}
	
	/**
	 * Set the capacity.
	 *
	 * @param capacity The capacity.
	 */
	public void setCapacity(final double capacity){
		this.capacity = capacity;
	}
}

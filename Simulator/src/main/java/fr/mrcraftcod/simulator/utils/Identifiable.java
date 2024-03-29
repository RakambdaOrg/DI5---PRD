package fr.mrcraftcod.simulator.utils;

import java.util.Objects;

/**
 * Represent an element in the environment that is uniquely identifiable.
 * <p>
 * Created by Thomas Couchoud (MrCraftCod - zerderr@gmail.com) on 2018-10-04.
 *
 * @author Thomas Couchoud
 */
public interface Identifiable{
	/**
	 * Tells if the elements are the same based on their content.
	 *
	 * @param identifiable The element to test against.
	 *
	 * @return True if the same, false otherwise.
	 */
	boolean haveSameValues(final Identifiable identifiable);
	
	/**
	 * Tells if the elements are the same based on their unique identifiers.
	 *
	 * @param identifiable The element to test against.
	 *
	 * @return True if the same, false otherwise.
	 */
	default boolean isSameElement(final Identifiable identifiable){
		return Objects.equals(getUniqueIdentifier(), identifiable.getUniqueIdentifier());
	}
	
	/**
	 * Get a unique ID among all Identifiable objects.
	 *
	 * @return The unique ID.
	 */
	default String getUniqueIdentifier(){
		return String.format("%s[%d]", getClass().getName(), getID());
	}
	
	/**
	 * Get an ID that should be unique among the instances of a same class.
	 *
	 * @return The ID.
	 */
	int getID();
}

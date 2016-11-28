package services;

import models.Entity;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Set;

/**
 * Created by Mike on 11/17/2016.
 */
public class EntityCollectionService implements Collectable<Entity>
{
    private static EntityCollectionService INSTANCE;
    private HashMap<String, Entity> map = new HashMap<String, Entity>();
    private HashMap<String, Entity> redundant_list = new HashMap<String, Entity>();

    private int Counter = 1;

    private EntityCollectionService(){}

    /**
     * Creates the single instance of this Class, or returns the only existing one
     * @return The single instance of this class
     */
    public static EntityCollectionService getInstance()
    {
        if (INSTANCE == null)
        {
            INSTANCE = new EntityCollectionService();
        }
        return INSTANCE;
    }

    /**
	 * CAUTION:
	 * Removes all of the mappings from this map. The map will be empty after this call returns.
	 *
	 * As a precaution, Map<key,value> is backed up to a redundant storage list.
	 * Redundant storage is cleared on the proceeding clear() method call which will effectively destroy
	 * any past data.
	 */
	public void clear()
	{
		redundant_list.clear();
		this.map = redundant_list;
	    this.map.clear();
	}

	/**
	 * Returns true if this map contains an Action mapping for the specified key.
	 * @param key: key whose presence in this map is to be tested.
	 * @return true if this map contains a mapping for the specified key.
	 */
	public boolean containsKey(String key)
	{
	    return map.containsKey(key);
	}


	/**
	 * Returns true if this map maps one or more keys to the specified value.
	 * @param value: value whose presence in this map is to be tested.
	 * @return true if this map maps one or more keys to the specified value.
	 */
	public boolean containsValue(Entity value)
	{
	    return map.containsValue(value);
	}


	public String defaultName()
	{
		String name = "ENTITY_" + Counter;
		Counter++;
		return name;
	}

	/**
	 * Returns a Set view of the mappings contained in this map.
	 * The set is backed by the map, so changes to the map are reflected in the set, and vice-versa.
	 *
	 * @return a set view of the mappings contained in this map.
	 */
	public Set<Entry<String, Entity>> entrySet()
	{
	    return map.entrySet();
	}

	/**
	 * Returns the value to which the specified key is mapped,
	 * or null if this map contains no mapping for the key.
	 *
	 * @param key the key whose associated value is to be returned
	 * @return the value to which the specified key is mapped, or null if this map contains no mapping for the key
	 */
	public Entity get(String key)
	{
	    return map.get(key);
	}

	/**
     * Associates the specified Entity with the specified key in this map.
     * If the map previously contained a mapping for the key, the old value is replaced.
     * The key is derived from the Entity object itself.
     * @param value  value to be associated with the specified key
     * @return the previous value associated with key, or null if there was no mapping for key.
     *         (A null return can also indicate that the map previously associated null with key.)
     */
    public Entity put(Entity value)
    {
    	String key = value.getEntityName().trim().toUpperCase();

        return this.map.put(key, value);
    }

	/**
     * Associates the specified value with the specified key in this map (optional operation).
     * If the map previously contained a mapping for the key, the old value is replaced by the specified value.
     *
     * @param key key with which the specified value is to be associated
     * @param value value to be associated with the specified key
     * @return the previous value associated with key, or null if there was no mapping for key.
     *         (A null return can also indicate that the map previously associated null with key,
     *         if the implementation supports null values.)
     */
    public Entity put(String key, Entity value)
    {
       return this.map.put(key, value);
    }


    /**
     * Copies all of the mappings from the specified map to this map.
     * These mappings will replace any mappings that this map had for any of the
     * keys currently in the specified map.
     *
     * @param map mappings to be stored in this map
     */
    public void putAll(HashMap<String,Entity> map)
    {
    	map.putAll(map);
    }

    /**
     * Retrieve the full map of Entities
     * @return Map of Entities
     */
    public HashMap<String, Entity> getMap()
    {
        return map;
    }

    /**
     * Removes the mapping for the specified key from this map if present.
     *
     * @param key key whose mapping is to be removed from the map
     * @return the previous value associated with key, or null if there was no mapping for key. (A null return can also indicate that the map previously associated null with key.)
     */
    public Entity remove(String key)
    {
        return map.remove(key);
    }

    /**
     * Returns a string representation of the object.
     * In general, the toString method returns a string that "textually represents" this object.
     * @return a string representation of the object.
     */
    public String toString()
    {
        String temp = "";
        for (String key : this.map.keySet())
        {
            temp = temp + this.map.get(key).toString() + "\n";
        }
        return temp;
    }



}

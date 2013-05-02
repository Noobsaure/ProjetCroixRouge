package views;

import java.util.Comparator;

import controllers.EntityController;

public class EntityComparator implements Comparator<EntityController>
{
	@Override
	public int compare(EntityController entity1, EntityController entity2)
	{
		return entity1.getName().toLowerCase().compareTo(entity2.getName().toLowerCase());
	}
}

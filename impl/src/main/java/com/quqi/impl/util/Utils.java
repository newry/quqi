package com.quqi.impl.util;

import java.util.Calendar;
import java.util.TimeZone;

import com.quqi.impl.entity.ObjectStatus;
import com.quqi.impl.entity.PersistentObject;

public class Utils {
	public static void updatePODataLastModified(PersistentObject po) {
		po.setDateLastModified(Calendar.getInstance(TimeZone.getTimeZone("UTC")));
	}

	public static Calendar getCalender(int hours) {
		Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
		cal.add(Calendar.HOUR, hours);
		return cal;
	}

	public static void updatePODateAdded(PersistentObject po) {
		po.setDateAdded(Calendar.getInstance(TimeZone.getTimeZone("UTC")));
	}

	public static void updatePOStatus(PersistentObject po, ObjectStatus status) {
		po.setStatus(status);
		updatePODataLastModified(po);
	}

}

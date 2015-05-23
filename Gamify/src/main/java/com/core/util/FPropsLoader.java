package com.core.util;

import java.util.MissingResourceException;
import java.util.ResourceBundle;

/**
 * @author Raghu Resource Bundle loader to dynamically reload props on changes
 */
public final class FPropsLoader {
	private final String _propsFile;

	public String getPropsFile() {
		return _propsFile;
	}

	private final ResourceBundle _props;

	/**
	 * ctor
	 */
	public FPropsLoader(final String propsFile) {
		_propsFile = propsFile;
		_props = ResourceBundle.getBundle(_propsFile);
	}

	public String getValue(final String key) {
		if (null == key) {
			throw new NullPointerException("Key is null!");
		}
		synchronized (_props) {
			return _props.getString(key);
		}
	}

	public String getValue(final String key, final String defaultValue) {
		if (null == key) {
			throw new NullPointerException("Key is null!");
		}
		String ret = null;
		synchronized (_props) {
			try {
				ret = _props.getString(key);
				return null == ret ? defaultValue : ret;
			} catch (final MissingResourceException mre) {
				return defaultValue;
			}
		}
	}

}

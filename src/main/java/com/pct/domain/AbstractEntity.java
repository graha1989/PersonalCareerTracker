package com.pct.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

/**
 * Abstract superclass for entity classes.
 * 
 * @author a.grahovac
 *
 */
@MappedSuperclass
public abstract class AbstractEntity implements Serializable{

	private static final long serialVersionUID = -4795760529415747169L;

	/**
	 * Primary key.
	 */
	@Id
	@GeneratedValue
	@Column(name = "id")
	private Long id;
	
	/**
	 * Constructor.
	 */
	protected AbstractEntity() {
		// EMPTY CONSTRUCTOR
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AbstractEntity other = (AbstractEntity) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		String returnValue;

		StringBuilder builder = new StringBuilder();

		builder.append(getClass().getName());
		builder.append('-');
		builder.append(this.id);
		builder.append('[');
		builder.append(hashCode());
		builder.append(']');

		returnValue = builder.toString();

		return returnValue;
	}
	
}

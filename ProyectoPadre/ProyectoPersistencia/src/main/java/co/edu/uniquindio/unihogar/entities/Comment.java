package co.edu.uniquindio.unihogar.entities;

import java.io.Serializable;
import java.lang.String;
import java.util.Date;
import javax.persistence.*;

/**
 * Entity implementation class for Entity: Comment.
 * 
 * @author Cristian G. Sanchez Pineda
 * @author Luisa F. Cotte Sanchez
 */

@Entity
@NamedQueries({
		@NamedQuery(name = Comment.GET_CLIENT_NO_REPEATED, query = "SELECT DISTINCT c.clientCode FROM Comment c WHERE c.code = :projectCode"),
		@NamedQuery(name = Comment.GET_COMMENT_BY_CLIENT, query = "SELECT c FROM Comment c WHERE c.clientCode.code =:clientCode"),
		@NamedQuery(name = Comment.GET_COMMENT_BY_PROYECT, query = "SELECT c FROM Comment c WHERE c.projectCode.code =:projectCode"),
		@NamedQuery(name = Comment.GET_ALL_COMMENT, query = "SELECT c FROM Comment c") })
public class Comment implements Serializable {

	/** The code. */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "code")
	private int code;

	/** The comment. */
	@Lob
	@Column(name = "comment", nullable = false)
	private String message;

	/** The date. */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP", name = "date", nullable = false)
	private Date date;

	/** The client code. */
	@ManyToOne
	@JoinColumn(name = "client_code")
	private Client clientCode;

	/** The project code. */
	@ManyToOne
	@JoinColumn(name = "project_code")
	private Project projectCode;

	private static final long serialVersionUID = 1L;

	// Queries
	public static final String GET_CLIENT_NO_REPEATED = "GET_CLIENT_NO_REPEATED";
	public static final String GET_COMMENT_BY_CLIENT = "GET_COMMENT_BY_CLIENT";
	public static final String GET_COMMENT_BY_PROYECT = "GET_COMMENT_BY_PROYECT";
	public static final String GET_ALL_COMMENT = "GET_ALL_COMMENT";

	/**
	 * Default constructor method.
	 */
	public Comment() {
		super();
	}

	/**
	 * Constructor method from comment.
	 *
	 * @param code    from {@link Comment} primary key, and generated value
	 * @param comment from {@link Comment} not nullable
	 */

	public Comment(int code, String comment) {
		super();
		this.code = code;
		this.message = comment;
		this.date = new Date();
	}

	/**
	 * Gets the code.
	 *
	 * @return the code
	 */
	public int getCode() {
		return this.code;
	}

	/**
	 * Sets the code.
	 *
	 * @param code the new code
	 */
	public void setCode(int code) {
		this.code = code;
	}

	

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	/**
	 * Gets the date.
	 *
	 * @return the date
	 */
	public Date getDate() {
		return this.date;
	}

	/**
	 * Sets the date.
	 *
	 * @param date the new date
	 */
	public void setDate(Date date) {
		this.date = date;
	}

	/**
	 * Gets the client code.
	 *
	 * @return the client code
	 */
	public Client getClientCode() {
		return clientCode;
	}

	/**
	 * Sets the client code.
	 *
	 * @param clientCode the new client code
	 */
	public void setClientCode(Client clientCode) {
		this.clientCode = clientCode;
	}

	/**
	 * Gets the project code.
	 *
	 * @return the project code
	 */
	public Project getProjectCode() {
		return projectCode;
	}

	/**
	 * Sets the project code.
	 *
	 * @param projectCode the new project code
	 */
	public void setProjectCode(Project projectCode) {
		this.projectCode = projectCode;
	}

	/**
	 * Hash code.
	 *
	 * @return the int
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + code;
		return result;
	}

	/**
	 * Equals.
	 *
	 * @param obj the obj
	 * @return true, if successful
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Comment other = (Comment) obj;
		if (code != other.code)
			return false;
		return true;
	}

	/**
	 * To string.
	 *
	 * @return the string
	 */
	@Override
	public String toString() {
		return   message+"\n--->"+clientCode.getCompleteName();
	}

}

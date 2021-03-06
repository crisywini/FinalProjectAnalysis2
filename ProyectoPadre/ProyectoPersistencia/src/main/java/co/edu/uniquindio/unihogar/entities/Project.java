package co.edu.uniquindio.unihogar.entities;

import java.io.Serializable;
import java.lang.String;
import java.util.LinkedList;
import java.util.List;

import javax.persistence.*;

/**
 * Entity implementation class for Entity: Project.
 *
 * @author Cristian G. Sanchez Pineda
 * @author Luisa F. Cotte Sanchez
 */

@Entity
@NamedQueries({ @NamedQuery(name = Project.GET_ALL_PROJECTS, query = "SELECT p FROM Project p"),
		@NamedQuery(name = Project.GET_ALL_PROJECTS_BY_CITY, query = "SELECT p FROM Project p WHERE p.city.name = :cityName"),
		@NamedQuery(name = Project.GET_PROJECT_BY_NAME, query = "SELECT p FROM Project p WHERE p.name LIKE :projectName"),
		@NamedQuery(name = Project.GET_PROJECT_BY_ID, query = "SELECT p FROM Project p WHERE p.code = :projectCode"),
		@NamedQuery(name = Project.GET_ALL_PROJECTS_BY_ESTATE_AGENCY, query = "SELECT p FROM Project p WHERE p.estateAgency.code = :estateAgencyCode"),
		@NamedQuery(name = Project.GET_PROJECT_BY_LATTITUDE_LENGTH, query = "SELECT p FROM Project p WHERE p.latitude = :latitude AND p.length = :length"),
		@NamedQuery(name = Project.GET_NAME_ESTATE_AGENCY, query = "SELECT p.estateAgency.name FROM Project p WHERE p.code = :projectCode"),
		@NamedQuery(name = Project.GET_CLIENTS_CONTACT, query = "SELECT c.clientCode FROM Project p JOIN p.contacts c WHERE p.code = :projectCode"),
		@NamedQuery(name = Project.GET_PROJECTS_RATING, query = "SELECT p, r.score FROM Project p LEFT JOIN p.ratings r"),
		@NamedQuery(name = Project.GET_PROJECTS_CITY_ASSOCIATED, query = "SELECT new co.edu.uniquindio.unihogar.dto.QueryProjectCityDTO( p.code, p.name, p.latitude, p.length, p.estateAgency.name ) FROM Project p WHERE p.city.code = :cityCode"),
		@NamedQuery(name = Project.GET_PROJECTS_HOUSE_PRICE, query = "SELECT p FROM Project p INNER JOIN p.dwellings d WHERE d.type = :type AND d.price >= :minPrice AND d.price <= :maxPrice"),
		@NamedQuery(name = Project.GET_PROJECTS_IMAGES_BY_SERVICE, query = "SELECT p, i FROM Project p LEFT JOIN p.images i LEFT JOIN p.services s WHERE s.name = :serviceOne OR s.name =:serviceTwo"),
		@NamedQuery(name = Project.GET_NUMBER_PROJECTS_SERVICE, query = "SELECT count(p) FROM Project p INNER JOIN p.services s WHERE s.name = :nameService"),
		@NamedQuery(name = Project.GET_NUMBER_DWELLINGS_BY_TYPE, query = "SELECT d.type, count(d) FROM Project p INNER JOIN p.dwellings d WHERE p.city.name = :nameCity GROUP BY d.type"),
		@NamedQuery(name = Project.GET_PROJECTS_WITHOUT_COMMENTS, query = "SELECT p FROM Project p WHERE p.comments IS EMPTY"),
		@NamedQuery(name = Project.GET_NUMBER_PROJECTS_BY_ESTATE_AGENCY, query = "SELECT new co.edu.uniquindio.unihogar.dto.QueryNumberProjectsEstateAgencyDTO(p.estateAgency.name, p.estateAgency.address, count(p)) FROM Project p GROUP BY p.estateAgency"),
		@NamedQuery(name = Project.GET_AVG_DWELLINGS, query = "SELECT avg(d.price) FROM Project p JOIN p.dwellings d JOIN p.services s WHERE p.city.name = :nameCity AND d.numBathrooms = 2 AND d.numRooms = 3 AND s.name = 'piscinita'"),
		@NamedQuery(name = Project.GET_PROJECTS_BY_SERVICES, query = "SELECT p FROM Project p JOIN p.services s WHERE s.name IN(:nameService1, :nameService2) GROUP BY p HAVING count(p) = 2"),
		@NamedQuery(name = Project.GET_MOST_EXPENSIVE_DWELLING_BY_CITY, query = "SELECT d FROM Project p JOIN p.dwellings d WHERE p.city.name = 'Armenia' AND d.price = SELECT MAX(d2.price) FROM Project p2 JOIN p.dwellings d2 WHERE p2.city.name = 'Armenia'"),
		@NamedQuery(name = Project.GET_MOST_EXPENSIVES_DWELLINGS_BY_CITY, query = "SELECT MAX(d.price), p.city.name FROM Project p LEFT JOIN p.dwellings d GROUP BY p.city"),
		@NamedQuery(name = Project.GET_TOP_PROJECTS_RATING, query = "SELECT p FROM Project p LEFT JOIN p.ratings r ORDER BY r.score DESC"),
		@NamedQuery(name = Project.GET_NUMBER_PROJECTS_BY_CITY, query = "SELECT new co.edu.uniquindio.unihogar.dto.QueryNumberProjectByCityDTO(COUNT(p), p.city.name) FROM Project p GROUP BY p.city.name") ,
		@NamedQuery(name = Project.GET_COMMENTS_BY_PROJECT, query = "SELECT c FROM Project p INNER JOIN p.comments c WHERE p.code = :code"),
		@NamedQuery(name = Project.GET_IMAGES_BY_PROJECT, query = "SELECT i FROM Project p INNER JOIN p.images i WHERE p.code = :code")

})
public class Project implements Serializable {

	/** The code. */
	@Id
	@Column(name = "code", nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int code;

	/** The name. */
	@Column(name = "name", nullable = false)
	private String name;

	/** The latitude. */
	@Column(name = "latitude", nullable = false)
	private double latitude;

	/** The length. */
	@Column(name = "length", nullable = false)
	private double length;

	/** The description. */
	@Lob
	@Column(name = "description", nullable = false)
	private String description;

	/** The images. */
	@ElementCollection
	@JoinColumn(name = "images")
	private List<String> images;

	/** The services. */
	@ManyToMany // Esta es la entidad no propietaria
	@JoinColumn(name = "associated_services")
	private List<Service> services;

	/** The estate agency. */
	@ManyToOne
	@JoinColumn(name = "associated_estate_agency")
	private EstateAgency estateAgency;// Esta es la entidad propietaria

	/** The city. */
	@ManyToOne
	@JoinColumn(name = "associated_city")
	private City city;

	/** The dwellings. */
	@OneToMany(mappedBy = "project") // Esta es la entidad no propietaria
	@JoinColumn(name = "associated_dwellings")
	private List<Dwelling> dwellings;

	/** The contacts. */
	@OneToMany(mappedBy = "projectCode") // Esta es la entidad no propietaria
	@JoinColumn(name = "associated_contacts")
	private List<Contact> contacts;

	/** The comments. */
	@OneToMany(mappedBy = "projectCode") // Esta es la entidad no propietaria
	@JoinColumn(name = "associated_comments")
	private List<Comment> comments;

	/** The ratings. */
	@OneToMany(mappedBy = "projectRating") // Esta es la entidad no propietaria
	@JoinColumn(name = "associated_ratings")
	private List<Rating> ratings;

	/** The favorite clients. */
	@ManyToMany(mappedBy = "favoriteProjects") // Esta es la entidad no propietaria
	@JoinColumn(name = "associated_favorite_clients")
	private List<Client> favoriteClients;

	private static final long serialVersionUID = 1L;

	// Queries
	public static final String GET_ALL_PROJECTS = "GET_ALL_PROJECTS";
	public static final String GET_ALL_PROJECTS_BY_CITY = "GET_ALL_PROJECTS_BY_CITY";
	public static final String GET_PROJECT_BY_NAME = "GET_PROJECT_BY_NAME";
	public static final String GET_PROJECT_BY_ID = "GET_PROJECT_BY_ID";
	public static final String GET_ALL_PROJECTS_BY_ESTATE_AGENCY = "GET_ALL_PROJECTS_BY_ESTATE_AGENCY";
	public static final String GET_PROJECT_BY_LATTITUDE_LENGTH = "GET_PROJECT_BY_LATTITUDE_LENGTH";
	public static final String GET_NAME_ESTATE_AGENCY = "GET_NAME_ESTATE_AGENCY";
	public static final String GET_CLIENTS_CONTACT = "GET_CLIENTS_CONTACT";
	public static final String GET_PROJECTS_RATING = "GET_PROJECTS_RATING";
	public static final String GET_PROJECTS_CITY_ASSOCIATED = "GET_PROJECTS_CITY_ASSOCIATED";
	public static final String GET_PROJECTS_HOUSE_PRICE = "GET_PROJECTS_HOUSE_PRICE";
	public static final String GET_PROJECTS_IMAGES_BY_SERVICE = "GET_PROJECTS_IMAGES_BY_SERVICE";
	public static final String GET_NUMBER_PROJECTS_SERVICE = "GET_NUMBER_PROJECTS_SERVICE";
	public static final String GET_NUMBER_DWELLINGS_BY_TYPE = "GET_NUMBER_DWELLINGS_BY_TYPE";
	public static final String GET_PROJECTS_WITHOUT_COMMENTS = "GET_PROJECTS_WITHOUT_COMMENTS";
	public static final String GET_NUMBER_PROJECTS_BY_ESTATE_AGENCY = "GET_NUMBER_PROJECTS_BY_ESTATE_AGENCY";
	public static final String GET_AVG_DWELLINGS = "GET_AVG_DWELLINGS";
	public static final String GET_PROJECTS_BY_SERVICES = "GET_PROJECTS_BY_SERVICES";
	public static final String GET_MOST_EXPENSIVE_DWELLING_BY_CITY = "GET_MOST_EXPENSIVE_DWELLING_BY_CITY";
	public static final String GET_MOST_EXPENSIVES_DWELLINGS_BY_CITY = "GET_MOST_EXPENSIVEs_DWELLINGS_BY_CITY";
	public static final String GET_TOP_PROJECTS_RATING = "GET_TOP_PROJECTS_RATING";
	public static final String GET_NUMBER_PROJECTS_BY_CITY = "GET_NUMBER_PROJECTS_BY_CITY";
	public static final String GET_COMMENTS_BY_PROJECT = "GET_COMMENTS_BY_PROJECT";
	public static final String GET_IMAGES_BY_PROJECT = "GET_IMAGES_BY_PROJECT";

	/**
	 * Default constructor method.
	 */
	public Project() {
		super();
	}

	/**
	 * Constructor method from Project.
	 * 
	 * @param code            from {@link Project} primary key and not nullable
	 * @param name            from {@link Project} not nullable
	 * @param latitude        from {@link Project} not nullable
	 * @param length          from {@link Project} not nullable
	 * @param description     from {@link Project} big description and not nullable
	 * @param services        from relationship with {@link Service}
	 * @param estateAgency    relationship with {@link EstateAgency}
	 * @param city            relationship with {@link City}
	 * @param dwellings       from relationship with {@link Dwelling}
	 * @param contacts        from relationship with {@link Contact}
	 * @param comments        from relationship with {@link Comment}
	 * @param ratings         from relationship with {@link Rating}
	 * @param favoriteClients from relationship with {@link Client}
	 */
	public Project(int code, String name, double latitude, double length, String description, List<Service> services,
			EstateAgency estateAgency, City city, List<Dwelling> dwellings, List<Contact> contacts,
			List<Comment> comments, List<Rating> ratings, List<Client> favoriteClients) {
		this.code = code;
		this.name = name;
		this.latitude = latitude;
		this.length = length;
		this.description = description;
		this.services = services;
		this.estateAgency = estateAgency;
		this.city = city;
		this.dwellings = dwellings;
		this.contacts = contacts;
		this.comments = comments;
		this.ratings = ratings;
		images = new LinkedList<String>();
		this.favoriteClients = favoriteClients;
	}

	/**
	 * Constructor method from Project.
	 * 
	 * @param code        from {@link Project} primary key and not nullable
	 * @param name        from {@link Project} not nullable
	 * @param latitude    from {@link Project} not nullable
	 * @param length      from {@link Project} not nullable
	 * @param description from {@link Project} big description and not nullable
	 */
	public Project(int code, String name, double latitude, double length, String description) {
		this.code = code;
		this.name = name;
		this.latitude = latitude;
		this.length = length;
		this.description = description;
		images = new LinkedList<String>();
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

	/**
	 * Gets the name.
	 *
	 * @return the name
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * Sets the name.
	 *
	 * @param name the new name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Gets the latitude.
	 *
	 * @return the latitude
	 */
	public double getLatitude() {
		return this.latitude;
	}

	/**
	 * Sets the latitude.
	 *
	 * @param latitude the new latitude
	 */
	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	/**
	 * Gets the length.
	 *
	 * @return the length
	 */
	public double getLength() {
		return this.length;
	}

	/**
	 * Sets the length.
	 *
	 * @param length the new length
	 */
	public void setLength(double length) {
		this.length = length;
	}

	/**
	 * Gets the description.
	 *
	 * @return the description
	 */
	public String getDescription() {
		return this.description;
	}

	/**
	 * Sets the description.
	 *
	 * @param description the new description
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * Gets the city.
	 *
	 * @return the city
	 */
	public City getCity() {
		return city;
	}

	/**
	 * Sets the city.
	 *
	 * @param city the new city
	 */
	public void setCity(City city) {
		this.city = city;
	}

	/**
	 * Gets the contacts.
	 *
	 * @return the contacts
	 */
	public List<Contact> getContacts() {
		return contacts;
	}

	/**
	 * Sets the contacts.
	 *
	 * @param contacts the new contacts
	 */
	public void setContacts(List<Contact> contacts) {
		this.contacts = contacts;
	}

	/**
	 * Gets the ratings.
	 *
	 * @return the ratings
	 */
	public List<Rating> getRatings() {
		return ratings;
	}

	/**
	 * Sets the ratings.
	 *
	 * @param ratings the new ratings
	 */
	public void setRatings(List<Rating> ratings) {
		this.ratings = ratings;
	}

	/**
	 * Gets the favorite clients.
	 *
	 * @return the favorite clients
	 */
	public List<Client> getFavoriteClients() {
		return favoriteClients;
	}

	/**
	 * Sets the favorite clients.
	 *
	 * @param favoriteClients the new favorite clients
	 */
	public void setFavoriteClients(List<Client> favoriteClients) {
		this.favoriteClients = favoriteClients;
	}

	/**
	 * Gets the images.
	 *
	 * @return the images
	 */
	public List<String> getImages() {
		return images;
	}

	/**
	 * Sets the images.
	 *
	 * @param images the new images
	 */
	public void setImages(List<String> images) {
		this.images = images;
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
		Project other = (Project) obj;
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
		return "Project [code=" + code + ", name=" + name + ", latitude=" + latitude + ", length=" + length
				+ ", description=" + description + ", images=" + images + ", services=" + services + ", estateAgency="
				+ estateAgency + ", city=" + city + ", dwellings=" + dwellings + ", contacts=" + contacts
				+ ", comments=" + comments + ", ratings=" + ratings + ", favoriteClients=" + favoriteClients + "]";
	}

	/**
	 * Gets the estate agency.
	 *
	 * @return the estate agency
	 */
	public EstateAgency getEstateAgency() {
		return estateAgency;
	}

	/**
	 * Sets the estate agency.
	 *
	 * @param estateAgency the new estate agency
	 */
	public void setEstateAgency(EstateAgency estateAgency) {
		this.estateAgency = estateAgency;
	}

	public List<Service> getServices() {
		return services;
	}

	public void setServices(List<Service> services) {
		this.services = services;
	}

	public List<Dwelling> getDwellings() {
		return dwellings;
	}

	public void setDwellings(List<Dwelling> dwellings) {
		this.dwellings = dwellings;
	}

	public List<Comment> getComments() {
		return comments;
	}

	public void setComments(List<Comment> comments) {
		this.comments = comments;
	}

}

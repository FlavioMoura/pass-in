package rocketseat.com.passin.dto.event;

public record EventDetailDTO(
		String id, 
		String title, 
		String details, 
		String slug, 
		Integer maximumAttendees,
		Integer attendeesAmount
) {

	public EventDetailDTO(String id, String title, String details, String slug, Integer maximumAttendees,
			Integer attendeesAmount) {
		this.id = id;
		this.title = title;
		this.details = details;
		this.slug = slug;
		this.maximumAttendees = maximumAttendees;
		this.attendeesAmount = attendeesAmount;
	}

}

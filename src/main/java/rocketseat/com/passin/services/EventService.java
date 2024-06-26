package rocketseat.com.passin.services;

import java.text.Normalizer;
import java.util.List;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import rocketseat.com.passin.domain.attendee.Attendee;
import rocketseat.com.passin.domain.event.Event;
import rocketseat.com.passin.domain.event.exceptions.EventNotFoundException;
import rocketseat.com.passin.dto.event.EventIdDTO;
import rocketseat.com.passin.dto.event.EventRequestDTO;
import rocketseat.com.passin.dto.event.EventResponseDTO;
import rocketseat.com.passin.repositories.EventRepository;

@Service
@RequiredArgsConstructor
public class EventService {
	
	public EventService(EventRepository eventRepository, AttendeeService attendeeService){
		this.eventRepository = eventRepository;
		this.attendeeService = attendeeService;
	}
	
	private final EventRepository eventRepository;
	private final AttendeeService attendeeService;
	
	public EventResponseDTO getEventDetail(String eventId){
		Event event = this.eventRepository.findById(eventId).orElseThrow(() -> new EventNotFoundException("Event not found with ID:" + eventId));
		List<Attendee> attendeeList = this.attendeeService.getAllAttendeesFromEvent(eventId);
		return new EventResponseDTO(event, attendeeList.size());
	}
	
	public EventIdDTO createEvent(EventRequestDTO eventDTO){
		Event newEvent = new Event();
		newEvent.setTitle(eventDTO.title());
		newEvent.setDetails(eventDTO.details());
		newEvent.setMaximumAttendees(eventDTO.maximumAttendees());
		newEvent.setSlug(createSlug(eventDTO.title()));
		
		this.eventRepository.save(newEvent);
		
		return new EventIdDTO(newEvent.getId());
	}
	
	private String createSlug(String text){
		String normalized = Normalizer.normalize(text, Normalizer.Form.NFD);
		return normalized.replaceAll("[\\p{InCOMBINING_DIACRITICAL_MARKS}]", "")
				.replaceAll("[^\\w\\s]", "")
				.replaceAll("\\s+", "-")
				.toLowerCase();
	}
}

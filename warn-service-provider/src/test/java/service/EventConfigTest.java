package service;

import java.util.HashMap;

import com.moseeker.thrift.gen.dao.struct.configdb.Event;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.moseeker.warn.service.EventConfigService;

public class EventConfigTest {
	
	private EventConfigService service;
	
	@SuppressWarnings("resource")
	@Before
	public void init() {
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
		context.scan("com.moseeker.warn");
        context.scan("com.moseeker.baseorm.config");
		context.scan("com.moseeker.baseorm.dao.configdb");
		context.refresh();
		service = context.getBean(EventConfigService.class);
	}
	
	@Test
	public void testGetEvents() {
		HashMap<String, Event> events = service.getEvents();
		events.forEach((key, value) -> {
			System.out.println((key.concat(":").concat(value.toString())));
		});
	}

}

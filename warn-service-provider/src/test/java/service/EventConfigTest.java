package service;

import com.moseeker.thrift.gen.dao.struct.configdb.Event;
import com.moseeker.warn.config.AppConfig;
import com.moseeker.warn.service.EventConfigService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.HashMap;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = AppConfig.class)
public class EventConfigTest {

    @Autowired
	private EventConfigService service;

	@Test
	public void testGetEvents() {
		HashMap<String, Event> events = service.getEvents();
		events.forEach((key, value) -> {
			System.out.println((key.concat(":").concat(value.toString())));
		});
	}

}

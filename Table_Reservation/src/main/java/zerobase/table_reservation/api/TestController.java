package zerobase.table_reservation.api;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/test")
public class TestController {
	@GetMapping
	@PreAuthorize("hasRole('PARTNER')")
	public String test() {
		return "called";
	}
}

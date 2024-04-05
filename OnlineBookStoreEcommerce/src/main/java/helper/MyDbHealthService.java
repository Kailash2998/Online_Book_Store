package helper;

import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;

public class MyDbHealthService implements HealthIndicator{
	
	public static final String DB_SERVICE="DataBase Service";
	
	public boolean isHealthGood() {
		//custom logic
		return true;
	}

	@Override
	public Health health() {
		if(isHealthGood()) {
			return Health.up().withDetail(DB_SERVICE,"DataBase Service is Running").build();
		}
		return Health.down().withDetail(DB_SERVICE, "DataBase Service is Running Down").build();
	}

}

package assgn1;

import java.time.*;

public class TimerSeth {
	
	private LocalTime begin;
	private LocalTime end;
	
	public TimerSeth() {
		
	}
	
	public void start() {
		begin = LocalTime.now();
	}
	
	public void stop() {
		end = LocalTime.now();
	}

	public long getDuration() {
		return Duration.between(begin, end).toMillis();
	}

}

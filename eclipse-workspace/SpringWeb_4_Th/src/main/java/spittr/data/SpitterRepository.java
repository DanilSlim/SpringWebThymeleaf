package spittr.data;

import spittr.domain.Spitter;

public interface SpitterRepository {
	
	Spitter save(Spitter spitter);
	
	Spitter finfByUsername(String userName); 

}

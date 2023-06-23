package pl.edu.pg.eti.game.playedgame;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class PlayedGameApplication {

	/**
	 * Number of trophies needed to get strength points increase.
	 */
	public static Integer numOfTrophiesToGetPoint;

	@Value("${played-game.num-of-trophies-to-get-point}")
	public void setNumOfTrophiesToGetPoint(Integer num) {
		numOfTrophiesToGetPoint = num;
	}

	/**
	 * Value by what the trophies increase the strength.
	 */
	public static Integer trophiesPointIncrease;

	@Value("${played-game.trophies-point-increase}")
	public void setTrophiesPointIncrease(Integer num) {
		trophiesPointIncrease = num;
	}

	/**
	 * Number of cards player can have on hand.
	 */
	public static Integer numOfCardsOnHand;

	@Value("${played-game.num-of-cards-on-hand}")
	public void setNumOfCardsOnHand(Integer num) {
		numOfCardsOnHand = num;
	}

	/**
	 * Number of trophies needed to get strength points increase.
	 */
	public static Integer lowDiceBound;

	@Value("${played-game.low-dice-bound}")
	public void setLowDiceBound(Integer num) {
		lowDiceBound = num;
	}

	/**
	 * Number of trophies needed to get strength points increase.
	 */
	public static Integer upDiceBound;

	@Value("${played-game.up-dice-bound}")
	public void setUpDiceBound(Integer num) {
		upDiceBound = num;
	}


	public static void main(String[] args) {
		SpringApplication.run(PlayedGameApplication.class, args);
	}

}

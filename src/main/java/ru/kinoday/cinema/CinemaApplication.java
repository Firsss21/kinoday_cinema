package ru.kinoday.cinema;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;
import ru.kinoday.cinema.cinema.model.*;
import ru.kinoday.cinema.cinema.service.*;

import java.sql.Timestamp;
import java.util.List;
import java.util.Set;

@EnableCaching
@SpringBootApplication
@EnableTransactionManagement
@Slf4j
public class CinemaApplication {

	public static void main(String[] args) {
		SpringApplication.run(CinemaApplication.class, args);
	}


	@Bean
	@Transactional
	CommandLineRunner run(
			@Autowired CinemaService cinemaService,
			@Autowired MovieService movieService,
			@Autowired ScheduleService scheduleService,
			@Autowired TicketService ticketService,
			@Autowired CinemaHallService cinemaHallService,
			@Autowired PlaceService placeService
			) {
		return args -> {

			for (int x = 1; x <= 30; x++) {
				for (int y = 1; y <= 30; y++) {
					placeService.addPlace(x,y);
				}
			}
			List<CinemaHall> big = List.of(
				cinemaHallService.addCinemaHall(new CinemaHall("01", CinemaHallType.BIG, placeService.getPlacesForHallType(CinemaHallType.BIG), Set.of(Format.type_3D_IMAX, Format.type_2D_ATMOS, Format.type_3D_ATMOS))),
				cinemaHallService.addCinemaHall(new CinemaHall("02", CinemaHallType.VIP, placeService.getPlacesForHallType(CinemaHallType.VIP), Set.of(Format.type_3D_ATMOS, Format.type_2D_ATMOS))),
				cinemaHallService.addCinemaHall(new CinemaHall("03", CinemaHallType.MEDIUM, placeService.getPlacesForHallType(CinemaHallType.MEDIUM), Set.of(Format.type_3D, Format.type_2D))),
				cinemaHallService.addCinemaHall(new CinemaHall("04", CinemaHallType.MEDIUM, placeService.getPlacesForHallType(CinemaHallType.MEDIUM), Set.of(Format.type_2D, Format.type_2D_ATMOS)))
			);
			List<CinemaHall> medium = List.of(
				cinemaHallService.addCinemaHall(new CinemaHall("01", CinemaHallType.MEDIUM, placeService.getPlacesForHallType(CinemaHallType.MEDIUM), Set.of(Format.type_2D_ATMOS, Format.type_2D))),
				cinemaHallService.addCinemaHall(new CinemaHall("02", CinemaHallType.MEDIUM, placeService.getPlacesForHallType(CinemaHallType.MEDIUM), Set.of(Format.type_3D, Format.type_2D))),
				cinemaHallService.addCinemaHall(new CinemaHall("03", CinemaHallType.VIP, placeService.getPlacesForHallType(CinemaHallType.VIP), Set.of(Format.type_3D_ATMOS, Format.type_2D_ATMOS)))
			);

			List<CinemaHall> small = List.of(
				cinemaHallService.addCinemaHall(new CinemaHall("01", CinemaHallType.SMALL, placeService.getPlacesForHallType(CinemaHallType.SMALL), Set.of(Format.type_2D, Format.type_2D_ATMOS))),
				cinemaHallService.addCinemaHall(new CinemaHall("02", CinemaHallType.MEDIUM, placeService.getPlacesForHallType(CinemaHallType.MEDIUM), Set.of(Format.type_2D, Format.type_3D)))
			);
			cinemaService.addCinema(new Cinema(big, "Самый крупный кинотеатр в городе!", "empty", "Пушкарион"));
			cinemaService.addCinema(new Cinema(medium, "Крутой кинотеатр прям то шо надо!", "empty", "Кино-Тор"));
			cinemaService.addCinema(new Cinema(small, "Малый да удалый!", "empty", "КиноКорн"));

			movieService.addMovie(new Movie("Побег из Шоушенка",
					"Бухгалтер Энди Дюфрейн обвинён в убийстве собственной жены и её любовника. Оказавшись в тюрьме под названием Шоушенк, он сталкивается с жестокостью и беззаконием, царящими по обе стороны решётки. Каждый, кто попадает в эти стены, становится их рабом до конца жизни. Но Энди, обладающий живым умом и доброй душой, находит подход как к заключённым, так и к охранникам, добиваясь их особого к себе расположения.",
					"empty",
					Genre.DRAMA,
					"США",
					"1994",
					142 * 60 * 60 * 1000L,
					"Фрэнк Дарабонт",
					new String[]{""},
					"skip",
					16
					));

			movieService.addMovie(new Movie("Зеленая миля",
					"В тюрьме для смертников появляется заключенный с божественным даром. Мистическая драма по роману Стивена Кинга",
					"empty",
					Genre.DRAMA,
					"США",
					"1999",
					189 * 60 * 60 * 1000L,
					"Фрэнк Дарабонт",
					new String[]{""},
					"skip",
					16
			));

			movieService.addMovie(new Movie("Форрест Гамп",
					"Сидя на автобусной остановке, Форрест Гамп — не очень умный, но добрый и открытый парень — рассказывает случайным встречным историю своей необыкновенной жизни. С самого малолетства он страдал от заболевания ног, и соседские хулиганы дразнили мальчика, и в один прекрасный день Форрест открыл в себе невероятные способности к бегу. Подруга детства Дженни всегда его поддерживала и защищала, но вскоре дороги их разошлись",
					"empty",
					Genre.DRAMA,
					"США",
					"1994",
					142 * 60 * 60 * 1000L,
					"Роберт Земекис",
					new String[]{""},
					"skip",
					16
			));
			movieService.addMovie(new Movie("1+1",
					"Пострадав в результате несчастного случая, богатый аристократ Филипп нанимает в помощники человека, который менее всего подходит для этой работы, – молодого жителя предместья Дрисса, только что освободившегося из тюрьмы. Несмотря на то, что Филипп прикован к инвалидному креслу, Дриссу удается привнести в размеренную жизнь аристократа дух приключений.",
					"empty",
					Genre.DRAMA,
					"Франция",
					"2011",
					112 * 60 * 60 * 1000L,
					"Оливье Накаш",
					new String[]{""},
					"skip",
					16
			));
			movieService.addMovie(new Movie("Криминальное чтиво",
					"Двое бандитов Винсент Вега и Джулс Винфилд ведут философские беседы в перерывах между разборками и решением проблем с должниками криминального босса Марселласа Уоллеса. В первой истории Винсент проводит незабываемый вечер с женой Марселласа Мией. Во второй рассказывается о боксёре Бутче Кулидже, купленном Уоллесом, чтобы сдать бой. В третьей истории Винсент и Джулс по нелепой случайности попадают в неприятности.",
					"empty",
					Genre.CRIME,
					"США",
					"1994",
					154 * 60 * 60 * 1000L,
					"Квентин Тарантино",
					new String[]{""},
					"skip",
					18
			));

			// schedule

			// tickets

		};
	}
}

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
import java.time.LocalDate;
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
			@Autowired PlaceService placeService,
			@Autowired KinopoiskService kinopoiskService
			) {
		return args -> {

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
			Cinema bigCinema = new Cinema(big, "Ретроградный кинотеатр в высотке на Котельнической набережной пережил капитальный ремонт — но шарма своего не растерял. На улице все та же вывеска, внутри — все те же люстры, лепнина и кинозал, в котором в ежедневном режиме можно посмотреть фильмы Госфильмофонда.", "/images/cinema/illusion.jpg", "Иллюзион");
			cinemaService.addCinema(bigCinema);
			bigCinema = cinemaService.getAllCinema().get(0);

			Cinema mediumCinema = new Cinema(medium, "«Художественный» — старейший столичный кинотеатр, открытый в 1909 году. Здание кинотеатра было спроектировано архитектором Николаем Благовещенским специально для показа фильмов, а позднее перестроено Федором Шехтелем. Это памятник архитектуры модерна и объект культурного наследия регионального значения. ", "/images/cinema/xydojestvenniy.jpg", "Художественный");
			cinemaService.addCinema(mediumCinema);
			mediumCinema = cinemaService.getAllCinema().get(1);

			Cinema smallCinema = new Cinema(small, "Перестроенный советский «Пионер» сделан по западным образцам жанра — кино на родном языке с субтитрами, лавочка с орешками и кофе вместо поп-корна, приличное кафе и книжный магазин как бонус.", "/images/cinema/pioner.jpg", "Пионер");
			cinemaService.addCinema(smallCinema);
			smallCinema = cinemaService.getAllCinema().get(2);


			movieService.addMovie(new Movie("Побег из Шоушенка",
					"Бухгалтер Энди Дюфрейн обвинён в убийстве собственной жены и её любовника. Оказавшись в тюрьме под названием Шоушенк, он сталкивается с жестокостью и беззаконием, царящими по обе стороны решётки. Каждый, кто попадает в эти стены, становится их рабом до конца жизни. Но Энди, обладающий живым умом и доброй душой, находит подход как к заключённым, так и к охранникам, добиваясь их особого к себе расположения.",
					"/images/movie/shoushenk	.webp",
					Genre.DRAMA,
					"США",
					"1994",
					142 * 60 * 1000L,
					"Фрэнк Дарабонт",
					"https://www.youtube.com/embed/kgAeKpAPOYk",
					16,
                    326
					));

			movieService.addMovie(new Movie("Зеленая миля",
					"В тюрьме для смертников появляется заключенный с божественным даром. Мистическая драма по роману Стивена Кинга",
					"/images/movie/green_mile.webp",
					Genre.DRAMA,
					"США",
					"1999",
					189 * 60 * 1000L,
					"Фрэнк Дарабонт",
					"https://www.youtube.com/embed/TODt_q-_4C4",
					16,
                    435
			));

			movieService.addMovie(new Movie("Форрест Гамп",
					"Сидя на автобусной остановке, Форрест Гамп — не очень умный, но добрый и открытый парень — рассказывает случайным встречным историю своей необыкновенной жизни. С самого малолетства он страдал от заболевания ног, и соседские хулиганы дразнили мальчика, и в один прекрасный день Форрест открыл в себе невероятные способности к бегу. Подруга детства Дженни всегда его поддерживала и защищала, но вскоре дороги их разошлись",
					"/images/movie/forest.webp",
					Genre.DRAMA,
					"США",
					"1994",
					142 * 60 * 1000L,
					"Роберт Земекис",
					"https://www.youtube.com/embed/otmeAaifX04",
					16,
                    448
			));
			movieService.addMovie(new Movie("1+1",
					"Пострадав в результате несчастного случая, богатый аристократ Филипп нанимает в помощники человека, который менее всего подходит для этой работы, – молодого жителя предместья Дрисса, только что освободившегося из тюрьмы. Несмотря на то, что Филипп прикован к инвалидному креслу, Дриссу удается привнести в размеренную жизнь аристократа дух приключений.",
					"/images/movie/1+1.webp",
					Genre.DRAMA,
					"Франция",
					"2011",
					112 * 60 * 1000L,
					"Оливье Накаш",
					"https://www.youtube.com/embed/tTwFeGArcrs",
					16,
                    535341
			));
			movieService.addMovie(new Movie("Криминальное чтиво",
					"Двое бандитов Винсент Вега и Джулс Винфилд ведут философские беседы в перерывах между разборками и решением проблем с должниками криминального босса Марселласа Уоллеса. В первой истории Винсент проводит незабываемый вечер с женой Марселласа Мией. Во второй рассказывается о боксёре Бутче Кулидже, купленном Уоллесом, чтобы сдать бой. В третьей истории Винсент и Джулс по нелепой случайности попадают в неприятности.",
					"/images/movie/kriminal_chtivo.webp",
					Genre.CRIME,
					"США",
					"1994",
					154 * 60 * 1000L,
					"Квентин Тарантино",
					"https://www.youtube.com/embed/vBADUmfa9Q4",
					18,
                    342
			));

			// schedule
            scheduleService.addSchedule(new ScheduleElement(Timestamp.valueOf(LocalDate.now().atTime(10, 0, 0)), bigCinema, bigCinema.getCinemaHallList().get(0), movieService.getMovie("Криминальное чтиво"), Format.type_2D_ATMOS, 500));
            scheduleService.addSchedule(new ScheduleElement(Timestamp.valueOf(LocalDate.now().atTime(12, 0, 0)), bigCinema, bigCinema.getCinemaHallList().get(0), movieService.getMovie("Криминальное чтиво"), Format.type_2D_ATMOS, 500));
            scheduleService.addSchedule(new ScheduleElement(Timestamp.valueOf(LocalDate.now().atTime(14, 0, 0)), bigCinema, bigCinema.getCinemaHallList().get(0), movieService.getMovie("Криминальное чтиво"), Format.type_2D_ATMOS, 500));
            scheduleService.addSchedule(new ScheduleElement(Timestamp.valueOf(LocalDate.now().atTime(14, 0, 0)), bigCinema, bigCinema.getCinemaHallList().get(1), movieService.getMovie("1+1"), Format.type_2D_ATMOS, 400));
            scheduleService.addSchedule(new ScheduleElement(Timestamp.valueOf(LocalDate.now().atTime(14, 0, 0)), bigCinema, bigCinema.getCinemaHallList().get(1), movieService.getMovie("1+1"), Format.type_2D_ATMOS, 400));
            scheduleService.addSchedule(new ScheduleElement(Timestamp.valueOf(LocalDate.now().atTime(14, 0, 0)), bigCinema, bigCinema.getCinemaHallList().get(1), movieService.getMovie("1+1"), Format.type_2D_ATMOS, 400));
            scheduleService.addSchedule(new ScheduleElement(Timestamp.valueOf(LocalDate.now().atTime(14, 0, 0)), bigCinema, bigCinema.getCinemaHallList().get(2), movieService.getMovie("Форрест Гамп"), Format.type_3D_ATMOS, 300));
            scheduleService.addSchedule(new ScheduleElement(Timestamp.valueOf(LocalDate.now().atTime(14, 0, 0)), bigCinema, bigCinema.getCinemaHallList().get(2), movieService.getMovie("Форрест Гамп"), Format.type_3D_ATMOS, 300));
            scheduleService.addSchedule(new ScheduleElement(Timestamp.valueOf(LocalDate.now().atTime(14, 0, 0)), bigCinema, bigCinema.getCinemaHallList().get(2), movieService.getMovie("Форрест Гамп"), Format.type_3D_ATMOS, 300));
			scheduleService.addSchedule(new ScheduleElement(Timestamp.valueOf(LocalDate.now().atTime(10, 0, 0)), mediumCinema, mediumCinema.getCinemaHallList().get(0), movieService.getMovie("Криминальное чтиво"), Format.type_2D_ATMOS, 500));
			scheduleService.addSchedule(new ScheduleElement(Timestamp.valueOf(LocalDate.now().atTime(12, 0, 0)), mediumCinema, mediumCinema.getCinemaHallList().get(0), movieService.getMovie("Криминальное чтиво"), Format.type_2D_ATMOS, 500));
			scheduleService.addSchedule(new ScheduleElement(Timestamp.valueOf(LocalDate.now().atTime(14, 0, 0)), mediumCinema, mediumCinema.getCinemaHallList().get(0), movieService.getMovie("Криминальное чтиво"), Format.type_2D_ATMOS, 500));
			scheduleService.addSchedule(new ScheduleElement(Timestamp.valueOf(LocalDate.now().atTime(14, 0, 0)), mediumCinema, mediumCinema.getCinemaHallList().get(1), movieService.getMovie("1+1"), Format.type_2D_ATMOS, 400));
			scheduleService.addSchedule(new ScheduleElement(Timestamp.valueOf(LocalDate.now().atTime(14, 0, 0)), mediumCinema, mediumCinema.getCinemaHallList().get(1), movieService.getMovie("1+1"), Format.type_2D_ATMOS, 400));
			scheduleService.addSchedule(new ScheduleElement(Timestamp.valueOf(LocalDate.now().atTime(14, 0, 0)), mediumCinema, mediumCinema.getCinemaHallList().get(1), movieService.getMovie("1+1"), Format.type_2D_ATMOS, 400));
			scheduleService.addSchedule(new ScheduleElement(Timestamp.valueOf(LocalDate.now().atTime(14, 0, 0)), mediumCinema, mediumCinema.getCinemaHallList().get(2), movieService.getMovie("Форрест Гамп"), Format.type_3D_ATMOS, 300));
			scheduleService.addSchedule(new ScheduleElement(Timestamp.valueOf(LocalDate.now().atTime(14, 0, 0)), mediumCinema, mediumCinema.getCinemaHallList().get(2), movieService.getMovie("Форрест Гамп"), Format.type_3D_ATMOS, 300));
			scheduleService.addSchedule(new ScheduleElement(Timestamp.valueOf(LocalDate.now().atTime(14, 0, 0)), mediumCinema, mediumCinema.getCinemaHallList().get(2), movieService.getMovie("Форрест Гамп"), Format.type_3D_ATMOS, 300));
			scheduleService.addSchedule(new ScheduleElement(Timestamp.valueOf(LocalDate.now().atTime(10, 0, 0)), smallCinema, smallCinema.getCinemaHallList().get(0), movieService.getMovie("Криминальное чтиво"), Format.type_2D_ATMOS, 500));
			scheduleService.addSchedule(new ScheduleElement(Timestamp.valueOf(LocalDate.now().atTime(12, 0, 0)), smallCinema, smallCinema.getCinemaHallList().get(0), movieService.getMovie("Криминальное чтиво"), Format.type_2D_ATMOS, 500));
			scheduleService.addSchedule(new ScheduleElement(Timestamp.valueOf(LocalDate.now().atTime(14, 0, 0)), smallCinema, smallCinema.getCinemaHallList().get(0), movieService.getMovie("Криминальное чтиво"), Format.type_2D_ATMOS, 500));
			scheduleService.addSchedule(new ScheduleElement(Timestamp.valueOf(LocalDate.now().atTime(14, 0, 0)), smallCinema, smallCinema.getCinemaHallList().get(1), movieService.getMovie("1+1"), Format.type_2D_ATMOS, 400));
			scheduleService.addSchedule(new ScheduleElement(Timestamp.valueOf(LocalDate.now().atTime(14, 0, 0)), smallCinema, smallCinema.getCinemaHallList().get(1), movieService.getMovie("1+1"), Format.type_2D_ATMOS, 400));
			scheduleService.addSchedule(new ScheduleElement(Timestamp.valueOf(LocalDate.now().atTime(14, 0, 0)), smallCinema, smallCinema.getCinemaHallList().get(1), movieService.getMovie("1+1"), Format.type_2D_ATMOS, 400));

            scheduleService.addSchedule(new ScheduleElement(Timestamp.valueOf(LocalDate.now().atTime(10, 0, 0).plusDays(1)), bigCinema, bigCinema.getCinemaHallList().get(0), movieService.getMovie("Криминальное чтиво"), Format.type_2D_ATMOS, 500));
            scheduleService.addSchedule(new ScheduleElement(Timestamp.valueOf(LocalDate.now().atTime(12, 0, 0).plusDays(1)), bigCinema, bigCinema.getCinemaHallList().get(0), movieService.getMovie("Криминальное чтиво"), Format.type_2D_ATMOS, 500));
            scheduleService.addSchedule(new ScheduleElement(Timestamp.valueOf(LocalDate.now().atTime(14, 0, 0).plusDays(1)), bigCinema, bigCinema.getCinemaHallList().get(0), movieService.getMovie("Криминальное чтиво"), Format.type_2D_ATMOS, 500));
            scheduleService.addSchedule(new ScheduleElement(Timestamp.valueOf(LocalDate.now().atTime(14, 0, 0).plusDays(1)), bigCinema, bigCinema.getCinemaHallList().get(1), movieService.getMovie("1+1"), Format.type_2D_ATMOS, 400));
            scheduleService.addSchedule(new ScheduleElement(Timestamp.valueOf(LocalDate.now().atTime(14, 0, 0).plusDays(1)), bigCinema, bigCinema.getCinemaHallList().get(1), movieService.getMovie("1+1"), Format.type_2D_ATMOS, 400));
            scheduleService.addSchedule(new ScheduleElement(Timestamp.valueOf(LocalDate.now().atTime(14, 0, 0).plusDays(1)), bigCinema, bigCinema.getCinemaHallList().get(1), movieService.getMovie("1+1"), Format.type_2D_ATMOS, 400));
            scheduleService.addSchedule(new ScheduleElement(Timestamp.valueOf(LocalDate.now().atTime(14, 0, 0).plusDays(1)), bigCinema, bigCinema.getCinemaHallList().get(2), movieService.getMovie("Форрест Гамп"), Format.type_3D_ATMOS, 300));
            scheduleService.addSchedule(new ScheduleElement(Timestamp.valueOf(LocalDate.now().atTime(14, 0, 0).plusDays(1)), bigCinema, bigCinema.getCinemaHallList().get(2), movieService.getMovie("Форрест Гамп"), Format.type_3D_ATMOS, 300));
            scheduleService.addSchedule(new ScheduleElement(Timestamp.valueOf(LocalDate.now().atTime(14, 0, 0).plusDays(1)), bigCinema, bigCinema.getCinemaHallList().get(2), movieService.getMovie("Форрест Гамп"), Format.type_3D_ATMOS, 300));
            scheduleService.addSchedule(new ScheduleElement(Timestamp.valueOf(LocalDate.now().atTime(10, 0, 0).plusDays(1)), mediumCinema, mediumCinema.getCinemaHallList().get(0), movieService.getMovie("Криминальное чтиво"), Format.type_2D_ATMOS, 500));
            scheduleService.addSchedule(new ScheduleElement(Timestamp.valueOf(LocalDate.now().atTime(12, 0, 0).plusDays(1)), mediumCinema, mediumCinema.getCinemaHallList().get(0), movieService.getMovie("Криминальное чтиво"), Format.type_2D_ATMOS, 500));
            scheduleService.addSchedule(new ScheduleElement(Timestamp.valueOf(LocalDate.now().atTime(14, 0, 0).plusDays(1)), mediumCinema, mediumCinema.getCinemaHallList().get(0), movieService.getMovie("Криминальное чтиво"), Format.type_2D_ATMOS, 500));
            scheduleService.addSchedule(new ScheduleElement(Timestamp.valueOf(LocalDate.now().atTime(14, 0, 0).plusDays(1)), mediumCinema, mediumCinema.getCinemaHallList().get(1), movieService.getMovie("1+1"), Format.type_2D_ATMOS, 400));
            scheduleService.addSchedule(new ScheduleElement(Timestamp.valueOf(LocalDate.now().atTime(14, 0, 0).plusDays(1)), mediumCinema, mediumCinema.getCinemaHallList().get(1), movieService.getMovie("1+1"), Format.type_2D_ATMOS, 400));
            scheduleService.addSchedule(new ScheduleElement(Timestamp.valueOf(LocalDate.now().atTime(14, 0, 0).plusDays(1)), mediumCinema, mediumCinema.getCinemaHallList().get(1), movieService.getMovie("1+1"), Format.type_2D_ATMOS, 400));
            scheduleService.addSchedule(new ScheduleElement(Timestamp.valueOf(LocalDate.now().atTime(14, 0, 0).plusDays(1)), mediumCinema, mediumCinema.getCinemaHallList().get(2), movieService.getMovie("Форрест Гамп"), Format.type_3D_ATMOS, 300));
            scheduleService.addSchedule(new ScheduleElement(Timestamp.valueOf(LocalDate.now().atTime(14, 0, 0).plusDays(1)), mediumCinema, mediumCinema.getCinemaHallList().get(2), movieService.getMovie("Форрест Гамп"), Format.type_3D_ATMOS, 300));
            scheduleService.addSchedule(new ScheduleElement(Timestamp.valueOf(LocalDate.now().atTime(14, 0, 0).plusDays(1)), mediumCinema, mediumCinema.getCinemaHallList().get(2), movieService.getMovie("Форрест Гамп"), Format.type_3D_ATMOS, 300));
            scheduleService.addSchedule(new ScheduleElement(Timestamp.valueOf(LocalDate.now().atTime(10, 0, 0).plusDays(1)), smallCinema, smallCinema.getCinemaHallList().get(0), movieService.getMovie("Криминальное чтиво"), Format.type_2D_ATMOS, 500));
            scheduleService.addSchedule(new ScheduleElement(Timestamp.valueOf(LocalDate.now().atTime(12, 0, 0).plusDays(1)), smallCinema, smallCinema.getCinemaHallList().get(0), movieService.getMovie("Криминальное чтиво"), Format.type_2D_ATMOS, 500));
            scheduleService.addSchedule(new ScheduleElement(Timestamp.valueOf(LocalDate.now().atTime(14, 0, 0).plusDays(1)), smallCinema, smallCinema.getCinemaHallList().get(0), movieService.getMovie("Криминальное чтиво"), Format.type_2D_ATMOS, 500));
            scheduleService.addSchedule(new ScheduleElement(Timestamp.valueOf(LocalDate.now().atTime(14, 0, 0).plusDays(1)), smallCinema, smallCinema.getCinemaHallList().get(1), movieService.getMovie("1+1"), Format.type_2D_ATMOS, 400));
            scheduleService.addSchedule(new ScheduleElement(Timestamp.valueOf(LocalDate.now().atTime(14, 0, 0).plusDays(1)), smallCinema, smallCinema.getCinemaHallList().get(1), movieService.getMovie("1+1"), Format.type_2D_ATMOS, 400));
            scheduleService.addSchedule(new ScheduleElement(Timestamp.valueOf(LocalDate.now().atTime(14, 0, 0).plusDays(1)), smallCinema, smallCinema.getCinemaHallList().get(1), movieService.getMovie("1+1"), Format.type_2D_ATMOS, 400));

            scheduleService.addSchedule(new ScheduleElement(Timestamp.valueOf(LocalDate.now().atTime(10, 0, 0).plusDays(2)), bigCinema, bigCinema.getCinemaHallList().get(0), movieService.getMovie("Криминальное чтиво"), Format.type_2D_ATMOS, 500));
            scheduleService.addSchedule(new ScheduleElement(Timestamp.valueOf(LocalDate.now().atTime(12, 0, 0).plusDays(2)), bigCinema, bigCinema.getCinemaHallList().get(0), movieService.getMovie("Криминальное чтиво"), Format.type_2D_ATMOS, 500));
            scheduleService.addSchedule(new ScheduleElement(Timestamp.valueOf(LocalDate.now().atTime(14, 0, 0).plusDays(2)), bigCinema, bigCinema.getCinemaHallList().get(0), movieService.getMovie("Криминальное чтиво"), Format.type_2D_ATMOS, 500));
            scheduleService.addSchedule(new ScheduleElement(Timestamp.valueOf(LocalDate.now().atTime(14, 0, 0).plusDays(2)), bigCinema, bigCinema.getCinemaHallList().get(1), movieService.getMovie("1+1"), Format.type_2D_ATMOS, 400));
            scheduleService.addSchedule(new ScheduleElement(Timestamp.valueOf(LocalDate.now().atTime(14, 0, 0).plusDays(2)), bigCinema, bigCinema.getCinemaHallList().get(1), movieService.getMovie("1+1"), Format.type_2D_ATMOS, 400));
            scheduleService.addSchedule(new ScheduleElement(Timestamp.valueOf(LocalDate.now().atTime(14, 0, 0).plusDays(2)), bigCinema, bigCinema.getCinemaHallList().get(1), movieService.getMovie("1+1"), Format.type_2D_ATMOS, 400));
            scheduleService.addSchedule(new ScheduleElement(Timestamp.valueOf(LocalDate.now().atTime(14, 0, 0).plusDays(2)), bigCinema, bigCinema.getCinemaHallList().get(2), movieService.getMovie("Форрест Гамп"), Format.type_3D_ATMOS, 300));
            scheduleService.addSchedule(new ScheduleElement(Timestamp.valueOf(LocalDate.now().atTime(14, 0, 0).plusDays(2)), bigCinema, bigCinema.getCinemaHallList().get(2), movieService.getMovie("Форрест Гамп"), Format.type_3D_ATMOS, 300));
            scheduleService.addSchedule(new ScheduleElement(Timestamp.valueOf(LocalDate.now().atTime(14, 0, 0).plusDays(2)), bigCinema, bigCinema.getCinemaHallList().get(2), movieService.getMovie("Форрест Гамп"), Format.type_3D_ATMOS, 300));
            scheduleService.addSchedule(new ScheduleElement(Timestamp.valueOf(LocalDate.now().atTime(10, 0, 0).plusDays(2)), mediumCinema, mediumCinema.getCinemaHallList().get(0), movieService.getMovie("Криминальное чтиво"), Format.type_2D_ATMOS, 500));
            scheduleService.addSchedule(new ScheduleElement(Timestamp.valueOf(LocalDate.now().atTime(12, 0, 0).plusDays(2)), mediumCinema, mediumCinema.getCinemaHallList().get(0), movieService.getMovie("Криминальное чтиво"), Format.type_2D_ATMOS, 500));
            scheduleService.addSchedule(new ScheduleElement(Timestamp.valueOf(LocalDate.now().atTime(14, 0, 0).plusDays(2)), mediumCinema, mediumCinema.getCinemaHallList().get(0), movieService.getMovie("Криминальное чтиво"), Format.type_2D_ATMOS, 500));
            scheduleService.addSchedule(new ScheduleElement(Timestamp.valueOf(LocalDate.now().atTime(14, 0, 0).plusDays(2)), mediumCinema, mediumCinema.getCinemaHallList().get(1), movieService.getMovie("1+1"), Format.type_2D_ATMOS, 400));
            scheduleService.addSchedule(new ScheduleElement(Timestamp.valueOf(LocalDate.now().atTime(14, 0, 0).plusDays(2)), mediumCinema, mediumCinema.getCinemaHallList().get(1), movieService.getMovie("1+1"), Format.type_2D_ATMOS, 400));
            scheduleService.addSchedule(new ScheduleElement(Timestamp.valueOf(LocalDate.now().atTime(14, 0, 0).plusDays(2)), mediumCinema, mediumCinema.getCinemaHallList().get(1), movieService.getMovie("1+1"), Format.type_2D_ATMOS, 400));
            scheduleService.addSchedule(new ScheduleElement(Timestamp.valueOf(LocalDate.now().atTime(14, 0, 0).plusDays(2)), mediumCinema, mediumCinema.getCinemaHallList().get(2), movieService.getMovie("Форрест Гамп"), Format.type_3D_ATMOS, 300));
            scheduleService.addSchedule(new ScheduleElement(Timestamp.valueOf(LocalDate.now().atTime(14, 0, 0).plusDays(2)), mediumCinema, mediumCinema.getCinemaHallList().get(2), movieService.getMovie("Форрест Гамп"), Format.type_3D_ATMOS, 300));
            scheduleService.addSchedule(new ScheduleElement(Timestamp.valueOf(LocalDate.now().atTime(14, 0, 0).plusDays(2)), mediumCinema, mediumCinema.getCinemaHallList().get(2), movieService.getMovie("Форрест Гамп"), Format.type_3D_ATMOS, 300));
            scheduleService.addSchedule(new ScheduleElement(Timestamp.valueOf(LocalDate.now().atTime(10, 0, 0).plusDays(2)), smallCinema, smallCinema.getCinemaHallList().get(0), movieService.getMovie("Криминальное чтиво"), Format.type_2D_ATMOS, 500));
            scheduleService.addSchedule(new ScheduleElement(Timestamp.valueOf(LocalDate.now().atTime(12, 0, 0).plusDays(2)), smallCinema, smallCinema.getCinemaHallList().get(0), movieService.getMovie("Криминальное чтиво"), Format.type_2D_ATMOS, 500));
            scheduleService.addSchedule(new ScheduleElement(Timestamp.valueOf(LocalDate.now().atTime(14, 0, 0).plusDays(2)), smallCinema, smallCinema.getCinemaHallList().get(0), movieService.getMovie("Криминальное чтиво"), Format.type_2D_ATMOS, 500));
            scheduleService.addSchedule(new ScheduleElement(Timestamp.valueOf(LocalDate.now().atTime(14, 0, 0).plusDays(2)), smallCinema, smallCinema.getCinemaHallList().get(1), movieService.getMovie("1+1"), Format.type_2D_ATMOS, 400));
            scheduleService.addSchedule(new ScheduleElement(Timestamp.valueOf(LocalDate.now().atTime(14, 0, 0).plusDays(2)), smallCinema, smallCinema.getCinemaHallList().get(1), movieService.getMovie("1+1"), Format.type_2D_ATMOS, 400));
            scheduleService.addSchedule(new ScheduleElement(Timestamp.valueOf(LocalDate.now().atTime(14, 0, 0).plusDays(2)), smallCinema, smallCinema.getCinemaHallList().get(1), movieService.getMovie("1+1"), Format.type_2D_ATMOS, 400));

            scheduleService.addSchedule(new ScheduleElement(Timestamp.valueOf(LocalDate.now().atTime(10, 0, 0).minusDays(1)), bigCinema, bigCinema.getCinemaHallList().get(0), movieService.getMovie("Криминальное чтиво"), Format.type_2D_ATMOS, 500));
            scheduleService.addSchedule(new ScheduleElement(Timestamp.valueOf(LocalDate.now().atTime(12, 0, 0).minusDays(1)), bigCinema, bigCinema.getCinemaHallList().get(0), movieService.getMovie("Криминальное чтиво"), Format.type_2D_ATMOS, 500));
            scheduleService.addSchedule(new ScheduleElement(Timestamp.valueOf(LocalDate.now().atTime(14, 0, 0).minusDays(1)), bigCinema, bigCinema.getCinemaHallList().get(0), movieService.getMovie("Криминальное чтиво"), Format.type_2D_ATMOS, 500));
            scheduleService.addSchedule(new ScheduleElement(Timestamp.valueOf(LocalDate.now().atTime(14, 0, 0).minusDays(1)), bigCinema, bigCinema.getCinemaHallList().get(1), movieService.getMovie("1+1"), Format.type_2D_ATMOS, 400));
            scheduleService.addSchedule(new ScheduleElement(Timestamp.valueOf(LocalDate.now().atTime(14, 0, 0).minusDays(1)), bigCinema, bigCinema.getCinemaHallList().get(1), movieService.getMovie("1+1"), Format.type_2D_ATMOS, 400));
            scheduleService.addSchedule(new ScheduleElement(Timestamp.valueOf(LocalDate.now().atTime(14, 0, 0).minusDays(1)), bigCinema, bigCinema.getCinemaHallList().get(1), movieService.getMovie("1+1"), Format.type_2D_ATMOS, 400));
            scheduleService.addSchedule(new ScheduleElement(Timestamp.valueOf(LocalDate.now().atTime(14, 0, 0).minusDays(1)), bigCinema, bigCinema.getCinemaHallList().get(2), movieService.getMovie("Форрест Гамп"), Format.type_3D_ATMOS, 300));
            scheduleService.addSchedule(new ScheduleElement(Timestamp.valueOf(LocalDate.now().atTime(14, 0, 0).minusDays(1)), bigCinema, bigCinema.getCinemaHallList().get(2), movieService.getMovie("Форрест Гамп"), Format.type_3D_ATMOS, 300));
            scheduleService.addSchedule(new ScheduleElement(Timestamp.valueOf(LocalDate.now().atTime(14, 0, 0).minusDays(1)), bigCinema, bigCinema.getCinemaHallList().get(2), movieService.getMovie("Форрест Гамп"), Format.type_3D_ATMOS, 300));
            scheduleService.addSchedule(new ScheduleElement(Timestamp.valueOf(LocalDate.now().atTime(10, 0, 0).minusDays(1)), mediumCinema, mediumCinema.getCinemaHallList().get(0), movieService.getMovie("Криминальное чтиво"), Format.type_2D_ATMOS, 500));
            scheduleService.addSchedule(new ScheduleElement(Timestamp.valueOf(LocalDate.now().atTime(12, 0, 0).minusDays(1)), mediumCinema, mediumCinema.getCinemaHallList().get(0), movieService.getMovie("Криминальное чтиво"), Format.type_2D_ATMOS, 500));
            scheduleService.addSchedule(new ScheduleElement(Timestamp.valueOf(LocalDate.now().atTime(14, 0, 0).minusDays(1)), mediumCinema, mediumCinema.getCinemaHallList().get(0), movieService.getMovie("Криминальное чтиво"), Format.type_2D_ATMOS, 500));
            scheduleService.addSchedule(new ScheduleElement(Timestamp.valueOf(LocalDate.now().atTime(14, 0, 0).minusDays(1)), mediumCinema, mediumCinema.getCinemaHallList().get(1), movieService.getMovie("1+1"), Format.type_2D_ATMOS, 400));
            scheduleService.addSchedule(new ScheduleElement(Timestamp.valueOf(LocalDate.now().atTime(14, 0, 0).minusDays(1)), mediumCinema, mediumCinema.getCinemaHallList().get(1), movieService.getMovie("1+1"), Format.type_2D_ATMOS, 400));
            scheduleService.addSchedule(new ScheduleElement(Timestamp.valueOf(LocalDate.now().atTime(14, 0, 0).minusDays(1)), mediumCinema, mediumCinema.getCinemaHallList().get(1), movieService.getMovie("1+1"), Format.type_2D_ATMOS, 400));
            scheduleService.addSchedule(new ScheduleElement(Timestamp.valueOf(LocalDate.now().atTime(14, 0, 0).minusDays(1)), mediumCinema, mediumCinema.getCinemaHallList().get(2), movieService.getMovie("Форрест Гамп"), Format.type_3D_ATMOS, 300));
            scheduleService.addSchedule(new ScheduleElement(Timestamp.valueOf(LocalDate.now().atTime(14, 0, 0).minusDays(1)), mediumCinema, mediumCinema.getCinemaHallList().get(2), movieService.getMovie("Форрест Гамп"), Format.type_3D_ATMOS, 300));
            scheduleService.addSchedule(new ScheduleElement(Timestamp.valueOf(LocalDate.now().atTime(14, 0, 0).minusDays(1)), mediumCinema, mediumCinema.getCinemaHallList().get(2), movieService.getMovie("Форрест Гамп"), Format.type_3D_ATMOS, 300));
            scheduleService.addSchedule(new ScheduleElement(Timestamp.valueOf(LocalDate.now().atTime(10, 0, 0).minusDays(1)), smallCinema, smallCinema.getCinemaHallList().get(0), movieService.getMovie("Криминальное чтиво"), Format.type_2D_ATMOS, 500));
            scheduleService.addSchedule(new ScheduleElement(Timestamp.valueOf(LocalDate.now().atTime(12, 0, 0).minusDays(1)), smallCinema, smallCinema.getCinemaHallList().get(0), movieService.getMovie("Криминальное чтиво"), Format.type_2D_ATMOS, 500));
            scheduleService.addSchedule(new ScheduleElement(Timestamp.valueOf(LocalDate.now().atTime(14, 0, 0).minusDays(1)), smallCinema, smallCinema.getCinemaHallList().get(0), movieService.getMovie("Криминальное чтиво"), Format.type_2D_ATMOS, 500));
            scheduleService.addSchedule(new ScheduleElement(Timestamp.valueOf(LocalDate.now().atTime(14, 0, 0).minusDays(1)), smallCinema, smallCinema.getCinemaHallList().get(1), movieService.getMovie("1+1"), Format.type_2D_ATMOS, 400));
            scheduleService.addSchedule(new ScheduleElement(Timestamp.valueOf(LocalDate.now().atTime(14, 0, 0).minusDays(1)), smallCinema, smallCinema.getCinemaHallList().get(1), movieService.getMovie("1+1"), Format.type_2D_ATMOS, 400));
            scheduleService.addSchedule(new ScheduleElement(Timestamp.valueOf(LocalDate.now().atTime(14, 0, 0).minusDays(1)), smallCinema, smallCinema.getCinemaHallList().get(1), movieService.getMovie("1+1"), Format.type_2D_ATMOS, 400));

            // create schedule po vsem filmam vo vseh kinozalah,


			// tickets

			// para for example

			// build расписания
		};
	}
}

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
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

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

			if (true)
				return;

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

			movieService.addMovie(new Movie("Как приручить дракона",
					"Вы узнаете историю подростка Иккинга, которому не слишком близки традиции его героического племени, много лет ведущего войну с драконами. Мир Иккинга переворачивается с ног на голову, когда он неожиданно встречает дракона Беззубика, который поможет ему и другим викингам увидеть привычный мир с совершенно другой стороны",
					"/images/movie/HOWTOTRAINYOURDRAGON.jpg",
					Genre.ANIMATION,
					"США",
					"2010",
					98 * 60 * 1000L,
					"Дин ДеБлуа, Крис Сандерс",
					"https://www.youtube.com/embed/6Gpj72qHMjE",
					6,
					280172
			));

			movieService.addMovie(new Movie("Джентельмены",
					"Один ушлый американец ещё со студенческих лет приторговывал наркотиками, а теперь придумал схему нелегального обогащения с использованием поместий обедневшей английской аристократии и очень неплохо на этом разбогател. Другой пронырливый журналист приходит к Рэю, правой руке американца, и предлагает тому купить киносценарий, в котором подробно описаны преступления его босса при участии других представителей лондонского криминального мира — партнёра-еврея, китайской диаспоры, чернокожих спортсменов и даже русского олигарха.",
					"/images/movie/gentlemen.jpg",
					Genre.CRIME,
					"Великобритания",
					"2019",
					113 * 60 * 1000L,
					"Гай Ричи",
					"https://www.youtube.com/embed/dABPCMxu074",
					18,
					1143242
			));

			movieService.addMovie(new Movie("12 стульев",
					"Товарищ Бендер в погоне за бриллиантами мадам Петуховой. Гайдаевская экранизация великого сатирического романа.",
					"/images/movie/12.jpg",
					Genre.COMEDY,
					"СССР",
					"1971",
					153 * 60 * 1000L,
					"Леонид Гайдай",
					"https://www.youtube.com/embed/_HxyahYOljU",
					6,
					46789
			));

			movieService.addMovie(new Movie("Русалочка",
					"Таинственные глубины моря хранят немало тайн, а обитатели океана могут рассказать множество удивительных историй. И самой невероятной из них была и остаётся история русалочки Ариэль. Дочь могущественного царя подводного мира Тритона, она однажды влюбляется в прекрасного и мужественного принца. Но разве они могут быть вместе - земной человек и морская дева?",
					"/images/movie/little mermaid.jpg",
					Genre.ANIMATION,
					"США",
					"1989",
					83 * 60 * 1000L,
					"Рон Клементс, Джон Маскер",
					"https://www.youtube.com/embed/OkdhkfN5P5k",
					6,
					8147
			));

			movieService.addMovie(new Movie("Начало",
					"Кобб – талантливый вор, лучший из лучших в опасном искусстве извлечения: он крадет ценные секреты из глубин подсознания во время сна, когда человеческий разум наиболее уязвим.",
					"/images/movie/beginning.jpg",
					Genre.FANTASY,
					"США",
					"2010",
					148 * 60 * 1000L,
					"Кристофер Нолан",
					"https://www.youtube.com/embed/85Zz1CCXyDI",
					12,
					447301
			));

			movieService.addMovie(new Movie("Люси",
					"Еще вчера она была просто сексапильной блондинкой, а сегодня - самое опасное и смертоносное создание на планете со сверхъестественными способностями и интеллектом. То, что совсем недавно лучшие умы мира считали фантастической теорией, для нее стало реальностью. И теперь из добычи она превратится в охотницу.",
					"/images/movie/lucy.jpg",
					Genre.ACTION,
					"Франция",
					"2014",
					89 * 60 * 1000L,
					"Люк Бессон",
					"https://www.youtube.com/embed/3zqMB_h4-zk",
					18,
					760326
			));

			movieService.addMovie(new Movie("Игра в имитацию",
					"Английский математик и логик Алан Тьюринг пытается взломать код немецкой шифровальной машины Enigma во время Второй мировой войны.",
					"/images/movie/PLAYING.jpg",
					Genre.BIOGRAPHY,
					"Великобритания",
					"2014",
					114 * 60 * 1000L,
					"Мортен Тильдум",
					"https://www.youtube.com/embed/z5ywP9odJXo",
					16,
					635772
			));

			movieService.addMovie(new Movie("Железный человек",
					"Миллиардер-изобретатель Тони Старк попадает в плен к Афганским террористам, которые пытаются заставить его создать оружие массового поражения. В тайне от своих захватчиков Старк конструирует высокотехнологичную киберброню, которая помогает ему сбежать. Однако по возвращении в США он узнаёт, что в совете директоров его фирмы плетётся заговор, чреватый страшными последствиями. Используя своё последнее изобретение, Старк пытается решить проблемы своей компании радикально...",
					"/images/movie/Ironman.jpg",
					Genre.FANTASY,
					"США",
					"2008",
					121 * 60 * 1000L,
					"Джон Фавро",
					"https://www.youtube.com/embed/fMKZMI8ByTs",
					12,
					61237
			));

			movieService.addMovie(new Movie("Голая правда",
					"Он - восходящая звезда нового телешоу, и его жизненное кредо — «секс, наркотики и рок-н-ролл», он совсем не признает равенство полов, политкорректность и так далее… В общем, настоящий мачо.",
					"/images/movie/UnglyTruth.jpg",
					Genre.DRAMA,
					"США",
					"2009",
					96 * 60 * 1000L,
					"Роберт Лукетич",
					"https://www.youtube.com/embed/f1gDN71M4EQ",
					16,
					39823
			));

			movieService.addMovie(new Movie("Жизнь как он есть",
					"Холли Беренсон – владелица кондитерской, Эрик Мессер – многообещающий технический директор на спортивных трансляциях. После рокового первого свидания единственное, что у них теперь общее, – это ненависть друг к другу и любовь к крестнице Софи. Но когда внезапно они становятся единственными, кто остается у Софи, Холли и Мессер вынуждены забыть о собственных несовпадениях. В попытке состыковать карьерные амбиции и несхожие планы досуга им всё-таки придется найти хоть что-то их объединяющее, ведь отныне им предстоит поселиться под одной крышей.",
					"/images/movie/Life as We Know It.jpg",
					Genre.DRAMA,
					"США",
					"2010",
					115 * 60 * 1000L,
					"Грег Берланти",
					"https://www.youtube.com/embed/yP_eN3Cnp2E",
					12,
					463952
			));

			movieService.addMovie(new Movie("Парфюмер: История одного убийцы",
					"Жестокий, никогда не знавший любви, сирота Жан-Батист Гренуй настоящих успехов достиг лишь на одном поприще – среди парфюмеров ему никогда не было равных. По его духам сходит с ума весь высший свет, не подозревая о том, какой страшной ценой будет получен последний, идеальный аромат.",
					"/images/movie/Perfume.jpg",
					Genre.FANTASY,
					"Германия",
					"2006",
					147 * 60 * 1000L,
					"Том Тыквер",
					"https://www.youtube.com/embed/-tRAfTzsu7M",
					18,
					78378
			));

			movieService.addMovie(new Movie("Бойфренд из будущего",
					"Тиму повезло – он молодой, симпатичный, многообещающий адвокат, а еще он… умеет перемещаться во времени. И чтобы очаровать девушку своей мечты, он готов повторять первое свидание вновь и вновь. Воистину, любовь с первого взгляда не всегда выходит с первого раза...",
					"/images/movie/About Time.jpg",
					Genre.FANTASY,
					"Великобритания",
					"2013",
					123 * 60 * 1000L,
					"Ричард Кёртис",
					"https://www.youtube.com/embed/dlmSBAqjiSA",
					16,
					662596
			));

			movieService.addMovie(new Movie("Sherlock Holmes",
					"Величайший в истории сыщик Шерлок Холмс вместе со своим верным соратником Ватсоном вступают в схватку, требующую нешуточной физической и умственной подготовки, ведь их враг представляет угрозу для всего Лондона.",
					"/images/movie/Sherlock Holmes.webp",
					Genre.DETECTIVE,
					"США",
					"2009",
					98 * 60 * 1000L,
					"Гай Ричи",
					"https://www.youtube.com/embed/4Q6f-ZqggYI",
					12,
					420923
			));

			// create schedule po vsem filmam vo vseh kinozalah,
			List<String> collect = movieService.getMovies().stream().map(Movie::getName).collect(Collectors.toList());
		for (int i = 0; i < 30; i++) {
			// -- 1st
			scheduleService.addSchedule(new ScheduleElement(Timestamp.valueOf(LocalDate.now().atTime(1, 0, 0).plusDays(i)), bigCinema, bigCinema.getCinemaHallList().get(0), movieService.getMovie(collect.get(ThreadLocalRandom.current().nextInt(0, collect.size()))), Format.type_2D_ATMOS, 250));
			scheduleService.addSchedule(new ScheduleElement(Timestamp.valueOf(LocalDate.now().atTime(4, 0, 0).plusDays(i)), bigCinema, bigCinema.getCinemaHallList().get(0), movieService.getMovie(collect.get(ThreadLocalRandom.current().nextInt(0, collect.size()))), Format.type_2D_ATMOS, 300));
			scheduleService.addSchedule(new ScheduleElement(Timestamp.valueOf(LocalDate.now().atTime(10, 0, 0).plusDays(i)), bigCinema, bigCinema.getCinemaHallList().get(0), movieService.getMovie(collect.get(ThreadLocalRandom.current().nextInt(0, collect.size()))), Format.type_3D_ATMOS, 400));
			scheduleService.addSchedule(new ScheduleElement(Timestamp.valueOf(LocalDate.now().atTime(13, 0, 0).plusDays(i)), bigCinema, bigCinema.getCinemaHallList().get(0), movieService.getMovie(collect.get(ThreadLocalRandom.current().nextInt(0, collect.size()))), Format.type_2D_ATMOS, 400));
			scheduleService.addSchedule(new ScheduleElement(Timestamp.valueOf(LocalDate.now().atTime(16, 0, 0).plusDays(i)), bigCinema, bigCinema.getCinemaHallList().get(0), movieService.getMovie(collect.get(ThreadLocalRandom.current().nextInt(0, collect.size()))), Format.type_3D_ATMOS, 600));
			scheduleService.addSchedule(new ScheduleElement(Timestamp.valueOf(LocalDate.now().atTime(19, 0, 0).plusDays(i)), bigCinema, bigCinema.getCinemaHallList().get(0), movieService.getMovie(collect.get(ThreadLocalRandom.current().nextInt(0, collect.size()))), Format.type_2D_ATMOS, 700));
			scheduleService.addSchedule(new ScheduleElement(Timestamp.valueOf(LocalDate.now().atTime(22, 0, 0).plusDays(i)), bigCinema, bigCinema.getCinemaHallList().get(0), movieService.getMovie(collect.get(ThreadLocalRandom.current().nextInt(0, collect.size()))), Format.type_3D_IMAX, 900));

			scheduleService.addSchedule(new ScheduleElement(Timestamp.valueOf(LocalDate.now().atTime(2, 0, 0).plusDays(i)), bigCinema, bigCinema.getCinemaHallList().get(1), movieService.getMovie(collect.get(ThreadLocalRandom.current().nextInt(0, collect.size()))), Format.type_3D_ATMOS, 400));
			scheduleService.addSchedule(new ScheduleElement(Timestamp.valueOf(LocalDate.now().atTime(5, 0, 0).plusDays(i)), bigCinema, bigCinema.getCinemaHallList().get(1), movieService.getMovie(collect.get(ThreadLocalRandom.current().nextInt(0, collect.size()))), Format.type_2D_ATMOS, 300));
			scheduleService.addSchedule(new ScheduleElement(Timestamp.valueOf(LocalDate.now().atTime(11, 0, 0).plusDays(i)), bigCinema, bigCinema.getCinemaHallList().get(1), movieService.getMovie(collect.get(ThreadLocalRandom.current().nextInt(0, collect.size()))), Format.type_3D_ATMOS, 500));
			scheduleService.addSchedule(new ScheduleElement(Timestamp.valueOf(LocalDate.now().atTime(14, 0, 0).plusDays(i)), bigCinema, bigCinema.getCinemaHallList().get(1), movieService.getMovie(collect.get(ThreadLocalRandom.current().nextInt(0, collect.size()))), Format.type_2D_ATMOS, 500));
			scheduleService.addSchedule(new ScheduleElement(Timestamp.valueOf(LocalDate.now().atTime(17, 0, 0).plusDays(i)), bigCinema, bigCinema.getCinemaHallList().get(1), movieService.getMovie(collect.get(ThreadLocalRandom.current().nextInt(0, collect.size()))), Format.type_3D_ATMOS, 900));
			scheduleService.addSchedule(new ScheduleElement(Timestamp.valueOf(LocalDate.now().atTime(20, 0, 0).plusDays(i)), bigCinema, bigCinema.getCinemaHallList().get(1), movieService.getMovie(collect.get(ThreadLocalRandom.current().nextInt(0, collect.size()))), Format.type_2D_ATMOS, 700));
			scheduleService.addSchedule(new ScheduleElement(Timestamp.valueOf(LocalDate.now().atTime(22, 0, 0).plusDays(i)), bigCinema, bigCinema.getCinemaHallList().get(1), movieService.getMovie(collect.get(ThreadLocalRandom.current().nextInt(0, collect.size()))), Format.type_3D_ATMOS, 900));

			scheduleService.addSchedule(new ScheduleElement(Timestamp.valueOf(LocalDate.now().atTime(3, 0, 0).plusDays(i)), bigCinema, bigCinema.getCinemaHallList().get(2), movieService.getMovie(collect.get(ThreadLocalRandom.current().nextInt(0, collect.size()))), Format.type_2D, 250));
			scheduleService.addSchedule(new ScheduleElement(Timestamp.valueOf(LocalDate.now().atTime(6, 0, 0).plusDays(i)), bigCinema, bigCinema.getCinemaHallList().get(2), movieService.getMovie(collect.get(ThreadLocalRandom.current().nextInt(0, collect.size()))), Format.type_2D, 300));
			scheduleService.addSchedule(new ScheduleElement(Timestamp.valueOf(LocalDate.now().atTime(12, 0, 0).plusDays(i)), bigCinema, bigCinema.getCinemaHallList().get(2), movieService.getMovie(collect.get(ThreadLocalRandom.current().nextInt(0, collect.size()))), Format.type_2D, 350));
			scheduleService.addSchedule(new ScheduleElement(Timestamp.valueOf(LocalDate.now().atTime(15, 0, 0).plusDays(i)), bigCinema, bigCinema.getCinemaHallList().get(2), movieService.getMovie(collect.get(ThreadLocalRandom.current().nextInt(0, collect.size()))), Format.type_2D, 400));
			scheduleService.addSchedule(new ScheduleElement(Timestamp.valueOf(LocalDate.now().atTime(18, 0, 0).plusDays(i)), bigCinema, bigCinema.getCinemaHallList().get(2), movieService.getMovie(collect.get(ThreadLocalRandom.current().nextInt(0, collect.size()))), Format.type_2D_ATMOS, 500));
			scheduleService.addSchedule(new ScheduleElement(Timestamp.valueOf(LocalDate.now().atTime(21, 0, 0).plusDays(i)), bigCinema, bigCinema.getCinemaHallList().get(2), movieService.getMovie(collect.get(ThreadLocalRandom.current().nextInt(0, collect.size()))), Format.type_2D_ATMOS, 500));
			scheduleService.addSchedule(new ScheduleElement(Timestamp.valueOf(LocalDate.now().atTime(0, 0, 0).plusDays(i)), bigCinema, bigCinema.getCinemaHallList().get(2), movieService.getMovie(collect.get(ThreadLocalRandom.current().nextInt(0, collect.size()))), Format.type_2D_ATMOS, 600));

			scheduleService.addSchedule(new ScheduleElement(Timestamp.valueOf(LocalDate.now().atTime(3, 0, 0).plusDays(i)), bigCinema, bigCinema.getCinemaHallList().get(3), movieService.getMovie(collect.get(ThreadLocalRandom.current().nextInt(0, collect.size()))), Format.type_2D, 250));
			scheduleService.addSchedule(new ScheduleElement(Timestamp.valueOf(LocalDate.now().atTime(6, 0, 0).plusDays(i)), bigCinema, bigCinema.getCinemaHallList().get(3), movieService.getMovie(collect.get(ThreadLocalRandom.current().nextInt(0, collect.size()))), Format.type_2D, 300));
			scheduleService.addSchedule(new ScheduleElement(Timestamp.valueOf(LocalDate.now().atTime(12, 0, 0).plusDays(i)), bigCinema, bigCinema.getCinemaHallList().get(3), movieService.getMovie(collect.get(ThreadLocalRandom.current().nextInt(0, collect.size()))), Format.type_2D, 350));
			scheduleService.addSchedule(new ScheduleElement(Timestamp.valueOf(LocalDate.now().atTime(15, 0, 0).plusDays(i)), bigCinema, bigCinema.getCinemaHallList().get(3), movieService.getMovie(collect.get(ThreadLocalRandom.current().nextInt(0, collect.size()))), Format.type_2D, 400));
			scheduleService.addSchedule(new ScheduleElement(Timestamp.valueOf(LocalDate.now().atTime(18, 0, 0).plusDays(i)), bigCinema, bigCinema.getCinemaHallList().get(3), movieService.getMovie(collect.get(ThreadLocalRandom.current().nextInt(0, collect.size()))), Format.type_2D_ATMOS, 500));
			scheduleService.addSchedule(new ScheduleElement(Timestamp.valueOf(LocalDate.now().atTime(21, 0, 0).plusDays(i)), bigCinema, bigCinema.getCinemaHallList().get(3), movieService.getMovie(collect.get(ThreadLocalRandom.current().nextInt(0, collect.size()))), Format.type_2D_ATMOS, 500));
			scheduleService.addSchedule(new ScheduleElement(Timestamp.valueOf(LocalDate.now().atTime(0, 0, 0).plusDays(i)), bigCinema, bigCinema.getCinemaHallList().get(3), movieService.getMovie(collect.get(ThreadLocalRandom.current().nextInt(0, collect.size()))), Format.type_2D_ATMOS, 600));
			// -- 2st
			scheduleService.addSchedule(new ScheduleElement(Timestamp.valueOf(LocalDate.now().atTime(1, 0, 0).plusDays(i)), mediumCinema, mediumCinema.getCinemaHallList().get(0), movieService.getMovie(collect.get(ThreadLocalRandom.current().nextInt(0, collect.size()))), Format.type_2D_ATMOS, 300));
			scheduleService.addSchedule(new ScheduleElement(Timestamp.valueOf(LocalDate.now().atTime(4, 0, 0).plusDays(i)), mediumCinema, mediumCinema.getCinemaHallList().get(0), movieService.getMovie(collect.get(ThreadLocalRandom.current().nextInt(0, collect.size()))), Format.type_2D_ATMOS, 300));
			scheduleService.addSchedule(new ScheduleElement(Timestamp.valueOf(LocalDate.now().atTime(10, 0, 0).plusDays(i)), mediumCinema, mediumCinema.getCinemaHallList().get(0), movieService.getMovie(collect.get(ThreadLocalRandom.current().nextInt(0, collect.size()))), Format.type_2D, 300));
			scheduleService.addSchedule(new ScheduleElement(Timestamp.valueOf(LocalDate.now().atTime(13, 0, 0).plusDays(i)), mediumCinema, mediumCinema.getCinemaHallList().get(0), movieService.getMovie(collect.get(ThreadLocalRandom.current().nextInt(0, collect.size()))), Format.type_2D, 300));
			scheduleService.addSchedule(new ScheduleElement(Timestamp.valueOf(LocalDate.now().atTime(16, 0, 0).plusDays(i)), mediumCinema, mediumCinema.getCinemaHallList().get(0), movieService.getMovie(collect.get(ThreadLocalRandom.current().nextInt(0, collect.size()))), Format.type_2D, 300));
			scheduleService.addSchedule(new ScheduleElement(Timestamp.valueOf(LocalDate.now().atTime(19, 0, 0).plusDays(i)), mediumCinema, mediumCinema.getCinemaHallList().get(0), movieService.getMovie(collect.get(ThreadLocalRandom.current().nextInt(0, collect.size()))), Format.type_2D_ATMOS, 500));
			scheduleService.addSchedule(new ScheduleElement(Timestamp.valueOf(LocalDate.now().atTime(22, 0, 0).plusDays(i)), mediumCinema, mediumCinema.getCinemaHallList().get(0), movieService.getMovie(collect.get(ThreadLocalRandom.current().nextInt(0, collect.size()))), Format.type_2D_ATMOS, 500));

			scheduleService.addSchedule(new ScheduleElement(Timestamp.valueOf(LocalDate.now().atTime(2, 0, 0).plusDays(i)), mediumCinema, mediumCinema.getCinemaHallList().get(1), movieService.getMovie(collect.get(ThreadLocalRandom.current().nextInt(0, collect.size()))), Format.type_2D_ATMOS, 200));
			scheduleService.addSchedule(new ScheduleElement(Timestamp.valueOf(LocalDate.now().atTime(5, 0, 0).plusDays(i)), mediumCinema, mediumCinema.getCinemaHallList().get(1), movieService.getMovie(collect.get(ThreadLocalRandom.current().nextInt(0, collect.size()))), Format.type_2D_ATMOS, 200));
			scheduleService.addSchedule(new ScheduleElement(Timestamp.valueOf(LocalDate.now().atTime(11, 0, 0).plusDays(i)), mediumCinema, mediumCinema.getCinemaHallList().get(1), movieService.getMovie(collect.get(ThreadLocalRandom.current().nextInt(0, collect.size()))), Format.type_3D, 300));
			scheduleService.addSchedule(new ScheduleElement(Timestamp.valueOf(LocalDate.now().atTime(14, 0, 0).plusDays(i)), mediumCinema, mediumCinema.getCinemaHallList().get(1), movieService.getMovie(collect.get(ThreadLocalRandom.current().nextInt(0, collect.size()))), Format.type_2D_ATMOS, 300));
			scheduleService.addSchedule(new ScheduleElement(Timestamp.valueOf(LocalDate.now().atTime(17, 0, 0).plusDays(i)), mediumCinema, mediumCinema.getCinemaHallList().get(1), movieService.getMovie(collect.get(ThreadLocalRandom.current().nextInt(0, collect.size()))), Format.type_3D, 500));
			scheduleService.addSchedule(new ScheduleElement(Timestamp.valueOf(LocalDate.now().atTime(20, 0, 0).plusDays(i)), mediumCinema, mediumCinema.getCinemaHallList().get(1), movieService.getMovie(collect.get(ThreadLocalRandom.current().nextInt(0, collect.size()))), Format.type_2D_ATMOS, 500));
			scheduleService.addSchedule(new ScheduleElement(Timestamp.valueOf(LocalDate.now().atTime(22, 0, 0).plusDays(i)), mediumCinema, mediumCinema.getCinemaHallList().get(1), movieService.getMovie(collect.get(ThreadLocalRandom.current().nextInt(0, collect.size()))), Format.type_3D, 500));

			scheduleService.addSchedule(new ScheduleElement(Timestamp.valueOf(LocalDate.now().atTime(3, 0, 0).plusDays(i)), mediumCinema, mediumCinema.getCinemaHallList().get(2), movieService.getMovie(collect.get(ThreadLocalRandom.current().nextInt(0, collect.size()))), Format.type_3D_ATMOS, 600));
			scheduleService.addSchedule(new ScheduleElement(Timestamp.valueOf(LocalDate.now().atTime(6, 0, 0).plusDays(i)), mediumCinema, mediumCinema.getCinemaHallList().get(2), movieService.getMovie(collect.get(ThreadLocalRandom.current().nextInt(0, collect.size()))), Format.type_3D_ATMOS, 600));
			scheduleService.addSchedule(new ScheduleElement(Timestamp.valueOf(LocalDate.now().atTime(12, 0, 0).plusDays(i)), mediumCinema, mediumCinema.getCinemaHallList().get(2), movieService.getMovie(collect.get(ThreadLocalRandom.current().nextInt(0, collect.size()))), Format.type_3D_ATMOS, 600));
			scheduleService.addSchedule(new ScheduleElement(Timestamp.valueOf(LocalDate.now().atTime(15, 0, 0).plusDays(i)), mediumCinema, mediumCinema.getCinemaHallList().get(2), movieService.getMovie(collect.get(ThreadLocalRandom.current().nextInt(0, collect.size()))), Format.type_3D_ATMOS, 800));
			scheduleService.addSchedule(new ScheduleElement(Timestamp.valueOf(LocalDate.now().atTime(18, 0, 0).plusDays(i)), mediumCinema, mediumCinema.getCinemaHallList().get(2), movieService.getMovie(collect.get(ThreadLocalRandom.current().nextInt(0, collect.size()))), Format.type_3D_ATMOS, 800));
			scheduleService.addSchedule(new ScheduleElement(Timestamp.valueOf(LocalDate.now().atTime(21, 0, 0).plusDays(i)), mediumCinema, mediumCinema.getCinemaHallList().get(2), movieService.getMovie(collect.get(ThreadLocalRandom.current().nextInt(0, collect.size()))), Format.type_3D_ATMOS, 900));
			scheduleService.addSchedule(new ScheduleElement(Timestamp.valueOf(LocalDate.now().atTime(0, 0, 0).plusDays(i)), mediumCinema, mediumCinema.getCinemaHallList().get(2), movieService.getMovie(collect.get(ThreadLocalRandom.current().nextInt(0, collect.size()))), Format.type_3D_ATMOS, 900));
			// - 3st
			scheduleService.addSchedule(new ScheduleElement(Timestamp.valueOf(LocalDate.now().atTime(1, 0, 0).plusDays(i)), smallCinema, smallCinema.getCinemaHallList().get(0), movieService.getMovie(collect.get(ThreadLocalRandom.current().nextInt(0, collect.size()))), Format.type_2D_ATMOS, 300));
			scheduleService.addSchedule(new ScheduleElement(Timestamp.valueOf(LocalDate.now().atTime(4, 0, 0).plusDays(i)), smallCinema, smallCinema.getCinemaHallList().get(0), movieService.getMovie(collect.get(ThreadLocalRandom.current().nextInt(0, collect.size()))), Format.type_2D_ATMOS, 300));
			scheduleService.addSchedule(new ScheduleElement(Timestamp.valueOf(LocalDate.now().atTime(10, 0, 0).plusDays(i)), smallCinema, smallCinema.getCinemaHallList().get(0), movieService.getMovie(collect.get(ThreadLocalRandom.current().nextInt(0, collect.size()))), Format.type_2D_ATMOS, 400));
			scheduleService.addSchedule(new ScheduleElement(Timestamp.valueOf(LocalDate.now().atTime(13, 0, 0).plusDays(i)), smallCinema, smallCinema.getCinemaHallList().get(0), movieService.getMovie(collect.get(ThreadLocalRandom.current().nextInt(0, collect.size()))), Format.type_2D, 350));
			scheduleService.addSchedule(new ScheduleElement(Timestamp.valueOf(LocalDate.now().atTime(16, 0, 0).plusDays(i)), smallCinema, smallCinema.getCinemaHallList().get(0), movieService.getMovie(collect.get(ThreadLocalRandom.current().nextInt(0, collect.size()))), Format.type_2D, 350));
			scheduleService.addSchedule(new ScheduleElement(Timestamp.valueOf(LocalDate.now().atTime(19, 0, 0).plusDays(i)), smallCinema, smallCinema.getCinemaHallList().get(0), movieService.getMovie(collect.get(ThreadLocalRandom.current().nextInt(0, collect.size()))), Format.type_2D, 400));
			scheduleService.addSchedule(new ScheduleElement(Timestamp.valueOf(LocalDate.now().atTime(22, 0, 0).plusDays(i)), smallCinema, smallCinema.getCinemaHallList().get(0), movieService.getMovie(collect.get(ThreadLocalRandom.current().nextInt(0, collect.size()))), Format.type_2D_ATMOS, 500));

			scheduleService.addSchedule(new ScheduleElement(Timestamp.valueOf(LocalDate.now().atTime(2, 0, 0).plusDays(i)), smallCinema, smallCinema.getCinemaHallList().get(1), movieService.getMovie(collect.get(ThreadLocalRandom.current().nextInt(0, collect.size()))), Format.type_3D, 300));
			scheduleService.addSchedule(new ScheduleElement(Timestamp.valueOf(LocalDate.now().atTime(5, 0, 0).plusDays(i)), smallCinema, smallCinema.getCinemaHallList().get(1), movieService.getMovie(collect.get(ThreadLocalRandom.current().nextInt(0, collect.size()))), Format.type_2D_ATMOS, 300));
			scheduleService.addSchedule(new ScheduleElement(Timestamp.valueOf(LocalDate.now().atTime(11, 0, 0).plusDays(i)), smallCinema, smallCinema.getCinemaHallList().get(1), movieService.getMovie(collect.get(ThreadLocalRandom.current().nextInt(0, collect.size()))), Format.type_3D, 300));
			scheduleService.addSchedule(new ScheduleElement(Timestamp.valueOf(LocalDate.now().atTime(14, 0, 0).plusDays(i)), smallCinema, smallCinema.getCinemaHallList().get(1), movieService.getMovie(collect.get(ThreadLocalRandom.current().nextInt(0, collect.size()))), Format.type_2D_ATMOS, 350));
			scheduleService.addSchedule(new ScheduleElement(Timestamp.valueOf(LocalDate.now().atTime(17, 0, 0).plusDays(i)), smallCinema, smallCinema.getCinemaHallList().get(1), movieService.getMovie(collect.get(ThreadLocalRandom.current().nextInt(0, collect.size()))), Format.type_3D, 400));
			scheduleService.addSchedule(new ScheduleElement(Timestamp.valueOf(LocalDate.now().atTime(20, 0, 0).plusDays(i)), smallCinema, smallCinema.getCinemaHallList().get(1), movieService.getMovie(collect.get(ThreadLocalRandom.current().nextInt(0, collect.size()))), Format.type_2D_ATMOS, 500));
			scheduleService.addSchedule(new ScheduleElement(Timestamp.valueOf(LocalDate.now().atTime(22, 0, 0).plusDays(i)), smallCinema, smallCinema.getCinemaHallList().get(1), movieService.getMovie(collect.get(ThreadLocalRandom.current().nextInt(0, collect.size()))), Format.type_3D, 500));
		}
			// tickets

			// para for example

			// build расписания
		};
	}
}

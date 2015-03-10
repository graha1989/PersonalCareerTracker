INSERT INTO student (name, surname, transcriptNumber) VALUES
	('Jovan', 'Jovanović', '151/08'),
	('Petar', 'Petrović', '214m/11'),
	('Dragan', 'Dragić', '218m/11'),
	('Milan', 'Milić', '100/08'),
	('Zoran', 'Božić', '188/10'),
	('Bogdan', 'Bogdanović', '555/09'),
	('Zoran', 'Božić', '188/10'),
	('Miloš', 'Dukić', '19/10'),
	('Dragana', 'Rodić', '189/10'),
	('Aleksandar', 'Grahovac', '214m/11'),
	('Biljana', 'Lužanin', '33/07'),
	('Nikola', 'Bjelica', '899/11'),
	('Bojan', 'Bojić', '18/10'),
	('Goran', 'Dragić', '222/12'),
	('Zorana', 'Milić', '99/09');
INSERT INTO thesis_type (finalPaperTypeName) VALUES
	('Bachelor teza'),
	('Master teza'),
	('Specijalistička teza'),
	('Doktorska teza');
INSERT INTO professor (userName, password, email, name, surname, fathersName, dateOfBirth, placeOfBirth, countryOfBirth, scientificArea, specialScientificArea) VALUES
	('admin', 'admin123', 'admin@email.com', 'Pera', 'Perić', 'Petar', '1988-04-04 00:00:00', 'Novi Sad', 'Srbija', 'Informatika', 'Informacioni sistemi'),
	('admin1', 'admin321', 'admin1@email.com', 'Mika', 'Mikić', 'Mirko', '1986-04-28 00:00:00', 'Vršac', 'Srbija', 'Hemija', 'Fizička hemija'),
	('user123', 'user1234', 'user123@email.com', 'Srđan', 'Bojić', 'Goran', '1985-04-30 00:00:00', 'Subotica', 'Srbija', 'Informatika', 'Informacioni sistemi'),
	('user9', 'user99999', 'user9@email.com', 'Bojana', 'Bratić', 'Ivan', '1980-04-30 00:00:00', 'Kikinda', 'Srbija', 'Hemija', 'Organska hemija'),
	('user1357', 'UserUser', 'user1357@email.com', 'Gordana', 'Grčić', 'Milan', '1982-11-30 00:00:00', 'Mostar', 'Bosna i Hercegovina', 'Geografija', 'Ruralni razvoj'),
	('admin2', 'admin456', 'admin2@email.com', 'Jova', 'Jović', 'Jovan', '1987-08-18 00:00:00', 'Beograd', 'Srbija', 'Fizika', 'Atomska fizika');
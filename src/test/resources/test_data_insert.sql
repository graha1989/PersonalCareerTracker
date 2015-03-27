INSERT INTO student (name, surname, transcriptNumber) VALUES
	('Jovan', 'Jovanović', '151/08'),
	('Petar', 'Petrović', '214m/11'),
	('Dragan', 'Dragić', '218m/11'),
	('Milan', 'Milić', '100/08'),
	('Zoran', 'Božić', '188/10'),
	('Bogdan', 'Bogdanović', '555/09'),
	('Zoran', 'Božić', '553/10'),
	('Miloš', 'Dukić', '19/10'),
	('Dragana', 'Rodić', '189/10'),
	('Aleksandar', 'Grahovac', '214/09'),
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
INSERT INTO language (name) VALUES
	('Srpski jezik'),
	('Engleski jezik'),
	('Ruski jezik'),
	('Francuski jezik'),
	('Mađarski jezik'),
	('Nemački jezik'),
	('Rumunski jezik'),
	('Slovački jezik'),
	('Grčki jezik'),
	('Bugarski jezik'),
	('Makedonski jezik'),
	('Slovenački jezik'),
	('Hrvatski jezik'),
	('Španski jezik'),
	('Portugalski jezik'),
	('Italijanski jezik');
INSERT INTO professor (userName, password, email, name, surname, fathersName, dateOfBirth, placeOfBirth, countryOfBirth, scientificArea, specialScientificArea) VALUES
	('admin', 'admin123', 'admin@email.com', 'Pera', 'Perić', 'Petar', '1988-04-04 00:00:00', 'Novi Sad', 'Srbija', 'Informatika', 'Informacioni sistemi'),
	('admin1', 'admin321', 'admin1@email.com', 'Mika', 'Mikić', 'Mirko', '1986-04-28 00:00:00', 'Vršac', 'Srbija', 'Hemija', 'Fizička hemija'),
	('user123', 'user1234', 'user123@email.com', 'Srđan', 'Bojić', 'Goran', '1985-04-30 00:00:00', 'Subotica', 'Srbija', 'Informatika', 'Informacioni sistemi'),
	('user9', 'user99999', 'user9@email.com', 'Bojana', 'Bratić', 'Ivan', '1980-04-30 00:00:00', 'Kikinda', 'Srbija', 'Hemija', 'Organska hemija'),
	('user1357', 'UserUser', 'user1357@email.com', 'Gordana', 'Grčić', 'Milan', '1982-11-30 00:00:00', 'Mostar', 'Bosna i Hercegovina', 'Geografija', 'Ruralni razvoj'),
	('admin2', 'admin456', 'admin2@email.com', 'Jova', 'Jović', 'Jovan', '1987-08-18 00:00:00', 'Beograd', 'Srbija', 'Fizika', 'Atomska fizika');
INSERT INTO thesis (title, paperScientificArea, dateOfGraduation, universityName, finalPaperTypeId, studentId, mentorId, commissionMemberId, commissionPresidentId) VALUES
	('Bachelor teza 1', 'Informatika', '2014-04-04 00:00:00', 'Univerzitet Novi Sad', '1', '1', '3', '2', '1'),
	('Master teza 1', 'Informatika', '2012-04-14 00:00:00', 'Univerzitet Niš', '2', '2', '3', '3', '2'),
	('Specijalistička teza 1', 'Informatika', '2010-11-11 00:00:00', 'Univerzitet Beograd', '3', '7', '3', '4', '5'),
	('Doktorska teza 1', 'Informatika', '2010-11-11 00:00:00', 'Univerzitet Beograd', '4', '10', '3', '5', '6');
INSERT INTO language_experience (profesorId, languageId, writing, reading, pronouncing) VALUES
	('3', '1', '1', '1', '1'),
	('3', '3', '1', '0', '0'),
	('3', '4', '0', '1', '1'),
	('3', '7', '1', '0', '1'),
	('3', '15', '0', '0', '1');
INSERT INTO award (awardName, awardedBy, dateOfAward, professorId, awardType, awardField) VALUES
	('Mileva Marić Anštajn', 'Univerzitet Novi Sad', '2006-09-15', '3', 'PRIZE', 'SCIENTIFIC_FIELD'),
	('Mihajlo Pupin', 'Univerzitet Novi Sad', '2008-09-15', '3', 'PRIZE', 'SCIENTIFIC_FIELD'),
	('Dositej Obradović', 'Univerzitet Novi Sad', '2013-09-15', '3', 'PRIZE', 'PEDAGOGICAL_FIELD');	
INSERT INTO project (name, financedBy, projectStartDate, projectEndDate, projectType, projectLeader) VALUES
	('XML Projekat', 'Vlada Republike Srbije', '2006-09-15', '2010-11-19', 'SCIENTIFIC', 'Vladimir Kurbalija; Zoran Budimac'),
	('Etika u informatici', 'Grad Novi Sad', '2012-04-22', '2015-01-01', 'OTHER', 'Mirjana Ivanović'),
	('SQL projekat', 'Grad Beograd', '2010-05-01', '2014-01-01', 'SCIENTIFIC', 'Dejan Mitrović');
INSERT INTO project_experience (projectId, professorId, professorLeader) VALUES
	('1', '3', '0'),
	('2', '3', '0');
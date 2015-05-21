
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
	('Zorana', 'Milić', '99/09')
	ON DUPLICATE KEY UPDATE name=VALUES(name), surname=VALUES(surname), transcriptNumber=VALUES(transcriptNumber);
	
INSERT INTO studies_thesis_type (type) VALUES
	('Bachelor'),
	('Master'),
	('Specijalističke'),
	('Doktorske')
	ON DUPLICATE KEY UPDATE type=VALUES(type);
	
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
	('Italijanski jezik')
	ON DUPLICATE KEY UPDATE name=VALUES(name);
	
INSERT INTO user (id, userName, password, email, name, surname) VALUES
	('1', 'admin', 'admin123', 'admin@email.com', 'Pera', 'Perić'),
	('2', 'admin1', 'admin321', 'admin1@email.com', 'Mika', 'Mikić'),
	('3', 'user123', 'user1234', 'user123@email.com', 'Srđan', 'Bojić'),
	('4', 'user9', 'user99999', 'user9@email.com', 'Bojana', 'Bratić'),
	('5', 'user1357', 'UserUser', 'user1357@email.com', 'Gordana', 'Grčić'),
	('6', 'admin2', 'admin456', 'admin2@email.com', 'Jova', 'Jović')
	ON DUPLICATE KEY UPDATE id=VALUES(id), userName=VALUES(userName), password=VALUES(password), email=VALUES(email), name=VALUES(name), surname=VALUES(surname);

INSERT INTO professor (id, fathersName, dateOfBirth, placeOfBirth, countryOfBirth, scientificArea, specialScientificArea) VALUES
	('1', 'Petar', '1988-04-04 00:00:00', 'Novi Sad', 'Srbija', 'Informatika', 'Informacioni sistemi'),
	('2', 'Mirko', '1986-04-28 00:00:00', 'Vršac', 'Srbija', 'Hemija', 'Fizička hemija'),
	('3', 'Goran', '1985-04-30 00:00:00', 'Subotica', 'Srbija', 'Informatika', 'Informacioni sistemi'),
	('4', 'Ivan', '1980-04-30 00:00:00', 'Kikinda', 'Srbija', 'Hemija', 'Organska hemija'),
	('5', 'Milan', '1982-11-30 00:00:00', 'Mostar', 'Bosna i Hercegovina', 'Geografija', 'Ruralni razvoj'),
	('6', 'Jovan', '1987-08-18 00:00:00', 'Beograd', 'Srbija', 'Fizika', 'Atomska fizika')
	ON DUPLICATE KEY UPDATE fathersName=VALUES(fathersName), dateOfBirth=VALUES(dateOfBirth), placeOfBirth=VALUES(placeOfBirth), countryOfBirth=VALUES(countryOfBirth), scientificArea=VALUES(scientificArea), specialScientificArea=VALUES(specialScientificArea);

INSERT INTO thesis (title, paperScientificArea, dateOfGraduation, universityName, studiesThesisTypeId, studentId, mentorId, commissionMemberId, commissionPresidentId) VALUES
	('Bachelor teza 1', 'Informatika', '2014-04-04 00:00:00', 'Univerzitet Novi Sad', '1', '1', '3', '2', '1'),
	('Master teza 1', 'Informatika', '2012-04-14 00:00:00', 'Univerzitet Niš', '2', '2', '3', '3', '2'),
	('Specijalistička teza 1', 'Informatika', '2010-11-11 00:00:00', 'Univerzitet Beograd', '3', '7', '3', '4', '5'),
	('Doktorska teza 1', 'Informatika', '2010-11-11 00:00:00', 'Univerzitet Beograd', '4', '10', '3', '5', '6')
	ON DUPLICATE KEY UPDATE title=VALUES(title), paperScientificArea=VALUES(paperScientificArea), dateOfGraduation=VALUES(dateOfGraduation), universityName=VALUES(universityName), studiesThesisTypeId=VALUES(studiesThesisTypeId), studentId=VALUES(studentId), mentorId=VALUES(mentorId), commissionMemberId=VALUES(commissionMemberId), commissionPresidentId=VALUES(commissionPresidentId);
	
INSERT INTO language_experience (professorId, languageId, writing, reading, pronouncing) VALUES
	('3', '1', '1', '1', '1'),
	('3', '3', '1', '0', '0'),
	('3', '4', '0', '1', '1'),
	('3', '7', '1', '0', '1'),
	('3', '15', '0', '0', '1')
	ON DUPLICATE KEY UPDATE professorId=VALUES(professorId), languageId=VALUES(languageId), writing=VALUES(writing), reading=VALUES(reading), pronouncing=VALUES(pronouncing);
	
INSERT INTO award (awardName, awardedBy, dateOfAward, professorId, awardType, awardField) VALUES
	('Mileva Marić Anštajn', 'Univerzitet Novi Sad', '2006-09-15', '3', 'PRIZE', 'SCIENTIFIC_FIELD'),
	('Mihajlo Pupin', 'Univerzitet Novi Sad', '2008-09-15', '3', 'PRIZE', 'SCIENTIFIC_FIELD'),
	('Dositej Obradović', 'Univerzitet Novi Sad', '2013-09-15', '3', 'PRIZE', 'PEDAGOGICAL_FIELD')
	ON DUPLICATE KEY UPDATE awardName=VALUES(awardName), awardedBy=VALUES(awardedBy), dateOfAward=VALUES(dateOfAward), professorId=VALUES(professorId), awardType=VALUES(awardType), awardField=VALUES(awardField);
	
INSERT INTO project (name, financedBy, projectType) VALUES
	('XML Projekat', 'Vlada Republike Srbije', 'SCIENTIFIC'),
	('Etika u informatici', 'Grad Novi Sad', 'OTHER'),
	('SQL projekat', 'Grad Beograd', 'SCIENTIFIC')
	ON DUPLICATE KEY UPDATE name=VALUES(name), financedBy=VALUES(financedBy), projectType=VALUES(projectType);
	
INSERT INTO project_leader (professorId, projectId, name, surname) VALUES
	('3', '1', null, null),
	(null, '1', 'Saša', 'Mrkić'),
	(null, '1', 'Goran', 'Nović'),
	(null, '2', 'Milan', 'Dvornik'),
	(null, '3', 'Dragoslav', 'Berić')
	ON DUPLICATE KEY UPDATE professorId=VALUES(professorId), projectId=VALUES(projectId), name=VALUES(name), surname=VALUES(surname);
/*	
INSERT INTO project_experience (projectId, professorId, startDate, endDate) VALUES
	('1', '3', '2006-09-15', '2008-09-15'),
	('2', '3', '2009-09-15', '2011-09-15')
	ON DUPLICATE KEY UPDATE projectId=VALUES(projectId), professorId=VALUES(professorId), startDate=VALUES(startDate), endDate=VALUES(endDate);
*/	
INSERT INTO publication_category (code, description, nsmPoints, ttbtPoints, shPoints) VALUES
	('M11', 'Istaknuta monografija međunarodnog značaja', '15', '15', '15'),
	('M12', 'Monografija međunarodnog značaja', '10', '10', '10'),
	('M13', 'Monografska studija/poglavlje u knjizi kategorije M11 ili rad u tematskom zborniku vodećeg međunarodnog značaja', '6', '6', '6'),
	('M14', 'Monografska studija/poglavlje u knjizi kategorije M12 ili rad u tematskom zborniku međunarodnog značaja', '4', '4', '4'),
	('M15', 'Leksikografska jedinica ili karta u naučnoj publikaciji vodećeg međunarodnog značaja', '3', '3', '3'),
	('M16', 'Leksikografska jedinica ili karta u publikaciji međunarodnog značaja', '2', '2', '2'),
	('M17', 'Uređivanje naučne monografije ili tematskog zbornika vodećeg međunarodnog značaja', '3', '3', '3'),
	('M18', 'Uređivanje naučne monografije, tematskog zbornika, leksikografske ili kartografske publikacije međunarodnog značaja', '2', '2', '2'),
	('M21', 'Rad u vrhunskom međunarodnom časopisu', '8', '8', '8'),
	('M22', 'Rad u istaknutom međunarodnom časopisu', '5', '5', '5'),
	('M23', 'Rad u međunarodnom časopisu', '3', '3', '4'),
	('M24', 'Rad u časopisu međunarodnog značaja verifikovanog posebnom odlukom', '3', '3', '4'),
	('M25', 'Naučna kritika i polemika u istaknutom međunarodnom časopisu', '1.5', '1.5', '1.5'),
	('M26', 'Naučna kritika i polemika u međunarodnom časopisu', '1', '1', '1'),
	('M27', 'Uređivanje istaknutog međunarodnog naučnog časopisa na god. nivou (gost urednik)', '3', '3', '3'),
	('M28', 'Uređivanje međunarodnog naučnog časopisa', '2', '2', '2'),
	('M31', 'Predavanje po pozivu sa međunarodnog skupa štampano u celini (neophodno pozivno pismo)', '3', '3', '3'),
	('M32', 'Predavanje po pozivu sa međunarodnog skupa štampano u izvodu', '1.5', '1.5', '1.5'),
	('M33', 'Saopštenje sa međunarodnog skupa štampano u celini', '1', '1', '1'),
	('M34', 'Saopštenje sa međunarodnog skupa štampano u izvodu', '0.5', '0.5', '0.5'),
	('M35', 'Autorizovana diskusija sa međunarodnog skupa', '0.3', '0.3', '0.3'),
	('M36', 'Uređivanje zbornika saopštenja međunarodnog naučnog skupa', '1', '1', '1')
	ON DUPLICATE KEY UPDATE code=VALUES(code), description=VALUES(description), nsmPoints=VALUES(nsmPoints), ttbtPoints=VALUES(ttbtPoints), shPoints=VALUES(shPoints);
	
INSERT INTO professor_publication (isbn, title, journalTitle, authors, publisher, pageRange, publicationType, quoted, publicationCategoryId, professorId, year) VALUES
	('ISBN:978-1-56619-909-4', 'Uvod u programiranje', 'Časopis informacione tehnologije', 'Mirjana Ivanović; Đura Paunić; Srđan Bojić', 'PMF', '50-90', 'SCIENTIFIC', '10', '5', '3', '2011'),
	('ISBN:32-10-20022-879-1', 'XML tehnologije u primeni', 'Časopis informacione tehnologije', 'Đorđe Herceg; Srđan Bojić', 'Stylos', '1-300', 'SCIENTIFIC', '19', '13', '3', '2013'),
	('ISBN:555-3-67780-010-5', 'Matematička logika', null, 'Gradimir Vojvodić; Srđan Bojić', 'PMF', '1-120', 'TEXTBOOK', '20', null, '3', '2012'),
	('ISBN:222-3-99633-011-4', 'Matematička analiza', null, 'Arpad Takači; Srđan Bojić', 'PMF', '1-120', 'TEXTBOOK', '6', null, '3', '2014')
	ON DUPLICATE KEY UPDATE isbn=VALUES(isbn), title=VALUES(title), journalTitle=VALUES(journalTitle), authors=VALUES(authors), publisher=VALUES(publisher), pageRange=VALUES(pageRange), publicationType=VALUES(publicationType), quoted=VALUES(quoted), publicationCategoryId=VALUES(publicationCategoryId), professorId=VALUES(professorId), year=VALUES(year);
	
INSERT INTO international_publication (isbn, title, journalTitle, authors, publisher, pagesWithQuotes, year, publicationType, publicationCategoryId, professorId) VALUES
	('ISBN:444-1-57200-544-1', 'Fuzzy Information Processing', 'International IT magazine', 'Srđan Bojić', 'PMF', '50-52', '2011', 'SCIENTIFIC', '5', '3'),
	('ISBN:544-3-25698-332-3', 'Fuzzy Database', null, 'Miloš Racković; Srđan Bojić', 'PMF', '40-50', '2009', 'SCIENTIFIC', '10', '3')
	ON DUPLICATE KEY UPDATE isbn=VALUES(isbn), title=VALUES(title), journalTitle=VALUES(journalTitle), authors=VALUES(authors), publisher=VALUES(publisher), pagesWithQuotes=VALUES(pagesWithQuotes), year=VALUES(year), publicationType=VALUES(publicationType), publicationCategoryId=VALUES(publicationCategoryId), professorId=VALUES(professorId);
	
INSERT INTO institution (institutionType, name, university, country, city) VALUES
	('FACULTY', 'Prirodno-matematički fakultet', 'Univerzitet u Novom Sadu', 'Srbija', 'Novi Sad'),
	('UNIVERSITY', 'Univerzitet u Beogradu', null, 'Srbija', 'Beograd'),
	('FACULTY', 'Prirodno-matematički fakultet', 'Univerzitet u Nišu', 'Srbija', 'Niš')
	ON DUPLICATE KEY UPDATE institutionType=VALUES(institutionType), name=VALUES(name), university=VALUES(university), country=VALUES(country), city=VALUES(city);
	
INSERT INTO work_experience (institutionId, professorId, workStartDate, workEndDate, title) VALUES
	('1', '3', '2012-04-14 00:00:00', '2014-08-04 00:00:00', 'asistent pripravnik'),
	('1', '3', '2014-10-07 00:00:00', '2015-02-07 00:00:00', 'asistent')
	ON DUPLICATE KEY UPDATE institutionId=VALUES(institutionId), professorId=VALUES(professorId), workStartDate=VALUES(workStartDate), workEndDate=VALUES(workEndDate), title=VALUES(title);
	
INSERT INTO studies (professorId, institutionId, studiesThesisTypeId, studyProgram, studyArea, studyStartDate, studyEndDate, averageGrade, thesisTitle, acquiredTitle) VALUES
	('3', '1', '1', 'MATHEMATICS_AND_INFORMATICS', 'Informatika', '2012-04-14 00:00:00', '2014-08-04 00:00:00', '9.50', 'Bachelor teza', 'asistent pripravnik'),
	('3', '3', '2', 'MATHEMATICS_AND_INFORMATICS', 'Informatika', '2012-04-14 00:00:00', '2014-08-04 00:00:00', '9.88', 'Master teza 1', 'asistent'),
	('3', '3', '3', 'MATHEMATICS_AND_INFORMATICS', 'Informatika', '2012-04-14 00:00:00', '2014-08-04 00:00:00', '9.88', 'Specialistička teza 1', 'asistent'),
	('3', '3', '4', 'MATHEMATICS_AND_INFORMATICS', 'Informatika', '2012-04-14 00:00:00', '2014-08-04 00:00:00', '9.88', 'Doktorska teza 1', 'asistent')
	ON DUPLICATE KEY UPDATE professorId=VALUES(professorId), institutionId=VALUES(institutionId), studiesThesisTypeId=VALUES(studiesThesisTypeId), studyProgram=VALUES(studyProgram), studyArea=VALUES(studyArea), studyStartDate=VALUES(studyStartDate), studyEndDate=VALUES(studyEndDate), averageGrade=VALUES(averageGrade), thesisTitle=VALUES(thesisTitle), acquiredTitle=VALUES(acquiredTitle);
	
INSERT INTO specialization_abroad (professorId, institutionId, city, country, startDate, endDate, purpose) VALUES
	('3', '1', 'Sofija', 'Bugarska', '2012-04-14 00:00:00', '2014-08-04 00:00:00', 'usavrsavanje')
	ON DUPLICATE KEY UPDATE professorId=VALUES(professorId), institutionId=VALUES(institutionId), startDate=VALUES(startDate), endDate=VALUES(endDate), purpose=VALUES(purpose);

INSERT INTO subject (name, program, numberOfTheoreticalLessons, numberOfPracticalLessons, numberOfTeachingLessons, institutionId, professorId, studiesThesisTypeId, active) VALUES
	('Paralelno programiranje', 'Informacione tehnologije', '3', '3', '0', '1', '3', '2', true),
	('Softversko inženjerstvo', 'Informacione tehnologije', '3', '3', '1', '1', '3', '2', false),
	('Baze podataka', 'Informacione tehnologije', '2', '2', '2', '1', '3', '2', null),
	('Razvoj zasnovan na komponentama', 'Informacione tehnologije', '2', '1', '2', '1', '3', '2', true)
	ON DUPLICATE KEY UPDATE name=VALUES(name), program=VALUES(program), numberOfTheoreticalLessons=VALUES(numberOfTheoreticalLessons), numberOfPracticalLessons=VALUES(numberOfPracticalLessons), numberOfTeachingLessons=VALUES(numberOfTeachingLessons), institutionId=VALUES(institutionId), professorId=VALUES(professorId), studiesThesisTypeId=VALUES(studiesThesisTypeId), active=VALUES(active);
	
INSERT INTO teaching_experience (subjectId, professorId) VALUES
	('1', '3')
	ON DUPLICATE KEY UPDATE subjectId=VALUES(subjectId), professorId=VALUES(professorId);
	
INSERT INTO survey (professorId, subjectId, academicYear, averageGrade, numberOfStudents) VALUES
	('3', '1', '2011/2012', '8.89', '151'),
	('3', '1', '2012/2013', '8.80', '110'),
	('3', '1', '2013/2014', '8.50', '103')
	ON DUPLICATE KEY UPDATE professorId=VALUES(professorId), subjectId=VALUES(subjectId), academicYear=VALUES(academicYear), averageGrade=VALUES(averageGrade), numberOfStudents=VALUES(numberOfStudents);
	
INSERT INTO role (name) VALUES
	('ROLE_ADMIN'),
	('ROLE_USER')
	ON DUPLICATE KEY UPDATE name=VALUES(name);

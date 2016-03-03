SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


--
-- Base de données :  `cami`
--

--
-- Contenu de la table `Agence`
--

INSERT INTO `Agence` (`id`, `version`, `code`, `intitule`, `region`) VALUES
(1, 0, 'BF205SW', 'CAMI Sud-Ouest', 'Sud-Ouest'),
(2, 0, 'BF205SE', 'CAMI Sud-Est', 'Sud-Est');

--
-- Contenu de la table `Categorie`
--

INSERT INTO `Categorie` (`id`, `version`, `intitule`) VALUES
(1, 0, 'Matériels de Bureau'),
(2, 0, 'Matériels Informatique'),
(3, 0, 'Matériels Publicitaire');

--
-- Contenu de la table `Departement`
--

INSERT INTO `Departement` (`id`, `version`, `code`, `intitule`, `agence_id`) VALUES
(1, 0, 'SC01', 'Service Comptable', 1),
(2, 0, 'SC02', 'Service Ressources Humaines', 1),
(3, 0, 'SC03', 'Service Ressources Financières ', 1);

--
-- Contenu de la table `TypeOperation`
--

INSERT INTO `TypeOperation` (`id`, `version`, `intitule`) VALUES
(1, 0, 'Audit'),
(2, 0, 'Entree'),
(3, 0, 'Sortie'),
(4, 0, 'Perte');

--
-- Contenu de la table `users`
--

INSERT INTO `users` (`username`, `email`, `enabled`, `nom`, `password`) VALUES
('sando', 'briceguemkam@gmail.com', 1, 'GUEMKAM SANDO Brice', '$2a$10$1sW7LimIXxeZkSBjAIjBjuFxXK50HzrdmioLvtp.eIrzTvs18IoWe');

--
-- Contenu de la table `user_roles`
--

INSERT INTO `user_roles` (`user_role_id`, `role`, `user_username`) VALUES
(1, 'ROLE_ADMIN', 'sando');
--
-- Contenu de la table `Entree`
--

INSERT INTO `Entree` (`id`, `version`, `dateEntree`, `numero`, `categorie_id`, `user_user_role_id`) VALUES
(1, 2, '2016-01-16', 'ET-29/12/2015-01', 1, 1),
(2, 2, '2016-01-16', 'ET-29/12/2015-02', 1, 1),
(3, 1, '2016-02-13', 'ET-13/02/2016-03', 1, 1),
(4, 1, '2016-02-13', 'ET-13/02/2016-04', 1, 1),
(5, 1, '2016-02-13', 'ET-13/02/2016-05', 2, 1),
(6, 1, '2016-02-13', 'ET-13/02/2016-06', 3, 1);

--
-- Contenu de la table `Fourniture`
--

INSERT INTO `Fourniture` (`id`, `version`, `designation`, `manque`, `perte`, `quantite`, `reference`, `seuil`, `categorie_id`) VALUES
(1, 28, 'DELL INSPIRON P5L', 0, 0, 13, 'LP20501', 3, 2),
(2, 5, 'DELL LATITUDE D112', 0, 0, 14, 'LP20501', 5, 2),
(3, 7, 'AIR STAR ONDULEUR', 0, 0, 17, 'OND20501', 10, 2),
(4, 17, 'ROUTEUR WIFI NETGEAR', 0, 0, 25, 'RT205001', 3, 2),
(5, 1, 'Rame de format A4', 0, 0, 10, 'MB-2016001', 5, 1),
(6, 1, 'Chemises cartonnées ', 0, 0, 100, 'MB-2016002', 50, 1),
(7, 1, 'Banderole 3 mètres  * 1.50', 0, 0, 12, 'MP-2016001', 2, 3);


--
-- Contenu de la table `Lot`
--

INSERT INTO `Lot` (`id`, `version`, `dateEntree`, `etat`, `modifiable`, `numero`, `prixUnitaire`, `prixVenteUnitaire`, `quantite`, `totalMontant`, `entree_id`, `fourniture_id`, `ligneOperation_id`) VALUES
(4, 6, '2015-12-29', 'OCCASION', 1, 'LT-29/12/2015-04', 10000, 13000, 1, 20000, 2, 4, NULL),
(5, 6, '2015-12-29', 'NEUF', 1, 'LT-29/12/2015-05', 17000, 19500, 11, 85000, 2, 4, NULL),
(18, 3, '2015-12-29', 'OCCASION', 1, 'LT-29/12/2015-18', 110000, 120000, 4, 1100000, 1, 1, NULL),
(20, 1, '2016-01-16', 'NEUF', 1, 'LT-16/01/2016-20', 275000, 320000, 5, 1375000, 1, 1, NULL),
(21, 1, '2016-01-16', 'OCCASION', 1, 'LT-16/01/2016-21', 190000, 210000, 4, 760000, 1, 1, NULL),
(23, 1, '2016-01-16', 'OK', 1, 'LT-16/01/2016-23', 100000, 150000, 8, 800000, 2, 4, NULL),
(24, 1, '2016-01-16', 'OCCASION', 1, 'LT-16/01/2016-24', 50000, 75000, 5, 250000, 2, 4, NULL),
(25, 1, '2016-02-13', 'Paper Line', 1, 'LT-13/02/2016-25', 1750, 2000, 3, 5250, 3, 5, NULL),
(26, 1, '2016-02-13', 'Africa High Quality', 1, 'LT-13/02/2016-26', 2000, 2500, 5, 10000, 3, 5, NULL),
(27, 1, '2016-02-13', 'White Sheet', 1, 'LT-13/02/2016-27', 1500, 1700, 2, 3000, 3, 5, NULL),
(28, 1, '2016-02-13', 'Vert', 1, 'LT-13/02/2016-28', 2500, 2750, 25, 62500, 4, 6, NULL),
(29, 1, '2016-02-13', 'Rouge', 1, 'LT-13/02/2016-29', 2500, 2750, 25, 62500, 4, 6, NULL),
(30, 1, '2016-02-13', 'Jaune', 1, 'LT-13/02/2016-30', 2500, 2750, 25, 62500, 4, 6, NULL),
(31, 1, '2016-02-13', 'Bleu', 1, 'LT-13/02/2016-31', 2500, 2750, 25, 62500, 4, 6, NULL),
(32, 1, '2016-02-13', 'Neuf', 1, 'LT-13/02/2016-32', 70000, 80000, 2, 140000, 5, 2, NULL),
(33, 1, '2016-02-13', 'Neuf', 1, 'LT-13/02/2016-33', 80000, 100000, 12, 960000, 5, 2, NULL),
(34, 1, '2016-02-13', 'Neuf', 1, 'LT-13/02/2016-34', 45000, 55000, 17, 765000, 5, 3, NULL),
(35, 1, '2016-02-13', 'En Bâche', 1, 'LT-13/02/2016-35', 47000, 55000, 5, 235000, 6, 7, NULL),
(36, 1, '2016-02-13', 'En Tissu', 1, 'LT-13/02/2016-36', 20000, 27000, 7, 140000, 6, 7, NULL);
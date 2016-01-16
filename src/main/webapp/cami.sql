SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";

INSERT INTO `Agence` (`id`, `version`, `code`, `intitule`, `region`) VALUES
(1, 0, 'CQDSCS', 'Express Union Agence', 'OUEST'),
(2, 0, 'EUCMROUMF01', 'Express Union Angers', 'Paris');

--
-- Contenu de la table `Categorie`
--

INSERT INTO `Categorie` (`id`, `version`, `intitule`) VALUES
(1, 0, 'Onduleurs'),
(2, 0, 'Laptops'),
(3, 0, 'Desktop');

--
-- Contenu de la table `Departement`
--

INSERT INTO `Departement` (`id`, `version`, `code`, `intitule`, `agence_id`) VALUES
(1, 0, 'CQDSCS', 'Service Comptable', 1),
(2, 0, 'CQDSCS01', 'Service Financier', 1);

--
-- Contenu de la table `Fourniture`
--

INSERT INTO `Fourniture` (`id`, `version`, `designation`, `quantite`, `reference`, `seuil`, `categorie_id`) VALUES
(1, NULL, 'Air Star', 25, 'OND205lb5', 12, 1),
(2, NULL, 'DELL INSPIRON 405M', 54, 'LP205018PL', 11, 2),
(3, 1, 'HP OCTACORE', 0, 'DSKTHP02', 5, 3);

--
-- Contenu de la table `TypeOperation`
--

INSERT INTO `TypeOperation` (`id`, `version`, `intitule`) VALUES
(1, NULL, 'Audit'),
(2, NULL, 'Entree'),
(3, NULL, 'Sortie'),
(4, NULL, 'Perte');

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
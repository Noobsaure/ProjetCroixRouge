-- phpMyAdmin SQL Dump
-- version 3.5.6
-- http://www.phpmyadmin.net
--
-- Host: 127.0.0.1
-- Generation Time: Apr 25, 2013 at 09:41 AM
-- Server version: 5.5.29-log
-- PHP Version: 5.4.11

SET SQL_MODE="NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Database: `symfony`
--

-- --------------------------------------------------------

--
-- Table structure for table `Carte`
--

CREATE TABLE IF NOT EXISTS `Carte` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `operation_id` int(11) DEFAULT NULL,
  `nom` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `visibilite` tinyint(1) NOT NULL,
  `image` longblob,
  PRIMARY KEY (`id`),
  KEY `IDX_7B15D0F944AC3583` (`operation_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci AUTO_INCREMENT=3 ;

-- --------------------------------------------------------

--
-- Table structure for table `Categorie`
--

CREATE TABLE IF NOT EXISTS `Categorie` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nom` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Table structure for table `DemandeSuppression`
--

CREATE TABLE IF NOT EXISTS `DemandeSuppression` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `utilisateur_id` int(11) DEFAULT NULL,
  `message_id` int(11) DEFAULT NULL,
  `date_demande` datetime NOT NULL,
  PRIMARY KEY (`id`),
  KEY `IDX_E5D655A7FB88E14F` (`utilisateur_id`),
  KEY `IDX_E5D655A7537A1329` (`message_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Table structure for table `Entite`
--

CREATE TABLE IF NOT EXISTS `Entite` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `statut_id` int(11) DEFAULT NULL,
  `pos_courante_id` int(11) DEFAULT NULL,
  `operateur_id` int(11) DEFAULT NULL,
  `operation_id` int(11) DEFAULT NULL,
  `nom` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `date_depart` datetime NOT NULL,
  `type` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `couleur` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `infos` longtext COLLATE utf8_unicode_ci NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UNIQ_1D851D11F6203804` (`statut_id`),
  KEY `IDX_1D851D117919FC77` (`pos_courante_id`),
  KEY `IDX_1D851D113F192FC` (`operateur_id`),
  KEY `IDX_1D851D1144AC3583` (`operation_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci AUTO_INCREMENT=1 ;

INSERT INTO `Entite` (`id`, `statut_id`, `pos_courante_id`, `operateur_id`, `operation_id`, `nom`, `date_depart`, `type`, `couleur`, `infos`) VALUES
(-1, NULL, NULL, NULL, NULL, 'JApplet', '0000-00-00 00:00:00', '', NULL, '');

-- --------------------------------------------------------

--
-- Table structure for table `EntiteHistorique`
--

CREATE TABLE IF NOT EXISTS `EntiteHistorique` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `equipier_id` int(11) DEFAULT NULL,
  `entite_id` int(11) DEFAULT NULL,
  `date_debut` datetime NOT NULL,
  `date_fin` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `IDX_2AD3EF7CEC685A8B` (`equipier_id`),
  KEY `IDX_2AD3EF7C9BEA957A` (`entite_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Table structure for table `Equipier`
--

CREATE TABLE IF NOT EXISTS `Equipier` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `operation_id` int(11) DEFAULT NULL,
  `entite_id` int(11) DEFAULT NULL,
  `nom` varchar(40) COLLATE utf8_unicode_ci NOT NULL,
  `prenom` varchar(40) COLLATE utf8_unicode_ci NOT NULL,
  `tel` varchar(10) COLLATE utf8_unicode_ci DEFAULT NULL,
  `enActivite` tinyint(1) NOT NULL,
  `motifRupture` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `autres` longtext COLLATE utf8_unicode_ci,
  PRIMARY KEY (`id`),
  KEY `IDX_B359EF044AC3583` (`operation_id`),
  KEY `IDX_B359EF09BEA957A` (`entite_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci AUTO_INCREMENT=3 ;

--
-- Dumping data for table `Equipier`
--

INSERT INTO `Equipier` (`id`, `operation_id`, `entite_id`, `nom`, `prenom`, `tel`, `enActivite`, `motifRupture`, `autres`) VALUES
(1, NULL, NULL, 'Sidhoum', 'Fouad', '0102030405', 1, NULL, 'x'),
(2, NULL, NULL, 'Leroy', 'Dorian', '0102030406', 1, NULL, 'x');

-- --------------------------------------------------------

--
-- Table structure for table `Localisation`
--

CREATE TABLE IF NOT EXISTS `Localisation` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `operation_id` int(11) DEFAULT NULL,
  `carte_id` int(11) DEFAULT NULL,
  `nom` varchar(40) COLLATE utf8_unicode_ci NOT NULL,
  `desc` longtext COLLATE utf8_unicode_ci NOT NULL,
  `x` int(11) DEFAULT NULL,
  `y` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `IDX_A7E2157744AC3583` (`operation_id`),
  KEY `IDX_A7E21577C9C7CEB6` (`carte_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Table structure for table `LocalisationHistorique`
--

CREATE TABLE IF NOT EXISTS `LocalisationHistorique` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `depuis_id` int(11) DEFAULT NULL,
  `vers_id` int(11) DEFAULT NULL,
  `entite_id` int(11) DEFAULT NULL,
  `date_debut` datetime DEFAULT NULL,
  `date_fin` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `IDX_13D9801FBCA220EC` (`depuis_id`),
  KEY `IDX_13D9801F29750479` (`vers_id`),
  KEY `IDX_13D9801F9BEA957A` (`entite_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Table structure for table `Message`
--

CREATE TABLE IF NOT EXISTS `Message` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `emetteur_entite_id` int(11) DEFAULT NULL,
  `destinataire_entite_id` int(11) DEFAULT NULL,
  `emetteur_utilisateur_id` int(11) DEFAULT NULL,
  `destinataire_utilisateur_id` int(11) DEFAULT NULL,
  `operateur_id` int(11) DEFAULT NULL,
  `categorie_id` int(11) DEFAULT NULL,
  `operation_id` int(11) DEFAULT NULL,
  `parent_id` int(11) DEFAULT NULL,
  `deplacement_id` int(11) DEFAULT NULL,
  `date_heure` datetime NOT NULL,
  `contenu` longtext COLLATE utf8_unicode_ci NOT NULL,
  `erreur` tinyint(1) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `IDX_790009E350D696C` (`emetteur_entite_id`),
  KEY `IDX_790009E3DB24DDDB` (`destinataire_entite_id`),
  KEY `IDX_790009E39BF74BC3` (`emetteur_utilisateur_id`),
  KEY `IDX_790009E38327D3F4` (`destinataire_utilisateur_id`),
  KEY `IDX_790009E33F192FC` (`operateur_id`),
  KEY `IDX_790009E3BCF5E72D` (`categorie_id`),
  KEY `IDX_790009E344AC3583` (`operation_id`),
  KEY `IDX_790009E3727ACA70` (`parent_id`),
  KEY `IDX_790009E3355B84A` (`deplacement_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Table structure for table `Operation`
--

CREATE TABLE IF NOT EXISTS `Operation` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nom` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `etat` tinyint(1) NOT NULL,
  `entreeUtilisateurs` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `nature` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `lieu` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `date_debut` datetime NOT NULL,
  `date_fin` datetime DEFAULT NULL,
  `date_debut_prevue` datetime NOT NULL,
  `date_fin_prevue` datetime NOT NULL,
  `commentaire` longtext COLLATE utf8_unicode_ci,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci AUTO_INCREMENT=2 ;

--
-- Dumping data for table `Operation`
--

INSERT INTO `Operation` (`id`, `nom`, `etat`, `entreeUtilisateurs`, `nature`, `lieu`, `date_debut`, `date_fin`, `date_debut_prevue`, `date_fin_prevue`, `commentaire`) VALUES
(1, 'Op1', 1, 'Elouan Rolland', 'x', 'x', '2013-04-25 11:39:59', '0000-00-00 00:00:00', '2013-04-25 11:39:00', '2013-04-30 11:39:00', 'x');

-- --------------------------------------------------------

--
-- Table structure for table `operations_utilisateurs`
--

CREATE TABLE IF NOT EXISTS `operations_utilisateurs` (
  `operation_id` int(11) NOT NULL,
  `utilisateur_id` int(11) NOT NULL,
  PRIMARY KEY (`operation_id`,`utilisateur_id`),
  KEY `IDX_FDEE6DA044AC3583` (`operation_id`),
  KEY `IDX_FDEE6DA0FB88E14F` (`utilisateur_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Dumping data for table `operations_utilisateurs`
--

INSERT INTO `operations_utilisateurs` (`operation_id`, `utilisateur_id`) VALUES
(1, 1);

-- --------------------------------------------------------

--
-- Table structure for table `Statut`
--

CREATE TABLE IF NOT EXISTS `Statut` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `entite_id` int(11) DEFAULT NULL,
  `dispo` tinyint(1) NOT NULL,
  `date_debut` datetime NOT NULL,
  `infos` longtext COLLATE utf8_unicode_ci NOT NULL,
  PRIMARY KEY (`id`),
  KEY `IDX_E2C8F5899BEA957A` (`entite_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Table structure for table `Utilisateur`
--

CREATE TABLE IF NOT EXISTS `Utilisateur` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `operation_id` int(11) DEFAULT NULL,
  `username` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `nom` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `prenom` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `tel` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `couleur` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `password` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `salt` varchar(32) COLLATE utf8_unicode_ci NOT NULL,
  `roles` longtext COLLATE utf8_unicode_ci NOT NULL COMMENT '(DC2Type:array)',
  `enActivite` tinyint(1) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UNIQ_9B80EC64F85E0677` (`username`),
  KEY `IDX_9B80EC6444AC3583` (`operation_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci AUTO_INCREMENT=3 ;

--
-- Dumping data for table `Utilisateur`
--

INSERT INTO `Utilisateur` (`id`, `operation_id`, `username`, `nom`, `prenom`, `tel`, `couleur`, `password`, `salt`, `roles`, `enActivite`) VALUES
(-1, NULL, 'JApplet', '', '', NULL, NULL, '', '', '', 1),
(1, NULL, 'admin', 'Rolland', 'Elouan', '0102030405', NULL, 'P9j/pcXK17USqAIpVKP3suGSLf+jzEqF55oOYmqtb76XT1Ng8CrXFauip4n7154Ktnru64eYIH1+/P0BMBYQ9Q==', '9da3f27b18a9dfb8accaaf813c455aa8', 'a:1:{i:0;s:10:"ROLE_ADMIN";}', 1),
(2, NULL, 'bobby', 'Hallier', 'Steven', '0102030405', NULL, 'P9j/pcXK17USqAIpVKP3suGSLf+jzEqF55oOYmqtb76XT1Ng8CrXFauip4n7154Ktnru64eYIH1+/P0BMBYQ9Q==', '9da3f27b18a9dfb8accaaf813c455aa8', 'a:1:{i:0;s:9:"ROLE_USER";}', 1);

-- --------------------------------------------------------

--
-- Table structure for table `Victime`
--

CREATE TABLE IF NOT EXISTS `Victime` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `categorie_id` int(11) DEFAULT NULL,
  `operation_id` int(11) DEFAULT NULL,
  `surnom` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `nom` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `prenom` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `date_naissance` datetime DEFAULT NULL,
  `adresse` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `statut` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `motif_sortie` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `date_entree` datetime DEFAULT NULL,
  `date_sortie` datetime DEFAULT NULL,
  `petit_soin` tinyint(1) DEFAULT NULL,
  `malaise` tinyint(1) DEFAULT NULL,
  `traumatisme` tinyint(1) DEFAULT NULL,
  `inconscient` tinyint(1) DEFAULT NULL,
  `arret_cardiaque` tinyint(1) DEFAULT NULL,
  `atteinte_details` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `soin` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UNIQ_62D017A56C6E55B5` (`nom`),
  UNIQUE KEY `UNIQ_62D017A5A625945B` (`prenom`),
  UNIQUE KEY `UNIQ_62D017A55F5F55C3` (`surnom`),
  KEY `IDX_62D017A5BCF5E72D` (`categorie_id`),
  KEY `IDX_62D017A544AC3583` (`operation_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Table structure for table `VictimeCat`
--

CREATE TABLE IF NOT EXISTS `VictimeCat` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nom` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Table structure for table `victimes_messages`
--

CREATE TABLE IF NOT EXISTS `victimes_messages` (
  `message_id` int(11) NOT NULL,
  `victime_id` int(11) NOT NULL,
  PRIMARY KEY (`message_id`,`victime_id`),
  KEY `IDX_5E78EEB2537A1329` (`message_id`),
  KEY `IDX_5E78EEB275FF0F4B` (`victime_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `Carte`
--
ALTER TABLE `Carte`
  ADD CONSTRAINT `FK_7B15D0F944AC3583` FOREIGN KEY (`operation_id`) REFERENCES `Operation` (`id`);

--
-- Constraints for table `DemandeSuppression`
--
ALTER TABLE `DemandeSuppression`
  ADD CONSTRAINT `FK_E5D655A7537A1329` FOREIGN KEY (`message_id`) REFERENCES `Message` (`id`),
  ADD CONSTRAINT `FK_E5D655A7FB88E14F` FOREIGN KEY (`utilisateur_id`) REFERENCES `Utilisateur` (`id`);

--
-- Constraints for table `Entite`
--
ALTER TABLE `Entite`
  ADD CONSTRAINT `FK_1D851D1144AC3583` FOREIGN KEY (`operation_id`) REFERENCES `Operation` (`id`),
  ADD CONSTRAINT `FK_1D851D113F192FC` FOREIGN KEY (`operateur_id`) REFERENCES `Utilisateur` (`id`),
  ADD CONSTRAINT `FK_1D851D117919FC77` FOREIGN KEY (`pos_courante_id`) REFERENCES `Localisation` (`id`),
  ADD CONSTRAINT `FK_1D851D11F6203804` FOREIGN KEY (`statut_id`) REFERENCES `Statut` (`id`);

--
-- Constraints for table `EntiteHistorique`
--
ALTER TABLE `EntiteHistorique`
  ADD CONSTRAINT `FK_2AD3EF7C9BEA957A` FOREIGN KEY (`entite_id`) REFERENCES `Entite` (`id`),
  ADD CONSTRAINT `FK_2AD3EF7CEC685A8B` FOREIGN KEY (`equipier_id`) REFERENCES `Equipier` (`id`);

--
-- Constraints for table `Equipier`
--
ALTER TABLE `Equipier`
  ADD CONSTRAINT `FK_B359EF09BEA957A` FOREIGN KEY (`entite_id`) REFERENCES `Entite` (`id`),
  ADD CONSTRAINT `FK_B359EF044AC3583` FOREIGN KEY (`operation_id`) REFERENCES `Operation` (`id`);

--
-- Constraints for table `Localisation`
--
ALTER TABLE `Localisation`
  ADD CONSTRAINT `FK_A7E21577C9C7CEB6` FOREIGN KEY (`carte_id`) REFERENCES `Carte` (`id`),
  ADD CONSTRAINT `FK_A7E2157744AC3583` FOREIGN KEY (`operation_id`) REFERENCES `Operation` (`id`);

--
-- Constraints for table `LocalisationHistorique`
--
ALTER TABLE `LocalisationHistorique`
  ADD CONSTRAINT `FK_13D9801F9BEA957A` FOREIGN KEY (`entite_id`) REFERENCES `Entite` (`id`),
  ADD CONSTRAINT `FK_13D9801F29750479` FOREIGN KEY (`vers_id`) REFERENCES `Localisation` (`id`),
  ADD CONSTRAINT `FK_13D9801FBCA220EC` FOREIGN KEY (`depuis_id`) REFERENCES `Localisation` (`id`);

--
-- Constraints for table `Message`
--
ALTER TABLE `Message`
  ADD CONSTRAINT `FK_790009E3355B84A` FOREIGN KEY (`deplacement_id`) REFERENCES `LocalisationHistorique` (`id`),
  ADD CONSTRAINT `FK_790009E33F192FC` FOREIGN KEY (`operateur_id`) REFERENCES `Utilisateur` (`id`),
  ADD CONSTRAINT `FK_790009E344AC3583` FOREIGN KEY (`operation_id`) REFERENCES `Operation` (`id`),
  ADD CONSTRAINT `FK_790009E350D696C` FOREIGN KEY (`emetteur_entite_id`) REFERENCES `Entite` (`id`),
  ADD CONSTRAINT `FK_790009E3727ACA70` FOREIGN KEY (`parent_id`) REFERENCES `Message` (`id`),
  ADD CONSTRAINT `FK_790009E38327D3F4` FOREIGN KEY (`destinataire_utilisateur_id`) REFERENCES `Utilisateur` (`id`),
  ADD CONSTRAINT `FK_790009E39BF74BC3` FOREIGN KEY (`emetteur_utilisateur_id`) REFERENCES `Utilisateur` (`id`),
  ADD CONSTRAINT `FK_790009E3BCF5E72D` FOREIGN KEY (`categorie_id`) REFERENCES `Categorie` (`id`),
  ADD CONSTRAINT `FK_790009E3DB24DDDB` FOREIGN KEY (`destinataire_entite_id`) REFERENCES `Entite` (`id`);

--
-- Constraints for table `operations_utilisateurs`
--
ALTER TABLE `operations_utilisateurs`
  ADD CONSTRAINT `FK_FDEE6DA0FB88E14F` FOREIGN KEY (`utilisateur_id`) REFERENCES `Utilisateur` (`id`) ON DELETE CASCADE,
  ADD CONSTRAINT `FK_FDEE6DA044AC3583` FOREIGN KEY (`operation_id`) REFERENCES `Operation` (`id`) ON DELETE CASCADE;

--
-- Constraints for table `Statut`
--
ALTER TABLE `Statut`
  ADD CONSTRAINT `FK_E2C8F5899BEA957A` FOREIGN KEY (`entite_id`) REFERENCES `Entite` (`id`);

--
-- Constraints for table `Utilisateur`
--
ALTER TABLE `Utilisateur`
  ADD CONSTRAINT `FK_9B80EC6444AC3583` FOREIGN KEY (`operation_id`) REFERENCES `Operation` (`id`);

--
-- Constraints for table `Victime`
--
ALTER TABLE `Victime`
  ADD CONSTRAINT `FK_62D017A544AC3583` FOREIGN KEY (`operation_id`) REFERENCES `Operation` (`id`),
  ADD CONSTRAINT `FK_62D017A5BCF5E72D` FOREIGN KEY (`categorie_id`) REFERENCES `VictimeCat` (`id`);

--
-- Constraints for table `victimes_messages`
--
ALTER TABLE `victimes_messages`
  ADD CONSTRAINT `FK_5E78EEB275FF0F4B` FOREIGN KEY (`victime_id`) REFERENCES `Victime` (`id`),
  ADD CONSTRAINT `FK_5E78EEB2537A1329` FOREIGN KEY (`message_id`) REFERENCES `Message` (`id`);

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
